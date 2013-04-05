/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Moz2_BorderRadiusRuleProcessor extends AbstractMapRuleProcessor {

    public Moz2_BorderRadiusRuleProcessor() {
        mapPropertyName.put("border-radius", "-moz-border-radius");

        mapPropertyName.put("border-top-left-radius",
                "-moz-border-radius-topleft");

        mapPropertyName.put("border-top-right-radius",
                "-moz-border-radius-topright");

        mapPropertyName.put("border-bottom-left-radius",
                "-moz-border-radius-bottomleft");

        mapPropertyName.put("border-bottom-right-radius",
                "-moz-border-radius-bottomright");
    }

}
