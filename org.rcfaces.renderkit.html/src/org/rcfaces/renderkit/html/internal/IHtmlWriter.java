/*
 * $Id$
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
public interface IHtmlWriter extends ISgmlWriter, IHtmlElements {

    void enableJavaScript();

    IJavaScriptEnableMode getJavaScriptEnableMode();
    
    void addSubFocusableComponent(String subComponentClientId);

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

    IHtmlWriter writeRel(String rel) throws WriterException;

    IHtmlWriter writeHRef(String url) throws WriterException;

    IHtmlWriter writeHttpEquiv(String equiv, String content)
            throws WriterException;

    IHtmlWriter writeCharset(String charset) throws WriterException;

    IHtmlWriter writeRole(String role) throws WriterException;

    IHtmlWriter writeAlt(String altText) throws WriterException;

    IHtmlWriter writeDir(String direction) throws WriterException;

    IHtmlWriter writeBorder(int size) throws WriterException;

    // IHtmlWriter writeAttributeNS(String ns, String name, String value)throws
    // WriterException;
}
