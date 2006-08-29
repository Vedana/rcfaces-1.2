/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.5  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.4  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.3  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.2  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/09/13 08:34:26  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */

class SelectItemsJsContext extends SelectItemsContext {
    private static final String REVISION = "$Revision$";

    private final List varIds = new ArrayList(8);

    private final boolean disabled;

    public SelectItemsJsContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            UIComponent rootComponent, Object value) {
        super(renderer, componentRenderContext, rootComponent, value);

        UIComponent component = getRootComponent();
        if (component instanceof IDisabledCapability) {
            disabled = ((IDisabledCapability) component).isDisabled();

        } else {
            disabled = false;
        }
    }

    public void popVarId() {
        varIds.remove(varIds.size() - 1);
    }

    public void pushVarId(String varId) {
        varIds.add(varId);
    }

    public String peekVarId() {
        if (varIds.isEmpty()) {
            throw new NullPointerException("No var available into stack !");
        }
        return (String) varIds.get(varIds.size() - 1);
    }

    public int countVarId() {
        return varIds.size();
    }

    public boolean isDisabled() {
        return disabled;
    }
}