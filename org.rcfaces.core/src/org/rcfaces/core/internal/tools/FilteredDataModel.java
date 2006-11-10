/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import javax.faces.FacesException;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

import org.rcfaces.core.model.IFilterProperties;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FilteredDataModel {
    private static final String REVISION = "$Revision$";

    public static DataModel filter(DataModel dataModel, IFilterProperties filters) {
        if (filters == null || filters.size() < 1) {
            return dataModel;
        }

        if (dataModel instanceof ArrayDataModel) {
            return dataModel;
        }

        throw new FacesException("Can not filter dataModel '" + dataModel
                + "'.");
    }

}
