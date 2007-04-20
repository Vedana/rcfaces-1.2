/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.capability;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IAsyncRenderer;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAsyncRenderComponent {

	IAsyncRenderer getAsyncRenderer(FacesContext facesContext);
}
