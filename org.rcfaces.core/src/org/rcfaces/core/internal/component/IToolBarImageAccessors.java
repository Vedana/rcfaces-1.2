/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import org.rcfaces.core.component.familly.IContentAccessors;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IToolBarImageAccessors extends IContentAccessors {
    IContentAccessor getSeparatorImageAccessor();

    IContentAccessor getControlImageAccessor();
}
