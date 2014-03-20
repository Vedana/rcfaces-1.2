/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertDoubleTag extends ConvertNumberTag {

    

    private static final long serialVersionUID = 6372503657660605243L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Double";
    }
}
