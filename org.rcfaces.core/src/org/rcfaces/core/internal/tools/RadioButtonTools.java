/*
 * $Id$
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

        return (RadioButtonComponent) getSelectedRadioGroupFromSameGroup(radioButtonComponent);
    }

    public static ImageRadioButtonComponent getSelectedImageRadioButtonFromSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        return (ImageRadioButtonComponent) getSelectedRadioGroupFromSameGroup(radioButtonComponent);
    }

    public static IRadioGroupCapability getSelectedRadioGroupFromSameGroup(
            IRadioGroupCapability radioButtonComponent) {

        UIComponent component = (UIComponent) radioButtonComponent;
        for (; component.getParent() != null;) {
            component = component.getParent();
        }

        return findRadioButtonSelectedWithSameGroupBox(component,
                radioButtonComponent.getGroupName());
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

    /*
    public static Object getGroupValue(IRadioValueCapability component) {
        IRadioValueCapability radioGroupCapability = (IRadioValueCapability) getSelectedRadioGroupFromSameGroup(component);
        if (radioGroupCapability == null) {
            return null;
        }

        return getValue(radioGroupCapability);
    }

    
    public static void setGroupValue(IComponentEngine engine,
            IRadioValueCapability component, Object value) {

        if (value instanceof ValueBinding) {
            engine.setProperty(Properties.GROUP_VALUE, value);
            return;
        }

        List list = new ArrayList(8);

        listRadioWithSameGroupBox((UIComponent) component, component
                .getGroupName(), list);
        if (list.isEmpty()) {
            return;
        }

        for (Iterator it = list.iterator(); it.hasNext();) {
            IRadioValueCapability radio = (IRadioValueCapability) it.next();

            boolean state = false;

            if (value != null && value.equals(getValue(radio))) {
                state = true;
            }

            select(radio, state);
        }

        engine.setProperty(Properties.GROUP_VALUE, value);
    }

    private static Object getValue(IRadioValueCapability groupValueCapability) {
        if (groupValueCapability instanceof ValueHolder) {
            return ((ValueHolder) groupValueCapability).getValue();
        }

        if (groupValueCapability instanceof UISelectItem) {
            return ((UISelectItem) groupValueCapability).getItemValue();
        }

        throw new FacesException("Unknown type of groupValueCapability: "
                + groupValueCapability);
    }

    private static void select(IRadioValueCapability groupValueCapability,
            boolean state) {

        if (groupValueCapability instanceof ISelectedCapability) {
            ISelectedCapability selectedCapability = (ISelectedCapability) groupValueCapability;

            if (selectedCapability.isSelected() != state) {
                selectedCapability.setSelected(state);
            }
            return;
        }

        if (groupValueCapability instanceof ICheckedCapability) {
            ICheckedCapability checkedCapability = (ICheckedCapability) groupValueCapability;
            if (checkedCapability.isChecked() != state) {
                checkedCapability.setChecked(state);
            }
            return;
        }

        throw new FacesException("Unknown to select groupValueCapability: "
                + groupValueCapability);
    }
    */

}
