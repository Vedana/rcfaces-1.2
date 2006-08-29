/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/19 15:44:31  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.model;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IImagesSelectItem {
	String getImageURL();

	String getDisabledImageURL();

	String getHoverImageURL();

	String getSelectedImageURL();

	String getExpandedImageURL();
}
