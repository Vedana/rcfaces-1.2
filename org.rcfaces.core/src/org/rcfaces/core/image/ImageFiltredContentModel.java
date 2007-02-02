/*
 * $Id$
 */
package org.rcfaces.core.image;

import org.rcfaces.core.model.IFilterProperties;
import org.rcfaces.core.model.IFiltredModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ImageFiltredContentModel extends ImageContentModel implements
        IFiltredModel {

    private IFilterProperties filter;

    public void setFilter(IFilterProperties filter) {
        this.filter = filter;
    }

    protected IFilterProperties getFilter() {
        return filter;
    }
}
