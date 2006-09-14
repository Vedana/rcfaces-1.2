/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.4  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.4  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.3  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.1  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeRenderContext extends SelectItemsJsContext {
    private static final String REVISION = "$Revision$";

    private final boolean checkable;

    private final boolean userExpandable;

    private final boolean selectable;

    private final boolean writeSelectionState;

    private final boolean writeCheckState;

    private String lastInteractiveParent = null;

    public TreeRenderContext(ISelectItemNodeWriter renderer,
            IComponentRenderContext componentRenderContext,
            TreeComponent treeComponent, int depth, boolean sendFullStates,
            String containerVarId) {
        super(renderer, componentRenderContext, treeComponent, null);

        FacesContext facesContext = componentRenderContext.getFacesContext();

        checkable = treeComponent.isCheckable(facesContext);
        if (checkable) {
            writeCheckState = (treeComponent
                    .isClientCheckFullState(facesContext) == false);
        } else {
            writeCheckState = false;
        }

        selectable = treeComponent.isSelectable(facesContext);
        if (selectable) {
            writeSelectionState = (treeComponent
                    .isClientSelectionFullState(facesContext) == false);
        } else {
            writeSelectionState = false;
        }

        userExpandable = treeComponent.isUserExpandable(facesContext);

        int preloadLevel = treeComponent.getPreloadedLevelDepth(facesContext);

        if (preloadLevel > 0) {
            preloadLevel += depth;

            setPreloadedLevelDepth(preloadLevel);
        }

        Object value = treeComponent.getValue();
        Object values[] = null;
        if (value != null) {
            if (value.getClass().isArray() == false) {
                values = new Object[] { value };

            } else {
                values = (Object[]) value;
            }
        }

        initializeTreeValue(facesContext, treeComponent, values);

        if (containerVarId != null) {
            pushVarId(containerVarId);
        }
    }

    protected void initializeValue(UIComponent component, Object value) {
    }

    protected void initializeTreeValue(FacesContext facesContext,
            TreeComponent treeComponent, Object values[]) {
        if (checkable) {
            // L'attribut checkValue est prioritaire, par contre lors de la mise
            // à jour,
            // la valeur de checkValue sera mise à NULL, et l'état check sera
            // mis dans value ! (pour le submit)

            Object checkValues = treeComponent.getCheckedValues(facesContext);
            if (checkValues != null) {
                initializeCheckValue(checkValues);

            } else {
                initializeCheckValue(values);
            }

            if (selectable) {
                initializeSelectionValue(treeComponent
                        .getSelectedValues(facesContext));
            }

        } else if (selectable) {
            Object selectionValues = treeComponent
                    .getSelectedValues(facesContext);
            if (selectionValues != null) {
                initializeSelectionValue(selectionValues);

            } else {
                initializeSelectionValue(values);
            }
        }

        initializeExpansionValue(treeComponent.getExpansionValues(facesContext));
    }

    public final boolean writeCheckFullState() {
        return writeCheckState;
    }

    public final boolean writeSelectionFullState() {
        return writeSelectionState;
    }

    public final boolean isUserExpandable() {
        return userExpandable;
    }

    public boolean isFirstInteractiveChild(String parentVarId) {
        if (lastInteractiveParent == parentVarId) {
            return false;
        }

        lastInteractiveParent = parentVarId;
        return true;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean isSelectable() {
        return selectable;
    }
}