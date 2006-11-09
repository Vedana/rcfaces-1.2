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

    private int imageWidth;

    private int imageHeight;

    public final int getImageHeight() {
        return imageHeight;
    }

    public final void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public final int getImageWidth() {
        return imageWidth;
    }

    public final void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

}
