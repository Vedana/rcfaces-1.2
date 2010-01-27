/*
 * $Id$
 */
package org.rcfaces.core.internal.images;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageResourceAdapter {
    boolean isContentSupported(String contentType, String suffix);
}
