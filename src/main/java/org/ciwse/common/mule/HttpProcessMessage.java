/**
 * Anypoint Connector
 *
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 *
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 */
package org.ciwse.common.mule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

/**
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class HttpProcessMessage extends BaseHttpMessage {

	private String processName;
	private String status;
	private String runtime;
	
	/* (non-Javadoc)
	 * @see org.ciwse.common.mule.BaseHttpMessage#getJSONMessage()
	 */
	@Override
	public String getJSONMessage(HttpProcessMessage msg) {
		HttpProcessMessage tmp = new HttpProcessMessage();
		tmp.setProcessName(msg.processName);
		tmp.setStatus(msg.status);
		tmp.setRuntime(msg.runtime);
		
		ObjectMapper mapper = new ObjectMapper();
		String message = null;
		try {
			String jsonStr = mapper.writeValueAsString(tmp);
			JSONObject json = new JSONObject(jsonStr);
			message = json.toString(4); 
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return message;
	}

	/* (non-Javadoc)
	 * @see org.ciwise.common.BaseObject#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ciwise.common.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.ciwise.common.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the runtime
	 */
	public String getRuntime() {
		return runtime;
	}

	/**
	 * @param runtime the runtime to set
	 */
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	
	public static void main(String[] args) {
		HttpProcessMessage message = new HttpProcessMessage();
		message.setProcessName("My Test Process");
		message.setStatus("SUCCESS!");
		message.setRuntime("2:00.34");
		
		System.out.println(message.getJSONMessage(message));
	}
}
