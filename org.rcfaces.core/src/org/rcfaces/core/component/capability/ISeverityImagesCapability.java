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

    /**
     * Returns an url string pointing to the image used for an info message.
     * 
     * @return url
     */
    String getInfoImageURL();

    /**
     * Sets an url string pointing to the image used for an info message.
     * 
     * @param infoImageURL
     *            url
     */
    void setInfoImageURL(String infoImageURL);

    /**
     * Returns an url string pointing to the image used for an error message.
     * 
     * @return url
     */
    String getErrorImageURL();

    /**
     * Sets an url string pointing to the image used for an error message.
     * 
     * @param errorImageURL
     *            url
     */
    void setErrorImageURL(String errorImageURL);

    /**
     * Returns an url string pointing to the image used for an warning message.
     * 
     * @return url
     */
    String getWarnImageURL();

    /**
     * Sets an url string pointing to the image used for an warning message.
     * 
     * @param warnImageURL
     *            url
     */
    void setWarnImageURL(String warnImageURL);

    /**
     * Returns an url string pointing to the image used for an fatal message.
     * 
     * @return url
     */
    String getFatalImageURL();

    /**
     * Sets an url string pointing to the image used for an fatal message.
     * 
     * @param fatalImageURL
     *            url
     */
    void setFatalImageURL(String fatalImageURL);
}