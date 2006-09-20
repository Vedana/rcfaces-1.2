/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.23  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.22  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.21  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.20  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.19  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.18  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.17  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.16  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.15  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.14  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.13  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.12  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.11  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.10  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.9  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.8  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.7  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.6  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.5  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;



/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboExRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

 /*
    protected void encodeBeforeDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeBeforeDecorator(writer, componentDecorator);

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        ComboExComponent component = (ComboExComponent) componentRenderContext
                .getComponent();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        writer.enableJavaScript();

        writer.startElement("TABLE");
        writeHtmlAttributes(writer);
        writeJavaScriptAttributes(writer);
        writeCssAttributes(writer);

        writer.writeAttribute("cellspacing", "0");
        writer.writeAttribute("cellpadding", "0");

        if (component.isDisabled(facesContext)) {
            writer.writeAttribute("v:disabled", "true");
        }
        if (component.isEditable(facesContext) == false) {
            writer.writeAttribute("v:editable", "false");
        }
        if (component.isReadOnly(facesContext)) {
            writer.writeAttribute("v:readOnly", "true");
        }
        if (component.isAutoCompletion(facesContext)) {
            writer.writeAttribute("v:autoCompletion", "true");
        }
        int rowNumber = component.getPopupRowNumber();
        if (rowNumber > 0) {
            writer.writeAttribute("v:popupRowNumber", rowNumber);
        }
    }

    protected void encodeAfterDecorator(IHtmlWriter writer,
            IComponentDecorator componentDecorator) throws WriterException {
        writer.endElement("TABLE");

        super.encodeAfterDecorator(writer, componentDecorator);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.COMBO_EX;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {
        return new ComboExDecorator(component, null);
    }

     protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {

        ComboExComponent comboExComponent = (ComboExComponent) component;

        String parameter = componentData.getComponentParameter();
        if (parameter != null) {
            comboExComponent.setText(parameter);

        } else {
            // Le composant etait peut �tre disabled !

            String text = componentData.getStringProperty("text");
            if (text != null) {
                comboExComponent.setText(text);
            }
        }

        super.decode(context, component, componentData);
    }
    */
}