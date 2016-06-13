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

import org.ciwise.common.BaseObject;

/**
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public abstract class BaseHttpMessage extends BaseObject {

	public abstract String getJSONMessage(HttpProcessMessage msg);
}
