/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.DateChooserComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
import org.rcfaces.renderkit.html.internal.AbstractCalendarRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlRenderContext;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;
import org.rcfaces.renderkit.html.internal.decorator.AbstractImageButtonFamillyDecorator;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.util.ListenerTools.INameSpace;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class DateChooserRenderer extends AbstractCalendarRenderer {
    private static final String REVISION = "$Revision$";

    private static final String DATE_CHOOSER_IMAGEURL = "dateChooser/dateChooser.gif";

    private static final String DATE_CHOOSER_DISABLED_IMAGEURL = "dateChooser/dateChooser_disabled.gif";

    private static final int DATE_CHOOSER_WIDTH = 16;

    private static final int DATE_CHOOSER_HEIGHT = 16;

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.DATE_CHOOSER;
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator componentDecorator = new DateChooserButtonDecorator(
                (IImageButtonFamilly) component);

        IComponentDecorator parent = super.createComponentDecorator(
                facesContext, component);
        if (parent != null) {
            componentDecorator.addChildDecorator(parent);
        }

        return componentDecorator;
    }

    protected String getActionEventName(INameSpace nameSpace) {
        return nameSpace.getSelectionEventName();
    }

    protected int getDateChooserImageWidth(IHtmlWriter htmlWriter) {
        return DATE_CHOOSER_WIDTH;
    }

    protected int getDateChooserImageHeight(IHtmlWriter htmlWriter) {
        return DATE_CHOOSER_HEIGHT;
    }

    protected IContentAccessor getDateChooserImageAccessor(
            IHtmlWriter htmlWriter) {

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        return htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetContentAccessor(DATE_CHOOSER_IMAGEURL,
                        IContentType.IMAGE);
    }

    public IContentAccessor getDateChooserDisabledImageAccessor(
            IHtmlWriter htmlWriter) {

        IHtmlRenderContext htmlRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext();

        return htmlRenderContext.getHtmlProcessContext()
                .getStyleSheetContentAccessor(DATE_CHOOSER_DISABLED_IMAGEURL,
                        IContentType.IMAGE);
    }

    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        DateChooserComponent dateChooserComponent = (DateChooserComponent) component;

        Date dateValue = (Date) componentData.getProperty("value");
        Date date = null;
        if (dateValue != null
                && dateChooserComponent.isValueLocked(context.getFacesContext()) == false) {
            date = dateValue;
        }

        dateChooserComponent.setSubmittedExternalValue(date);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class DateChooserButtonDecorator extends
            AbstractImageButtonFamillyDecorator {
        private static final String REVISION = "$Revision$";

        private boolean firstLine = true;

        private boolean defaultImageAccessor = false;

        private IContentAccessor imageAccessor;

        public DateChooserButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected String getMainStyleClassName() {
            return DateChooserRenderer.this.getMainStyleClassName();
        }

        protected void writeAttributes(String classSuffix)
                throws WriterException {
            writeHtmlAttributes(writer);
            writeJavaScriptAttributes(writer);
            writeCssAttributes(writer, classSuffix, CSS_ALL_MASK);

            FacesContext facesContext = writer.getComponentRenderContext()
                    .getFacesContext();
            encodeAttributes(facesContext);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {

            if (imageButtonFamilly.isDisabled(facesContext)) {
                writer.writeAttribute("v:disabled", true);
            }

            if (imageButtonFamilly.isReadOnly(facesContext)) {
                writer.writeAttribute("v:readOnly", true);
            }

            IComponentRenderContext componentRenderContext = writer
                    .getComponentRenderContext();
            DateChooserComponent dateChooserComponent = (DateChooserComponent) componentRenderContext
                    .getComponent();

            Calendar componentCalendar = CalendarTools.getCalendar(
                    componentRenderContext.getRenderContext()
                            .getProcessContext(), dateChooserComponent, false);

            writeCalendarAttributes(writer, componentCalendar);

            Date homeDate = dateChooserComponent.getHomeDate(facesContext);
            if (homeDate != null) {

                StringAppender sb = new StringAppender(16);
                appendDate(componentCalendar, homeDate, sb, true);
                writer.writeAttribute("v:homeDate", sb.toString());

                String homeDateLabel = dateChooserComponent
                        .getHomeDateLabel(facesContext);
                if (homeDateLabel != null) {
                    writer.writeAttribute("v:homeDateLabel", homeDateLabel);
                }
            }

            String forComponent = dateChooserComponent.getFor(facesContext);
            if (forComponent != null) {
                writer.writeAttribute("v:for", forComponent);

                String forValueFormat = dateChooserComponent
                        .getForValueFormat(facesContext);
                if (forValueFormat != null) {
                    forValueFormat = CalendarTools.normalizeFormat(writer
                            .getComponentRenderContext(), forValueFormat);

                    writer.writeAttribute("v:forValueFormat", forValueFormat);
                }
            }
        }

        protected void writeEndRow(int nextRowCount) throws WriterException {
            if (firstLine == false) {
                super.writeEndRow(nextRowCount);
                return;
            }

            firstLine = false;

            writeComboImage(nextRowCount);

            super.writeEndRow(nextRowCount);
        }

        protected int computeHorizontalSpan() {
            return super.computeHorizontalSpan() + 1;
        }

        protected IContentAccessor getImageAccessor(IHtmlWriter htmlWriter) {
            if (imageAccessor != null) {
                return imageAccessor;
            }

            imageAccessor = super.getImageAccessor(htmlWriter);
            if (imageAccessor != null) {
                return imageAccessor;
            }

            imageAccessor = getDateChooserImageAccessor(htmlWriter);
            defaultImageAccessor = true;

            imageButtonFamilly
                    .setImageWidth(getDateChooserImageWidth(htmlWriter));
            imageButtonFamilly
                    .setImageHeight(getDateChooserImageHeight(htmlWriter));

            return imageAccessor;
        }

        protected IContentAccessor getDisabledImageAccessor(
                IHtmlWriter htmlWriter) {
            if (defaultImageAccessor) {
                return getDateChooserDisabledImageAccessor(htmlWriter);
            }

            return super.getDisabledImageAccessor(htmlWriter);
        }

        protected IContentAccessor getDateChooserImageAccessor(
                IHtmlWriter htmlWriter) {
            return DateChooserRenderer.this
                    .getDateChooserImageAccessor(htmlWriter);
        }

        protected IContentAccessor getDateChooserDisabledImageAccessor(
                IHtmlWriter htmlWriter) {
            return DateChooserRenderer.this
                    .getDateChooserDisabledImageAccessor(htmlWriter);
        }

        protected int getDateChooserImageHeight(IHtmlWriter htmlWriter) {
            return DateChooserRenderer.this
                    .getDateChooserImageHeight(htmlWriter);
        }

        protected int getDateChooserImageWidth(IHtmlWriter htmlWriter) {
            return DateChooserRenderer.this
                    .getDateChooserImageWidth(htmlWriter);
        }

        protected boolean useImageFilterIfNecessery() {
            return true;
        }

        protected boolean isCompositeComponent() {
            return true;
        }

        protected void writeEndCompositeComponent() throws WriterException {
            if (htmlBorderWriter == null) {
                writeComboImage();
            }

            super.writeEndCompositeComponent();
        }
    }

}
