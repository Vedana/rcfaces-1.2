/*
 * $Id$
 */
package org.rcfaces.core.model;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImagesSelectItem {
	String getImageURL();

	String getDisabledImageURL();

	String getHoverImageURL();

	String getSelectedImageURL();

	String getExpandedImageURL();
}
