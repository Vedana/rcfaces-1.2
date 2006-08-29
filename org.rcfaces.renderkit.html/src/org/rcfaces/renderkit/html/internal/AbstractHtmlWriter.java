package org.rcfaces.renderkit.html.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.rcfaces.core.internal.renderkit.AbstractRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.ISgmlWriter;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractHtmlWriter extends
        AbstractHtmlComponentlRenderContext implements IHtmlWriter {
    private static final String REVISION = "$Revision$";

    private static final char LF = '\n';

    private final ResponseWriter responseWriter;

    protected final AbstractRenderContext renderContext;

    public AbstractHtmlWriter(FacesContext facesContext,
            AbstractRenderContext renderContext) {
        super(facesContext, renderContext.getComponent(), renderContext
                .getComponentId());

        this.renderContext = renderContext;

        this.responseWriter = facesContext.getResponseWriter();
    }

    public final IComponentRenderContext getComponentRenderContext() {
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

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#write(java.lang.String)
     */
    public ISgmlWriter write(String s) throws WriterException {
        try {
            responseWriter.write(s);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    public ISgmlWriter write(char buf[], int offset, int length)
            throws WriterException {
        try {
            responseWriter.write(buf, offset, length);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#encodeAndWrite(java.lang.String)
     */
    public ISgmlWriter writeText(String s) throws WriterException {
        try {
            responseWriter.writeText(s, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#write(char)
     */
    public ISgmlWriter write(char c) throws WriterException {
        try {
            responseWriter.write(c);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#write(int)
     */
    public ISgmlWriter write(int value) throws WriterException {
        return write(String.valueOf(value));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#write(int)
     */
    public ISgmlWriter writeln() throws WriterException {
        return write(LF);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#writeAttribute(java.lang.String,
     *      java.lang.String)
     */
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
        try {
            responseWriter.writeAttribute(name, Boolean.TRUE, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#writeComment(java.lang.String)
     */
    public ISgmlWriter writeComment(String comment) throws WriterException {
        try {
            responseWriter.writeComment(comment);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#writeStartElement(java.lang.String,
     *      javax.faces.component.UIComponent)
     */
    public ISgmlWriter startElement(String name, UIComponent component)
            throws WriterException {
        try {
            responseWriter.startElement(name, component);

        } catch (IOException e) {
            throw new WriterException(null, e, component);
        }

        return this;
    }

    public ISgmlWriter startElement(String name) throws WriterException {
        return startElement(name, this.getComponent());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#writeEndElement(java.lang.String)
     */
    public ISgmlWriter endElement(String name) throws WriterException {
        try {
            responseWriter.endElement(name);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#writeURIAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public ISgmlWriter writeURIAttribute(String name, Object value)
            throws WriterException {
        try {
            responseWriter.writeURIAttribute(name, value, null);

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.IWriter#flushElement()
     */
    public IComponentWriter flush() throws WriterException {
        try {
            responseWriter.flush();

        } catch (IOException e) {
            throw new WriterException(null, e, renderContext.getComponent());
        }

        return this;
    }
}