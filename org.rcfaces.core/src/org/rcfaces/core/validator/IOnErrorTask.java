/*
 * $Id$
 */
package org.rcfaces.core.validator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IOnErrorTask extends IClientValidatorTask {

    int CHECK_TASK = 1;

    int FORMATTER_TASK = 2;

    int BEHAVIOR_TASK = 4;

    void performError(IClientValidatorContext context, int task,
            boolean errorPerformed);
}
