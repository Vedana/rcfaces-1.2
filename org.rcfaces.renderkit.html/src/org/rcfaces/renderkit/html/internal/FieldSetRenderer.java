/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.27  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.26  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.25  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.24  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.23  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.22  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.21  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.20  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.19  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.18  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.17  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.16  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.15  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.rcfaces.core.component.FieldSetComponent;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.renderkit.border.ITitledBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.IHtmlBorderRenderer;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class FieldSetRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String BORDER_RENDERER = "camelia.customButton.borderRenderer";

    private static final String CELL_BODY = "_cellBody";

    private static final String DIV_BODY = "_cellBody";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FieldSetComponent component = (FieldSetComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        encodeFieldSetTop(htmlWriter, component);
    }

    protected void encodeFieldSetTop(IHtmlWriter writer,
            FieldSetComponent fieldSetComponent) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        writer.startElement("DIV");
        writeCoreAttributes(writer);
        String className = getStyleClassName(componentRenderContext,
                fieldSetComponent);
        writer.writeAttribute("class", className);

        writeJavaScriptAttributes(writer);

        ICssWriter cssWriter = createCssWriter();
        cssWriter.writePosition(fieldSetComponent);
        cssWriter.writeSize(fieldSetComponent);
        cssWriter.writeMargin(fieldSetComponent);
        cssWriter.writeVisibility(fieldSetComponent);
        cssWriter.writeBackground(fieldSetComponent, null);
        cssWriter.close(writer);

        IHtmlBorderRenderer borderRenderer = getHtmlBorderRenderer(writer,
                fieldSetComponent);

        if (borderRenderer != null) {
            componentRenderContext
                    .setAttribute(BORDER_RENDERER, borderRenderer);

            String width = fieldSetComponent.getWidth(facesContext);
            String height = fieldSetComponent.getHeight(facesContext);

            borderRenderer
                    .initialize(writer, width, height, 1, 1, false, false);

            if (borderRenderer instanceof ITitledBorderRenderer) {
                ((ITitledBorderRenderer) borderRenderer).setText(writer,
                        fieldSetComponent.getText(facesContext));
            }

            borderRenderer.startComposite(writer);

            borderRenderer.startRow(writer);

            borderRenderer.startChild(writer, CELL_BODY);
        }

        writer.startElement("DIV")
                .writeAttribute("class", className + DIV_BODY);
    }

    protected IHtmlBorderRenderer getHtmlBorderRenderer(IHtmlWriter writer,
            FieldSetComponent fieldSetComponent) {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        String borderType = fieldSetComponent.getBorderType(facesContext);

        IBorderRenderersRegistry borderRendererRegistry = RcfacesContext
                .getInstance(facesContext.getExternalContext())
                .getBorderRenderersRegistry();

        return (IHtmlBorderRenderer) borderRendererRegistry.getBorderRenderer(
                facesContext, RenderKitFactory.HTML_BASIC_RENDER_KIT,
                fieldSetComponent.getFamily(), fieldSetComponent
                        .getRendererType(), borderType);
    }

    protected String getDefaultBorderType(FieldSetComponent fieldSetComponent) {
        return "rounded";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FieldSetComponent component = (FieldSetComponent) componentContext
                .getComponent();

        encodeFieldSetBottom((IHtmlWriter) writer, component);

        super.encodeEnd(writer);
    }

    protected void encodeFieldSetBottom(IHtmlWriter htmlWriter,
            FieldSetComponent component) throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        htmlWriter.endElement("DIV");

        IHtmlBorderRenderer borderRenderer = (IHtmlBorderRenderer) componentRenderContext
                .getAttribute(BORDER_RENDERER);

        if (borderRenderer != null) {
            borderRenderer.endChild(htmlWriter);

            borderRenderer.endRow(htmlWriter);

            borderRenderer.endComposite(htmlWriter);
        }

        htmlWriter.endElement("DIV");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#decode(org.rcfaces.core.internal.renderkit.IRequestContext,
     *      javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FieldSetComponent fieldSetComponent = (FieldSetComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            fieldSetComponent.setText(text);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.FIELD_SET;
    }
}