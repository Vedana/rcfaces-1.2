/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.17  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.16  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.15  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.14  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.13  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.12  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.11  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.10  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.9  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.8  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.7  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.6  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.5  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.4  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/31 16:48:58  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/27 09:57:21  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/26 13:13:42  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ImageRadioButtonComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageRadioButtonRenderer extends ImageCheckButtonRenderer {
    private static final String REVISION = "$Revision$";

    public void encodeEnd(IComponentWriter writer) throws WriterException {
        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        ImageRadioButtonComponent radioButton = (ImageRadioButtonComponent) writer
                .getComponentRenderContext().getComponent();

        if (radioButton.isDisabled(facesContext) == false
                || radioButton.isSelected(facesContext)) {
            ((IHtmlWriter) writer).enableJavaScript();
        }

        super.encodeEnd(writer);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_RADIO_BUTTON;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ImageRadioButtonWriter((IImageButtonFamilly) component);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    protected class ImageRadioButtonWriter extends ImageCheckButtonDecorator {
        private static final String REVISION = "$Revision$";

        public ImageRadioButtonWriter(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            super.encodeAttributes(facesContext);

            String group = ((IRadioGroupCapability) imageButtonFamilly)
                    .getGroupName();
            if (group != null) {
                writer.writeAttribute("v:groupName", group);
            }
        }
    }
}