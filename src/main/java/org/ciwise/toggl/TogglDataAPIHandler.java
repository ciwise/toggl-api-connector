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
	
	private static final String AUTH_URL = "https://www.toggl.com/api/v8/me";
	private static final String GET_DETAIL_REPORT_URL = "https://toggl.com/reports/api/v2/details"; 
	private static final String WORKSPACES_URL = "https://www.toggl.com/api/v8/workspaces";
	/**
	 * No public default constructor.
	 */
	private TogglDataAPIHandler() {}
	
	/**
	 * singleton instance.
	 */
	private static TogglDataAPIHandler instance;
	
	/**
	 * static get instance method
	 * @return the instance
	 */
	public static TogglDataAPIHandler getInstance() {
		if (instance == null) {
			instance = new TogglDataAPIHandler();
		}
		return instance;
	}

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
		    String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

		    conn.setRequestProperty("Authorization", basicAuth);
		      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      String line;
		      while ((line = rd.readLine()) != null) {
		         result.append(line);
		      }
		      rd.close();
		      getInstance().setApiToken(getInstance().getTokenFromJSON(result.toString()));
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

	public String getDetailReportDataForProjectNoTags(String workspaceId, String projectId, String user) { // 17654629
		String json = null;
		StringBuilder result = new StringBuilder();
	      URL url;
	      String loaded = GET_DETAIL_REPORT_URL + "?workspace_id=" + workspaceId + "&tag_ids=0&project_ids=" + projectId  + "&user_agent=" + user;
	      
		try {
			url = new URL(loaded);
			HttpURLConnection conn;
				conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken() + ":api_token";
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);
		      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
	
	public boolean tagProcessedRecords(String user, String userpass, String workspaceId, String projectId) {

		boolean retVal = false;
		
		if (getInstance().authenticate(userpass)) {
			String jsonData = getInstance().getDetailReportDataForProjectNoTags(workspaceId, projectId, user);
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
				String authtoken = TogglDataAPIHandler.getInstance().getApiToken() + ":api_token";
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authtoken.getBytes());
				conn.setRequestProperty("Authorization", basicAuth);
				
				OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
				osw.write(String.format("{\"time_entry\":{\"tags\":[\"processed\"], \"tag_action\": \"add\"}}", random.nextInt(30), random.nextInt(20)));
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
		}

		return retVal;
	}
	
	public String getProjectId(final String projectName, final String workspaceId) {
		String projectId = null;
			JSONArray jsonArray = new JSONArray(getProjectsByWorkspaceId(workspaceId));
			for(int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject object = jsonArray.getJSONObject(i);
				String name = object.getString("name");
				if (name.equals(projectName)) {
					Integer id = (Integer) object.get("id");
					projectId = id.toString();
				}
			}
		return projectId;
	}
	
	/**
	 * This method returns a JSON array of projects using the Toggl workspaceId
	 * @param workspaceId the string version of the workspaceId integer
	 * @return JSON array of projects
	 */
	private String getProjectsByWorkspaceId(String workspaceId) {
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
	      URL url;
	      String loaded = WORKSPACES_URL + "/" + workspaceId + "/projects";
	      
		try {
			url = new URL(loaded);
			HttpURLConnection conn;
				conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			String authtoken = TogglDataAPIHandler.getInstance().getApiToken() + ":api_token";
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authtoken.getBytes());
			conn.setRequestProperty("Authorization", basicAuth);
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
	 * @param apiToken the apiToken to set
	 */
	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	private String getTokenFromJSON(final String json) {

		JSONObject jsonObject = new JSONObject(json);
        JSONObject newJSON = jsonObject.getJSONObject("data");

	    String token = newJSON.getString("api_token");
	    return token;
	}
	
	private String getDataArrayFromJSON(final String json) {

		JSONObject jsonObject = new JSONObject(json);
        JSONArray newJSONArray = jsonObject.getJSONArray("data");
        
		return newJSONArray.toString();
	}

	private String delimitedTimeEntryIds(final String json) {
		String retString = "";
		JSONArray jsonArray = new JSONArray(json);
		for(int i = 0; i < jsonArray.length(); i++)
		{
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
