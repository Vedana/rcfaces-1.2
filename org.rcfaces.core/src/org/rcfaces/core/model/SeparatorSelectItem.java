/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/20 17:21:12  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.model;

import javax.faces.model.SelectItem;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class SeparatorSelectItem {
	private static final String REVISION = "$Revision$";

	private static final String SEPARATOR_KEY = "$-camelia#separator-$";

	public static final SelectItem SEPARATOR = new SelectItem() {
		private static final String REVISION = "$Revision$";

		public Object getValue() {
			return SEPARATOR_KEY;
		}

		public void setValue(Object value) {
			throw new IllegalStateException(
					"Can not change value of Separator item !");
		}
	};

	public static final boolean isSeparator(SelectItem selectItem) {
		if (selectItem == SEPARATOR) {
			return true;
		}

		if (SEPARATOR_KEY.equals(selectItem.getValue())) {
			return true;
		}

		return false;
	}

}
