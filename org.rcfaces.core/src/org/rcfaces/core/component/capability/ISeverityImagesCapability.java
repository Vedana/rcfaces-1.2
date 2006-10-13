/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISeverityImagesCapability extends IImageCapability {

    String getInfoImageURL();

    void setInfoImageURL(String infoImageURL);

    String getErrorImageURL();

    void setErrorImageURL(String errorImageURL);

    String getWarnImageURL();

    void setWarnImageURL(String warnImageURL);

    String getFatalImageURL();

    void setFatalImageURL(String fatalImageURL);

}