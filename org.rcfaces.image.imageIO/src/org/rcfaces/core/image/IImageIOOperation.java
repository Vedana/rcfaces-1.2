/*
 * $Id$
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageIOOperation extends IImageOperation {

    BufferedImage filter(Map filterParameters, BufferedImage source,
            BufferedImage destination);

}
