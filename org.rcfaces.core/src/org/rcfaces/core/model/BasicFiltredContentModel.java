/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicFiltredContentModel extends BasicContentModel implements
        IFiltredModel {
    private static final String REVISION = "$Revision$";

    private IFilterProperties filter;

    public BasicFiltredContentModel() {
    }

    public BasicFiltredContentModel(Object value) {
        super(value);
    }

    public BasicFiltredContentModel(Object value, Map attributes) {
        super(value, attributes);
    }

    public void setFilter(IFilterProperties filter) {
        this.filter = filter;
    }

    protected IFilterProperties getFilter() {
        return filter;
    }

}