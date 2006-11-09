/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISeverityImageAccessors extends IImageAccessors {

    IContentAccessor getInfoImageAccessor();

    IContentAccessor getErrorImageAccessor();

    IContentAccessor getWarnImageAccessor();

    IContentAccessor getFatalImageAccessor();
}
