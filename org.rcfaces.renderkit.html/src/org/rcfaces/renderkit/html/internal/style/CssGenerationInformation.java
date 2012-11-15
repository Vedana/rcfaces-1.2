/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.style;

import org.rcfaces.core.internal.contentAccessor.BasicGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssGenerationInformation extends
        BasicGenerationResourceInformation implements
        IProcessRulesGenerationInformation {

    public CssGenerationInformation(boolean processRules) {
        if (processRules) {
            setAttribute(PROCESS_RULES_ATTRIBUTE, Boolean.TRUE);
        }
    }

    public boolean isProcessRulesEnabled() {
        return Boolean.TRUE.equals(getAttribute(PROCESS_RULES_ATTRIBUTE));
    }
}