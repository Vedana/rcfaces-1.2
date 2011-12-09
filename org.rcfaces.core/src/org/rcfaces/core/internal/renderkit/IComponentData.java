/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

import java.io.Serializable;

import org.rcfaces.core.internal.IReleasable;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentData extends IProperties, IReleasable, IEventData {

    String getComponentParameter();

    String[] getComponentParameters();

    String getParameter(Serializable parameterName);

    String[] getParameters(Serializable parameterName);

    boolean isEventComponent();
}
