/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertFloatTag extends ConvertNumberTag {
    

    private static final long serialVersionUID = 243949867891698025L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Float";
    }

}
