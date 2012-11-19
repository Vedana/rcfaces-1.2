/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

import org.rcfaces.css.internal.CssDeclarationListIterator;

import com.steadystate.css.dom.Property;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IRuleProcessor {

    void process(CssDeclarationListIterator declarationList,
            UserAgentPropertyRule ur, Property p);

}
