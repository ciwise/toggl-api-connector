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
	 * Tagging successful
	 */
	@Configurable
	@Default("SUCCESS: tagging complete!")
	private String successProcessTag;
	
	/**
	 * Tagging error
	 */
	@Configurable
	@Default("ERROR: tagging failed!")
	private String errorProcessTag;
	
	/**
	 * Authentication error
	 */
	@Configurable
	@Default("ERROR: authentication failure!")
	private String errorAuthenticate;

	/**
	 * @return the successProcessTag
	 */
	public String getSuccessProcessTag() {
		return successProcessTag;
	}

	/**
	 * @param successProcessTag the successProcessTag to set
	 */
	public void setSuccessProcessTag(String successProcessTag) {
		this.successProcessTag = successProcessTag;
	}

	/**
	 * @return the errorProcessTag
	 */
	public String getErrorProcessTag() {
		return errorProcessTag;
	}

	/**
	 * @param errorProcessTag the errorProcessTag to set
	 */
	public void setErrorProcessTag(String errorProcessTag) {
		this.errorProcessTag = errorProcessTag;
	}

	/**
	 * @return the errorAuthenticate
	 */
	public String getErrorAuthenticate() {
		return errorAuthenticate;
	}

	/**
	 * @param errorAuthenticate the errorAuthenticate to set
	 */
	public void setErrorAuthenticate(String errorAuthenticate) {
		this.errorAuthenticate = errorAuthenticate;
	}
	
}