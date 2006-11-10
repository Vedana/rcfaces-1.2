/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import org.rcfaces.core.internal.IReleasable;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentData extends IProperties, IReleasable, IEventData {

    String getComponentParameter();

    String[] getComponentParameters();

    String getParameter(String parameterName);

    String[] getParameters(String parameterName);

    boolean isEventComponent();
}
