/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/09/05 08:57:21  oeuillot
 * Dernières corrections pour la migration Rcfaces
 *
 */
package org.rcfaces.core.provider;

import org.rcfaces.core.provider.IURLRewritingProvider.IURLRewritingInformation;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractURLRewritingInformation implements
        IURLRewritingInformation {
    private static final String REVISION = "$Revision$";

    private final IURLRewritingInformation parent;

    protected AbstractURLRewritingInformation() {
        this(null);
    }

    protected AbstractURLRewritingInformation(IURLRewritingInformation parent) {
        this.parent = parent;
    }

    public final IURLRewritingInformation getParent() {
        return parent;
    }

}
