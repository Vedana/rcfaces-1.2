/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.8  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.7  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.6  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.5  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.4  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.2  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.1  2006/01/31 16:04:24  oeuillot
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

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.ExpandBarComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ExpandBarRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String COLLAPSED_BUTTON_IMAGE_URL = "expandBar/arrow_collapsed.gif";

    private static final String EXPANDED_BUTTON_IMAGE_URL = "expandBar/arrow_expanded.gif";

    private static final String HEAD_FACET_NAME = "head";

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("UL");
        writeCoreAttributes(htmlWriter);
        writeTitle(htmlWriter, expandBarComponent);
        writeHelp(htmlWriter, expandBarComponent);
        writeClientData(htmlWriter, expandBarComponent);

        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String accessKey = expandBarComponent.getAccessKey(facesContext);
        if (accessKey != null && accessKey.length() > 0) {
            htmlWriter.writeAttribute("v:accessKey", accessKey);
        }

        String effect = expandBarComponent.getCollapseEffect(facesContext);
        if (effect != null) {
            htmlWriter.writeAttribute("v:effect", effect);
        }

        String groupName = expandBarComponent.getGroupName(facesContext);
        if (groupName != null && groupName.length() > 0) {
            htmlWriter.writeAttribute("v:groupName", groupName);
        }

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);
        if (collapsed) {
            htmlWriter.writeAttribute("v:collapsed", "true");
        }

        String className = getStyleClassName(componentContext);

        htmlWriter.startElement("LI");
        htmlWriter.writeAttribute("class", className + "_head");

        writeHeader(htmlWriter, className);

        htmlWriter.endElement("LI").writeln();

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(htmlWriter);

        boolean asyncRenderEnable = htmlRenderContext.isAsyncRenderEnable();
        if (asyncRenderEnable == false) {
            // ??? return;
        }

        boolean asyncRender = false;

        if (collapsed) {
            if (asyncRenderEnable) {
                if (expandBarComponent.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    htmlWriter.writeAttribute("v:asyncRender", "true");

                    getHtmlRenderContext(htmlWriter)
                            .pushInteractiveRenderComponent(htmlWriter);

                    asyncRender = true;
                }
            }
        }
        setAsyncRenderer(htmlWriter, expandBarComponent, asyncRender);

        htmlWriter.startElement("LI");
        htmlWriter.writeAttribute("class", className + "_tbody");
        if (collapsed) {
            htmlWriter.writeAttribute("style", "display:none");
        }

        htmlWriter.startElement("DIV");
        htmlWriter.writeAttribute("class", className + "_body");
        if (collapsed) {
            // On masque le LI le Javascript masquera le DIV !
        }

        htmlWriter.enableJavaScript();
    }

    private void writeHeader(IHtmlWriter htmlWriter, String className)
            throws WriterException {

        IComponentRenderContext componentContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentContext.getFacesContext();

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentContext
                .getComponent();

        String buttonId = componentContext.getComponentId() + "__button";

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("type", "IMAGE");
        htmlWriter.writeAttribute("class", className + "_button");
        htmlWriter.writeAttribute("id", buttonId);

        boolean collapsed = expandBarComponent.isCollapsed(facesContext);

        htmlWriter.writeAttribute("src", getButtonImage(htmlWriter, collapsed));

        writeAccessKey(htmlWriter, expandBarComponent);
        writeTabIndex(htmlWriter, expandBarComponent);

        htmlWriter.endElement("INPUT");

        htmlWriter.startElement("LABEL");
        htmlWriter.writeAttribute("for", buttonId);
        htmlWriter.writeAttribute("class", className + "_label");

        String text = expandBarComponent.getText(facesContext);
        if (text != null && text.length() > 0) {
            HtmlTools.writeSpanAccessKey(htmlWriter, expandBarComponent, text,
                    false);
        }

        htmlWriter.endElement("LABEL");

        UIComponent component = expandBarComponent.getFacet(HEAD_FACET_NAME);
        if (component != null) {

            htmlWriter.startElement("SPAN");
            htmlWriter.writeAttribute("class", className + "_hfacet");

            htmlWriter.flush();

            ComponentTools.encodeRecursive(facesContext, component);

            htmlWriter.endElement("SPAN");
        }
    }

    protected String getButtonImage(IHtmlWriter htmlWriter, boolean collapsed) {

        String imageURL;
        if (collapsed) {
            imageURL = COLLAPSED_BUTTON_IMAGE_URL;

        } else {
            imageURL = EXPANDED_BUTTON_IMAGE_URL;
        }

        return getHtmlRenderContext(htmlWriter).getHtmlExternalContext()
                .getStyleSheetURI(imageURL);
    }

    protected void encodeEnd(IComponentWriter writer) throws WriterException {
        // IComponentRenderContext componentContext =
        // writer.getComponentRenderContext();

        // ExpandBarComponent expandBarComponent = (ExpandBarComponent)
        // componentContext.getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.endElement("DIV");
        htmlWriter.endElement("LI");
        htmlWriter.endElement("UL");

        super.encodeEnd(htmlWriter);
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

        ExpandBarComponent expandBarComponent = (ExpandBarComponent) component;

        String text = componentData.getStringProperty("text");
        if (text != null) {
            expandBarComponent.setText(text);
        }

        Boolean collapsed = componentData.getBooleanProperty("collapsed");
        if (collapsed != null) {
            expandBarComponent.setCollapsed(collapsed.booleanValue());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.EXPAND_BAR;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        ExpandBarComponent expandBarComponent = (ExpandBarComponent) componentRenderContext
                .getComponent();
        if (expandBarComponent.getCollapseEffect(componentRenderContext
                .getFacesContext()) != null) {

            IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                    htmlWriter).getJavaScriptRenderContext();

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.EXPAND_BAR, "effect");
        }
    }
}