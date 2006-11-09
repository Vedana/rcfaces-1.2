/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/11/09 19:08:57  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:28  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.1  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IComponentDecorator {

    String HALIGN_LEFT = "left";

    String HALIGN_RIGHT = "right";

    String HALIGN_CENTER = "center";

    String VALIGN_CENTER = "middle";

    String VALIGN_BOTTOM = "bottom";

    String VALIGN_TOP = "top";

    void addChildDecorator(IComponentDecorator decorator);

    void encodeContainer(IHtmlWriter writer, Renderer renderer)
            throws WriterException;

    void encodeContainerEnd(IHtmlWriter writer, Renderer renderer)
            throws WriterException;

    void encodeJavaScript(IJavaScriptWriter jsWriter) throws WriterException;

    void decode(IRequestContext context, UIComponent component,
            IComponentData componentData);

}
