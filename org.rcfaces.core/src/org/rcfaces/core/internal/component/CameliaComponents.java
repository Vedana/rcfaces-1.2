/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.4  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.1  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.component;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.rcfaces.core.internal.renderkit.IRendererExtension;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CameliaComponents {
    private static final String REVISION = "$Revision$";

    public static final String FAMILY = "rcfaces";

    public static void processDecodes(FacesContext context,
            UIComponent component, Renderer renderer) {

        IRendererExtension rendererExtension = (IRendererExtension) renderer;

        try {
            renderer.decode(context, component);

        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }

        if (rendererExtension.getDecodesChildren()) {
            try {
                rendererExtension.decodeChildren(context, component);

            } catch (RuntimeException e) {
                context.renderResponse();
                throw e;
            }

        } else {
            Iterator kids = component.getFacetsAndChildren();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                kid.processDecodes(context);
            }
        }

        try {
            rendererExtension.decodeEnd(context, component);

        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }
}