/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/08/30 14:34:39  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ItemActionEvent extends ActionEvent {

	private final Object itemValue;
	
	public ItemActionEvent(UIComponent component, Object itemValue) {
		super(component);
		
		this.itemValue=itemValue;
	}

	public Object getItemValue() {
		return itemValue;
	}
	
	
}
