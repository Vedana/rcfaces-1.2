/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertByteTag extends ConvertNumberTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 2569526670834596770L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Byte";
    }

}
