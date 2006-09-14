package org.rcfaces.core.model;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.capability.IAcceleratorKeyCapability;
import org.rcfaces.core.component.capability.IAccessKeyCapability;
import org.rcfaces.core.component.capability.ICheckedCapability;
import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IRadioGroupCapability;
import org.rcfaces.core.component.capability.IServerDataCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicSelectItem extends SelectItem implements
        IAccessKeySelectItem, IAcceleratorKeySelectItem, IGroupSelectItem,
        ICheckSelectItem, IStyledSelectItem, IVisibleSelectItem,
        IServerDataSelectItem, IClientDataSelectItem, IStyleClassItem {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 6953469102413843158L;

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    private Map serverDatas;

    private Map clientDatas;

    private String accessKey;

    private String acceleratorKey;

    private String groupName;

    private boolean checked;

    private boolean visible;

    private int style;

    private String styleClass;

    public BasicSelectItem() {
    }

    public BasicSelectItem(Object value) {
        super(value);
    }

    public BasicSelectItem(Object value, String label) {
        super(value, label);
    }

    public BasicSelectItem(Object value, String label, String description) {
        super(value, label, description);
    }

    public BasicSelectItem(Object value, String label, String description,
            boolean disabled) {
        super(value, label, description, disabled);
    }

    public BasicSelectItem(UISelectItem component) {
        super(getValue(component), component.getItemLabel(), component
                .getItemDescription(), component.isItemDisabled());

        int s = AS_PUSH_BUTTON;

        if (component instanceof IAccessKeyCapability) {
            accessKey = ((IAccessKeyCapability) component).getAccessKey();

        } else {
            accessKey = null;
        }

        if (component instanceof IAcceleratorKeyCapability) {
            acceleratorKey = ((IAcceleratorKeyCapability) component)
                    .getAcceleratorKey();

        } else {
            acceleratorKey = null;
        }

        if (component instanceof ICheckedCapability) {
            checked = ((ICheckedCapability) component).isChecked();
            s = AS_CHECK_BOX;
        } else {
            checked = false;
        }

        if (component instanceof IRadioGroupCapability) {
            groupName = ((IRadioGroupCapability) component).getGroupName();
            s = AS_RADIO_BUTTON;

        } else {
            groupName = null;
        }

        if (component instanceof IVisibilityCapability) {
            Boolean b = ((IVisibilityCapability) component).getVisible();

            visible = (Boolean.FALSE.equals(b) == false);

        } else {
            visible = true;
        }

        if (component instanceof IServerDataCapability) {
            IServerDataCapability serverDataCapability = (IServerDataCapability) component;

            Map map = getServerDataMap();
            if (map.isEmpty() == false) {
                getServerDataMap().putAll(
                        serverDataCapability.getServerDataMap());
            }
        }

        if (component instanceof IClientDataCapability) {
            IClientDataCapability clientDataCapability = (IClientDataCapability) component;

            if (clientDataCapability.getClientDataCount() > 0) {
                Map map = clientDataCapability.getClientDataMap();

                getClientDataMap().putAll(map);
            }
        }

        this.style = s;

        if (component instanceof IStyleClassCapability) {
            IStyleClassCapability styleClassCapability = (IStyleClassCapability) component;

            styleClass = styleClassCapability.getStyleClass();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.IAccessKeySelectItem#getAccessKey()
     */
    public String getAccessKey() {
        return accessKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public static Object getValue(UISelectItem component) {
        Object value = component.getItemValue();
        if (value != null) {
            return value;
        }

        // Ben OUI on retourne l'ID !!!!
        // En tout cas il ne faut pas retourner NULL !
        return component.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.model.ICheckSelectItem#isChecked()
     */
    public boolean isChecked() {
        return checked;
    }

    public int getStyle() {
        return style;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isServerDataEmpty() {
        if (serverDatas == null) {
            return true;
        }

        return serverDatas.isEmpty();
    }

    public Map getServerDataMap() {
        if (serverDatas == null) {
            serverDatas = new HashMap();
        }

        return serverDatas;
    }

    public boolean isClientDataEmpty() {
        if (clientDatas == null) {
            return true;
        }

        return clientDatas.isEmpty();
    }

    public Map getClientDataMap() {
        if (clientDatas == null) {
            clientDatas = new HashMap();
        }

        return clientDatas;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public String getAcceleratorKey() {
        return acceleratorKey;
    }
}