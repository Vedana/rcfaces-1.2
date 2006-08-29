/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.18  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.17  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.16  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.15  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.14  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.13  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.12  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.11  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.10  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.9  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.7  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/27 09:57:21  oeuillot
 * *** empty log message ***
 *
 * Revision 1.5  2004/08/24 13:39:54  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.ImageComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.provider.IURLRewritingProvider;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageRenderer extends AbstractCssRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();
        ImageComponent image = (ImageComponent) componentRenderContext
                .getComponent();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("IMG");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        String src = image
                .getImageURL(componentRenderContext.getFacesContext());
        if (src != null) {
            src = rewriteURL(componentRenderContext,
                    IURLRewritingProvider.IMAGE_URL_TYPE, "imageURL", src, null);

            if (src != null) {
                htmlWriter.writeAttribute("src", src);
            }
        }

        htmlWriter.endElement("IMG");

        super.encodeEnd(htmlWriter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.IMAGE;
    }
}