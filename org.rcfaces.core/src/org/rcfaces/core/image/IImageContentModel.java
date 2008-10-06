/*
 * $Id$
 */
package org.rcfaces.core.image;

import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageContentModel extends IContentModel {

    String WIDTH_PROPERTY = "org.rfcaces.core.model.WIDTH";

    String HEIGHT_PROPERTY = "org.rfcaces.core.model.HEIGHT";

    String ENCODER_MIME_TYPE_PROPERTY = "org.rcfaces.encoder.MIME_TYPE";

    String ENCODER_SUFFIX_PROPERTY = "org.rcfaces.encoder.SUFFIX";

    String COMPRESSION_QUALITY = "org.rfcaces.encore.COMPRESSION_QUALITY";

    String COMPRESSION_MODE = "org.rfcaces.encore.COMPRESSION_MODE";

    String COMPRESSION_TYPE = "org.rfcaces.encore.COMPRESSION_TYPE";

    String COMPRESSION_PROGRESSIVE_MODE = "org.rfcaces.encore.PROGRESSIVE_MODE";

}
