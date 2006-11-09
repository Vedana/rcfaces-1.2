/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.Collections;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.rcfaces.core.model.IFilterProperties;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractContentAccessor implements IContentAccessor {
    private static final String REVISION = "$Revision$";

    private final IContentAccessor rootContentAccessor;

    private final IContentType type;

    private final IContentVersionHandler contentVersionHandler;

    protected AbstractContentAccessor(IContentType type,
            IContentVersionHandler contentVersionHandler) {
        this(type, null, contentVersionHandler);
    }

    protected AbstractContentAccessor(IContentAccessor contentAccessor) {
        this(contentAccessor.getType(), contentAccessor, contentAccessor
                .getContentVersionHandler());
    }

    protected AbstractContentAccessor(IContentType type,
            IContentAccessor contentAccessor,
            IContentVersionHandler contentVersionHandler) {
        this.rootContentAccessor = contentAccessor;
        this.type = type;
        this.contentVersionHandler = contentVersionHandler;
    }

    public IContentAccessor getRootAccessor() {
        return rootContentAccessor;
    }

    public final IContentType getType() {
        return type;
    }

    public IContentVersionHandler getContentVersionHandler() {
        return contentVersionHandler;
    }

    public final String resolveURL(FacesContext facesContext,
            IContentInformation contentInformation,
            IFilterProperties filterProperties) {
        return ContentAccessorEngine.resolveURL(facesContext, this,
                contentInformation, filterProperties);
    }

    public Object getAttribute(String attributeName) {
        if (rootContentAccessor != null) {
            return rootContentAccessor.getAttribute(attributeName);
        }

        return null;
    }

    public Map getAttributes() {
        if (rootContentAccessor != null) {
            return rootContentAccessor.getAttributes();
        }

        return Collections.EMPTY_MAP;
    }

}