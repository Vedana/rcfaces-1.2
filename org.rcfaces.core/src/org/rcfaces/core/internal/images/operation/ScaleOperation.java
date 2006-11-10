/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.images.operation;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ScaleOperation extends AbstractScaleOperation {
    private static final String REVISION = "$Revision$";

    private float scale;

    public void configure(Map configuration) {
        super.configure(configuration);

        String scale = (String) configuration.get(getScalePropertyName());
        if (scale != null) {
            this.scale = Float.parseFloat(scale);
        }
    }

    protected String getScalePropertyName() {
        return "scale";
    }

    protected float[] computeScales(BufferedImage sourceImage, Map configuration) {
        float scale = this.scale;

        String sscale = (String) configuration.get(getScalePropertyName());
        if (sscale == null) {
            sscale = (String) configuration.get("#0");
        }
        if (sscale != null) {
            scale = Float.parseFloat(sscale);
        }

        return new float[] { scale, scale };
    }
}
