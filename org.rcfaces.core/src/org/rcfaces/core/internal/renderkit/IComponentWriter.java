/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.renderkit;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentWriter {

    IComponentRenderContext getComponentRenderContext();

    IComponentWriter endComponent() throws WriterException;
}
