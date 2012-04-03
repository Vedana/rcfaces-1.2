/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IComponentWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlComponentWriter extends IComponentWriter {

    IHtmlComponentRenderContext getHtmlComponentRenderContext();

}
