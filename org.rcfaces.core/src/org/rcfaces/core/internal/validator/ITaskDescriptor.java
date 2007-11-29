/*
 * $Id$
 */
package org.rcfaces.core.internal.validator;

import org.rcfaces.core.validator.IClientValidatorTask;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ITaskDescriptor {
    String getClientTaskExpression();

    String getClientTaskExpressionType();

    IClientValidatorTask getServerTask();
}
