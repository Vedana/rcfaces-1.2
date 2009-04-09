/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.renderkit.html.internal.AbstractHtmlRenderer;
import org.rcfaces.renderkit.html.internal.decorator.FilesCollectorDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.FileItemSource;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class AbstractFilesCollectorRenderer extends AbstractHtmlRenderer {

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        return new FilesCollectorDecorator(component);
    }

    protected FileItemSource[] listSources(
            IComponentRenderContext componentRenderContext) {
        FilesCollectorDecorator decorator = (FilesCollectorDecorator) getComponentDecorator(componentRenderContext);

        return decorator.listSources();
    }
}
