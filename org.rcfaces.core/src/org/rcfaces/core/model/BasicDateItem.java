package org.rcfaces.core.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UISelectItem;
import javax.faces.model.SelectItem;

import org.rcfaces.core.component.capability.IClientDataCapability;
import org.rcfaces.core.component.capability.IStyleClassCapability;
import org.rcfaces.core.internal.converter.LocalizedDateConverter;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicDateItem extends SelectItem implements IStyleClassItem,
        IClientDataSelectItem {
    private static final String REVISION = "$Revision$";

    private String styleClass;

    private Map clientDatas;

    public BasicDateItem() {
    }

    public BasicDateItem(UISelectItem component) {
        super(computeValue(component), component.getItemLabel(), component
                .getItemDescription(), component.isItemDisabled());

        if (component instanceof IStyleClassCapability) {
            IStyleClassCapability dateItemComponent = (IStyleClassCapability) component;

            styleClass = dateItemComponent.getStyleClass();
        }

        if (component instanceof IClientDataCapability) {
            IClientDataCapability clientDataCapability = (IClientDataCapability) component;

            if (clientDataCapability.getClientDataCount() > 0) {
                Map map = clientDataCapability.getClientDataMap();

                getClientDataMap().putAll(map);
            }
        }
    }

    private static Object computeValue(UISelectItem component) {
        Object value = component.getItemValue();
        if (value == null || (value instanceof Date)
                || (value instanceof Date[])) {
            return value;
        }

        if (value instanceof String) {
            return LocalizedDateConverter.SINGLETON.getAsObject(null,
                    component, (String) value);
        }

        throw new FacesException("Invalid itemValue for DateItem '" + value
                + "'.");
    }

    public String getStyleClass() {
        return styleClass;
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
}