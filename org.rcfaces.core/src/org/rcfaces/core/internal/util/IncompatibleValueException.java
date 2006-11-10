/*
 * $Id$
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
