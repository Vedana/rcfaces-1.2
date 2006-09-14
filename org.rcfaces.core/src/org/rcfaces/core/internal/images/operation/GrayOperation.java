/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.internal.images.operation;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.util.Map;

import org.rcfaces.core.image.AbstractImageOperation;
import org.rcfaces.core.image.IIndexedImageOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GrayOperation extends AbstractImageOperation implements
        IIndexedImageOperation {
    private static final String REVISION = "$Revision$";

    private ColorSpace colorSpace;

    private BufferedImageOp grayOperation;

    public void configure(Map configuration) {
        colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        grayOperation = new ColorConvertOp(colorSpace, null);
    }

    public BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination) {

        return grayOperation.filter(source, source);
    }

    public int indexedColorModelSupport() {
        return INDEX_COLOR_MODEL_COLORS_MAP;
    }

    protected boolean ignoreRequestParameter() {
        return true;
    }

    public IndexColorModel filter(Map requestParameter, IndexColorModel source,
            BufferedImage sourceImage) {
        int mapSize = source.getMapSize();

        int colorMap[] = new int[mapSize];
        source.getRGBs(colorMap);

        int transparentPixel = source.getTransparentPixel();

        float rgbvalue[] = new float[3];
        for (int i = 0; i < colorMap.length; i++) {
            if (i == transparentPixel) {
                continue;
            }

            int c = colorMap[i];
            rgbvalue[0] = ((c >> 16) & 255) / 255.0f;
            rgbvalue[1] = ((c >> 8) & 255) / 255.0f;
            rgbvalue[2] = (c & 255) / 255.0f;

            float ret[] = colorSpace.fromRGB(rgbvalue);

            int dc = (int) (ret[0] * 255.0f);
            colorMap[i] = (dc << 16) | (dc << 8) | dc | (c & 0xff000000);
        }

        return new IndexColorModel(8, colorMap.length, colorMap, 0, source
                .hasAlpha(), source.getTransparentPixel(), DataBuffer.TYPE_BYTE);
    }
}
