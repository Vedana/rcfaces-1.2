/*
 * $Id$
 */
package org.rcfaces.core.model;

import org.rcfaces.core.internal.contentAccessor.BasicContentInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageContentInformation extends BasicContentInformation {
    private static final String REVISION = "$Revision$";

    public final int getImageHeight() {
        Integer i = (Integer) getAttribute(IContentModel.HEIGHT_PROPERTY);
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }

    public final void setImageHeight(int imageHeight) {
        setAttribute(IContentModel.HEIGHT_PROPERTY, new Integer(imageHeight));
    }

    public final int getImageWidth() {
        Integer i = (Integer) getAttribute(IContentModel.WIDTH_PROPERTY);
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }

    public final void setImageWidth(int imageWidth) {
        setAttribute(IContentModel.WIDTH_PROPERTY, new Integer(imageWidth));
    }

}
