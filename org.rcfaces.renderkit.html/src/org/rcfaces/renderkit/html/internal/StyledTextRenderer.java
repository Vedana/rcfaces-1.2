/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.10  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.9  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.8  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.7  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.6  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.5  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2004/08/12 14:21:06  oeuillot
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/06 15:55:31  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/06 15:44:44  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class StyledTextRenderer extends TextRenderer {
    private static final String REVISION = "$Revision$";

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.TextRenderer#writeText(org.rcfaces.core.internal.renderkit.IWriter,
     *      org.rcfaces.core.component.TextComponent)
     */
    protected boolean writeText(IHtmlWriter writer, TextComponent textComponent)
            throws WriterException {

        String text = textComponent.getText();
        if (text == null || text.length() < 1) {
            return false;
        }

        writer.write(text);

        return false;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.STYLED_TEXT;
    }
}