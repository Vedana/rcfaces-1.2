/*
 * $Id$
 */
package org.rcfaces.jfreechart.renderer;

import java.io.Serializable;

import javax.faces.FacesException;

import org.jfree.ui.Drawable;
import org.rcfaces.core.image.GenerationImageInformation;
import org.rcfaces.core.internal.lang.StringAppender;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class GenerationChartInformation extends GenerationImageInformation {

    private static final String CHART_DRAWABLE_ATTRIBUTE = "org.rcfaces.jfreechart.DRAWABLE";

    public void setDrawable(Drawable drawable) {
        if ((drawable instanceof Serializable) == false) {
            throw new FacesException("Drawable must be serializable !");
        }
        setAttribute(CHART_DRAWABLE_ATTRIBUTE, drawable);
    }

    public Drawable getDrawable() {
        return (Drawable) getAttribute(CHART_DRAWABLE_ATTRIBUTE);
    }

    @Override
    protected void appendToKey(StringAppender sa, String propertyName,
            Object value) {
        if (CHART_DRAWABLE_ATTRIBUTE.equals(propertyName)) {
            participeSerializableHashCode(sa, propertyName,
                    (Serializable) value);
            return;
        }

        super.appendToKey(sa, propertyName, value);
    }

}
