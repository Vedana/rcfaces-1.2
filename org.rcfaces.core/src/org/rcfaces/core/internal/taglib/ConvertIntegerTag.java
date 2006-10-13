/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertIntegerTag extends ConvertNumberTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 2036254497469923597L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Integer";
    }

}
