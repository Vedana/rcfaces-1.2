/*
 * $Id$
 * 
 * $Log$
 * Revision 1.5  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.2  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlWriter extends ISgmlWriter {
    void enableJavaScript();

    boolean isJavaScriptEnabled();

    IHtmlComponentRenderContext getHtmlComponentRenderContext();

    IHtmlWriter writeType(String type) throws WriterException;

    IHtmlWriter writeMaxLength(int maxLength) throws WriterException;

    IHtmlWriter writeSize(int size) throws WriterException;

    IHtmlWriter writeName(String name) throws WriterException;

    IHtmlWriter writeId(String id) throws WriterException;

    IHtmlWriter writeClass(String className) throws WriterException;

    IHtmlWriter writeDisabled() throws WriterException;

    IHtmlWriter writeReadOnly() throws WriterException;

    IHtmlWriter writeValue(String value) throws WriterException;

    IHtmlWriter writeAccessKey(String accessKey) throws WriterException;

    IHtmlWriter writeTabIndex(int tabIndex) throws WriterException;

    IHtmlWriter writeWidth(int width) throws WriterException;

    IHtmlWriter writeWidth(String width) throws WriterException;

    IHtmlWriter writeHeight(int height) throws WriterException;

    IHtmlWriter writeFor(String id) throws WriterException;

    ICssWriter writeStyle();

    ICssWriter writeStyle(int size);

    IHtmlWriter writeTitle(String title) throws WriterException;

    IHtmlWriter writeChecked() throws WriterException;

    IHtmlWriter writeCellSpacing(int cellSpacing) throws WriterException;

    IHtmlWriter writeCellPadding(int cellPadding) throws WriterException;

    IHtmlWriter writeAlign(String align) throws WriterException;

    IHtmlWriter writeSrc(String url) throws WriterException;

    IHtmlWriter writeMultiple() throws WriterException;

    IHtmlWriter writeVAlign(String valign) throws WriterException;

    IHtmlWriter writeHeight(String height) throws WriterException;

    IHtmlWriter writeColSpan(int colspan) throws WriterException;

    IHtmlWriter writeRowSpan(int rowspan) throws WriterException;

    IHtmlWriter writeCols(int col) throws WriterException;

    IHtmlWriter writeRows(int row) throws WriterException;

    IHtmlWriter writeLabel(String text) throws WriterException;

    IHtmlWriter writeSelected() throws WriterException;
}
