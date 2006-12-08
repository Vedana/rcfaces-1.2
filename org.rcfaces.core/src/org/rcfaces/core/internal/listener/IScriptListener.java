/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.listener;

import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.renderkit.IProcessContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptListener extends FacesListener {
    String getScriptType(IProcessContext processContext);

    String getCommand();
}
