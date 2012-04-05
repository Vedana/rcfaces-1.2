/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.context.FacesContext;

import org.rcfaces.core.component.HeadingComponent;
import org.rcfaces.core.component.TextComponent;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.renderkit.html.internal.IHtmlElements;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HeadingRenderer extends TextRenderer {

    @Override
    protected String getTextElement(IHtmlWriter htmlWriter) {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        HeadingComponent textComponent = (HeadingComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        int ariaLevel = textComponent.getLevel(facesContext);

        if (ariaLevel < 1) {
            ariaLevel = textComponent.getAriaLevel();
        }

        if (ariaLevel > 0) {
            return IHtmlElements.H_BASE + ariaLevel;
        }

        return super.getTextElement(htmlWriter);
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.HEADING;
    }

    @Override
    protected ICssStyleClasses createStyleClasses(IHtmlWriter htmlWriter) {
        ICssStyleClasses cssStyleClasses = super.createStyleClasses(htmlWriter);

        HeadingComponent headingComponent = (HeadingComponent) htmlWriter
                .getComponentRenderContext().getComponent();
        int ariaLevel = headingComponent.getLevel();

        if (ariaLevel < 1) {
            ariaLevel = headingComponent.getAriaLevel();
        }

        if (ariaLevel > 0) {
            cssStyleClasses.addSuffix("_h" + ariaLevel);
        }

        return cssStyleClasses;
    }

    @Override
    protected void writeAriaLevelAttribute(IHtmlWriter htmlWriter,
            TextComponent textComponent) throws WriterException {
        int ariaLevel = ((HeadingComponent) textComponent).getLevel(htmlWriter
                .getComponentRenderContext().getFacesContext());

        if (ariaLevel > 0) {
            return;
        }

        super.writeAriaLevelAttribute(htmlWriter, textComponent);
    }

}