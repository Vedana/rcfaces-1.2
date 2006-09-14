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
 * Revision 1.5  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.4  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.ImageRadioButtonComponent;
import org.rcfaces.core.component.RadioButtonComponent;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.ISelectedCapability;
import org.rcfaces.core.component.iterator.IImageRadioButtonIterator;
import org.rcfaces.core.component.iterator.IRadioButtonIterator;
import org.rcfaces.core.internal.util.ComponentIterators;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RadioButtonTools {
    private static final String REVISION = "$Revision$";

    private static final IRadioButtonIterator EMPTY_RADIO_BUTTON_COMPONENT_ITERATOR = new RadioButtonListIterator(
            Collections.EMPTY_LIST);

    private static final IImageRadioButtonIterator EMPTY_IMAGE_RADIO_BUTTON_COMPONENT_ITERATOR = new ImageRadioButtonListIterator(
            Collections.EMPTY_LIST);

    public static RadioButtonComponent getSelectedRadioButtonFromSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        UIComponent component = (UIComponent) radioButtonComponent;
        for (; component.getParent() != null;) {
            component = component.getParent();
        }

        return (RadioButtonComponent) findRadioButtonSelectedWithSameGroupBox(
                component, radioButtonComponent.getGroupName());
    }

    public static ImageRadioButtonComponent getSelectedImageRadioButtonFromSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        UIComponent component = (UIComponent) radioButtonComponent;
        for (; component.getParent() != null;) {
            component = component.getParent();
        }

        return (ImageRadioButtonComponent) findRadioButtonSelectedWithSameGroupBox(
                component, radioButtonComponent.getGroupName());
    }

    public static IRadioButtonIterator listRadioButtonSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        UIComponent component = (UIComponent) radioButtonComponent;
        for (; component.getParent() != null;) {
            component = component.getParent();
        }

        List result = new ArrayList(8);

        listRadioWithSameGroupBox(component, radioButtonComponent
                .getGroupName(), result);

        if (result.isEmpty()) {
            return EMPTY_RADIO_BUTTON_COMPONENT_ITERATOR;
        }

        return new RadioButtonListIterator(result);
    }

    public static IImageRadioButtonIterator listImageRadioButtonSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        UIComponent component = (UIComponent) radioButtonComponent;
        for (; component.getParent() != null;) {
            component = component.getParent();
        }

        List result = new ArrayList(8);

        listRadioWithSameGroupBox(component, radioButtonComponent
                .getGroupName(), result);

        if (result.isEmpty()) {
            return EMPTY_IMAGE_RADIO_BUTTON_COMPONENT_ITERATOR;
        }

        return new ImageRadioButtonListIterator(result);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class RadioButtonListIterator extends
            ComponentIterators.ComponentListIterator implements
            IRadioButtonIterator {
        private static final String REVISION = "$Revision$";

        public RadioButtonListIterator(List list) {
            super(list);
        }

        public final RadioButtonComponent next() {
            return (RadioButtonComponent) nextComponent();
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static final class ImageRadioButtonListIterator extends
            ComponentIterators.ComponentListIterator implements
            IImageRadioButtonIterator {
        private static final String REVISION = "$Revision$";

        public ImageRadioButtonListIterator(List list) {
            super(list);
        }

        public final ImageRadioButtonComponent next() {
            return (ImageRadioButtonComponent) nextComponent();
        }

        public ImageRadioButtonComponent[] toArray() {
            return (ImageRadioButtonComponent[]) toArray(new ImageRadioButtonComponent[count()]);
        }
    }

    private static final IRadioGroupCapability findRadioButtonSelectedWithSameGroupBox(
            UIComponent container, String groupName) {

        for (Iterator i = container.getChildren().iterator(); i.hasNext();) {
            UIComponent e = (UIComponent) i.next();

            if (e instanceof IRadioGroupCapability) {
                IRadioGroupCapability rb = (IRadioGroupCapability) e;

                if (groupName.equals(rb.getGroupName()) == false) {
                    continue;
                }

                if (e instanceof ISelectedCapability) {
                    ISelectedCapability selectedCapability = (ISelectedCapability) e;

                    if (selectedCapability.isSelected()) {
                        return rb;
                    }
                }

                continue;
            }

            if (e.getChildCount() == 0) {
                continue;
            }

            IRadioGroupCapability rb = findRadioButtonSelectedWithSameGroupBox(
                    e, groupName);
            if (rb == null) {
                continue;
            }

            return rb;
        }

        return null;
    }

    private static final void listRadioWithSameGroupBox(UIComponent container,
            String groupName, List list) {

        for (Iterator i = container.getChildren().iterator(); i.hasNext();) {
            UIComponent e = (UIComponent) i.next();

            if (e instanceof IRadioGroupCapability) {
                IRadioGroupCapability rb = (IRadioGroupCapability) e;

                if (groupName.equals(rb.getGroupName()) == false) {
                    continue;
                }

                list.add(e);
                continue;
            }

            if (e.getChildCount() == 0) {
                continue;
            }

            listRadioWithSameGroupBox(e, groupName, list);
        }
    }

}