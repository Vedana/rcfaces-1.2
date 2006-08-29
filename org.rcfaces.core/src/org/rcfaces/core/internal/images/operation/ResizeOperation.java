/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 */
package org.rcfaces.core.internal.images.operation;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ResizeOperation extends AbstractScaleOperation {
    private static final String REVISION = "$Revision$";

    private float size;

    public String getName() {
        return "resize";
    }

    public void configure(Map configuration) {
        super.configure(configuration);

        String size = (String) configuration.get(getSizePropertyName());
        if (size != null) {
            this.size = Float.parseFloat(size);
        }
    }

    protected String getSizePropertyName() {
        return "size";
    }

    protected String getDefaultPropertyName() {
        return getSizePropertyName();
    }

    protected float[] computeScales(BufferedImage sourceImage, Map configuration) {

        if (sourceImage == null) {
            return new float[] { 1.0f, 1.0f };
        }

        float size = this.size;

        String ssize = (String) configuration.get(getSizePropertyName());
        if (ssize == null) {
            ssize = (String) configuration.get("#0");
        }
        if (ssize != null) {
            size = Float.parseFloat(ssize);
        }

        float w = size / sourceImage.getWidth();
        float h = size / sourceImage.getHeight();

        if (w > h) {
            w = h;
        }

        return new float[] { w, w };
    }

}
