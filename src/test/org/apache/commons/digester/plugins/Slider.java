/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//digester/src/test/org/apache/commons/digester/plugins/Slider.java,v 1.2 2003/10/05 15:30:23 rdonkin Exp $
 * $Revision: 1.2 $
 * $Date: 2003/10/05 15:30:23 $
 *
 * ====================================================================
 * 
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2003 The Apache Software Foundation.  All rights
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
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Apache", "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache" nor may "Apache" appear in their names without prior 
 *    written permission of the Apache Software Foundation.
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

package org.apache.commons.digester.plugins;

import org.apache.commons.digester.Digester;

public class Slider implements Widget {
    private String label = "nolabel";
    private int min = 0;
    private int max = 0;

    // define rules on this class
    public static void addRules(Digester digester, String pattern) {
        digester.addSetProperties(pattern);
        
        Class[] paramtypes = {Integer.class};
        digester.addCallMethod(pattern+"/min", "setMin", 0, paramtypes);  
        digester.addCallMethod(pattern+"/max", "setMax", 0, paramtypes);  
    }
    
    // define different rules on this class
    public static void addRangeRules(Digester digester, String pattern) {
        // note: deliberately no addSetProperties rule
        Class[] paramtypes = {Integer.class, Integer.class};
        digester.addCallMethod(pattern+"/range", "setRange", 2, paramtypes);  
        digester.addCallParam(pattern+"/range", 0, "min");  
        digester.addCallParam(pattern+"/range", 1, "max");  
    }
    
    public Slider() {}
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public void setMin(int min) {
        this.min = min;
    }
    
    public int getMin() {
        return min;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public int getMax() {
        return max;
    }
    
    public void setRange(int min, int max) {
        this.min = min;
        this.max = max;
    }
}