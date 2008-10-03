/*
 * $Id$
 */
package org.rcfaces.renderkit.svg.internal.image;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.renderkit.svg.item.INodeItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ShapeValue {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ShapeValue.class);

    private final Shape shape;

    private final Object value;

    private final String label;

    private final String description;

    private final boolean disabled;

    private final String alternateText;

    public ShapeValue(Shape shape, INodeItem nodeItem) {
        this.shape = shape;
        this.value = nodeItem.getValue();

        this.label = nodeItem.getLabel();
        this.description = nodeItem.getDescription();
        this.disabled = nodeItem.isDisabled();

        this.alternateText = nodeItem.getAlternateText();
    }

    public final Shape getShape() {
        return shape;
    }

    public final Object getValue() {
        return value;
    }

    public final String getLabel() {
        return label;
    }

    public final String getDescription() {
        return description;
    }

    public final boolean isDisabled() {
        return disabled;
    }

    public final String getAlternateText() {
        return alternateText;
    }

    public String[] computeOutline(AffineTransform transform, double flatness) {
        PathIterator pathIterator = shape.getPathIterator(transform, flatness);

        StringAppender sa = new StringAppender(256);

        List l = null;

        float fs[] = new float[6];
        for (; pathIterator.isDone() == false; pathIterator.next()) {
            int type = pathIterator.currentSegment(fs);

            if (type == PathIterator.SEG_CLOSE) {
                // Pas de point !
                continue;
            }

            if (type == PathIterator.SEG_MOVETO) {
                // Le premier !

                if (sa.length() > 0) {
                    l = new ArrayList();
                    l.add(sa.toString());

                    sa.setLength(0);
                }

                sa.append(String.valueOf((int) fs[0])).append(',').append(
                        String.valueOf((int) fs[1]));

                continue;
            }

            if (type == PathIterator.SEG_LINETO) {

                sa.append(',').append(String.valueOf((int) fs[0])).append(',')
                        .append(String.valueOf((int) fs[1]));
                continue;
            }

            LOG.error("Invalid path iterator ... ? " + type);
        }

        if (l == null) {
            if (sa.length() == 0) {
                return null;
            }

            return new String[] { sa.toString() };
        }

        if (sa.length() > 0) {
            l.add(sa.toString());
        }

        return (String[]) l.toArray(new String[l.size()]);
    }

}
