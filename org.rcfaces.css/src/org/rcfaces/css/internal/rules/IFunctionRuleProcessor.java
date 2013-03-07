/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

import org.rcfaces.css.internal.CssPropertyListIterator;
import org.w3c.css.sac.LexicalUnit;

import com.steadystate.css.dom.Property;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFunctionRuleProcessor {

    void process(CssPropertyListIterator declarationList,
            UserAgentPropertyRule ur, Property p, LexicalUnit lexicalUnit);

}
