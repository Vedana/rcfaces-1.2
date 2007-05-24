/*
 * $Id$
 */
package org.rcfaces.core.model;

import javax.faces.component.UIComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISortedDataModel {
    void setSortParameters(UIComponent component,
            ISortedComponent sortedComponents[]);
}
