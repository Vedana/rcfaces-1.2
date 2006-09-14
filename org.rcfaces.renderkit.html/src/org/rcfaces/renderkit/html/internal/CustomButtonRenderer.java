/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.11  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.10  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.9  2006/05/04 13:40:13  oeuillot
 * Ajout de f_findComponent cot� client
 *
 * Revision 1.8  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.7  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.6  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.5  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.4  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.3  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.rcfaces.core.component.CustomButtonComponent;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.ValuesTools;
import org.rcfaces.renderkit.html.internal.border.AbstractHtmlBorderRenderer;
import org.rcfaces.renderkit.html.internal.border.IHtmlBorderRenderer;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CustomButtonRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    private static final String BORDER_RENDERER = "camelia.customButton.borderRenderer";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.CUSTOM_BUTTON;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        CustomButtonComponent component = (CustomButtonComponent) componentRenderContext
                .getComponent();

        IHtmlBorderRenderer borderRenderer = null;
        String borderType = null;
        if (component.isBorder(facesContext)) {
            borderType = component.getBorderType(facesContext);

            IBorderRenderersRegistry borderRendererRegistry = RcfacesContext
                    .getInstance(facesContext).getBorderRenderersRegistry();

            borderRenderer = (IHtmlBorderRenderer) borderRendererRegistry
                    .getBorderRenderer(facesContext,
                            RenderKitFactory.HTML_BASIC_RENDER_KIT, component
                                    .getFamily(), component.getRendererType(),
                            borderType);
        }

        String className = getStyleClassName(componentRenderContext, component);

        boolean disabled = component.isDisabled(facesContext);

        String width = component.getWidth(facesContext);
        String height = component.getHeight(facesContext);

        boolean selected = false;
        if (component instanceof ISelectedCapability) {
            ISelectedCapability selectedCapability = (ISelectedCapability) component;

            selected = selectedCapability.isSelected();
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");

        if (borderRenderer != null) {
            htmlWriter.writeAttribute("v:borderType", borderType);
        }

        String classSuffix = null;
        if (disabled) {
            classSuffix = "_disabled";
        }

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter, classSuffix);

        if (classSuffix != null) {
            htmlWriter.writeAttribute("v:className", className);
        }

        encodeAttributes(htmlWriter, component);

        if (borderRenderer != null) {
            borderRenderer.initialize(htmlWriter, width, height, 1, 1,
                    disabled, selected);

            borderRenderer.startComposite(htmlWriter);

            componentRenderContext
                    .setAttribute(BORDER_RENDERER, borderRenderer);
        }
        /*
         * Le javascript s'occupe de ca ! if (button == false && imageJavascript ==
         * false) { htmlWriter.writeAttribute("href", "javascript:void(0)"); }
         */

        htmlWriter.enableJavaScript();
    }

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext facesContext, UIComponent component)
            throws IOException {

        IRenderContext renderContext = getRenderContext(facesContext);

        IHtmlWriter htmlWriter = (IHtmlWriter) renderContext
                .getComponentWriter(facesContext);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        IHtmlBorderRenderer borderRenderer = (IHtmlBorderRenderer) componentRenderContext
                .getAttribute(BORDER_RENDERER);

        String className = getStyleClassName(componentRenderContext, component);

        if (borderRenderer == null) {
            htmlWriter.startElement("A");

            htmlWriter.writeAttribute("class", className + "_link");

            encodeChildren(htmlWriter);

            htmlWriter.endElement("A");

            return;
        }

        borderRenderer.startRow(htmlWriter);

        try {
            borderRenderer.startChild(htmlWriter,
                    AbstractHtmlBorderRenderer.TD_TEXT);
            try {
                htmlWriter.startElement("A");

                htmlWriter.writeAttribute("class", className + "_link");

                encodeChildren(htmlWriter);

                htmlWriter.endElement("A");

            } finally {
                borderRenderer.endChild(htmlWriter);
            }

        } finally {
            borderRenderer.endRow(htmlWriter);
        }

    }

    protected void encodeChildren(IComponentWriter writer)
            throws WriterException {

        writer.flush();

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        ComponentTools.encodeChildrenRecursive(componentRenderContext
                .getFacesContext(), componentRenderContext.getComponent());
    }

    public void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        IHtmlBorderRenderer borderRenderer = (IHtmlBorderRenderer) componentRenderContext
                .getAttribute(BORDER_RENDERER);

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        if (borderRenderer != null) {
            borderRenderer.endComposite(htmlWriter);
        }

        htmlWriter.endElement("DIV");

        super.encodeEnd(htmlWriter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#encodeJavaScript(org.rcfaces.core.internal.renderkit.html.IJavaScriptWriter)
     * 
     * protected void encodeJavaScript(IJavaScriptWriter htmlWriter) throws
     * WriterException { super.encodeJavaScript(htmlWriter);
     * 
     * IComponentRenderContext componentContext = htmlWriter
     * .getComponentRenderContext();
     * 
     * FacesContext facesContext = componentContext.getFacesContext(); }
     */

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }

    protected void encodeAttributes(IHtmlWriter htmlWriter,
            CustomButtonComponent component) throws WriterException {
        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        if (component.isDisabled(facesContext)) {
            htmlWriter.writeAttribute("v:disabled", "true");
        }

        if (component.isReadOnly(facesContext)) {
            htmlWriter.writeAttribute("v:readOnly", "true");
        }

        Object value = component.getValue();
        if (value != null) {
            String svalue = ValuesTools.convertValueToString(value, component,
                    facesContext);

            if (svalue != null) {
                htmlWriter.writeAttribute("v:value", svalue);
            }
        }
    }
}