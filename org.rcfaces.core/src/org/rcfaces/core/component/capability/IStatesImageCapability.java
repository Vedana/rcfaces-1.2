/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStatesImageCapability extends IImageCapability {
    String getHoverImageURL();

    void setHoverImageURL(String url);

    String getSelectedImageURL();

    void setSelectedImageURL(String url);

    String getDisabledImageURL();

    void setDisabledImageURL(String url);
}