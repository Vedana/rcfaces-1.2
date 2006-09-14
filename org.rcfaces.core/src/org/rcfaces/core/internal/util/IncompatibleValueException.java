/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.util;

import javax.faces.FacesException;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class IncompatibleValueException extends FacesException {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -2959146007402643205L;

    public IncompatibleValueException(Object value, String types) {
        super("Value '" + value.getClass() + "' is not compatible with: "
                + types, null);
    }

}