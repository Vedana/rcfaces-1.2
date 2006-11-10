/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IIndexedImageOperation extends IImageOperation {

    int INDEX_COLOR_MODEL_NOT_SUPPORTED = 0;

    int INDEX_COLOR_MODEL_SUPPORTED = 1;

    int INDEX_COLOR_MODEL_COLORS_MAP = 2;

    int indexedColorModelSupport();

    IndexColorModel filter(Map requestParameter, IndexColorModel source, BufferedImage sourceImage);

}
