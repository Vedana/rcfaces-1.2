/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.IRequestContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlRequestContext extends IRequestContext {
    IHtmlProcessContext getHtmlProcessContext();
}
