/**
 * Anypoint Connector
 *
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 *
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 */

package org.ciwise.modules.togglapi.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

	/**
	 * Success prefix
	 */
	@Configurable
	@Default("SUCCESS:")
	private String successPrefix;
	
	/**
	 * Error prefix
	 */
	@Configurable
	@Default("ERROR:")
	private String errorPrefix;
	
	/**
	 * 
	 * @return the success prefix
	 */
	public String getSuccessPrefix() {
		return this.successPrefix;
	}

	/**
	 * 
	 * @return the error prefix
	 */
	public String getErrorPrefix() {
		return this.errorPrefix;
	}

	/**
	 * @param successPrefix the successPrefix to set
	 */
	public void setSuccessPrefix(String successPrefix) {
		this.successPrefix = successPrefix;
	}

	/**
	 * @param errorPrefix the errorPrefix to set
	 */
	public void setErrorPrefix(String errorPrefix) {
		this.errorPrefix = errorPrefix;
	}
	
	
}