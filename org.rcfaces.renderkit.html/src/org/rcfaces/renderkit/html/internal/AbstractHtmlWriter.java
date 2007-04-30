/**
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.io.IOException;
import java.util.Stack;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import org.rcfaces.core.internal.renderkit.AbstractRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractHtmlWriter extends
        AbstractHtmlComponentlRenderContext implements IHtmlWriter {
    private static final String REVISION = "$Revision$";

    private static final char LF = '\n';

    private static final String TAG_STACK_PROPERTY = "org.rcfaces.core.internal.writer.TAG_STACK";

    private static final String NONE_WAI_ROLE_NS = "none";

    private final ResponseWriter responseWriter;

    protected final AbstractRenderContext renderContext;

    private ICssWriter cssWriter;

    public AbstractHtmlWriter(AbstractRenderContext renderContext) {
        super(renderContext.getFacesContext(), renderContext.getComponent(),
                renderContext.getComponentClientId());

        this.renderContext = renderContext;

        this.responseWriter = getFacesContext().getResponseWriter();
    }

    public final IComponentRenderContext getComponentRenderContext() {
        return this;
    }

    public final IHtmlComponentRenderContext getHtmlComponentRenderContext() {
        return this;
    }

    public final Object getAttribute(String key) {
        return renderContext.getComponentContextAttribute(key);
    }

    public final Object setAttribute(String key, Object value) {
        return renderContext.setComponentContextAttribute(key, value);
    }

    public boolean containsAttribute(String key) {
        return renderContext.containsComponentContextAttribute(key);
    }

    public final IRenderContext getRenderContext() {
        return renderContext;
    }

    public ISgmlWriter write(String s) throws WriterException {
        closeCssWriter();

        try {
            responseWriter.write(s);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter write(char buf[], int offset, int length)
            throws WriterException {
        closeCssWriter();

        try {
            responseWriter.write(buf, offset, length);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    protected void closeCssWriter() throws WriterException {
        if (cssWriter == null) {
            return;
        }

        cssWriter.done();

        cssWriter = null;
    }

    public ISgmlWriter writeText(String s) throws WriterException {
        closeCssWriter();

        try {
            responseWriter.writeText(s, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter write(char c) throws WriterException {
        closeCssWriter();

        try {
            responseWriter.write(c);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter write(int value) throws WriterException {
        return write(String.valueOf(value));
    }

    public ISgmlWriter writeln() throws WriterException {
        return write(LF);
    }

    public ISgmlWriter writeAttribute(String name, String value)
            throws WriterException {

        try {
            responseWriter.writeAttribute(name, value, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter writeAttribute(String name, long value)
            throws WriterException {
        return writeAttribute(name, String.valueOf(value));
    }

    public ISgmlWriter writeAttribute(String name) throws WriterException {
        closeCssWriter();

        try {
            responseWriter.writeAttribute(name, Boolean.TRUE, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter writeComment(String comment) throws WriterException {
        closeCssWriter();

        try {
            responseWriter.writeComment(comment);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter startElement(String name, UIComponent component)
            throws WriterException {
        closeCssWriter();

        try {
            responseWriter.startElement(name, component);

        } catch (IOException e) {
            throw new WriterException(null, e, component);
        }

        return this;
    }

    public ISgmlWriter startElement(String name) throws WriterException {
        if (Constants.VERIFY_TAG_STACK) {
            Stack tagStack = (Stack) getFacesContext().getExternalContext()
                    .getRequestMap().get(TAG_STACK_PROPERTY);
            if (tagStack == null) {
                tagStack = new Stack();

                getFacesContext().getExternalContext().getRequestMap().put(
                        TAG_STACK_PROPERTY, tagStack);
            }

            tagStack.push(name);
        }

        return startElement(name, this.getComponent());
    }

    public ISgmlWriter endElement(String name) throws WriterException {
        closeCssWriter();

        if (Constants.VERIFY_TAG_STACK) {
            Stack tagStack = (Stack) getFacesContext().getExternalContext()
                    .getRequestMap().get(TAG_STACK_PROPERTY);
            if (tagStack == null || tagStack.isEmpty()) {
                throw new IllegalStateException("Tag stack is empty !");
            }

            String current = (String) tagStack.pop();

            if (current.equals(name) == false) {
                throw new IllegalStateException("Invalid close of tag '" + name
                        + "' current='" + current + "' !");
            }
        }

        try {
            responseWriter.endElement(name);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter writeURIAttribute(String name, Object value)
            throws WriterException {
        closeCssWriter();

        try {
            responseWriter.writeURIAttribute(name, value, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter endComponent() throws WriterException {
        closeCssWriter();

        try {
            responseWriter.flush();

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public final IHtmlWriter writeMaxLength(int maxLength)
            throws WriterException {
        writeAttribute("maxlength", maxLength);

        return this;
    }

    public final IHtmlWriter writeSize(int size) throws WriterException {
        writeAttribute("size", size);

        return this;
    }

    public final IHtmlWriter writeType(String type) throws WriterException {
        writeAttribute("type", type);

        return this;
    }

    public final IHtmlWriter writeId(String id) throws WriterException {
        writeAttribute("id", id);

        return this;
    }

    public final IHtmlWriter writeName(String name) throws WriterException {
        writeAttribute("name", name);

        return this;
    }

    public final IHtmlWriter writeClass(String className)
            throws WriterException {
        writeAttribute("class", className);

        return this;
    }

    public final IHtmlWriter writeDisabled() throws WriterException {
        writeAttribute("DISABLED");

        return this;
    }

    public final IHtmlWriter writeReadOnly() throws WriterException {
        writeAttribute("READONLY");

        return this;
    }

    public final IHtmlWriter writeValue(String value) throws WriterException {
        writeAttribute("value", value);

        return this;
    }

    public final IHtmlWriter writeAccessKey(String accessKey)
            throws WriterException {
        writeAttribute("accessKey", accessKey);

        return this;
    }

    public final IHtmlWriter writeTabIndex(int tabIndex) throws WriterException {
        writeAttribute("tabIndex", tabIndex);

        return this;
    }

    public final IHtmlWriter writeChecked() throws WriterException {
        writeAttribute("CHECKED");

        return this;
    }

    public final IHtmlWriter writeFor(String id) throws WriterException {
        writeAttribute("for", id);

        return this;
    }

    public final IHtmlWriter writeHeight(int height) throws WriterException {
        writeAttribute("height", height);

        return this;
    }

    public IHtmlWriter writeHeight(String height) throws WriterException {
        writeAttribute("height", height);

        return this;
    }

    public final IHtmlWriter writeStyle(String style) throws WriterException {
        writeAttribute("style", style);

        return this;
    }

    public final IHtmlWriter writeTitle(String title) throws WriterException {
        writeAttribute("title", title);

        return this;
    }

    public final IHtmlWriter writeWidth(int width) throws WriterException {
        writeAttribute("width", width);

        return this;
    }

    public final IHtmlWriter writeWidth(String width) throws WriterException {
        writeAttribute("width", width);

        return this;
    }

    public IHtmlWriter writeAlign(String align) throws WriterException {
        writeAttribute("align", align);

        return this;
    }

    public IHtmlWriter writeVAlign(String valign) throws WriterException {
        writeAttribute("valign", valign);

        return this;
    }

    public IHtmlWriter writeCellPadding(int cellPadding) throws WriterException {
        writeAttribute("cellpadding", cellPadding);

        return this;
    }

    public IHtmlWriter writeCellSpacing(int cellSpacing) throws WriterException {
        writeAttribute("cellspacing", cellSpacing);

        return this;
    }

    public IHtmlWriter writeSrc(String src) throws WriterException {
        writeAttribute("src", src);

        return this;
    }

    public IHtmlWriter writeAlt(String alt) throws WriterException {
        writeAttribute("alt", alt);

        return this;
    }

    public IHtmlWriter writeMultiple() throws WriterException {
        writeAttribute("multiple");

        return this;
    }

    public ICssWriter writeStyle() {
        if (cssWriter != null) {
            return cssWriter;
        }
        cssWriter = new CssWriter(this);

        return cssWriter;
    }

    public ICssWriter writeStyle(int size) {
        if (cssWriter != null) {
            return cssWriter;
        }

        cssWriter = new CssWriter(this, size);

        return cssWriter;
    }

    public IHtmlWriter writeColSpan(int colspan) throws WriterException {
        writeAttribute("colspan", colspan);

        return this;
    }

    public IHtmlWriter writeRowSpan(int rowspan) throws WriterException {
        writeAttribute("rowspan", rowspan);

        return this;
    }

    public IHtmlWriter writeCols(int cols) throws WriterException {
        writeAttribute("cols", cols);

        return this;
    }

    public IHtmlWriter writeRows(int rows) throws WriterException {
        writeAttribute("rows", rows);

        return this;
    }

    public IHtmlWriter writeLabel(String label) throws WriterException {
        writeAttribute("label", label);

        return this;
    }

    public IHtmlWriter writeSelected() throws WriterException {
        writeAttribute("SELECTED");

        return this;
    }

    public IHtmlWriter writeHRef(String url) throws WriterException {
        writeAttribute("href", url);

        return this;
    }

    public IHtmlWriter writeRel(String rel) throws WriterException {
        writeAttribute("rel", rel);

        return this;
    }

    public IHtmlWriter writeCharset(String charset) throws WriterException {
        writeAttribute("charset", charset);

        return this;
    }

    public IHtmlWriter writeHttpEquiv(String equiv, String content)
            throws WriterException {
        writeAttribute("http-equiv", equiv);

        writeAttribute("content", content);

        return this;
    }

    public IHtmlWriter writeRole(String role) throws WriterException {
        if (Constants.ACCESSIBILITY_ROLE_SUPPORT == false) {
            return this;
        }

        String waiRoleNS = getHtmlRenderContext().getWaiRolesNS();

        if (NONE_WAI_ROLE_NS.equals(waiRoleNS)) {
            return this;
        }

        if (waiRoleNS != null && waiRoleNS.length() > 0) {
            writeAttribute(waiRoleNS + ":role", role);
            return this;
        }

        writeAttribute("role", role);

        return this;
    }
}