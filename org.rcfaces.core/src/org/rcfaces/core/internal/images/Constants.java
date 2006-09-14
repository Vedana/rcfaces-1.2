/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.images;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Constants {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(Constants.class);

    public static final boolean IMAGES_WEAK_MAP_CACHE_ENABLED = true;

    private static final boolean USE_INCLUDE_IMAGE_LOADER_FACTORY = true;

    public static final int DEFAULT_MAX_IMAGE_CACHE_SIZE = 256;

    private static final String CONSTANT_PREFIX;
    static {
        String name = Constants.class.getPackage().getName();
        if (name.endsWith(".internal")) {
            name = name.substring(0, name.lastIndexOf('.'));
        }

        CONSTANT_PREFIX = name;
    }

    private static final IImageLoaderFactory IMAGE_LOADER_FACTORY;

    static {
        LOG.info("IMAGES_WEAK_MAP_CACHE_ENABLED="
                + IMAGES_WEAK_MAP_CACHE_ENABLED);

        if (USE_INCLUDE_IMAGE_LOADER_FACTORY) {
            IMAGE_LOADER_FACTORY = new IncludeImageLoaderFactory();

        } else {
            IMAGE_LOADER_FACTORY = new ResourceImageLoaderFactory();
        }

        LOG.info("USE_INCLUDE_IMAGE_LOADER_FACTORY="
                + USE_INCLUDE_IMAGE_LOADER_FACTORY + " ("
                + IMAGE_LOADER_FACTORY.getName() + ")");
    }

    public static final String getPackagePrefix() {
        return CONSTANT_PREFIX;
    }

    public static final IImageLoaderFactory getImageLoaderFactory() {
        return IMAGE_LOADER_FACTORY;
    }
}
