/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICheckListener extends FacesListener {

	void processCheck(CheckEvent event) throws AbortProcessingException;
	
}
