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

/**
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class ProcessTimer {
	
	static ProcessTimer instance;
	private Long startTime;
	private Long endTime;
	
	
	private ProcessTimer() {}
	
	public static ProcessTimer getInstance() {
		if (instance == null) {
			instance = new ProcessTimer();
		}
		return instance;
	}
	
	public void startTimer() {
		this.startTime = System.nanoTime(); 
	}

	public void stopTimer() {
		this.endTime = System.nanoTime();
	}
	
	public String getMillisecondTimeDuration() {
    	long duration = (this.endTime - this.startTime); //divide by 1000000 to get milliseconds.
    	String runtime = Long.toString(duration/1000000); // runtime in milliseconds.
    	return runtime;
	}
}
