/*
 * $Id$
 */
package org.rcfaces.core.internal.images.operation;

import java.util.Map;

import org.rcfaces.core.image.AbstractConversionImageOperation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PNGConversionImageOperation extends
        AbstractConversionImageOperation {
    private static final String REVISION = "$Revision$";

    public static final String MIME_TYPE = "image/png";

    public static final String SUFFIX = "png";

    public PNGConversionImageOperation() {
        super(MIME_TYPE, SUFFIX);
    }

    public static void fillSuffixByMimeType(Map suffixByMimeType) {
        suffixByMimeType.put(MIME_TYPE, SUFFIX);
    }
}
