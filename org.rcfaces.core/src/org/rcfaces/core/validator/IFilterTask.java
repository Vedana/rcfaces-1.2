/*
 * $Id$
 */
package org.rcfaces.core.validator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IFilterTask extends IClientValidatorTask {

    boolean applyFilter(IClientValidatorContext context, char keyChar);
}
