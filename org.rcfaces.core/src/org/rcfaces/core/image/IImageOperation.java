/*
 * $Id$
 * 
 */
package org.rcfaces.core.image;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.rcfaces.core.internal.content.IBufferOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageOperation extends IBufferOperation {

    String getForceSuffix();

    void setForceSuffix(String suffix);

    String getExternalContentType();

    void setExternalContentType(String contentType);

    String getInternalContentType();

    void setInternalContentType(String contentType);

    BufferedImage filter(Map requestParameter, BufferedImage source,
            BufferedImage destination);
}
