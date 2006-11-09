/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IExpandImageCapability extends IStatesImageCapability {

    String getExpandedImageURL();

    void setExpandedImageURL(String url);

}
