/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.validator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDescriptor {
    String[] listRequiredClasses();

    IParameter[] listParameters();
}
