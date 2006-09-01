/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.images.ico;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataFormatImpl;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ICOMetadataFormat extends IIOMetadataFormatImpl {
    private static final String REVISION = "$Revision$";

    private static IIOMetadataFormat instance = null;

    private ICOMetadataFormat() {
        super(ICOMetadata.nativeMetadataFormatName, CHILD_POLICY_SOME);

        addElement("ImageDescriptor", ICOMetadata.nativeMetadataFormatName,
                CHILD_POLICY_EMPTY);

        addAttribute("ImageDescriptor", "Width", DATATYPE_INTEGER, true, null,
                "0", "65535", true, true);
        addAttribute("ImageDescriptor", "Height", DATATYPE_INTEGER, true, null,
                "1", "65535", true, true);
    }

    public boolean canNodeAppear(String elementName,
            ImageTypeSpecifier imageType) {
        return true;
    }

    public static synchronized IIOMetadataFormat getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new ICOMetadataFormat();

        return instance;
    }
}