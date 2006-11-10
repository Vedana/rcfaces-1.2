/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptComponent {
    void initializeJavaScript(IJavaScriptWriter javaScriptWriter)
            throws WriterException;

    void initializePendingComponents(IJavaScriptWriter writer)
            throws WriterException;

    void initializeJavaScriptComponent(IJavaScriptWriter javaScriptWriter)
            throws WriterException;

    void releaseJavaScript(IJavaScriptWriter javaScriptWriter)
            throws WriterException;
}
