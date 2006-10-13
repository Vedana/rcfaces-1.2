/*
 * $Id$
 *
 * $Log$
 * Revision 1.5  2006/10/13 18:04:38  oeuillot
 * Ajout de:
 * DateEntry
 * StyledMessage
 * MessageFieldSet
 * xxxxConverter
 * Adapter
 *
 * Revision 1.4  2006/10/04 12:31:42  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.39  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.38  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.37  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.36  2006/05/19 20:40:42  oeuillot
 * Ajout de la gestion du disabled pour le treeNode
 * Generalisation du fa_cardinality
 * Ajout de la cardinalit� de selection pour l'arbre
 * Correction des Sets javascript
 * Optimisation importante des perfs du javascript
 *
 * Revision 1.35  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.34  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.33  2006/03/23 19:12:39  oeuillot
 * Ajout des marges
 * Ajout des processors
 * Amelioration des menus
 *
 * Revision 1.32  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.31  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.30  2006/02/03 11:37:32  oeuillot
 * Calcule les classes pour le Javascript, plus les fichiers !
 *
 * Revision 1.29  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.28  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.27  2005/12/28 11:12:48  oeuillot
 * Ajout des writer de Menu et ImageButton
 * Split de l'aspect fa_menu
 * Gestion de l'heritage d'aspect !
 *
 * Revision 1.26  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.25  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.24  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.23  2005/10/28 14:41:50  oeuillot
 * InteractiveRenderer, CardBox, Card
 * Corrections de validations
 * PasswordEntry
 *
 * Revision 1.22  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.21  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.20  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.19  2005/03/07 10:47:03  oeuillot
 * Systeme de Logging
 * Debuggage
 *
 * Revision 1.18  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.17  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.16  2004/12/24 15:10:04  oeuillot
 * Refonte des tabbedPanes
 * Correction de problemes de value sur FieldSet nottament
 *
 * Revision 1.15  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.14  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.rcfaces.core.component.IClientValidator;
import org.rcfaces.core.component.IMenuComponent;
import org.rcfaces.core.component.MenuComponent;
import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.component.iterator.IMenuIterator;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.config.ClientValidatorsRegistryImpl.ClientValidator;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IComponentWriter;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.CommandParserIterator;
import org.rcfaces.core.internal.util.CommandParserIterator.ICommand;
import org.rcfaces.core.internal.validator.IClientValidatorDescriptor;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.validator.IParameter;
import org.rcfaces.renderkit.html.internal.decorator.IComponentDecorator;
import org.rcfaces.renderkit.html.internal.decorator.SubMenuDecorator;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEntryRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    private static final IClientValidatorDescriptor AUTO_TAB_VALIDATOR_DESCRIPTOR = new ClientValidator();

    private static final String VALIDATOR_COMMAND = "camelia.validator.command";

    private static final String VALIDATOR_DESCRIPTOR = "camelia.validator.descriptor";

    private static final String DEFAULT_VALIDATOR_REQUIRED_CLASSES[] = { "f_vb" };

    private static final boolean ALLOCATE_VALIDATOR_PARAMETERS = false;

    // private static final boolean ATTRIBUTE_VALIDATOR = true;

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.render.Renderer#encodeBegin(javax.faces.context.FacesContext,
     *      javax.faces.component.UIComponent)
     * 
     * public void encodeBegin(IWriter writer) throws WriterException {
     * super.encodeBegin(writer); }
     */

    protected void writeTextEntryAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TextEntryComponent textEntryComponent = (TextEntryComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        int maxTextLength = textEntryComponent.getMaxTextLength(facesContext);
        if (maxTextLength > 0) {
            htmlWriter.writeAttribute("maxLength", maxTextLength);
        }

        int iCol = textEntryComponent.getColumnNumber(facesContext);
        if (iCol > 0) {
            htmlWriter.writeAttribute("size", iCol);
        }

        if (textEntryComponent.isAutoCompletion(facesContext) == false) {
            htmlWriter.writeAttribute("autocomplete", "off");
        }
    }

    protected void writeValidatorAttributes(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        TextEntryComponent textEntryComponent = (TextEntryComponent) componentRenderContext
                .getComponent();

        String helpMessage = textEntryComponent.getHelpMessage(facesContext);
        if (helpMessage != null) {
            htmlWriter.writeAttribute("v:helpMessage", helpMessage);

            htmlWriter.enableJavaScript();
        }

        String emptyMessage = textEntryComponent.getEmptyMessage(facesContext);
        if (emptyMessage != null) {
            htmlWriter.writeAttribute("v:emptyMessage", emptyMessage);

            htmlWriter.enableJavaScript();
        }

        boolean useValidator = false;

        if (textEntryComponent.isAutoTab(facesContext)) {
            htmlWriter.writeAttribute("v:autoTab", "true");

            // C'est un validateur, il faut forcer le stub pour le RESET
            useValidator = true;
        }

        boolean renderValidator = false;

        String validator = textEntryComponent.getClientValidator(facesContext);
        if (validator != null) {
            renderValidator = installValidator(componentRenderContext,
                    validator);

            // C'est un validateur, il faut forcer le stub pour le RESET
            useValidator = true;
        }

        if (renderValidator == false) {
            if (textEntryComponent.getValidationParametersCount(facesContext) > 0) {
                renderValidator = true;
            }
        }

        if (renderValidator) {
            renderAttributeValidator(htmlWriter);
        }

        Validator validators[] = textEntryComponent.getValidators();
        if (validators != null && validators.length > 0) {
            appendValidators(facesContext, htmlWriter, validators);
        }

        if (htmlWriter.isJavaScriptEnabled() == false) {
            if (textEntryComponent.isRequired()) {
                // Il nous faut le javascript, car c'est un traitement
                // javascript !

                // C'est un validateur, il faut forcer le stub pour le RESET
                useValidator = true;

            } else if (textEntryComponent.getFocusStyleClass(facesContext) != null) {
                htmlWriter.enableJavaScript();
            }
        }

        if (useValidator) {
            htmlWriter.getHtmlComponentRenderContext().getHtmlRenderContext()
                    .getJavaScriptRenderContext().forceJavaScriptStub();
        }
    }

    protected boolean useHtmlAccessKeyAttribute() {
        return true;
    }

    protected void writeValueAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        TextEntryComponent textEntryComponent = (TextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        String text = convertValue(facesContext, textEntryComponent, null);
        if (text != null) {
            htmlWriter.writeAttribute("value", text);
        }
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {

        htmlWriter.startElement("INPUT");

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);
        writeCssAttributes(htmlWriter);
        writeInputAttributes(htmlWriter);
        writeTextEntryAttributes(htmlWriter);
        writeValidatorAttributes(htmlWriter);
        writeValueAttributes(htmlWriter);

        htmlWriter.endElement("INPUT");
    }

    protected boolean isNameEqualsId() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.AbstractCameliaRenderer#encodeEnd(org.rcfaces.core.internal.renderkit.IWriter)
     */
    protected void encodeEnd(IComponentWriter writer) throws WriterException {

        IHtmlWriter htmlWriter = (IHtmlWriter) writer;

        // Il faut ecrire la VALUE à la fin, car des converters peuvent être
        // insérés entre le tag !
        encodeComponent(htmlWriter);

        super.encodeEnd(htmlWriter);
    }

    protected boolean installValidator(IComponentRenderContext renderContext,
            String validator) {
        Iterator it = new CommandParserIterator(validator);
        if (it.hasNext() == false) {
            return false;
        }

        CommandParserIterator.ICommand command = (ICommand) it.next();

        if (it.hasNext()) {
            throw new FacesException(
                    "Validator does not support multiple expression.");
        }

        FacesContext facesContext = renderContext.getFacesContext();

        IClientValidatorsRegistry clientValidatorManager = RcfacesContext
                .getInstance(facesContext).getClientValidatorsRegistry();
        if (clientValidatorManager == null) {
            throw new FacesException(
                    "Can not get descriptorManager from faces context !");
        }

        IClientValidatorDescriptor validatorDescriptor = clientValidatorManager
                .getClientValidatorById(facesContext, command.getName());
        if (validatorDescriptor == null) {
            throw new FacesException("Can not find validator '"
                    + command.getName() + "' for component '"
                    + renderContext.getComponentClientId() + "' !");
        }

        renderContext.setAttribute(VALIDATOR_COMMAND, command);
        renderContext.setAttribute(VALIDATOR_DESCRIPTOR, validatorDescriptor);

        return true;
    }

    protected void renderAttributeValidator(IHtmlWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        IValidationParameters validationCapability = (IValidationParameters) componentRenderContext
                .getComponent();

        Map parametersMap = validationCapability
                .getClientValidationParametersMap();
        if (parametersMap.isEmpty() == false) {
            // On travaille avec une copie ... car on fait des removes aprés...
            parametersMap = new HashMap(parametersMap);
        }

        List params = new ArrayList(8);

        IClientValidatorDescriptor validatorDescriptor = (IClientValidatorDescriptor) componentRenderContext
                .getAttribute(VALIDATOR_DESCRIPTOR);
        if (validatorDescriptor != null) {
            CommandParserIterator.ICommand command = (ICommand) componentRenderContext
                    .getAttribute(VALIDATOR_COMMAND);

            IParameter expressionParameters[] = command.listParameters();
            IParameter defaultParameters[] = validatorDescriptor
                    .listParameters();
            if ((expressionParameters != null && expressionParameters.length > 0)
                    || (defaultParameters != null && defaultParameters.length > 0)) {

                int i = 0;

                for (; i < expressionParameters.length; i++) {
                    IParameter parameter = expressionParameters[i];

                    String name;
                    if (i < defaultParameters.length) {
                        name = defaultParameters[i].getName();

                    } else {
                        name = parameter.getName();
                    }
                    params.add(name);

                    // Les valeurs de l'expression sont prioritaires
                    parametersMap.remove(name);

                    String value = parameter.getValue();
                    params.add(value);
                }

                for (; i < defaultParameters.length; i++) {
                    IParameter parameter = defaultParameters[i];

                    String name = parameter.getName();
                    params.add(name);

                    // Les valeurs des parametres sont prioritaires
                    String value = (String) parametersMap.remove(name);
                    if (value == null) {
                        value = parameter.getValue();
                    }

                    params.add(value);
                }
            }
        }

        if (parametersMap.isEmpty() == false) {
            for (Iterator it = parametersMap.entrySet().iterator(); it
                    .hasNext();) {

                Map.Entry entry = (Map.Entry) it.next();

                params.add(entry.getKey());
                params.add(entry.getValue());
            }
        }

        StringAppender sb = new StringAppender(128);
        if (params.isEmpty() == false) {
            for (Iterator it = params.iterator(); it.hasNext();) {
                String value = (String) it.next();

                if (sb.length() > 0) {
                    sb.append(':');
                }
                if (value == null) {
                    value = "%";
                }

                EventsRenderer.appendCommand(sb, value);
            }
        }

        // Meme vide ! Car c'est cet attribut qui spécifie qu'il y a un
        // validateur !
        htmlWriter.writeAttribute("v:clientValidator", sb.toString());

        if (validatorDescriptor != null) {
            String filter = validatorDescriptor.getFilterCall();
            if (filter != null) {
                htmlWriter.writeAttribute("v:vFilter", filter);
            }
            String translator = validatorDescriptor.getTranslatorCall();
            if (translator != null) {
                htmlWriter.writeAttribute("v:vTranslator", translator);
            }
            String checker = validatorDescriptor.getCheckerCall();
            if (checker != null) {
                htmlWriter.writeAttribute("v:vChecker", checker);
            }
            String formatter = validatorDescriptor.getFormatterCall();
            if (formatter != null) {
                htmlWriter.writeAttribute("v:vFormatter", formatter);
            }
            String behavior = validatorDescriptor.getBehaviorCall();
            if (behavior != null) {
                htmlWriter.writeAttribute("v:vBehavior", behavior);
            }
            String error = validatorDescriptor.getOnErrorCall();
            if (error != null) {
                htmlWriter.writeAttribute("v:vError", error);
            }
            String checkError = validatorDescriptor.getOnCheckErrorCall();
            if (checkError != null) {
                htmlWriter.writeAttribute("v:vCheckError", checkError);
            }
            String converter = validatorDescriptor.getConverter();
            if (converter != null) {
                htmlWriter.writeAttribute("v:converter", converter);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractInputRenderer#getInputType()
     */
    protected String getInputType(UIComponent component) {
        return TEXT_TYPE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.CameliaRenderer#decode(javax.faces.component.UIComponent,
     *      org.rcfaces.core.internal.renderkit.IComponentData)
     */
    protected void decode(IRequestContext context, UIComponent component,
            IComponentData componentData) {
        super.decode(context, component, componentData);

        FacesContext facesContext = context.getFacesContext();

        TextEntryComponent textEntryComponent = (TextEntryComponent) component;

        String newValue = componentData.getStringProperty("internal");
        if (newValue == null) {
            // Ce cas peut subvenir quand le TEXT-ENTRY est disabled ...
            newValue = componentData.getStringProperty("text");

            if (newValue == null) {
                // Toujours rien ... on essaye les données du form !

                newValue = componentData.getComponentParameter();
            }
        }

        if (newValue != null) {
            textEntryComponent.setSubmittedValue(newValue);
        }

        Boolean autoComplete = componentData.getBooleanProperty("autoComplete");
        if (autoComplete != null) {
            boolean old = textEntryComponent.isAutoCompletion(facesContext);

            if (autoComplete.booleanValue() != old) {
                textEntryComponent.setAutoCompletion(autoComplete
                        .booleanValue());

                component.queueEvent(new PropertyChangeEvent(component,
                        Properties.AUTO_COMPLETION, Boolean.valueOf(old),
                        autoComplete));
            }

        }
    }

    protected final void writeClientValidator(IJavaScriptWriter htmlWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        CommandParserIterator.ICommand command = (ICommand) componentRenderContext
                .getAttribute(VALIDATOR_COMMAND);
        IClientValidatorDescriptor validatorDescriptor = (IClientValidatorDescriptor) componentRenderContext
                .getAttribute(VALIDATOR_DESCRIPTOR);

        List params = new ArrayList();
        IParameter cParameters[] = command.listParameters();
        IParameter vParameters[] = validatorDescriptor.listParameters();
        if ((cParameters != null && cParameters.length > 0)
                || (vParameters != null && vParameters.length > 0)) {

            int i = 0;

            for (; i < cParameters.length; i++) {
                IParameter parameter = cParameters[i];

                String name;
                if (i < vParameters.length) {
                    name = vParameters[i].getName();

                } else {
                    name = parameter.getName();
                }

                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    name = htmlWriter.allocateString(name);
                }
                params.add(name);

                String value = parameter.getValue();
                // Value peut etre null !
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    value = htmlWriter.allocateString(value);
                }
                params.add(value);
            }

            for (; i < vParameters.length; i++) {
                IParameter parameter = vParameters[i];

                String name = parameter.getName();
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    name = htmlWriter.allocateString(name);
                }
                params.add(name);

                String value = parameter.getValue();
                // Value peut etre null !
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    value = htmlWriter.allocateString(value);
                }
                params.add(value);
            }

        }

        htmlWriter.write("var validator=").writeCall("f_clientValidator",
                "f_newInstance").write(htmlWriter.getComponentVarName());

        if (params.isEmpty() == false) {
            int pred = 0;

            for (Iterator it = params.iterator(); it.hasNext();) {
                String value = (String) it.next();

                if (value == null) {
                    pred++;
                    continue;
                }

                for (; pred > 0; pred--) {
                    htmlWriter.write(',').writeNull();
                }

                htmlWriter.write(',');
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    htmlWriter.write(value);

                } else {
                    htmlWriter.writeString(value);
                }
            }
        }

        htmlWriter.writeln(");");

        String filter = validatorDescriptor.getFilterCall();
        if (filter != null) {
            htmlWriter.writeCall("validator", "f_addFilter")
                    .writeSymbol(filter).writeln(");");
        }
        String translator = validatorDescriptor.getTranslatorCall();
        if (translator != null) {
            htmlWriter.writeCall("validator", "f_addTranslator").writeSymbol(
                    translator).writeln(");");
        }
        String checker = validatorDescriptor.getCheckerCall();
        if (checker != null) {
            htmlWriter.writeCall("validator", "f_addChecker").writeSymbol(
                    checker).writeln(");");
        }
        String formatter = validatorDescriptor.getFormatterCall();
        if (formatter != null) {
            htmlWriter.writeCall("validator", "f_addFormatter").writeSymbol(
                    formatter).writeln(");");
        }
        String behavior = validatorDescriptor.getBehaviorCall();
        if (behavior != null) {
            htmlWriter.writeCall("validator", "f_addBehavior").writeSymbol(
                    behavior).writeln(");");
        }
        String error = validatorDescriptor.getOnErrorCall();
        if (error != null) {
            htmlWriter.writeCall("validator", "f_addOnError")
                    .writeSymbol(error).writeln(");");
        }
        String checkError = validatorDescriptor.getOnCheckErrorCall();
        if (checkError != null) {
            htmlWriter.writeCall("validator", "f_addOnCheckError").writeSymbol(
                    checkError).writeln(");");
        }
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_ENTRY;
    }

    protected void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        IJavaScriptRenderContext javaScriptRenderContext = getHtmlRenderContext(
                htmlWriter).getJavaScriptRenderContext();

        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();
        FacesContext facesContext = componentRenderContext.getFacesContext();
        TextEntryComponent component = (TextEntryComponent) componentRenderContext
                .getComponent();

        IClientValidatorDescriptor validatorDescriptor = (IClientValidatorDescriptor) componentRenderContext
                .getAttribute(VALIDATOR_DESCRIPTOR);
        if (validatorDescriptor != null) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TEXT_ENTRY, "validator");

            String requiredClasses[] = validatorDescriptor
                    .listRequiredClasses();
            if (requiredClasses == null) {
                requiredClasses = DEFAULT_VALIDATOR_REQUIRED_CLASSES;
            }

            if (requiredClasses != null && requiredClasses.length > 0) {
                classes.addAll(Arrays.asList(requiredClasses));
            }
        }

        if (component.isAutoTab(facesContext) || component.isRequired()) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.TEXT_ENTRY, "features");
        }

        IMenuIterator menuIterator = component.listMenus();
        if (menuIterator.hasNext()) {
            javaScriptRenderContext.appendRequiredClasses(classes,
                    JavaScriptClasses.BOX, "menu");
        }
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    protected IComponentDecorator createComponentDecorator(
            FacesContext facesContext, UIComponent component) {

        IComponentDecorator decorator = null;

        TextEntryComponent textEntryComponent = (TextEntryComponent) component;

        IMenuIterator menuIterator = textEntryComponent.listMenus();
        for (; menuIterator.hasNext();) {
            MenuComponent menuComponent = menuIterator.next();

            IComponentDecorator menuDecorator = new SubMenuDecorator(
                    menuComponent, menuComponent.getMenuId(), null,
                    menuComponent.isRemoveAllWhenShown(facesContext),
                    getItemImageWidth(menuComponent),
                    getItemImageHeight(menuComponent));

            if (decorator == null) {
                decorator = menuDecorator;
                continue;
            }

            menuDecorator.addChildDecorator(decorator);
            decorator = menuDecorator;
        }

        return decorator;
    }

    protected int getItemImageHeight(IMenuComponent menuComponent) {
        return -1;
    }

    protected int getItemImageWidth(IMenuComponent menuComponent) {
        return -1;
    }

    private void appendValidators(FacesContext facesContext,
            IHtmlWriter writer, Validator[] validators) throws WriterException {

        IClientValidatorsRegistry clientValidatorManager = RcfacesContext
                .getInstance(facesContext).getClientValidatorsRegistry();
        if (clientValidatorManager == null) {
            throw new FacesException(
                    "Can not get validator registry from faces context !");
        }

        IRenderContext renderContext = writer.getComponentRenderContext()
                .getRenderContext();

        List vls = null;

        int expressionsLength = 0;

        for (int i = 0; i < validators.length; i++) {
            Validator validator = validators[i];

            String expression = null;
            if (validator instanceof IClientValidator) {
                IClientValidator clientValidator = (IClientValidator) validator;

                expression = clientValidator.getExpression();

            } else {
                expression = clientValidatorManager
                        .convertFromValidatorToExpression(renderContext,
                                validator);
            }

            if (expression == null || expression.length() < 1) {
                continue;
            }

            if (vls == null) {
                vls = new ArrayList(validators.length - i);
            }

            vls.add(expression);
            expressionsLength += expression.length();
        }

        if (vls == null) {
            return;
        }

        StringAppender sa = new StringAppender(expressionsLength + vls.size()
                * 8);

        for (Iterator it = vls.iterator(); it.hasNext();) {
            String expression = (String) it.next();

            if (sa.length() > 0) {
                sa.append('|');
            }

            EventsRenderer.appendCommand(sa, expression);
        }

        writer.writeAttribute("v:validators", sa.toString());
    }
}
