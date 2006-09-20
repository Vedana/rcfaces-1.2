/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.5  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.4  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.model.IFilterProperties;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComboExDecorator extends AbstractSelectItemsDecorator {
 
    private static final String REVISION = "$Revision$";

    protected ComboExDecorator(UIComponent component, IFilterProperties filterProperties) {
        super(component, filterProperties);
    }

    protected SelectItemsContext createHtmlContext() {
         return null;
    }

    protected SelectItemsContext createJavaScriptContext() throws WriterException {
        return null;
    }

    public int encodeNodeBegin(UIComponent component, SelectItem selectItem, boolean hasChild, boolean isVisible) throws WriterException {
         return 0;
    }

    public void encodeNodeEnd(UIComponent component, SelectItem selectItem, boolean hasChild, boolean isVisible) throws WriterException {
        
    }

    /*
    private static final String ARROW_BLANK_URI = "comboEx/blank.gif";

    private static final String INPUT = "_input";

    public ComboExDecorator(UIComponent component,
            IFilterProperties filterProperties) {
        super(component, filterProperties);
    }

    public void preEncodeContainer() throws WriterException {
        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        ComboExComponent component = (ComboExComponent) componentRenderContext
                .getComponent();
        IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) componentRenderContext
                .getRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        writer.enableJavaScript();

        writer.startElement("TR");

        String blankImageURL = htmlRenderContext.getHtmlExternalContext()
                .getStyleSheetURI(ARROW_BLANK_URI);

        if (mapSelectItems(componentRenderContext,
                SelectItemMappers.SEARCH_IMAGE_MAPPER) == false) {
            // Y a une image ...

            String className = getClassName();

            writer.startElement("TD");
            writer.startElement("IMG");
            writer.writeAttribute("class", className + "_itemImage");
            writer.writeAttribute("src", blankImageURL);
            writer.endElement("TD");
        }

        writer.startElement("TD");
        if (component.getWidth() != null) {
            writer.writeAttribute("width", "100%");
        }

        writer.startElement("INPUT");
        writer.writeAttribute("type", "text");
        writer.writeAttribute("name", componentRenderContext.getComponentId());

        int maxTextLength = component.getMaxTextLength(facesContext);
        if (maxTextLength > 0) {
            writer.writeAttribute("maxLength", maxTextLength);
        }

        int iCol = component.getColumnNumber(facesContext);
        if (iCol > 0) {
            writer.writeAttribute("size", iCol);
        }

        if (component.getWidth(facesContext) != null) {
            writer.writeAttribute("style", "width:100%");
        }

        if (component.isEditable(facesContext) == false
                || component.isReadOnly(facesContext)) {
            writer.writeAttribute("READONLY");
        }

        if (component.isDisabled(facesContext)) {
            writer.writeAttribute("DISABLED");
        }

        String txt = component.getText(facesContext);
        if (txt == null) {
            txt = "";
        }

        String className = getClassName();

        writer.writeAttribute("value", txt);
        writer.endElement("TD");
        writer.startElement("TD");
        writer.writeAttribute("class", className + "_cimage");

        writer.startElement("IMG");
        if (component.isDisabled(facesContext)) {
            writer.writeAttribute("class", className + "_image_disabled");
        } else {
            writer.writeAttribute("class", className + "_image");
        }
        writer.writeAttribute("src", blankImageURL);

        writer.endElement("TD");
        writer.endElement("TR");

        super.preEncodeContainer();
    }

     protected SelectItemsContext createHtmlContext() {
        return null;
    }

    protected SelectItemsContext createJavaScriptContext() {
        IComponentRenderContext componentRenderContext = javaScriptWriter
                .getComponentRenderContext();

        UIInput input = (UIInput) getComponent();

        return new SelectItemsJsContext(this, componentRenderContext, input,
                input.getValue());
    }

    public int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) throws WriterException {

        SelectItemsJsContext ctx = (SelectItemsJsContext) getContext();

        String varId = javaScriptWriter.getJavaScriptRenderContext()
                .allocateVarName();

        String parentVarId = null;
        if (ctx.getDepth() > 1) {
            parentVarId = ctx.peekVarId();
        }

        ctx.pushVarId(varId);

        String imageURL = null;
        if (selectItem instanceof IImagesSelectItem) {
            imageURL = ((BasicImagesSelectItem) selectItem).getImageURL();
            imageURL = javaScriptWriter.allocateString(imageURL);
        }

        javaScriptWriter.write("var ").write(varId);
        if (parentVarId != null) {
            javaScriptWriter.write('=').writeMethodCall("_addSubItem").write(
                    parentVarId).write(',');

        } else {
            javaScriptWriter.write('=').writeMethodCall("_addItem");
        }

        String label = selectItem.getLabel();
        if (label == null) {
            label = "";
        }
        javaScriptWriter.writeString(label);

        javaScriptWriter.write(',');

        String value = convertItemValue(javaScriptWriter
                .getComponentRenderContext(), selectItem.getValue());
        if (value != null) {
            javaScriptWriter.writeString(value);

        } else {
            javaScriptWriter.writeNull();
        }

        boolean disabled = selectItem.isDisabled();

        if (imageURL != null) {
            javaScriptWriter.write(',').write(imageURL);

        } else if (disabled) {
            javaScriptWriter.write(',').writeNull();
        }

        if (disabled) {
            javaScriptWriter.write(',').writeBoolean(true);
        }

        javaScriptWriter.writeln(");");

        return EVAL_NODE;
    }

      public void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChildren, boolean isVisible) {

        ((SelectItemsJsContext) getContext()).popVarId();
    }

*/
}
