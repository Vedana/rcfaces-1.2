/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaTagExtraInfo extends TagExtraInfo {

    private static final String REVISION = "$Revision$";

    private static final VariableInfo[] VARIABLE_INFO_EMPTY_ARRAY = new VariableInfo[0];

    public VariableInfo[] getVariableInfo(TagData tagData) {
        return VARIABLE_INFO_EMPTY_ARRAY;
    }

}
