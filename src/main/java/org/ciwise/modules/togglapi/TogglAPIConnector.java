package org.ciwise.modules.togglapi;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.ciwise.modules.togglapi.config.ConnectorConfig;
import org.ciwise.toggl.TogglDataAPIHandler;

@Connector(name="toggl-api", friendlyName="TogglAPI")
public class TogglAPIConnector {

    @Config
    ConnectorConfig config;

    /**
     * Custom processor
     *
     * @param friend Name to be used to generate a greeting message.
     * @return A greeting message
     */
    @Processor
    public String greet(String friend) {
        /*
         * MESSAGE PROCESSOR CODE GOES HERE
         */
        return config.getGreeting() + " " + friend + ". " + config.getReply();
    }

    @Processor(friendlyName="Unprocessed Detail Report Data by Project")
    public String getUnprocessedProjectTimeData(final String user, final String userPass, final String projectId) {
    	String json = null;
    	
    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
        	json = TogglDataAPIHandler.getInstance().getDetailReportDataForProjectNoTags(projectId, user); // 17654629
    	}
    	
    	return json;
    }
    
    @Processor(friendlyName="Tag Unprocessed Time Records")
    public String addProcessTags(final String user, final String userPass, final String projectId) {
    	String msg = null;

    	if (TogglDataAPIHandler.getInstance().authenticate(userPass)) {
        	if (TogglDataAPIHandler.getInstance().tagProcessedRecords()) {
        		msg = config.getSuccessPrefix() + " Process tagging is complete.";
        	} else {
        		msg = config.getFailurePrefix() + " Process tagging failed.";
        	}
    	} else {
    		msg = config.getFailurePrefix() + " Authentication failure.";
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