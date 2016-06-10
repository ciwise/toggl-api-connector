/**
 * 
 */
package org.ciwise.toggl;

import org.ciwise.common.BaseObject;

/**
 * This class provides an abstraction for API Handler classes.
 *
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 */
public abstract class ClientAPIHandler extends BaseObject {
	
	/**
	 * A unique serial identifier.
	 */
	private static final long serialVersionUID = 7718368554644023380L;

	/**
	 * This method authenticates with the host API.
	 * @return true/false for authentication status
	 */
	public abstract boolean authenticate(); // return HTTP 403 via HTTP listen if false
	
	

}
