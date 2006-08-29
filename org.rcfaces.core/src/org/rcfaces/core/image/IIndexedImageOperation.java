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
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IIndexedImageOperation extends IImageOperation {

    int INDEX_COLOR_MODEL_NOT_SUPPORTED = 0;

    int INDEX_COLOR_MODEL_SUPPORTED = 1;

    int INDEX_COLOR_MODEL_COLORS_MAP = 2;

    int indexedColorModelSupport();

    IndexColorModel filter(Map requestParameter, IndexColorModel source, BufferedImage sourceImage);

}
