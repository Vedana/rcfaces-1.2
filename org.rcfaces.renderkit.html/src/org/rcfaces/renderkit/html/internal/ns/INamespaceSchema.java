/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.ns;

import org.rcfaces.renderkit.html.internal.ns.NamespaceServlet.IBuffer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface INamespaceSchema {
    String getName();

    String getNameSpace();

    String getSchemaLocation();

    IBuffer getBuffer(String resourceName);
}
