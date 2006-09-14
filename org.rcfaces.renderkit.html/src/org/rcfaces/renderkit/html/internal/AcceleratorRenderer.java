/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.8  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.7  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.6  2006/03/23 19:12:40  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.5  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.4  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.3  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.AcceleratorComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.KeyTools;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AcceleratorRenderer extends AbstractJavaScriptRenderer {
    private static final String REVISION = "$Revision$";

    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        AcceleratorComponent acceleratorComponent = (AcceleratorComponent) componentRenderContext
                .getComponent();

        String keyBinding = acceleratorComponent.getKeyBinding(facesContext);
        if (keyBinding == null || keyBinding.length() == 0) {
            return;
        }

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        String forComponent = acceleratorComponent.getFor(facesContext);
        if (forComponent != null) {
            htmlWriter.writeAttribute("v:for", forComponent);
        }

        String forItemValue = acceleratorComponent
                .getForItemValue(facesContext);
        if (forItemValue != null) {
            htmlWriter.writeAttribute("v:forItemValue", forItemValue);
        }

        KeyTools.State state = KeyTools.parseKeyBinding(keyBinding);

        if (state.character > 0) {
            htmlWriter.writeAttribute("v:character", String
                    .valueOf(state.character));
        }

        if (state.virtualKey != null) {
            htmlWriter.writeAttribute("v:virtualKey", state.virtualKey
                    .intValue());
        }

        if (state.keyFlags > 0) {
            htmlWriter.writeAttribute("v:keyFlags", state.keyFlags);
        }

        htmlWriter.endElement(AbstractJavaScriptRenderer.LAZY_INIT_TAG);

        setAlreadyLazyComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    /*
     * protected IWriter writeIdAttribute(IWriter htmlWriter) throws
     * WriterException { Pas ca car il nous faut un ID ! (en cas de premier
     * composant a initialiser ! if
     * (ComponentTools.isAnonymousComponentId(htmlWriter
     * .getComponentRenderContext().getComponentId())) { return htmlWriter; }
     * 
     * return super.writeIdAttribute(htmlWriter); }
     */

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.ACCELERATOR;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getKeyPressEventName();
    }

    protected boolean sendCompleteComponent() {
        return false;
    }
}
