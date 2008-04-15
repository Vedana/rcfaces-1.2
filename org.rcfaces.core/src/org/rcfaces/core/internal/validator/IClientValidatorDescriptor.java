/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.validator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientValidatorDescriptor extends IDescriptor {

    ITaskDescriptor getFilterTask();

    ITaskDescriptor getTranslatorTask();

    ITaskDescriptor getCheckerTask();

    ITaskDescriptor getFormatterTask();

    ITaskDescriptor getBehaviorTask();

    ITaskDescriptor getProcessorTask();

    ITaskDescriptor getOnErrorTask();

    ITaskDescriptor getOnCheckErrorTask();

    String getConverter();

    IServerConverter getServerConverter();

    String[] listRequiredClasses();

    // IStringAdapterDescriptor getStringFormatter();

}
