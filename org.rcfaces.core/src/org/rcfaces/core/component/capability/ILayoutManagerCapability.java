/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ILayoutManagerCapability {
    int INHERITED_LAYOUT_TYPE = 0;

    int NONE_LAYOUT_TYPE = 1;

    int ABSOLUTE_LAYOUT_TYPE = 2;

    int getLayoutType();

    void setLayoutType(int type);
}
