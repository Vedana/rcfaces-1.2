/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.1  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.renderkit.border;

import org.rcfaces.core.internal.renderkit.IComponentWriter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface ITitledBorderRenderer {
    void setText(IComponentWriter writer, String text);
}
