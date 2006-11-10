/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBorderTypeCapability {
    String NONE_BORDER_TYPE_NAME = "none";

    String getBorderType();

    void setBorderType(String borderType);
}
