/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.1  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 */
package org.rcfaces.core.model;

import javax.faces.component.UISelectItem;

import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.internal.component.ExpandableItemComponent;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicImagesSelectItem extends BasicSelectItem implements
        IImagesSelectItem {
    private static final String REVISION = "$Revision$";

    private String imageURL;

    private String disabledImageURL;

    private String hoverImageURL;

    private String selectedImageURL;

    private String expandedImageURL;

    public BasicImagesSelectItem() {
        super();
    }

    public BasicImagesSelectItem(UISelectItem component) {
        super(component);

        if (component instanceof IImageCapability) {
            imageURL = ((IImageCapability) component).getImageURL();
        }

        if (component instanceof IStatesImageCapability) {
            IStatesImageCapability is = (IStatesImageCapability) component;

            disabledImageURL = is.getDisabledImageURL();
            hoverImageURL = is.getHoverImageURL();
            selectedImageURL = is.getSelectedImageURL();
        }

        if (component instanceof ExpandableItemComponent) {
            expandedImageURL = ((ExpandableItemComponent) component)
                    .getExpandedImageURL();
        }

    }

    public String getImageURL() {
        return imageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getHoverImageURL()
     */
    public String getHoverImageURL() {
        return hoverImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getSelectedImageURL()
     */
    public String getSelectedImageURL() {
        return selectedImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getExpandedImageURL()
     */
    public String getExpandedImageURL() {
        return expandedImageURL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IImagesSelectItem#getDisabledImageURL()
     */
    public String getDisabledImageURL() {
        return disabledImageURL;
    }

    public void setDisabledImageURL(String disabledImageURL) {
        this.disabledImageURL = disabledImageURL;
    }

    public void setExpandedImageURL(String expandedImageURL) {
        this.expandedImageURL = expandedImageURL;
    }

    public void setHoverImageURL(String hoverImageURL) {
        this.hoverImageURL = hoverImageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSelectedImageURL(String selectedImageURL) {
        this.selectedImageURL = selectedImageURL;
    }

}
