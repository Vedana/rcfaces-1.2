/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CardBoxTools {
    private static final String REVISION = "$Revision$";

    private static final ICardIterator EMPTY_COMPONENT_ITERATOR = new CardListIterator(
            Collections.EMPTY_LIST);

    public static ICardIterator listCards(CardBoxComponent component) {
        List list = ComponentIterators.list(component, CardComponent.class);
        if (list.isEmpty()) {
            return EMPTY_COMPONENT_ITERATOR;
        }

        return new CardListIterator(list);
    }

    public static CardComponent getSelectedCard(CardBoxComponent component) {
        Object value = component.getSubmittedValue();

        if (value == null) {
            value = component.getValue();
        }

        if (value instanceof SelectItem) {
            value = ((SelectItem) value).getValue();
        }

        if (value != null) {
            CardComponent byId = null;

            ICardIterator iterator = listCards(component);
            for (; iterator.hasNext();) {
                CardComponent card = iterator.next();

                if (value.equals(card.getValue())) {
                    return card;
                }

                if (value.equals(card.getId())) {
                    byId = card;
                }
            }

            return byId; // On retourne NULL si on trouve pas l'onglet
            /*
             * if (byId != null) { return byId; }
             */

        }

        // On prend le premier tab dans ce cas !

        ICardIterator iterator = listCards(component);
        if (iterator.hasNext() == false) {
            return null;
        }

        return iterator.next();
    }

    private static final class CardListIterator extends
            ComponentIterators.ComponentListIterator implements ICardIterator {
        private static final String REVISION = "$Revision$";

        public CardListIterator(List list) {
            super(list);
        }

        public final CardComponent next() {
            return (CardComponent) nextComponent();
        }

        public CardComponent[] toArray() {
            return (CardComponent[]) toArray(new CardComponent[count()]);
        }
    }

    public static void selectCard(CardBoxComponent component, CardComponent card) {
        if (card == null) {
            component.setValue(null);
            return;
        }

        Object value = card.getValue();
        if (value == null) {
            value = card.getId();
        }

        component.setValue(value);
    }

    public static CardBoxComponent getCardBox(CardComponent component) {
        UIComponent parent = component.getParent();

        if (parent == null || (parent instanceof CardBoxComponent) == false) {
            throw new FacesException(
                    "Invalid parent of Tab component. (Must be a CardBox component) parent='"
                            + parent + "'");
        }

        return (CardBoxComponent) parent;
    }
}
