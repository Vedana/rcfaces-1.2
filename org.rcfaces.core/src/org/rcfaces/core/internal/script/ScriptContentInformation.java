/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

import org.rcfaces.core.internal.contentAccessor.BasicContentInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ScriptContentInformation extends BasicContentInformation {
    private static final String REVISION = "$Revision$";

    public String getCharSet() {
        return (String) getAttribute(IScriptContentModel.CHARSET_PROPERTY);
    }

    public void setCharSet(String charSet) {
        setAttribute(IScriptContentModel.CHARSET_PROPERTY, charSet);
    }
}
