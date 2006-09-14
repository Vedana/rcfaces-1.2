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
 * Revision 1.13  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.12  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.11  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.10  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.9  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.7  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/30 15:14:16  oeuillot
 * *** empty log message ***
 *
 * Revision 1.5  2004/08/30 14:30:47  jmerlin
 * *** empty log message ***
 *
 * Revision 1.4  2004/08/27 09:57:21  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.HelpMessageZoneComponent;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HelpMessageZoneRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HELP_MESSAGE_ZONE;
    }

    public void encodeBegin(IComponentWriter writer) throws WriterException {
        HelpMessageZoneComponent component = (HelpMessageZoneComponent) writer
                .getComponentRenderContext().getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("DIV");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String text = component.getText();
        if (text != null) {
            htmlWriter.writeText(text);
        }

        htmlWriter.endElement("DIV");
        // Done in lazy mode by the HELP package itself
        // see package F_HELP in f_help.js
        // enableJavaScript(htmlWriter);
    }

    /*
     * Pour le client lourd...
     * 
     * protected void encodeJavaScript(IJavaScriptWriter htmlWriter) throws
     * WriterException {
     * 
     * super.encodeJavaScript(htmlWriter); UIComponent component =
     * htmlWriter.getWriter().getComponent(); htmlWriter.write("if (F_HELP)
     * F_HELP.SetHelpMessageZone(");
     * htmlWriter.write(htmlWriter.getComponentVarName()).writeln(");"); }
     */
}