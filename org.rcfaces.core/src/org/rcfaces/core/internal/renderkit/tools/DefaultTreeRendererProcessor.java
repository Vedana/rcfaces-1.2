/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit.tools;

import java.io.IOException;
import java.io.Writer;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.StateFieldMarkerTools;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DefaultTreeRendererProcessor extends AbstractTreeRendererProcessor {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(DefaultTreeRendererProcessor.class);

    public DefaultTreeRendererProcessor(FacesContext facesContext) {
        super(facesContext);
    }

    public boolean hasSaveStateFieldMarker(String content) {

        String saveStateFieldMarker = StateFieldMarkerTools
                .getStateFieldMarker(facesContext);
        if (saveStateFieldMarker == null || saveStateFieldMarker.length() < 1) {
            return false;
        }

        if (content.indexOf(saveStateFieldMarker) < 0) {
            return false;
        }

        return true;
    }

    public void writeFilteredContent(Writer writer, String content)
            throws IOException {

        String saveStateFieldMarker = StateFieldMarkerTools
                .getStateFieldMarker(facesContext);
        if (saveStateFieldMarker == null) {
            throw new FacesException("Save state field marker is null !");
        }

        String saveValue = StateFieldMarkerTools.getStateValue(facesContext);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Save value=" + saveValue);
        }

        for (int start = 0;;) {
            int offset = content.indexOf(saveStateFieldMarker, start);
            if (offset < 0) {
                if (start < content.length()) {
                    writer.write(content.substring(start));
                }
                break;
            }
            if (offset > start) {
                writer.write(content.substring(start, offset));
            }
            writer.write(saveValue);
            start = offset + saveStateFieldMarker.length();
        }
    }

    public void encodeChildrenRecursive(UIComponent component,
            String componentId) throws WriterException {

        ComponentTools.encodeChildrenRecursive(facesContext, component);
    }

}
