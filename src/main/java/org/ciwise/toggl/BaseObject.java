/**
 * Anypoint Connector (Temporary Use Only)
 *
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 *
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 */

package org.ciwise.toggl;

import java.io.Serializable;

/**
 * This class provides the Java Object overriden methods recommended by Joshua
 * J. Bloch, author of Effective Java, Second Edition
 *
 * @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 */
public abstract class BaseObject implements Serializable {

    /**
     * Unique serial class identifier.
     */
    private static final long serialVersionUID = -9157212094150650829L;

    /**
     * Return a multi-line String with key=value pairs.
     *
     * @return a String representation of this class.
     */
    @Override
    public abstract String toString();

    /**
     * Compares object equality.
     *
     * @param o
     *            object to compare to
     * @return true/false based on equality tests
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     *
     * @return hashCode
     */
    @Override
    public abstract int hashCode();
}