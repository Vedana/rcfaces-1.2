/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICellImageCapability {

    /**
     * Returns an url string pointing to the default image.
     * 
     * @return image url
     */
    String getDefaultCellImageURL();

    /**
     * Sets an url string pointing to the default image.
     * 
     * @param defaultCellImageURL
     *            image url
     */
    void setDefaultCellImageURL(String defaultCellImageURL);

    /**
     * Returns an url string pointing to the image.
     * 
     * @return image url
     */
    String getCellImageURL();

    /**
     * Sets an url string pointing to the image.
     * 
     * @param cellImageURL
     *            image url for the cell
     */
    void setCellImageURL(String cellImageURL);
}
