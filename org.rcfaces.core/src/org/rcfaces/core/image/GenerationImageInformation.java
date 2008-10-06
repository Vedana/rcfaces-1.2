/*
 * $Id$
 */
package org.rcfaces.core.image;

import org.rcfaces.core.internal.contentAccessor.BasicGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GenerationImageInformation extends
        BasicGenerationResourceInformation implements
        IGenerationImageInformation {
    private static final String REVISION = "$Revision$";

    public GenerationImageInformation() {
        super();
    }

    public final int getImageHeight() {
        Integer i = (Integer) getAttribute(IImageContentModel.HEIGHT_PROPERTY);
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }

    public final void setImageHeight(int imageHeight) {
        setAttribute(IImageContentModel.HEIGHT_PROPERTY, new Integer(
                imageHeight));
    }

    public final int getImageWidth() {
        Integer i = (Integer) getAttribute(IImageContentModel.WIDTH_PROPERTY);
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }

    public final void setImageWidth(int imageWidth) {
        setAttribute(IImageContentModel.WIDTH_PROPERTY, new Integer(imageWidth));
    }
}
