/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.5  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.4  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.3  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/26 17:16:33  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/19 15:44:31  oeuillot
 * *** empty log message ***
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
        Object value = component.getValue();

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

            if (byId != null) {
                return byId;
            }
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