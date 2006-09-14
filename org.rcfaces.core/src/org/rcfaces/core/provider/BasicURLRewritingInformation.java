/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Derni�res corrections pour la migration Rcfaces
 *
 */
package org.rcfaces.core.provider;

import org.rcfaces.core.provider.IURLRewritingProvider.IURLRewritingInformation;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicURLRewritingInformation extends
        AbstractURLRewritingInformation {
    private static final String REVISION = "$Revision$";

    public BasicURLRewritingInformation() {
        super();
    }

    public BasicURLRewritingInformation(IURLRewritingInformation parent) {
        super(parent);
    }

    public String getContentType() {
        return null;
    }

    public String getRootURL() {
        return null;
    }
}
