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
public class SetSizeOperation extends AbstractScaleOperation {
    private static final String REVISION = "$Revision$";

    private float sizeWidth;

    private float sizeHeight;

    public String getName() {
        return "setSize";
    }

    public void configure(Map configuration) {
        super.configure(configuration);

        String sizeWidth = (String) configuration.get(getWidthPropertyName());
        String sizeHeight = (String) configuration.get(getHeightPropertyName());
        if (sizeWidth != null && sizeHeight != null) {
            this.sizeWidth = Float.parseFloat(sizeWidth);
            this.sizeHeight = Float.parseFloat(sizeHeight);
        }
    }

    private String getHeightPropertyName() {
        return "height";
    }

    private String getWidthPropertyName() {
        return "width";
    }

    protected float[] computeScales(BufferedImage sourceImage, Map configuration) {

        if (sourceImage == null) {
            return null;
        }

        float sizeWidth = this.sizeWidth;
        float sizeHeight = this.sizeHeight;

        String ssizeWidth = (String) configuration.get(getWidthPropertyName());
        if (ssizeWidth == null) {
            ssizeWidth = (String) configuration.get("#0");
        }
        String ssizeHeight = (String) configuration
                .get(getHeightPropertyName());
        if (ssizeHeight == null) {
            ssizeHeight = (String) configuration.get("#1");
        }
        if (ssizeWidth != null && ssizeHeight != null) {
            sizeWidth = Float.parseFloat(ssizeWidth);
            sizeHeight = Float.parseFloat(ssizeHeight);
        }

        float w = sizeWidth / sourceImage.getWidth();
        float h = sizeHeight / sourceImage.getHeight();

        return new float[] { w, h };
    }
}
