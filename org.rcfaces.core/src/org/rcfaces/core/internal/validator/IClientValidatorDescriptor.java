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

    String getFilterCall();

    String getTranslatorCall();

    String getCheckerCall();

    String getFormatterCall();

    String getBehaviorCall();

    String getProcessorCall();

    String getOnErrorCall();

    String getOnCheckErrorCall();

    String getConverter();

//    IStringAdapterDescriptor getStringFormatter();

}
