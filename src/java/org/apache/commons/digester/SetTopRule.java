/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//digester/src/java/org/apache/commons/digester/SetTopRule.java,v 1.12 2002/01/27 00:52:06 craigmcc Exp $
 * $Revision: 1.12 $
 * $Date: 2002/01/27 00:52:06 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


package org.apache.commons.digester;


import java.lang.ClassLoader;
import java.lang.reflect.Method;

import org.xml.sax.Attributes;
import org.apache.commons.beanutils.MethodUtils;


/**
 * Rule implementation that calls a "set parent" method on the top (child)
 * object, passing the (top-1) (parent) object as an argument.
 *
 * @author Craig McClanahan
 * @author Scott Sanders
 * @author Janek Bogucki
 * @version $Revision: 1.12 $ $Date: 2002/01/27 00:52:06 $
 */

public class SetTopRule extends Rule {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a "set parent" rule with the specified method name.  The
     * "set parent" method's argument type is assumed to be the class of the
     * parent object.
     *
     * @param digester The associated Digester
     * @param methodName Method name of the "set parent" method to call
     */
    public SetTopRule(Digester digester, String methodName) {

        this(digester, methodName, null);

    }


    /**
     * Construct a "set parent" rule with the specified method name.
     *
     * @param digester The associated Digester
     * @param methodName Method name of the "set parent" method to call
     * @param paramType Java class of the "set parent" method's argument
     *  (if you wish to use a primitive type, specify the corresonding
     *  Java wrapper class instead, such as <code>java.lang.Boolean</code>
     *  for a <code>boolean</code> parameter)
     */
    public SetTopRule(Digester digester, String methodName,
                      String paramType) {

        super(digester);
        this.methodName = methodName;
        this.paramType = paramType;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The method name to call on the child object.
     */
    protected String methodName = null;


    /**
     * The Java class name of the parameter type expected by the method.
     */
    protected String paramType = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Process the end of this element.
     */
    public void end() throws Exception {

        // Identify the objects to be used
        Object child = digester.peek(0);
        Object parent = digester.peek(1);
        if (digester.log.isDebugEnabled()) {
            if (child == null) {
                digester.log.debug("[SetTopRule]{" + digester.match +
                        "} Call [NULL CHILD]." +
                        methodName + "(" + parent + ")");
            } else {
                digester.log.debug("[SetTopRule]{" + digester.match +
                        "} Call " + child.getClass().getName() + "." +
                        methodName + "(" + parent + ")");
            }
        }

        // Call the specified method
        Class paramTypes[] = new Class[1];
        if (paramType != null) {
            paramTypes[0] =
                    digester.getClassLoader().loadClass(paramType);
        } else {
            paramTypes[0] = parent.getClass();
        }
        MethodUtils.invokeExactMethod(child, methodName,
                new Object[]{ parent }, paramTypes);

    }


    /**
     * Render a printable version of this Rule.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("SetTopRule[");
        sb.append("methodName=");
        sb.append(methodName);
        sb.append(", paramType=");
        sb.append(paramType);
        sb.append("]");
        return (sb.toString());

    }


}
