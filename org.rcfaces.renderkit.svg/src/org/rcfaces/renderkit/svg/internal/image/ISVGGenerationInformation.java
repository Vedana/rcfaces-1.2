/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.image;

import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISVGGenerationInformation extends
        IGenerationResourceInformation {

    String WIDTH_PROPERTY = "org.rfcaces.core.model.WIDTH";

    String HEIGHT_PROPERTY = "org.rfcaces.core.model.HEIGHT";

    void setHeight(int imageHeight);

    int getHeight();

    void setWidth(int imageWidth);

    int getWidth();
}
