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
    private static final String REVISION = "$Revision$";

    private IFilterProperties filter;

    public ImageFiltredContentModel() {

    }

    public void setFilter(IFilterProperties filter) {
        this.filter = filter;
    }

    protected IFilterProperties getFilter() {
        return filter;
    }
}