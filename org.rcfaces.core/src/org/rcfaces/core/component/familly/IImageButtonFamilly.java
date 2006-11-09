/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/11/09 19:09:07  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.2  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.1  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 */
package org.rcfaces.core.component.familly;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.capability.IBorderCapability;
import org.rcfaces.core.component.capability.IBorderTypeCapability;
import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IImageCapability;
import org.rcfaces.core.component.capability.IImageSizeCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.capability.IStatesImageCapability;
import org.rcfaces.core.component.capability.ITextCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageButtonFamilly extends IImageCapability,
        IStatesImageCapability, IBorderCapability, IBorderTypeCapability,
        ITextCapability, ISelectionEventCapability, IReadOnlyCapability,
        IDisabledCapability, ITextPositionCapability, IImageSizeCapability {

    String getImageURL(FacesContext facesContext);

    boolean isReadOnly(FacesContext facesContext);

    boolean isDisabled(FacesContext facesContext);

    boolean isBorder(FacesContext facesContext);

    String getBorderType(FacesContext facesContext);

    String getHoverImageURL(FacesContext facesContext);

    String getSelectedImageURL(FacesContext facesContext);

    String getDisabledImageURL(FacesContext facesContext);

    String getText(FacesContext facesContext);

    int getTextPosition(FacesContext facesContext);

    int getImageWidth(FacesContext facesContext);

    int getImageHeight(FacesContext facesContext);

    IContentAccessors getImageAccessors(FacesContext facesContext);
}
