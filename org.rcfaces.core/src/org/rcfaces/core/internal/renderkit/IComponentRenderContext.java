/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/13 18:04:51  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.3  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.2  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.renderkit;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentRenderContext {
    FacesContext getFacesContext();

    IRenderContext getRenderContext();

    UIComponent getComponent();

    String getComponentClientId();

    boolean containsAttribute(String key);

    Object getAttribute(String key);

    Object setAttribute(String key, Object value);

    Object removeAttribute(String key);
}
