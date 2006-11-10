/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageOperation {

    String getName();

    void setName(String name);

    String getForceSuffix();

    void setForceSuffix(String suffix);

    String getExternalContentType();

    void setExternalContentType(String contentType);

    String getInternalContentType();

    void setInternalContentType(String contentType);

    void configure(Map configuration);

    BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination);
}
