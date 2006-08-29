/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.1  2005/02/18 14:46:08  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 */
package org.rcfaces.core.internal.tools;

import javax.faces.FacesException;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;

import org.rcfaces.core.model.IFilterProperties;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
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
