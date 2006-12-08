/*
 * $Id$
 */
package org.rcfaces.core.internal.renderkit.tools;

import java.io.IOException;
import java.io.Writer;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentTreeRenderProcessor {

    boolean hasSaveStateFieldMarker(String content);

    void writeFilteredContent(Writer writer, String content) throws IOException;

    void encodeChildrenRecursive(UIComponent component,
            String componentId) throws WriterException;
}
