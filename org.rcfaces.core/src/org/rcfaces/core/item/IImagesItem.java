/*
 * $Id$
 */
package org.rcfaces.core.item;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImagesItem {
    String getImageURL();

    String getDisabledImageURL();

    String getHoverImageURL();

    String getSelectedImageURL();

    String getExpandedImageURL();
}
