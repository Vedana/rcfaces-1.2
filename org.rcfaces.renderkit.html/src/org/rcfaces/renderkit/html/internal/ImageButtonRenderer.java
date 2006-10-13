/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:43  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.30  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.29  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.28  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.27  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.26  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.25  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.24  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.23  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.22  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.21  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.20  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.19  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.18  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.17  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.16  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.15  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.14  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.13  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.12  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.11  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.10  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.9  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 * Revision 1.8  2004/09/01 13:47:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.AbstractImageButtonFamillyDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageButtonRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    public static final String IMAGE_BUTTON_WRITER = "camelia.writer.ImageButton";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE_BUTTON;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        if (hasComponenDecoratorSupport() == false) {
            encodeComponent((IHtmlWriter) writer);
        }

        // Il faut activer le Javascript
        // car l'attribut SELECTED doit être envoyé a chaque requete du client vers le serveur !
        ((IHtmlWriter) writer).enableJavaScript();

        super.encodeEnd(writer);
    }

    protected void encodeComponent(IHtmlWriter writer) throws WriterException {
        throw new WriterException("Render is not implemented !", null, writer
                .getComponentRenderContext().getComponent());
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new ImageButtonDecorator((IImageButtonFamilly) component);
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class ImageButtonDecorator extends
            AbstractImageButtonFamillyDecorator {
        private static final String REVISION = "$Revision$";

        public ImageButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void writeAttributes(String classSuffix)
                throws WriterException {
            writeHtmlAttributes(writer);
            writeJavaScriptAttributes(writer);
            writeCssAttributes(writer, classSuffix, ~CSS_FONT_MASK);

            FacesContext facesContext = writer.getComponentRenderContext()
                    .getFacesContext();
            encodeAttributes(facesContext);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            if (imageButtonFamilly.isDisabled(facesContext)) {
                writer.writeAttribute("v:disabled", "true");
            }

            if (imageButtonFamilly.isReadOnly(facesContext)) {
                writer.writeAttribute("v:readOnly", "true");
            }
        }
    }
}