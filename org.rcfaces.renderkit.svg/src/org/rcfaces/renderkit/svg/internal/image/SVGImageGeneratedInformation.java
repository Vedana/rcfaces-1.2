/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.image;

import java.awt.geom.AffineTransform;

import org.rcfaces.core.image.GeneratedImageInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class SVGImageGeneratedInformation extends GeneratedImageInformation {
    private static final String SHAPE_VALUES_PROPERTY = "org.rcfaces.svg.SHAPE_VALUES";

    private static final String GLOBAL_TRANSFORM_PROPERTY = "org.rcfaces.svg.GLOBAL_TRANSFORM";

    public void setShapeValues(ShapeValue shapeValues[]) {
        setAttribute(SHAPE_VALUES_PROPERTY, shapeValues);
    }

    public ShapeValue[] getShapeValues() {
        return (ShapeValue[]) getAttribute(SHAPE_VALUES_PROPERTY);
    }

    public void setGlobalTransform(AffineTransform transform) {
        setAttribute(GLOBAL_TRANSFORM_PROPERTY, transform);
    }

    public AffineTransform getGlobalTransform() {
        return (AffineTransform) getAttribute(GLOBAL_TRANSFORM_PROPERTY);
    }

}
