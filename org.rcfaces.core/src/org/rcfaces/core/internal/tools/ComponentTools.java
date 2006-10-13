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
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.13  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.12  2006/06/19 17:22:19  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.11  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.10  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.9  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.8  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.7  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.6  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.core.internal.tools;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.internal.renderkit.WriterException;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ComponentTools {
    private static final String REVISION = "$Revision$";

    public static final String[] STRING_EMPTY_ARRAY = new String[0];

    public static final boolean isAnonymousComponentId(String componentId) {
        if (componentId == null) {
            return false;
        }

        int idx = componentId.lastIndexOf(NamingContainer.SEPARATOR_CHAR);
        if (idx >= 0) {
            componentId = componentId.substring(idx + 1);
        }

        return componentId.startsWith(UIViewRoot.UNIQUE_ID_PREFIX);
    }

    public static final void encodeRecursive(FacesContext context,
            UIComponent component) throws WriterException {
        if (component.isRendered() == false) {
            return;
        }

        try {
            component.encodeBegin(context);

            if (component.getRendersChildren()) {
                component.encodeChildren(context);

            } else {
                encodeChildrenRecursive(context, component);
            }

            component.encodeEnd(context);

        } catch (WriterException ex) {
            throw ex;

        } catch (IOException ex) {
            throw new WriterException("Can not encode component '"
                    + component.getId() + "'.", ex, component);
        }
    }

    public static final void encodeChildrenRecursive(FacesContext context,
            UIComponent component) throws WriterException {
        Iterator children = component.getChildren().iterator();

        for (; children.hasNext();) {
            UIComponent child = (UIComponent) children.next();
            if (child.isRendered() == false) {
                continue;
            }

            try {
                child.encodeBegin(context);

                if (child.getRendersChildren()) {
                    child.encodeChildren(context);

                } else {
                    encodeChildrenRecursive(context, child);
                }

                child.encodeEnd(context);

            } catch (WriterException ex) {
                throw ex;

            } catch (IOException ex) {
                throw new WriterException("Can not encode child component '"
                        + child.getId() + "'.", ex, child);
            }
        }
    }

    public static UIComponent getForComponent(FacesContext context,
            String forComponent, UIComponent component) {
        if (forComponent == null || forComponent.length() < 1) {
            return null;
        }

        try {
            // Check the naming container of the current
            // component for component identified by
            // 'forComponent'
            for (UIComponent currentParent = component; currentParent != null; currentParent = currentParent
                    .getParent()) {

                UIComponent result = currentParent.findComponent(forComponent);
                if (result != null) {
                    return result;
                }
            }

            return findUIComponentBelow(context.getViewRoot(), forComponent);

        } catch (RuntimeException ex) {
            throw ex;

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static UIComponent findUIComponentBelow(UIComponent startPoint,
            String forComponent) {
        UIComponent retComp = null;
        List children = startPoint.getChildren();

        int size = children.size();
        for (int i = 0; i < size; i++) {
            UIComponent comp = (UIComponent) children.get(i);

            if (comp instanceof NamingContainer) {
                retComp = comp.findComponent(forComponent);
                if (retComp != null) {
                    return retComp;
                }
            }

            if (comp.getChildCount() > 0) {
                retComp = findUIComponentBelow(comp, forComponent);
                if (retComp != null) {
                    return retComp;
                }
            }
        }

        return null;
    }

    public static UIViewRoot getViewRoot(UIComponent component) {
        for (; component != null;) {

            if (component instanceof UIViewRoot) {
                return (UIViewRoot) component;
            }

            component = component.getParent();
        }

        return null;
    }

    public static Converter createConverter(FacesContext facesContext,
            String converterId) {
        return facesContext.getApplication().createConverter(converterId);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class Entry implements Map.Entry {
        private final Object key;

        private Object value;

        /**
         * Create new entry.
         */
        Entry(Object k, Object v) {
            value = v;
            key = k;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2))) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return key.hashCode() ^ (value == null ? 0 : value.hashCode());
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }

        public Object setValue(Object value) {
            throw new UnsupportedOperationException("Not implemented !");
        }
    }
}
