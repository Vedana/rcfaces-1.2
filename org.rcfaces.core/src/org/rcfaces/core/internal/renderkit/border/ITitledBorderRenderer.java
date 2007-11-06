/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit.border;

import org.rcfaces.core.internal.renderkit.IComponentWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITitledBorderRenderer {
    void setText(IComponentWriter writer, String text, String textComponentId);
}
