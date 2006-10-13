/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.3  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.33  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.32  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.31  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.30  2006/04/27 13:49:47  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.29  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.28  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.27  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.26  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.25  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.24  2006/01/04 18:54:31  oeuillot
 * Mise au point des popup menus
 *
 * Revision 1.23  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.22  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.21  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.20  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.19  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.18  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.17  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.16  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.15  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.14  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.13  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.12  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.11  2004/12/22 12:16:14  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.10  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.9  2004/09/29 20:49:39  oeuillot
 * *** empty log message ***
 *
 * Revision 1.8  2004/09/24 14:01:35  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.lang.reflect.Array;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.TreeComponent;
import org.rcfaces.core.component.capability.ICardinality;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.ISelectItemNodeWriter;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;
import org.rcfaces.renderkit.html.internal.decorator.TreeDecorator;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TreeRenderer extends AbstractSelectItemsRenderer {
    private static final String REVISION = "$Revision$";

    private static final String NODE_ROW_ID = "#node";

    protected void encodeBegin(IComponentWriter writer) throws WriterException {
        IComponentRenderContext componentContext = writer
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentContext
                .getComponent();
        FacesContext facesContext = componentContext.getFacesContext();

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        htmlWriter.startElement("UL");
        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);

        if (treeComponent.isCheckable(facesContext)) {
            int cardinality = treeComponent.getCheckCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ICardinality.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:checkCardinality", cardinality);

            if (treeComponent.isClientCheckFullState(facesContext)) {
                htmlWriter.writeAttribute("v:clientCheckFullState", "true");
            }
        }

        if (treeComponent.isSelectable(facesContext)) {
            int cardinality = treeComponent
                    .getSelectionCardinality(facesContext);
            if (cardinality == 0) {
                cardinality = ICardinality.DEFAULT_CARDINALITY;
            }

            htmlWriter.writeAttribute("v:selectionCardinality", cardinality);

            if (treeComponent.isClientSelectionFullState(facesContext)) {
                htmlWriter.writeAttribute("v:clientSelectionFullState", "true");
            }
        }

        if (treeComponent.isUserExpandable(facesContext) == false) {
            htmlWriter.writeAttribute("v:userExpandable", "false");
        }

        if (treeComponent.isHideRootExpandSign(facesContext)) {
            htmlWriter.writeAttribute("v:hideRootExpandSign", "true");
        }

        int depthLevel = treeComponent.getPreloadedLevelDepth(facesContext);
        if (depthLevel > 0) {
            htmlWriter.writeAttribute("v:preloadedLevelDepth", depthLevel);
        }

        super.encodeBegin(htmlWriter);
    }

    protected void encodeAfterDecorator(IHtmlWriter htmlWriter,
            IComponentDecorator componentDecorator) throws WriterException {
        super.encodeAfterDecorator(htmlWriter, componentDecorator);

        htmlWriter.endElement("UL");
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                htmlWriter).getJavaScriptRenderContext();

        TreeComponent treeComponent = (TreeComponent) htmlWriter
                .getComponentRenderContext().getComponent();
        IMenuIterator menuIterator = treeComponent.listMenus();
        if (menuIterator.hasNext()) {

            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TREE, "menu");
        }

        if (treeComponent.getPreloadedLevelDepth(facesContext) > 0) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TREE, "ajax");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#getJavaScriptClassName()
     */
    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TREE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer#writeCustomCss(org.rcfaces.core.internal.renderkit.IWriter,
     *      org.rcfaces.core.internal.renderkit.html.AbstractCssRenderer.CssWriter)
     */
    protected void writeCustomCss(IHtmlWriter htmlWriter, ICssWriter cssWriter) {
        super.writeCustomCss(htmlWriter, cssWriter);

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TreeComponent treeComponent = (TreeComponent) componentRenderContext
                .getComponent();
        FacesContext facesContext = componentRenderContext.getFacesContext();

        if (treeComponent.getWidth(facesContext) != null
                || treeComponent.getHeight(facesContext) != null) {
            cssWriter.writeOverflow("auto");
        }

        if (treeComponent.isBorder(facesContext) == false) {
            cssWriter.writeBorderStyle("none");
        }
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = new TreeDecorator(
                (TreeComponent) component);

        TreeComponent treeComponent = (TreeComponent) component;

        IComponentDecorator menuDecorators = null;
        IMenuIterator menuIterator = treeComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getItemImageWidth(menuComponent),
                    getItemImageHeight(menuComponent));

            if (menuDecorators == null) {
                menuDecorators = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(menuDecorators);
            menuDecorators = menuDecorator;
        }

        if (menuDecorators != null) {
            decorator.addChildDecorator(menuDecorators);
        }

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    public void encodeNodes(IJavaScriptWriter jsWriter,
            TreeComponent treeComponent, ISelectItemNodeWriter nodeRenderer,
            int depth, String containerVarId) throws WriterException {

        TreeDecorator selectItemNodeWriter = (TreeDecorator) getComponentDecorator(jsWriter
                .getComponentRenderContext());

        selectItemNodeWriter.encodeNodes(jsWriter, treeComponent, nodeRenderer,
                depth, containerVarId);
    }

    public ISelectItemNodeWriter getSelectItemNodeWriter(
            IComponentRenderContext componentRenderContext) {
        return (ISelectItemNodeWriter) getComponentDecorator(componentRenderContext);
    }

    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException {

        if (submittedValue == null
                || submittedValue.getClass().isArray() == false
                || Array.getLength(submittedValue) < 1) {
            return super.getConvertedValue(context, component, submittedValue);
        }

        Object array[] = (Object[]) submittedValue;
        Object ret[] = new Object[array.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = super.getConvertedValue(context, component, array[i]);
        }

        return ret;
    }
}