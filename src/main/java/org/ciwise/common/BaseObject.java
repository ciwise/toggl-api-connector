/**
 * Copyright (c) 2016 David L. Whitehurst and CI Wise Inc.
 *
 * Permission is hereby granted, free of charge, to any person 
 * obtaining a copy of this software and associated documentation 
 * files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, 
 * publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

package org.ciwise.common;

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