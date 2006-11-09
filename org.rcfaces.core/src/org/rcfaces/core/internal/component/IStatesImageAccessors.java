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
public interface IStatesImageAccessors extends IImageAccessors {

    IContentAccessor getHoverImageAccessor();

    IContentAccessor getSelectedImageAccessor();

    IContentAccessor getDisabledImageAccessor();
}
