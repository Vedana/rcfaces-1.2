/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

import org.rcfaces.css.internal.CssPropertyListIterator;

import com.steadystate.css.dom.Property;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPropertyRuleProcessor {

    void process(CssPropertyListIterator declarationList,
            UserAgentPropertyRule ur, Property p);

}
