/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertShortTag extends ConvertNumberTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 7353845743026183612L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Short";
    }

}
