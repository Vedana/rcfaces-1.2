package org.rcfaces.core.util;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.component.capability.ISortEventCapability;
import org.rcfaces.core.internal.listener.IScriptListener;
import org.rcfaces.core.model.ISortedComponent;
/**
 * 
 * @author jbmeslin@vedana.com
 * @version 
 *
 */
public class GridTools {

	/**
	 * 
	 * @param sortedComponent table of the current sorted columns
	 * @return boolean true if one is sort on server
	 */
	public static boolean hasSortedServerListener(
			ISortedComponent sortedComponent[]) {

		for (ISortedComponent iSortedComponent : sortedComponent) {
			UIComponent columnComponent = (UIComponent) iSortedComponent
					.getComponent();
			if ((columnComponent instanceof ISortEventCapability) == false) {
				continue;
			}
			FacesListener[] facesListeners = ((ISortEventCapability) columnComponent)
					.listSortListeners();
			for (FacesListener facesListener : facesListeners) {
				if (facesListener instanceof IScriptListener) {
					String command = ((IScriptListener) facesListener)
							.getCommand();
					if (ISortEventCapability.SORT_SERVER.equals(command)) {
						return true;
					}
				}

			}
		}
		return false;
	}
}
