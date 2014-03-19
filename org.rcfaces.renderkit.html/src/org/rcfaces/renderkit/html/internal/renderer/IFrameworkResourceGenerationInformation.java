/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.rcfaces.core.internal.contentAccessor.IGenerationResourceInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFrameworkResourceGenerationInformation extends
        IGenerationResourceInformation {

    String FRAMEWORK_ATTRIBUTE = "org.rcfaces.FrameworkResource";

    boolean isFrameworkResource();

}
