/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.ITextPositionCapability;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextPositionConverter extends HorizontalTextPositionConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new TextPositionConverter();

    private static final String TOP_POSITION_NAME = "top";

    private static final String BOTTOM_POSITION_NAME = "bottom";

    private static final Integer DEFAULT_POSITION = new Integer(
            ITextPositionCapability.DEFAULT_POSITION);

    private static Map TEXT_POSITIONS = new HashMap(HORIZONTAL_TEXT_POSITIONS);
    static {
        Integer i = new Integer(ITextPositionCapability.BOTTOM_POSITION);
        TEXT_POSITIONS.put(BOTTOM_POSITION_NAME, i);

        i = new Integer(ITextPositionCapability.TOP_POSITION);
        TEXT_POSITIONS.put(TOP_POSITION_NAME, i);
    }

    protected Map getTextPositions() {
        return TEXT_POSITIONS;
    }

    protected Integer getDefaultPosition() {
        return DEFAULT_POSITION;
    }
}
