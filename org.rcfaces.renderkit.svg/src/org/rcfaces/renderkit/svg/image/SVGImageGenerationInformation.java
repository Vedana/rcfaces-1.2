/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.image;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.image.GenerationImageInformation;
import org.rcfaces.renderkit.svg.item.INodeItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGImageGenerationInformation extends GenerationImageInformation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(SVGImageGenerationInformation.class);

    private static final INodeItem[] NODE_ITEM_EMPTY_ARRAY = new INodeItem[0];

    private static final String PIXEL_UNIT_TO_MILLIMETER_PROPERTY = "org.rfcaces.svg.PIXEL_UNIT_TO_MILLIMETER";

    private static final String DEFAULT_FONT_FAMILY_PROPERTY = "org.rfcaces.svg.DEFAULT_FONT_FAMILY";

    private INodeItem[] nodes;

    public SVGImageGenerationInformation() {
    }

    public SVGImageGenerationInformation(INodeItem[] nodes) {
        this.nodes = nodes;
    }

    public INodeItem[] getNodes() {
        return nodes;
    }

    public final void setNodes(INodeItem[] nodes) {
        this.nodes = nodes;
    }

    public void restoreState(FacesContext facesContext, Object state) {
        Object states[] = (Object[]) state;

        super.restoreState(facesContext, states[0]);

        INodeItem nodes[];
        if (states.length == 1) {
            nodes = NODE_ITEM_EMPTY_ARRAY;

        } else {
            nodes = new INodeItem[states.length - 1];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = (INodeItem) UIComponentBase.restoreAttachedState(
                        facesContext, states[i + 1]);
            }
        }

        setNodes(nodes);
    }

    public Object saveState(FacesContext facesContext) {

        INodeItem nodes[] = getNodes();
        if (nodes == null) {
            nodes = NODE_ITEM_EMPTY_ARRAY;
        }

        Object states[] = new Object[1 + nodes.length];

        states[0] = super.saveState(facesContext);

        for (int i = 0; i < nodes.length; i++) {
            states[i + 1] = UIComponentBase.saveAttachedState(facesContext,
                    nodes[i]);
        }

        return states;
    }

    public float getPixelUnitToMillimeter() {
        Float f = (Float) getAttribute(PIXEL_UNIT_TO_MILLIMETER_PROPERTY);
        if (f != null) {
            return f.floatValue();
        }

        return 0f;
    }

    public void setPixelUnitToMillimeter(float f) {
        setAttribute(PIXEL_UNIT_TO_MILLIMETER_PROPERTY, new Float(f));
    }

    public String getDefaultFontFamily() {
        return (String) getAttribute(DEFAULT_FONT_FAMILY_PROPERTY);
    }

    public void setDefaultFontFamily(String fontFamily) {
        setAttribute(DEFAULT_FONT_FAMILY_PROPERTY, fontFamily);
    }
}
