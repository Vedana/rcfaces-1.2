/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 */
package org.rcfaces.core.internal.renderkit;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractComponentWriter implements IComponentWriter {
    private static final String REVISION = "$Revision$";

    private final IComponentRenderContext componentRenderContext;

    protected AbstractComponentWriter(
            IComponentRenderContext componentRenderContext) {
        this.componentRenderContext = componentRenderContext;
    }

    public IComponentRenderContext getComponentRenderContext() {
        return componentRenderContext;
    }
}
