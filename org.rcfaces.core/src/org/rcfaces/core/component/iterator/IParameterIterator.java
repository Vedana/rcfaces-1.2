/*
 * $Id$
 */
package org.rcfaces.core.component.iterator;

import javax.faces.component.UIParameter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IParameterIterator extends IComponentIterator {

    UIParameter next();

    UIParameter[] toArray();
}
