/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
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
