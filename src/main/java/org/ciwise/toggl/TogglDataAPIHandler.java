/**
 * Anypoint Connector
 *
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 *
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 */

package org.ciwise.toggl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class TogglDataAPIHandler extends BaseObject {

	/**
	 * A unique serial identifier.
	 */
	private static final long serialVersionUID = -7879426364851304062L;

	private String apiToken;
	private String userName;

	private static final String AUTH_URL = "https://www.toggl.com/api/v8/me";
	private static final String GET_DETAIL_REPORT_URL = "https://toggl.com/reports/api/v2/details";
	private static final String WORKSPACES_URL = "https://www.toggl.com/api/v8/workspaces";

	/**
	 * No public default constructor.
	 */
	private TogglDataAPIHandler() {
	}

	/**
	 * singleton instance.
	 */
	private static TogglDataAPIHandler instance;

	/**
	 * static get instance method
	 * 
	 * @return the instance
	 */
	public static TogglDataAPIHandler getInstance() {
		if (instance == null) {
			instance = new TogglDataAPIHandler();
		}
		return instance;
	}

	/**
	 * This method should be called first to authenticate and set the api-token
	 * and username.
	 * 
	 * @param secret
	 *            the user+colon+password combo
	 * @return true or false status
	 */
	public boolean authenticate(final String secret) {
		boolean retVal = false;
		StringBuilder result = new StringBuilder();
		URL url;
		try {
			url = new URL(AUTH_URL);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String userpass = secret;
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(userpass.getBytes());

			conn.setRequestProperty("Authorization", basicAuth);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			getInstance().setApiToken(
					getInstance().getTokenFromJSON(result.toString()));
			getInstance().setUserName(
					getInstance().getUserProperFromJSON(result.toString()));

			retVal = true;
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	/**
	 * This method returns a JSON array (string) of detail report time data for
	 * the user and project id given.
	 * 
	 * @param projectId
	 *            a string representation of the project id integer
	 * @param user
	 *            the Toggl user e.g. joe@yahoo.com
	 * @return a string in the form of a JSON array of time data
	 */
	public String getDetailReportDataForProjectNoTags(String projectId, String user) { 
		String json = null;
		StringBuilder result = new StringBuilder();
		URL url;
		String loaded = GET_DETAIL_REPORT_URL + "?workspace_id="
				+ getWorkspaceId(getWorkspaceName())
				+ "&tag_ids=0&project_ids=" + projectId + "&user_agent=" + user;

		try {
			url = new URL(loaded);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken()
					+ ":api_token";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			json = result.toString();

		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getDataArrayFromJSON(json);
	}

	/**
	 * This method adds a <i>processed</i> tag to each untagged record for the
	 * project id given.
	 * 
	 * @param user the Toggl username e.g. joe@yahoo.com
	 * @param userpass the user+colon+password
	 * @param projectId the string representation of the integer project id
	 * @return true or false status
	 */
	public boolean tagProcessedRecords(String user, String projectId) {

		boolean retVal = false;

		String jsonData = getInstance().getDetailReportDataForProjectNoTags(
				projectId, user);
		String ids = getInstance().delimitedTimeEntryIds(jsonData);
		System.out.println("Time entry ids: " + ids);

		// build and send PUT request
		Random random = new Random();
		URL url;
		try {
			url = new URL("https://www.toggl.com/api/v8/time_entries/" + ids);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken()
					+ ":api_token";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);

			OutputStreamWriter osw = new OutputStreamWriter(
					conn.getOutputStream());
			osw.write(String
					.format("{\"time_entry\":{\"tags\":[\"processed\"], \"tag_action\": \"add\"}}",
							random.nextInt(30), random.nextInt(20)));
			osw.flush();
			osw.close();

			if (conn.getResponseCode() == 200) {
				retVal = true;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	/**
	 * This method provides the workspace id for the workspace name given.
	 * 
	 * @param workspaceName the Toggl workspace name
	 * @return a string representation of the integer workspace id
	 */
	public String getWorkspaceId(final String workspaceName) {
		String workspaceId = null;
		JSONArray jsonArray = new JSONArray(getWorkspacesByAuthenticatedUser());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String name = object.getString("name");
			if (name.equals(workspaceName)) {
				Integer id = (Integer) object.get("id");
				workspaceId = id.toString();
			}
		}
		return workspaceId;
	}

	/**
	 * This method provides the project id for the project name given.
	 * 
	 * @param projectName the Toggl project name
	 * @return a string representation of the integer project id
	 */
	public String getProjectId(final String projectName) {
		String projectId = null;
		JSONArray jsonArray = new JSONArray(getProjectsByWorkspaceId());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			String name = object.getString("name");
			if (name.equals(projectName)) {
				Integer id = (Integer) object.get("id");
				projectId = id.toString();
			}
		}
		return projectId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the apiToken
	 */
	public String getApiToken() {
		return apiToken;
	}

	/**
	 * @param apiToken
	 *            the apiToken to set
	 */
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String getWorkspacesByAuthenticatedUser() {
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		URL url;
		String loaded = WORKSPACES_URL;

		try {
			url = new URL(loaded);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken()
					+ ":api_token";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			jsonStr = result.toString();

		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	private String getWorkspaceName() {
		return this.userName + "'s workspace";
	}

	private String getProjectsByWorkspaceId() {
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		URL url;
		String workspaceName = getWorkspaceName();
		String loaded = WORKSPACES_URL + "/"
				+ getWorkspaceId(getWorkspaceName()) + "/projects";

		try {
			url = new URL(loaded);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken()
					+ ":api_token";
			String basicAuth = "Basic "
					+ javax.xml.bind.DatatypeConverter
							.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			jsonStr = result.toString();

		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;

	}

	private String getTokenFromJSON(final String json) {

		JSONObject jsonObject = new JSONObject(json);
		JSONObject newJSON = jsonObject.getJSONObject("data");

		String token = newJSON.getString("api_token");
		return token;
	}

	private String getUserProperFromJSON(final String json) {

		JSONObject jsonObject = new JSONObject(json);
		JSONObject newJSON = jsonObject.getJSONObject("data");

		String username = newJSON.getString("fullname");
		return username;

	}

	private String getDataArrayFromJSON(final String json) {

		JSONObject jsonObject = new JSONObject(json);
		JSONArray newJSONArray = jsonObject.getJSONArray("data");

		return newJSONArray.toString();
	}

	private String delimitedTimeEntryIds(final String json) {
		String retString = "";
		JSONArray jsonArray = new JSONArray(json);
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);
			Integer id = (Integer) object.get("id");
			String sId = id.toString();
			if (i == 0) {
				retString = retString + sId;
			} else {
				retString = retString + "," + sId;
			}
		}
		return retString;
	}

}
