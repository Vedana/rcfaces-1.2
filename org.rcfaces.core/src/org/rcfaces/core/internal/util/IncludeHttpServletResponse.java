package org.rcfaces.core.internal.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.ExtendedHttpServlet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class IncludeHttpServletResponse extends HttpServletResponseWrapper {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(IncludeHttpServletResponse.class);

    private final ServletOutputStream servletOutputStream;

    private final PrintWriter writer;

    private int status = HttpServletResponse.SC_OK;

    private int contentLength;

    private String contentType;

    private long lastModified;

    private IncludeHttpServletResponse(HttpServletResponse servletResponse,
            final OutputStream outputStream, Writer writer) {
        super(servletResponse);

        this.writer = (writer != null) ? new PrintWriter(writer) : null;

        servletOutputStream = new ServletOutputStream() {
            private static final String REVISION = "$Revision$";

            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            public void flush() throws IOException {
                outputStream.flush();
            }

            public void write(byte[] b, int off, int len) throws IOException {
                outputStream.write(b, off, len);
            }

            public void write(byte[] b) throws IOException {
                outputStream.write(b);
            }

        };
    }

    public static IncludeHttpServletResponse create(
            HttpServletResponse servletResponse, OutputStream output,
            String defaultCharset) {

        String charset = servletResponse.getCharacterEncoding();
        if (charset == null) {
            charset = defaultCharset;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Use charset '" + charset + "' for writer.");
        }

        Writer writer = null;
        try {
            writer = new OutputStreamWriter(output, charset);

        } catch (UnsupportedEncodingException e) {
            LOG.error("Unsupported encoding '" + charset + "'.", e);
        }

        return new IncludeHttpServletResponse(servletResponse, output, writer);
    }

    public int getContentLength() {
        return contentLength;
    }

    public long getLastModified() {
        return lastModified;
    }

    public String getContentType() {
        return contentType;
    }

    public int getStatus() {
        return status;
    }

    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            throw new IOException("No writer supported !");
        }
        return writer;
    }

    public void addCookie(Cookie arg0) {
    }

    public void addDateHeader(String name, long date) {
        setDateHeader(name, date);
    }

    public void addHeader(String arg0, String arg1) {
        setHeader(arg0, arg1);
    }

    public void addIntHeader(String arg0, int arg1) {
        setIntHeader(arg0, arg1);
    }

    public void sendRedirect(String arg0) {
    }

    public void setDateHeader(String name, long date) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Inclusion set date header '" + name + "' to '" + date
                    + "'.");
        }

        if (this.lastModified == 0
                && ExtendedHttpServlet.HTTP_LAST_MODIFIED.equals(name)) {

            this.lastModified = date;
        }
    }

    public void setHeader(String name, String value) {
        if (LOG.isTraceEnabled()) {
            LOG
                    .trace("Inclusion set header '" + name + "' to '" + value
                            + "'.");
        }

        if (lastModified == 0
                && ExtendedHttpServlet.HTTP_LAST_MODIFIED.equals(name)) {

            try {
                Date d = ExtendedHttpServlet.parseHttpDate(value);
                LOG.trace("Inclusion set Last-Modified property to " + d);

                if (d != null) {
                    lastModified = d.getTime();
                }

            } catch (ParseException ex) {
                LOG.error("Can not parse http date '" + value + "'.", ex);
            }
        }
    }

    public void setIntHeader(String arg0, int arg1) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Inclusion set int header '" + arg0 + "' to '" + arg1
                    + "'.");
        }
    }

    public void flushBuffer() {
    }

    public void reset() {
    }

    public void resetBuffer() {
    }

    public void setBufferSize(int arg0) {
    }

    public void setCharacterEncoding(String arg0) {
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setLocale(Locale arg0) {
    }

    public void sendError(int status) throws IOException {
        super.sendError(status);

        this.status = status;
    }

    public void setStatus(int status) {
        super.setStatus(status);

        this.status = status;
    }

    public void sendError(int status, String arg1) throws IOException {
        super.sendError(status, arg1);

        this.status = status;
    }

    public void setStatus(int status, String arg1) {
        super.setStatus(status, arg1);

        this.status = status;
    }
}