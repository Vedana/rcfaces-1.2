/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
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
public abstract class AbstractURLRewritingInformation implements
        IURLRewritingInformation {
    private static final String REVISION = "$Revision$";

    private final IURLRewritingInformation parent;

    private boolean versioned;

    protected AbstractURLRewritingInformation() {
        this(null);
    }

    protected AbstractURLRewritingInformation(IURLRewritingInformation parent) {
        this.parent = parent;
    }

    public final IURLRewritingInformation getParent() {
        return parent;
    }

    public boolean isVersioned() {
        return versioned;
    }

    public void setVersioned() {
        versioned = true;
    }

}
