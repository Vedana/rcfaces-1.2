/*
 * $Id$
 */
package org.rcfaces.core.provider;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ImageURLRewritingInformation extends
        AbstractURLRewritingInformation {
    private static final String REVISION = "$Revision$";

    private String originalImageURL;

    private String rootImageURL;

    private String contentType;

    public ImageURLRewritingInformation() {
    }

    public ImageURLRewritingInformation(ImageURLRewritingInformation parent) {
        super(parent);
    }

    public String getOriginalImageURL() {
        return originalImageURL;
    }

    public void setOriginalImageURL(String originalImageURL) {
        this.originalImageURL = originalImageURL;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String mimeType) {
        this.contentType = mimeType;
    }

    public String getRootURL() {
        return rootImageURL;
    }

    public void setRootImageURL(String rootImageURL) {
        this.rootImageURL = rootImageURL;
    }

}