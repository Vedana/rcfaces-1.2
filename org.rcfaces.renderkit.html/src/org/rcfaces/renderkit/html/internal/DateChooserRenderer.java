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
 * Revision 1.11  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.10  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.9  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.8  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.7  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.6  2006/05/16 13:58:18  oeuillot
 * Suite de l'impl�mentation du Calendar
 * D�but d'implementation de dateChooser
 * Creation du CalendarObject
 * R�vision et optimisation du modele de chargement des classes
 * Gestion complete des f_bundle
 * Ajout des DatesItems pour la gestion de jours f�riers
 *
 * Revision 1.5  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 * Revision 1.4  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.3  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.2  2005/12/27 16:08:16  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.rcfaces.core.component.DateChooserComponent;
import org.rcfaces.core.component.familly.IImageButtonFamilly;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.tools.CalendarTools;
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

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected class DateChooserButtonDecorator extends
            AbstractImageButtonFamillyDecorator {
        private static final String REVISION = "$Revision$";

        private static final String DATE_CHOOSER_IMAGEURL = "dateChooser/dateChooser.gif";

        private static final int DATE_CHOOSER_WIDTH = 16;

        private static final int DATE_CHOOSER_HEIGHT = 16;

        private boolean firstLine = true;

        public DateChooserButtonDecorator(IImageButtonFamilly imageButtonFamilly) {
            super(imageButtonFamilly);
        }

        protected void writeAttributes(String classSuffix)
                throws WriterException {
            writeHtmlAttributes(writer);
            writeJavaScriptAttributes(writer);
            writeCssAttributes(writer, classSuffix);

            FacesContext facesContext = writer.getComponentRenderContext()
                    .getFacesContext();
            encodeAttributes(facesContext);
        }

        protected void encodeAttributes(FacesContext facesContext)
                throws WriterException {
            writeCalendarAttributes(writer);

            DateChooserComponent dateChooserComponent = (DateChooserComponent) writer
                    .getComponentRenderContext().getComponent();

            Date homeDate = dateChooserComponent.getHomeDate(facesContext);
            if (homeDate != null) {
                Calendar calendar = CalendarTools
                        .getAttributesCalendar(dateChooserComponent);

                StringAppender sb = new StringAppender(16);
                appendDate(calendar, homeDate, sb, true);
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
                    forValueFormat = CalendarTools.normalizeDateFormat(writer
                            .getComponentRenderContext(), forValueFormat);

                    writer.writeAttribute("v:forValueFormat", forValueFormat);
                }
            }
        }

        protected boolean initializeJavaScript() {
            return true;
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

        protected String getImageURL(FacesContext facesContext) {
            String imageURL = super.getImageURL(facesContext);
            if (imageURL != null) {
                return imageURL;
            }

            IHtmlRenderContext htmlRenderContext = (IHtmlRenderContext) writer
                    .getComponentRenderContext().getRenderContext();

            imageURL = htmlRenderContext.getHtmlExternalContext()
                    .getStyleSheetURI(DATE_CHOOSER_IMAGEURL);

            imageButtonFamilly.setImageWidth(DATE_CHOOSER_WIDTH);
            imageButtonFamilly.setImageHeight(DATE_CHOOSER_HEIGHT);

            return imageURL;
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
