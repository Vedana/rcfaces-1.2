package org.rcfaces.core.internal.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentStorage.IResolvedContent;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.lang.IAdaptable;
import org.rcfaces.core.model.BasicContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractOperationContentModel extends BasicContentModel
        implements Serializable, IResolvedContent, IAdaptable {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractOperationContentModel.class);

    /**
     * 
     */
    protected static final IFileBuffer INVALID_BUFFERED_FILE = new IFileBuffer() {
        private static final String REVISION = "$Revision$";

        public int getSize() {
            return 0;
        }

        public String getName() {
            return "*** Invalid buffer ***";
        }

        public InputStream getContent() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public long getModificationDate() {
            return 0;
        }

        public String getHash() {
            return null;
        }

        public String getETag() {
            return null;
        }

        public boolean isInitialized() {
            return true;
        }

        public void setErrored() {
        }

        public boolean isErrored() {
            return true;
        }

        public String getRedirection() {
            return null;
        }

        public void initializeRedirection(String url) {
        }

    };

    private final String resourceURL;

    private final String operationId;

    private String filterParametersToParse;

    private String resourceKey;

    private transient Map filterParameters;

    private transient IBufferOperation bufferOperation;

    private boolean versioned;

    private transient IFileBuffer fileBuffer;

    public AbstractOperationContentModel(String resourceURL,
            String contentType, String versionId, String operationId,
            String filterParametersToParse, Map attributes,
            IBufferOperation bufferOperation) {
        this.resourceURL = resourceURL;
        this.operationId = operationId;
        this.filterParametersToParse = filterParametersToParse;
        this.bufferOperation = bufferOperation;

        StringAppender sa = new StringAppender(operationId, 128);

        if (filterParametersToParse != null) {
            sa.append(filterParametersToParse);
        }

        sa.append("::");

        sa.append(resourceURL);

        if (versionId != null) {
            sa.append(":$:");
            sa.append(versionId);
        }

        this.resourceKey = sa.toString();

        setProcessDataAtRequest(true);

        if (contentType != null) {
            setContentType(contentType);
        }

        if (attributes != null && attributes.isEmpty() == false) {
            putAllAttributes(attributes);
        }

        setWrappedData(this);
    }

    public final synchronized Map getFilterParameters() {
        if (filterParameters != null) {
            return filterParameters;
        }

        if (filterParametersToParse == null
                || filterParametersToParse.length() < 1) {
            filterParameters = Collections.EMPTY_MAP;
            return filterParameters;
        }

        StringTokenizer st = new StringTokenizer(filterParametersToParse, ",");

        filterParameters = new HashMap(st.countTokens());
        int idx = 0;
        for (; st.hasMoreTokens();) {
            String token = st.nextToken().trim();

            String pName;
            String pValue;

            int idxEq = token.indexOf('=');
            if (idxEq >= 0) {
                pName = token.substring(0, idxEq).trim();
                pValue = token.substring(idxEq + 1).trim();

            } else {
                pName = "#" + idx;
                pValue = token.trim();

                idx++;
            }

            filterParameters.put(pName, pValue);
        }

        return filterParameters;
    }

    public Object getAdapter(Class adapter, Object parameter) {
        if (IResolvedContent.class.equals(adapter)) {
            return this;
        }

        return null;
    }

    public boolean isProcessAtRequest() {
        return false;
    }

    public boolean isErrored() {
        return getFileBuffer().isErrored();
    }

    private synchronized IFileBuffer getFileBuffer() {
        if (fileBuffer != null) {
            return fileBuffer;
        }

        fileBuffer = createFileBuffer();

        setContentType(fileBuffer.getContentType());

        return fileBuffer;
    }

    public InputStream getInputStream() throws IOException {
        return getFileBuffer().getContent();
    }

    public long getModificationDate() {
        return getFileBuffer().getModificationDate();
    }

    public int getLength() {
        return getFileBuffer().getSize();
    }

    public IBufferOperation getBufferOperation(FacesContext facesContext) {
        if (bufferOperation != null) {
            return bufferOperation;
        }

        bufferOperation = createBufferOperation(facesContext);

        return bufferOperation;
    }

    protected abstract IBufferOperation createBufferOperation(
            FacesContext facesContext);

    protected abstract IFileBuffer createFileBuffer();

    protected final String getResourceURL() {
        return resourceURL;
    }

    protected final String getOperationId() {
        return operationId;
    }

    public String getETag() {
        return getFileBuffer().getETag();
    }

    public String getHash() {
        return getFileBuffer().getHash();
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public boolean isVersioned() {
        return versioned;
    }

    public void setVersioned(boolean versioned) {
        this.versioned = versioned;
    }

}