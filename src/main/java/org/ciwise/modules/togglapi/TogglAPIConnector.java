/**
 * Anypoint Connector
 *
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 *
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 */

package org.ciwise.modules.togglapi;

import java.util.StringTokenizer;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.ciwise.modules.togglapi.config.ConnectorConfig;
import org.ciwise.toggl.TogglDataAPIHandler;
import org.ciwse.common.mule.HttpProcessMessage;
import org.ciwse.common.mule.ProcessTimer;

/**
 * 
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
@Connector(name="toggl-api", friendlyName="TogglAPI")
public class TogglAPIConnector {

    @Config
    ConnectorConfig config;

    /**
     * This method returns a JSON array of timekeeping data that has not been written
     * to the database or tagged as processed.
     *  
     * @param userPass a user, colon, and password
     * @param workspaceId a string integer key for workspace identification
     * @param projectId a string integer key for project identification
     * @return JSON Array of time data
     */
    @Processor(friendlyName="Unprocessed Detail Report Data by Project")
    public String getUnprocessedProjectTimeData(final String userPass, final String projectName) {
    	ProcessTimer.getInstance().startTimer();
    	String json = null;
    	
    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
    		String projectId = TogglDataAPIHandler.getInstance().getProjectId(projectName);
    		json = TogglDataAPIHandler.getInstance().getDetailReportDataForProjectNoTags(projectId, getUser(userPass)); // 17654629
    	}
    	
    	return json;
    }
    
    /**
     * This method tags all timekeeping records on Toggl for a project that are not tagged.
     * 
     * @param userPass a user, colon, and password
     * @param workspaceId a string integer key for workspace identification
     * @param projectId a string integer key for project identification
     * @return JSON Array of time data
     */
    @Processor(friendlyName="Tag Unprocessed Time Records by Project")
    public String addProcessTags(final String userPass, final String projectName) {

    	HttpProcessMessage msgObj = new HttpProcessMessage();
    	msgObj.setProcessName("Tag Unprocessed Time Records by Project");
    	
    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
        	String projectId = TogglDataAPIHandler.getInstance().getProjectId(projectName);

    		if (TogglDataAPIHandler.getInstance().tagProcessedRecords(getUser(userPass), projectId)) {
        		msgObj.setStatus(config.getSuccessProcessTag());
        	} else {
        		msgObj.setStatus(config.getErrorProcessTag());
        	}
    	} else {
    		msgObj.setStatus(config.getErrorAuthenticate());
    	}

    	ProcessTimer.getInstance().stopTimer();
    	
    	msgObj.setRuntime(ProcessTimer.getInstance().getMillisecondTimeDuration());
    	return msgObj.getJSONMessage(msgObj);
    }
    
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }
    
    private String getUser(String userpass) {
    	StringTokenizer toker = new StringTokenizer(userpass, ":");
    	return toker.nextToken();
    }
}