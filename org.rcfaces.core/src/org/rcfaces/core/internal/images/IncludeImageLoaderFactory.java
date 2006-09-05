/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 * Revision 1.1  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 */
package org.rcfaces.core.internal.images;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.webapp.ParametredHttpServlet;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */

public class IncludeImageLoaderFactory extends AbstractImageLoaderFactory {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(IncludeImageLoaderFactory.class);

    public String getName() {
        return "Load image by servlet inclusion.";
    }

    public IImageLoader loadImage(ServletContext context,
            HttpServletRequest request, HttpServletResponse response, String uri) {
        return new IncludeImageLoader(context, request, response, uri);
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class IncludeImageLoader implements IImageLoader {
        private static final String REVISION = "$Revision$";

        private static final String TEMP_FILE_PREFIX = "include-image.";

        private static final String TEMP_FILE_SUFFIX = ".tmp";

        private static final int BUFFER_DEFAULT_SIZE = 0;

        private static final String DEFAULT_CHARSET = "UTF-8";

        private static final long MEMORY_BUFFER_MAX_SIZE = 4096;

        private final ServletContext servletContext;

        private final HttpServletRequest servletRequest;

        private final HttpServletResponse servletResponse;

        private final String uri;

        private int contentLength;

        private long lastModified;

        private String contentType;

        private boolean errored;

        private boolean bufferInitialized;

        private File bufferFile;

        private byte bufferArray[];

        public IncludeImageLoader(ServletContext servletContext,
                HttpServletRequest servletRequest,
                HttpServletResponse servletResponse, String uri) {
            this.servletContext = servletContext;
            this.servletRequest = servletRequest;
            this.servletResponse = servletResponse;
            this.uri = uri;
        }

        public int getContentLength() {
            if (isErrored()) {
                return -1;
            }
            return contentLength;
        }

        public long getLastModified() {
            if (isErrored()) {
                return -1;
            }
            return lastModified;
        }

        public String getContentType() {
            if (isErrored()) {
                return null;
            }
            return contentType;
        }

        public boolean isErrored() {
            if (errored == false && bufferInitialized == false) {
                getContent();
            }
            return errored;
        }

        public InputStream openStream() {
            if (isErrored()) {
                return null;
            }

            if (bufferArray != null) {
                return new ByteArrayInputStream(bufferArray);
            }

            if (bufferFile != null) {
                try {
                    return new FileInputStream(bufferFile);

                } catch (FileNotFoundException e) {
                    LOG.error("Can not open buffer file '" + bufferFile
                            + "' for image url '" + uri + "'.", e);
                }
            }

            return null;
        }

        private void getContent() {
            if (bufferInitialized) {
                return;
            }
            bufferInitialized = true;

            File fileTemp;
            try {
                fileTemp = File.createTempFile(TEMP_FILE_PREFIX,
                        TEMP_FILE_SUFFIX);
            } catch (IOException ex) {
                LOG.error("Failed to call request " + uri + ".", ex);
                errored = true;
                return;
            }

            try {
                OutputStream output = null;
                if (fileTemp != null) {

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Use temp file='"
                                + fileTemp.getAbsolutePath()
                                + "' to store response of image request '"
                                + uri + "'.");
                    }

                    try {
                        output = new FileOutputStream(fileTemp);

                    } catch (FileNotFoundException ex) {
                        LOG.error(
                                "Can not write into file '" + fileTemp + "'.",
                                ex);
                    }
                }
                if (output == null) {
                    output = new ByteArrayOutputStream(BUFFER_DEFAULT_SIZE);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Use memory byte array (size="
                                + BUFFER_DEFAULT_SIZE
                                + ") to store response of image request '"
                                + uri + "'.");
                    }
                }

                String charset = servletResponse.getCharacterEncoding();
                if (charset == null) {
                    charset = DEFAULT_CHARSET;
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Use charset '" + charset
                            + "' for writer of image '" + uri + "'.");
                }

                Writer writer = null;
                try {
                    writer = new OutputStreamWriter(output, charset);

                } catch (UnsupportedEncodingException e) {
                    LOG.error("Unsupported encoding '" + charset + "'.", e);
                }

                HttpServletRequestWrapper request = new HttpServletRequestWrapper(
                        servletRequest);
                IncludeHttpServletResponse response = new IncludeHttpServletResponse(
                        servletResponse, output, writer);

                String requestURI = "/" + uri;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Send request to get image content of '"
                            + requestURI + "'.");
                }

                RequestDispatcher requestDispatcher = servletContext
                        .getRequestDispatcher(requestURI);
                if (requestDispatcher == null) {
                    LOG.error("Can not get request dispatcher for url '"
                            + requestURI + "'.");
                    errored = true;
                    return;
                }

                try {
                    requestDispatcher.include(request, response);

                } catch (Exception ex) {
                    LOG.error("Failed to call request " + uri + ".", ex);
                    errored = true;
                    return;
                }

                if (writer != null) {
                    try {
                        writer.close();

                    } catch (IOException ex) {
                        LOG.error("Can not close writer !", ex);
                    }
                }

                try {
                    output.close();

                } catch (IOException ex) {
                    LOG.error("Can not close buffer !", ex);
                }

                if (response.getStatus() != HttpServletResponse.SC_OK) {
                    LOG.error("Invalid response status: "
                            + response.getStatus());
                    errored = true;
                    return;
                }

                this.lastModified = response.getLastModified();
                this.contentLength = response.getContentLength();
                this.contentType = response.getContentType();

                if (LOG.isDebugEnabled()) {
                    LOG
                            .debug("Resource '"
                                    + uri
                                    + "' contentType='"
                                    + contentType
                                    + "' contentLength="
                                    + contentLength
                                    + " lastModified="
                                    + ((lastModified > 0) ? new Date(
                                            lastModified).toString() : String
                                            .valueOf(lastModified)));
                }

                if (fileTemp != null) {
                    bufferFile = fileTemp;
                    bufferFile.deleteOnExit();

                } else {
                    bufferArray = ((ByteArrayOutputStream) output)
                            .toByteArray();
                }

            } finally {
                if (errored && fileTemp != null) {
                    fileTemp.delete();
                }
            }
        }

        protected void finalize() throws Throwable {
            if (bufferFile != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Finalize image loader, delete file '"
                            + bufferFile.getAbsolutePath() + "'.");
                }
                try {
                    bufferFile.delete();

                } catch (Throwable th) {
                    LOG.error("Can not delete file '"
                            + bufferFile.getAbsolutePath() + "'.", th);
                }
                bufferFile = null;
            }

            super.finalize();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
     */
    private static class IncludeHttpServletResponse extends
            HttpServletResponseWrapper {
        private static final String REVISION = "$Revision$";

        private final ServletOutputStream servletOutputStream;

        private final PrintWriter writer;

        private int status = HttpServletResponse.SC_OK;

        private int contentLength;

        private String contentType;

        private long lastModified;

        public IncludeHttpServletResponse(HttpServletResponse servletResponse,
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

                public void write(byte[] b, int off, int len)
                        throws IOException {
                    outputStream.write(b, off, len);
                }

                public void write(byte[] b) throws IOException {
                    outputStream.write(b);
                }

            };
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
                LOG.trace("Inclusion set date header '" + name + "' to '"
                        + date + "'.");
            }

            if (this.lastModified == 0
                    && ParametredHttpServlet.HTTP_LAST_MODIFIED.equals(name)) {

                this.lastModified = date;
            }
        }

        public void setHeader(String name, String value) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Inclusion set header '" + name + "' to '" + value
                        + "'.");
            }

            if (lastModified == 0
                    && ParametredHttpServlet.HTTP_LAST_MODIFIED.equals(name)) {

                try {
                    Date d = ParametredHttpServlet.parseHttpDate(value);
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
}
