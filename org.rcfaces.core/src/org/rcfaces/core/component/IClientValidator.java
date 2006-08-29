/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2004/08/12 14:21:06  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component;

import javax.faces.validator.Validator;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IClientValidator extends Validator {
    String getExpression();
}
