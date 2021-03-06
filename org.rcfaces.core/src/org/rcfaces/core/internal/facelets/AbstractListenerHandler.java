/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.util.ClassLocator;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagException;
import com.sun.facelets.tag.TagHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractListenerHandler extends TagHandler {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(AbstractListenerHandler.class);

    private final TagAttribute typeParam;

    public AbstractListenerHandler(TagConfig config) {
        super(config);
        this.typeParam = this.getRequiredAttribute("type");
    }

    public final void apply(FaceletContext facesContext, UIComponent parent) {
        if (parent == null) {
            throw new TagException(this.tag, "Parent UIComponent was null");
        }

        // only process if the parent is new to the tree
        if (parent.getParent() != null) {
            return;
        }

        String type = typeParam.getValue(facesContext);
        if (type == null || type.length() == 0) {
            throw new TagException(this.tag,
                    "Type of listener is null or empty");
        }

        Class< ? > listenerClass;
        try {
            listenerClass = ClassLocator.load(type, this, facesContext);

        } catch (ClassNotFoundException e) {
            throw new FacesException("Can not get class '" + type + "'.", e);
        }

        Object listener;
        try {
            listener = listenerClass.newInstance();

        } catch (Throwable th) {
            throw new FacesException("Can not instanciate listener class '"
                    + listenerClass + "'.", th);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Add " + getListenerName() + " listener '" + listener
                    + "' to component '" + parent.getId() + "'.");
        }

        addListener(listener, parent);
    }

    protected abstract void addListener(Object listener, UIComponent component);

    protected abstract String getListenerName();
}