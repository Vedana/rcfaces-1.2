/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IAsyncRenderModeCapability {

    int NONE_ASYNC_RENDER_MODE = 0;

    int BUFFER_ASYNC_RENDER_MODE = 1;

    int TREE_ASYNC_RENDER_MODE = 2;

    int DEFAULT_ASYNC_RENDER_MODE = NONE_ASYNC_RENDER_MODE;

    int getAsyncRenderMode();

    void setAsyncRenderMode(int renderMode);
}
