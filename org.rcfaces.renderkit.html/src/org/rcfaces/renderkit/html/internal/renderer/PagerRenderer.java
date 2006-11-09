/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.5  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.4  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.3  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.7  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.6  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.5  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.4  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.PagerComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.AbstractCssRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PagerRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.PAGER;
    }

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        super.encodeBegin(writer);

        FacesContext facesContext = writer.getComponentRenderContext()
                .getFacesContext();

        PagerComponent pagerComponent = (PagerComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        String componentTag = "SPAN";
        if (pagerComponent.getWidth(facesContext) != null
                || pagerComponent.getHeight(facesContext) != null) {
            componentTag = "DIV";
        }

        htmlWriter.startElement(componentTag);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String forValue = pagerComponent.getFor(facesContext);
        if (forValue == null) {
            throw new WriterException("'for' attribute must be specified !",
                    null, pagerComponent);
        }

        htmlWriter.writeAttribute("v:for", forValue);

        String message = pagerComponent.getMessage(facesContext);
        if (message != null) {
            htmlWriter.writeAttribute("v:message", message);

            String zeroResultMessage = pagerComponent
                    .getZeroResultMessage(facesContext);
            if (zeroResultMessage != null) {
                htmlWriter.writeAttribute("v:zeroResultMessage",
                        zeroResultMessage);
            }

            String oneResultMessage = pagerComponent
                    .getOneResultMessage(facesContext);
            if (oneResultMessage != null) {
                htmlWriter.writeAttribute("v:oneResultMessage",
                        oneResultMessage);
            }

            String manyResultsMessage = pagerComponent
                    .getManyResultsMessage(facesContext);
            if (manyResultsMessage != null) {
                htmlWriter.writeAttribute("v:manyResultMessage",
                        manyResultsMessage);
            }
        }

        String noPagedMessage = pagerComponent.getNoPagedMessage(facesContext);
        if (noPagedMessage != null) {
            htmlWriter.writeAttribute("v:noPagedMessage", noPagedMessage);
        }

        htmlWriter.endElement(componentTag);

        htmlWriter.enableJavaScript();
    }
}