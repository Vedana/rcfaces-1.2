/*
 * $Id$
 */
package org.rcfaces.core.internal.contentStorage;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IGzipedResolvedContent extends IResolvedContent {

    IResolvedContent getGzipedContent();
}
