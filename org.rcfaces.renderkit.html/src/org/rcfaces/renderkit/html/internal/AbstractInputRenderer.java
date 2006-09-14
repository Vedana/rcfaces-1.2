/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.26  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.25  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.24  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.23  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.22  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.21  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.20  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.19  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.18  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.17  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.16  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.15  2005/02/18 14:46:07  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.14  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.13  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.12  2004/12/22 12:16:25  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.10  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.9  2004/09/01 13:47:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.8  2004/08/31 16:48:58  oeuillot
 * *** empty log message ***
 *
 * Revision 1.7  2004/08/31 15:30:33  oeuillot
 * *** empty log message ***
 *
 * Revision 1.6  2004/08/30 12:19:13  oeuillot
 * *** empty log message ***
 *
 * Revision 1.5  2004/08/12 14:21:06  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.component.capability.IReadOnlyCapability;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractInputRenderer extends AbstractCssRenderer {

    private static final String REVISION = "$Revision$";

    public static final String BUTTON_TYPE = "button";

    public static final String RADIO_TYPE = "radio";

    public static final String CHECKBOX_TYPE = "checkbox";

    public static final String TEXT_TYPE = "text";

    public static final String PASSWORD_TYPE = "password";

    public static final String RESET_TYPE = "reset";

    public static final String SUBMIT_TYPE = "submit";

    public static final String IMAGE_TYPE = "image";

    protected abstract String getInputType(UIComponent component);

    protected IHtmlWriter writeInputAttributes(IHtmlWriter writer)
            throws WriterException {
        UIComponent component = writer.getComponentRenderContext()
                .getComponent();

        return writeInputAttributes(writer, component.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#writeAttributes(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected final IHtmlWriter writeInputAttributes(IHtmlWriter writer,
            String id) throws WriterException {

        IComponentRenderContext componentRenderContext = writer
                .getComponentRenderContext();

        UIComponent component = componentRenderContext.getComponent();

        String name = getInputName(componentRenderContext, id);
        if (name != null) {
            writer.writeAttribute("name", name);
        }

        String type = getInputType(component);
        if (type != null) {
            writer.writeAttribute("type", type);
        }

        if (component instanceof IReadOnlyCapability) {
            writeReadOnly(writer, (IReadOnlyCapability) component);
        }

        if (component instanceof IDisabledCapability) {
            writeEnabled(writer, (IDisabledCapability) component);
        }

        return writer;
    }

    /*
     * Il faut ecrire l'ID de toute facon, car il peut y avoir des regles CSS !
     * protected IWriter writeIdAttribute(IWriter writer) throws WriterException {
     * if (isNameEqualsId()) { return writer; }
     * 
     * return super.writeIdAttribute(writer); }
     */

    protected boolean isNameEqualsId() {
        return false;
    }

    protected String getInputName(
            IComponentRenderContext componentRenderContext, String id) {
        return componentRenderContext.getComponentId();
    }
}