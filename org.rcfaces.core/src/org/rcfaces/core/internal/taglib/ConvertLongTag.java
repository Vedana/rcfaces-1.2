/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertLongTag extends ConvertNumberTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -7314605990150080216L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Long";
    }

}
