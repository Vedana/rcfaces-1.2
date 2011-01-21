/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.layout;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import org.rcfaces.core.component.capability.ILayoutManagerCapability;
import org.rcfaces.core.component.capability.ISizeCapability;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;

public abstract class AbstractLayoutProcessor implements ILayoutProcessor {

    private static final String CACHED_SIZE_ATTRIBUTE = "org.rcfaces.html.CACHED_SIZE";

    private final IViewSizeDetector viewSizeDetector = new SessionCookieViewSizeDetector();

    protected static final Size INVALID_SIZE = new Size();

    protected AbstractLayoutProcessor() {

    }

    protected Size computeSize(UIComponent component) {
        Size size = getCachedSize(component);
        if (size != null) {
            return size;
        }

        size = computeUserSize(component);
        if (size == null) {
            setCachedSize(component, INVALID_SIZE);
            return null;
        }

        setCachedSize(component, size);
        return size;
    }

    private Size computeUserSize(UIComponent component) {
        if ((component instanceof ISizeCapability) == false) {
            return null;
        }

        ISizeCapability sizeCapability = (ISizeCapability) component;

        String width = sizeCapability.getWidth();
        Length widthLength = Length.getLength(width);
        if (widthLength == null) {
            return null;
        }

        String height = sizeCapability.getHeight();
        Length heightLength = Length.getLength(height);
        if (heightLength == null) {
            return null;
        }

        if (widthLength.getUnit() == Length.PERCENT_UNIT
                || heightLength.getUnit() == Length.PERCENT_UNIT) {

            Size size = computeParentUserSize(component);
            if (size == null) {
                return null;
            }

            if (widthLength.getUnit() == Length.PERCENT_UNIT) {
                widthLength = widthLength.applyParentLength(size.getWidth());
            }
            if (heightLength.getUnit() == Length.PERCENT_UNIT) {
                heightLength = heightLength.applyParentLength(size.getHeight());
            }

        }

        return new Size(widthLength, heightLength);
    }

    private Size computeParentUserSize(UIComponent component) {
        for (; component != null; component = component.getParent()) {
            if (component instanceof ISizeCapability) {
                return computeSize(component);
            }

            if (component instanceof UIViewRoot) {
                return getSizeOfViewRoot(component);
            }
        }

        return null;
    }

    protected Size getSizeOfViewRoot(UIComponent component) {

        return null;
    }

    private Size getCachedSize(UIComponent component) {
        if ((component instanceof ITransientAttributesManager) == false) {
            return null;
        }
        Size size = (Size) ((ITransientAttributesManager) component)
                .getTransientAttribute(CACHED_SIZE_ATTRIBUTE);

        return size;
    }

    private void setCachedSize(UIComponent component, Size size) {
        if ((component instanceof ITransientAttributesManager) == false) {
            return;
        }

        ((ITransientAttributesManager) component).setTransientAttribute(
                CACHED_SIZE_ATTRIBUTE, size);
    }

    public static int computeInheritedLayoutType(UIComponent component) {
        for (; component != null; component = component.getParent()) {
            if ((component instanceof ILayoutManagerCapability) == false) {
                continue;
            }

            ILayoutManagerCapability layoutManager = (ILayoutManagerCapability) component;

            int layoutType = layoutManager.getLayoutType();
            if (layoutType != ILayoutManagerCapability.INHERITED_LAYOUT_TYPE) {
                return layoutType;
            }

        }
        return ILayoutManagerCapability.INHERITED_LAYOUT_TYPE;
    }
}
