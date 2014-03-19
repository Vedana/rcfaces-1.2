/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * A label which will be outlined
 * 
 * @version $Revision$ $Date$
 */
public interface IOutlinedLabelCapability {

    public enum Method {
        IgnoreCase, IgnoreAccents, Multiple, StartsWith, WordOnly, FullText, Server
    }

    /**
     * Returns the label which is outlined
     * 
     * @return The label
     */
    String getOutlinedLabel();

    /**
     * Sets the label which will be outlined
     * 
     * @param label
     *            Label which is outlined
     */
    void setOutlinedLabel(String label);

    String getOutlinedLabelMethod();

    void setOutlinedLabelMethod(String method);
}
