package org.mule.modules.toggldata;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;

import org.mule.modules.toggldata.config.ConnectorConfig;

@Connector(name="toggl-data", friendlyName="TogglData")
public class TogglDataConnector {

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

    public String extractTransformLoad(final String projectId) {
    	if (projectId == null || projectId.equals("")) {
    		// do all projects
    	} else {
    		// do specific project
    	}
    	return config.getSuccessMsg() + ":" + projectId + " was loaded into remote persistent database.";
    }
    
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}