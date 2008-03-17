/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICompositeContentAccessorHandler extends
        IContentAccessorHandler {

    String COMPOSITE_OPERATION_ID = "composite";

    ICompositeURLDescriptor createCompositeURLDescriptor(String mainCharSet);

    ICompositeURLDescriptor parseCompositeURLDescriptor(String compositeURL);

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface ICompositeURLDescriptor {
        void addUrl(String url, String charSet);

        String generateURL();

        IURLInformation[] listURLs();

        String getMainCharSet();

        public interface IURLInformation {
            String getURL();

            String getCharSet();
        }
    }
}
