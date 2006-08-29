/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.13  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.12  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.11  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.10  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.9  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.8  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.7  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.5  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.4  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.3  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 * Revision 1.2  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.1  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.TabComponent;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class TabRenderer extends CardRenderer {
    private static final String REVISION = "$Revision$";

    private static final String TAB = "_tab";

    protected String getDefaultCardStyleClassPrefix() {
        return JavaScriptClasses.TABBED_PANE;
    }

    protected String getCardStyleClassSuffix() {
        return TAB;
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TAB;
    }

    protected boolean declareCard(IJavaScriptWriter js,
            CardComponent cardComponent, String var, boolean selected)
            throws WriterException {

        TabComponent tab = (TabComponent) cardComponent;
        TabbedPaneComponent tabbedPane = tab.getTabbedPane();

        IHtmlWriter writer = js.getWriter();

        IHtmlRenderContext htmlRenderContext = getHtmlRenderContext(writer);

        FacesContext facesContext = js.getFacesContext();

        String imageURL = tab.getImageURL(facesContext);
        if (imageURL != null) {
            imageURL = js.allocateString(imageURL);
        }

        String disabledImageURL = tab.getDisabledImageURL(facesContext);
        if (disabledImageURL != null) {
            disabledImageURL = js.allocateString(disabledImageURL);
        }

        String selectedImageURL = tab.getSelectedImageURL(facesContext);
        if (selectedImageURL != null) {
            selectedImageURL = js.allocateString(selectedImageURL);
        }

        String hoverImageURL = tab.getHoverImageURL(facesContext);
        if (hoverImageURL != null) {
            hoverImageURL = js.allocateString(hoverImageURL);
        }

        String tadComponentId = writer.getComponentRenderContext()
                .getComponentId();

        js.writeCall(var, "_declareTab").writeString(tadComponentId);

        int pred = 0;

        if (selected) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }

            js.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        String text = tab.getText(facesContext);
        if (text != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').writeString(text);
        } else {
            pred++;
        }

        String accessKey = tab.getAccessKey(facesContext);
        if (accessKey != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').writeString(accessKey);
        } else {
            pred++;
        }

        boolean asyncRender = false;

        if (selected == false) {
            if (htmlRenderContext.isAsyncRenderEnable()) {
                if (tabbedPane.getAsyncRenderMode(facesContext) != IAsyncRenderModeCapability.NONE_ASYNC_RENDER_MODE) {
                    getHtmlRenderContext(writer)
                            .pushInteractiveRenderComponent(writer);

                    asyncRender = true;
                }
            }
        }

        setAsyncRenderer(writer, tab, asyncRender);

        if (tab.isDisabled(facesContext)) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').writeBoolean(true);
        } else {
            pred++;
        }

        if (imageURL != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').write(imageURL);
        } else {
            pred++;
        }

        if (disabledImageURL != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').write(disabledImageURL);
        } else {
            pred++;
        }

        if (selectedImageURL != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').write(selectedImageURL);
        } else {
            pred++;
        }

        if (hoverImageURL != null) {
            for (; pred > 0; pred--) {
                js.write(',').writeNull();
            }
            js.write(',').write(hoverImageURL);
        } else {
            pred++;
        }

        js.writeln(");");

        return asyncRender;
    }

}
