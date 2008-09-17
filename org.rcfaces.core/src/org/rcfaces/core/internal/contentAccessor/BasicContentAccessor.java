package org.rcfaces.core.internal.contentAccessor;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.tools.BindingTools;
import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicContentAccessor extends AbstractContentAccessor {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(BasicContentAccessor.class);

    private final Object value;

    public BasicContentAccessor(FacesContext facesContext, Object url,
            IContentFamily contentFamily,
            IContentVersionHandler contentVersionHandler) {
        super(contentFamily, contentVersionHandler);

        this.value = convertURL(facesContext, url);
    }

    public BasicContentAccessor(FacesContext facesContext, Object url,
            IContentAccessor root, int pathType) {
        super(root);

        if (pathType == UNDEFINED_PATH_TYPE) {
            this.value = convertURL(facesContext, url);
        } else {
            this.value = url;
            setPathType(pathType);
        }
    }

    public Object getContentRef() {
        return value;
    }

    protected Object convertURL(FacesContext facesContext, Object url) {
        if (url == null) {
            setPathType(IContentAccessor.UNDEFINED_PATH_TYPE);

            return null;
        }

        url = BindingTools.resolveBinding(facesContext, url);

        if ((url instanceof String) == false) {
            setPathType(IContentAccessor.UNDEFINED_PATH_TYPE);
            return url;
        }

        return resolvePath(facesContext, (String) url);
    }

    public String toString() {
        return "[AbstractContentAccessor contentType=" + getContentFamily()
                + " pathType=" + getPathTypeName(getPathType())
                + " versionHandler=" + getContentVersionHandler()
                + " content='" + value + "' root=" + getParentAccessor() + "]";
    }
}