/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import org.rcfaces.core.lang.ApplicationException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IApplicationExceptionCapability {
    ApplicationException getApplicationException();

    void setApplicationException(ApplicationException applicationException);
}
