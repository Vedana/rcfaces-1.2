/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.layout;

import javax.faces.component.UIViewRoot;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IViewSizeDetector {
    Size getViewSize(UIViewRoot viewRoot);
}
