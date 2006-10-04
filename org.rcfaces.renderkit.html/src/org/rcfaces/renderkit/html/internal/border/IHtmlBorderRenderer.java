/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:43  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.1  2006/01/03 15:21:40  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.2  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.renderkit.html.internal.border;

import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlBorderRenderer {

    void initialize(IHtmlWriter writer, String width, String height,
            int horizontalSpan, int verticalSpan, boolean disabled,
            boolean selected) throws WriterException;

    IHtmlWriter startComposite(IHtmlWriter writer) throws WriterException;

    IHtmlWriter startRow(IHtmlWriter writer) throws WriterException;

    IHtmlWriter endRow(IHtmlWriter writer) throws WriterException;

    IHtmlWriter endComposite(IHtmlWriter writer) throws WriterException;

    IHtmlWriter startChild(IHtmlWriter writer, String classSuffix)
            throws WriterException;

    IHtmlWriter startChild(IHtmlWriter writer, String classSuffix,
            String halign, String valign, String width, String height,
            int colspan, int rowspan) throws WriterException;

    IHtmlWriter endChild(IHtmlWriter writer) throws WriterException;

    void writeComboImage(IHtmlWriter writer, String componentClassName) throws WriterException;
}
