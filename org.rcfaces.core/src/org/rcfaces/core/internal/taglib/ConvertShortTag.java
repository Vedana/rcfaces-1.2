/*
 * $Id: ConvertShortTag.java,v 1.1.12.1 2014/02/27 13:12:02 jbmeslin Exp $
 */
package org.rcfaces.core.internal.taglib;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author: jbmeslin $)
 * @version $Revision: 1.1.12.1 $ $Date: 2014/02/27 13:12:02 $
 */
public class ConvertShortTag extends ConvertNumberTag {
    

    private static final long serialVersionUID = 7353845743026183612L;

    protected String getDefaultConverterId() {
        return "org.rcfaces.Short";
    }

}
