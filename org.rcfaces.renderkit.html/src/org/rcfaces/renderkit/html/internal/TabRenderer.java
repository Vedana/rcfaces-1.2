/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.CardComponent;
import org.rcfaces.core.component.TabComponent;
import org.rcfaces.core.component.TabbedPaneComponent;
import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
                .getComponentClientId();

        js.writeCall(var, "f_declareTab").writeString(tadComponentId);

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
