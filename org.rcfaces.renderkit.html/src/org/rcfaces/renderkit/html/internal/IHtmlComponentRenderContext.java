/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlComponentRenderContext extends IComponentRenderContext {
    boolean hasClientDatas(boolean clear);

    IHtmlRenderContext getHtmlRenderContext();
}
