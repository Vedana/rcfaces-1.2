/*
 * $Id$
 *
 * $Log$
 * Revision 1.4  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.8  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.7  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.6  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.5  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.3  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.2  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.14  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.SpinnerComponent;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SpinnerRenderer extends TextEntryRenderer {
    private static final String REVISION = "$Revision$";

    protected boolean isNameEqualsId() {
        return false;
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        SpinnerComponent spinner = (SpinnerComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        htmlWriter.startElement("TABLE");
        htmlWriter.writeAttribute("cellpadding", "0");
        htmlWriter.writeAttribute("cellpspacing", "0");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeValidatorAttributes(htmlWriter);
        writeSpinnerAttributes(htmlWriter);

        htmlWriter.startElement("COL");
        htmlWriter.writeAttribute("width", "1*");
        htmlWriter.endElement("COL");

        htmlWriter.startElement("COL");
        htmlWriter.writeAttribute("width", "16");
        htmlWriter.endElement("COL");

        htmlWriter.startElement("TR");
        htmlWriter.writeAttribute("valign", "middle");

        htmlWriter.startElement("TD");

        String className = getStyleClassName(htmlWriter
                .getComponentRenderContext());

        htmlWriter.startElement("INPUT");
        htmlWriter.writeAttribute("class", className + "_input");
        writeInputAttributes(htmlWriter);
        writeTextEntryAttributes(htmlWriter);
        writeValueAttributes(htmlWriter);
        htmlWriter.endElement("INPUT");

        htmlWriter.endElement("TD");

        htmlWriter.startElement("TD");
        htmlWriter.writeAttribute("width", "16");

        String buttonSuffix = "";
        if (spinner.isDisabled(htmlWriter.getComponentRenderContext()
                .getFacesContext())) {
            buttonSuffix = "_disabled";
        }

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(htmlWriter);
        String blankImageURL = htmlRenderContext.getHtmlExternalContext()
                .getStyleSheetURI(BLANK_IMAGE_URL);

        htmlWriter.startElement("IMG");
        htmlWriter.writeAttribute("class", className + "_up" + buttonSuffix);
        htmlWriter.writeAttribute("src", blankImageURL);
        htmlWriter.writeAttribute("width", "16");
        htmlWriter.writeAttribute("height", "10");
        htmlWriter.endElement("IMG");

        htmlWriter.startElement("IMG");
        htmlWriter.writeAttribute("class", className + "_down" + buttonSuffix);
        htmlWriter.writeAttribute("src", blankImageURL);
        htmlWriter.writeAttribute("width", "16");
        htmlWriter.writeAttribute("height", "10");
        htmlWriter.endElement("IMG");

        htmlWriter.endElement("TD");

        htmlWriter.endElement("TR");

        htmlWriter.endElement("TABLE");

        htmlWriter.enableJavaScript();

    }

    protected void writeSpinnerAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        SpinnerComponent spinnerComponent = (SpinnerComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        if (spinnerComponent.isMinimumSetted()) {
            double minimum = spinnerComponent.getMinimum(facesContext);

            htmlWriter.writeAttribute("v:minimum", String.valueOf(minimum));
        }

        if (spinnerComponent.isMaximumSetted()) {
            double maximum = spinnerComponent.getMaximum(facesContext);

            htmlWriter.writeAttribute("v:maximum", String.valueOf(maximum));
        }

        if (spinnerComponent.isStepSetted()) {
            double step = spinnerComponent.getStep(facesContext);

            htmlWriter.writeAttribute("v:step", String.valueOf(step));
        }

        if (spinnerComponent.isCycleValue(facesContext)) {
            htmlWriter.writeAttribute("v:cycle", "true");

        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.SPINNER;
    }
}