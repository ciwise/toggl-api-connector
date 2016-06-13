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

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.ciwise.modules.togglapi.config.ConnectorConfig;
import org.ciwise.toggl.TogglDataAPIHandler;

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
     * @param user the toggl user
     * @param userPass a user, colon, and password
     * @param workspaceId a string integer key for workspace identification
     * @param projectId a string integer key for project identification
     * @return JSON Array of time data
     */
    @Processor(friendlyName="Unprocessed Detail Report Data by Project")
    public String getUnprocessedProjectTimeData(final String user, final String userPass, final String workspaceId, final String projectId) {
    	String json = null;
    	
    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
        	json = TogglDataAPIHandler.getInstance().getDetailReportDataForProjectNoTags(workspaceId, projectId, user); // 17654629
    	}
    	
    	return json;
    }
    
    /**
     * This method tags all timekeeping records on Toggl for a project that are not tagged.
     * 
     * @param user the toggl user
     * @param userPass a user, colon, and password
     * @param workspaceId a string integer key for workspace identification
     * @param projectId a string integer key for project identification
     * @return JSON Array of time data
     */
    @Processor(friendlyName="Tag Unprocessed Time Records by Project")
    public String addProcessTags(final String user, final String userPass, final String workspaceId, final String projectId) {
    	String msg = null;

    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
        	if (TogglDataAPIHandler.getInstance().tagProcessedRecords(user, userPass, workspaceId, projectId)) {
        		msg = config.getSuccessPrefix() + " Process tagging is complete.";
        	} else {
        		msg = config.getErrorPrefix() + " Process tagging failed.";
        	}
    	} else {
    		msg = config.getErrorPrefix() + " Authentication failure.";
    	}
    	return msg;
    }
    
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}