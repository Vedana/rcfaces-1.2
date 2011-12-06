/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FiltredContentModelWrapper extends ContentModelWrapper implements
		IFiltredModel {
	public void setFilter(IFilterProperties filter) {
		IContentModel contentModel = getContentModel();

		if ((contentModel instanceof IFiltredModel) == false) {
			return;
		}

		((IFiltredModel) contentModel).setFilter(filter);
	}
}
