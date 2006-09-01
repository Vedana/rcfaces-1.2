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

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class DisableOperation extends GrayOperation {
    private static final String REVISION = "$Revision$";

    private static byte[] disableTable = new byte[256];
    static { // Initialize the arrays
        for (int i = 0; i < 256; i++) {
            double v = Math.sqrt(i / 255.0) * 210;

            v += 45;

            if (v > 255) {
                v = 255;
            }
            disableTable[i] = (byte) v;
        }
    }

    private LookupOp disableOperation;

    protected LookupTable getLookupTable() {
        return new ByteLookupTable(0, disableTable);
    }

    public void configure(Map configuration) {
        super.configure(configuration);

        disableOperation = new LookupOp(getLookupTable(), null);
    }

    public BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination) {

        BufferedImage dest = super
                .filter(requestParameter, source, destination);

        return disableOperation.filter(dest, destination);
    }

    public IndexColorModel filter(Map requestParameter, IndexColorModel source,
            BufferedImage sourceImage) {
        IndexColorModel dest = super.filter(requestParameter, source,
                sourceImage);

        int mapSize = dest.getMapSize();

        int colorMap[] = new int[mapSize];
        dest.getRGBs(colorMap);

        int transparentPixel = source.getTransparentPixel();

        for (int i = 0; i < colorMap.length; i++) {
            if (i == transparentPixel) {
                continue;
            }

            int c = colorMap[i];

            int r = disableTable[(c >> 16) & 255] & 255;
            int g = disableTable[(c >> 8) & 255] & 255;
            int b = disableTable[c & 255] & 255;

            colorMap[i] = (r << 16) | (g << 8) | b | (c & 0xff000000);
        }

        return new IndexColorModel(8, colorMap.length, colorMap, 0, source
                .hasAlpha(), source.getTransparentPixel(), DataBuffer.TYPE_BYTE);
    }
}
