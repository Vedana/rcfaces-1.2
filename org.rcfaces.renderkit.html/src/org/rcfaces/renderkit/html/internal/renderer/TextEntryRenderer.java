/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.IClientValidator;
import org.rcfaces.core.component.TextEntryComponent;
import org.rcfaces.core.event.PropertyChangeEvent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.config.ClientValidatorsRegistryImpl.ClientValidator;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.manager.IValidationParameters;
import org.rcfaces.core.internal.renderkit.IComponentData;
import org.rcfaces.core.internal.renderkit.IComponentRenderContext;
import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IRequestContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.util.CommandParserIterator;
import org.rcfaces.core.internal.util.ParamUtils;
import org.rcfaces.core.internal.util.CommandParserIterator.ICommand;
import org.rcfaces.core.internal.validator.IClientValidatorDescriptor;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.validator.IServerConverter;
import org.rcfaces.core.internal.validator.ITaskDescriptor;
import org.rcfaces.core.validator.IBehaviorTask;
import org.rcfaces.core.validator.ICheckerTask;
import org.rcfaces.core.validator.IClientValidatorContext;
import org.rcfaces.core.validator.IFilterTask;
import org.rcfaces.core.validator.IFormatterTask;
import org.rcfaces.core.validator.IOnErrorTask;
import org.rcfaces.core.validator.IParameter;
import org.rcfaces.core.validator.ITranslatorTask;
import org.rcfaces.renderkit.html.internal.AbstractInputRenderer;
import org.rcfaces.renderkit.html.internal.EventsRenderer;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;
import org.rcfaces.renderkit.html.internal.IJavaScriptRenderContext;
import org.rcfaces.renderkit.html.internal.IJavaScriptWriter;
import org.rcfaces.renderkit.html.internal.JavaScriptClasses;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEntryRenderer extends AbstractInputRenderer {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(TextEntryRenderer.class);

    private static final IClientValidatorDescriptor AUTO_TAB_VALIDATOR_DESCRIPTOR = new ClientValidator();

    private static final String VALIDATOR_COMMAND = "camelia.validator.command";

    private static final String VALIDATOR_DESCRIPTOR = "camelia.validator.descriptor";

    private static final String DEFAULT_VALIDATOR_REQUIRED_CLASSES[] = { "f_vb" };

    private static final boolean ALLOCATE_VALIDATOR_PARAMETERS = false;

    private static final String VALUE_ATTRIBUTE = "camelia.validator.value";

    // private static final boolean ATTRIBUTE_VALIDATOR = true;

    protected void writeTextEntryAttributes(IHtmlWriter htmlWriter)
            throws WriterException {
        IComponentRenderContext componentRenderContext = htmlWriter
                .getComponentRenderContext();

        TextEntryComponent textEntryComponent = (TextEntryComponent) componentRenderContext
                .getComponent();

        FacesContext facesContext = componentRenderContext.getFacesContext();

        int maxTextLength = textEntryComponent.getMaxTextLength(facesContext);
        if (maxTextLength > 0) {
            htmlWriter.writeMaxLength(maxTextLength);
        }

        int iCol = textEntryComponent.getColumnNumber(facesContext);
        if (iCol > 0) {
            htmlWriter.writeSize(iCol);
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
            helpMessage = ParamUtils.formatMessage(textEntryComponent,
                    helpMessage);

            htmlWriter.writeAttribute("v:helpMessage", helpMessage);

            htmlWriter.enableJavaScript();
        }

        String emptyMessage = textEntryComponent.getEmptyMessage(facesContext);
        if (emptyMessage != null) {
            emptyMessage = ParamUtils.formatMessage(textEntryComponent,
                    emptyMessage);

            htmlWriter.writeAttribute("v:emptyMessage", emptyMessage);

            htmlWriter.enableJavaScript();
        }

        boolean useValidator = false;

        if (textEntryComponent.isAutoTab(facesContext)) {
            htmlWriter.writeAttribute("v:autoTab", true);

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

        String text = (String) htmlWriter.getComponentRenderContext()
                .getAttribute(VALUE_ATTRIBUTE);
        if (text != null) {
            htmlWriter.writeValue(text);
            return;
        }

        TextEntryComponent textEntryComponent = (TextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        text = convertValue(facesContext, textEntryComponent);
        if (text != null) {
            htmlWriter.writeValue(text);
        }
    }

    protected void encodeComponent(IHtmlWriter htmlWriter)
            throws WriterException {
        TextEntryComponent textEntryComponent = (TextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        // FacesContext facesContext =
        // htmlWriter.getComponentRenderContext().getFacesContext();

        htmlWriter.startElement(IHtmlWriter.INPUT);

        writeHtmlAttributes(htmlWriter);
        writeJavaScriptAttributes(htmlWriter);

        writeValidatorAttributes(htmlWriter); // Le validateur peut influencer
        // sur le CSS !

        writeCssAttributes(htmlWriter);

        writeInputAttributes(htmlWriter);
        writeTextEntryAttributes(htmlWriter);
        writeValueAttributes(htmlWriter);
        writeTextDirection(htmlWriter, textEntryComponent);
        writeAlternateText(htmlWriter, textEntryComponent);

        htmlWriter.endElement(IHtmlWriter.INPUT);
    }

    protected boolean isNameEqualsId() {
        return true;
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

        IClientValidatorsRegistry clientValidatorManager = renderContext
                .getRenderContext().getProcessContext().getRcfacesContext()
                .getClientValidatorsRegistry();
        if (clientValidatorManager == null) {
            // throw new FacesException("Can not get descriptorManager from
            // faces context !");

            // Designer mode
            return false;
        }

        IProcessContext processContext = renderContext.getRenderContext()
                .getProcessContext();
        Locale locale = processContext.getUserLocale();
        TimeZone timeZone = processContext.getUserTimeZone();

        IClientValidatorDescriptor validatorDescriptor = clientValidatorManager
                .getClientValidatorById(facesContext, command.getName(),
                        locale, timeZone);
        if (validatorDescriptor == null) {
            throw new FacesException("Can not find validator '"
                    + command.getName() + "' for component '"
                    + renderContext.getComponentClientId() + "' !");
        }

        renderContext.setAttribute(VALIDATOR_COMMAND, command);
        renderContext.setAttribute(VALIDATOR_DESCRIPTOR, validatorDescriptor);

        UIComponent component = renderContext.getComponent();
        if (component instanceof ValueHolder) {
            ValueHolder valueHolder = (ValueHolder) component;

            if (valueHolder.getConverter() == null) {
                IServerConverter serverConverter = validatorDescriptor
                        .getServerConverter();

                if (serverConverter != null) {
                    Converter converter = computeConverter(facesContext,
                            serverConverter, command.getName());

                    if (converter != null) {
                        valueHolder.setConverter(converter);
                    }
                }
            }
        }

        return true;
    }

    private Converter computeConverter(FacesContext facesContext,
            IServerConverter serverConverter, String validatorId) {
        Converter converter = serverConverter.getInstance(facesContext);

        if (converter == null) {
            throw new FacesException(
                    "Invalid server converter for validatorId='" + validatorId
                            + "'.");
        }

        return converter;
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
            boolean internalValue = true;

            ITaskDescriptor filterTask = validatorDescriptor.getFilterTask();
            if (filterTask != null) {
                writeTaskDescriptor(htmlWriter, filterTask, "v:vFilter");

                if (filterTask.getClientTaskExpression() != null
                        && filterTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor translatorTask = validatorDescriptor
                    .getTranslatorTask();
            if (translatorTask != null) {
                writeTaskDescriptor(htmlWriter, translatorTask, "v:vTranslator");

                if (translatorTask.getClientTaskExpression() != null
                        && translatorTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor checkerTask = validatorDescriptor.getCheckerTask();
            if (checkerTask != null) {
                writeTaskDescriptor(htmlWriter, checkerTask, "v:vChecker");

                if (checkerTask.getClientTaskExpression() != null
                        && checkerTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor formatterTask = validatorDescriptor
                    .getFormatterTask();
            if (formatterTask != null) {
                writeTaskDescriptor(htmlWriter, formatterTask, "v:vFormatter");

                if (formatterTask.getClientTaskExpression() != null
                        && formatterTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor behaviorTask = validatorDescriptor
                    .getBehaviorTask();
            if (behaviorTask != null) {
                writeTaskDescriptor(htmlWriter, behaviorTask, "v:vBehavior");

                if (behaviorTask.getClientTaskExpression() != null
                        && behaviorTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor errorTask = validatorDescriptor.getOnErrorTask();
            if (errorTask != null) {
                writeTaskDescriptor(htmlWriter, errorTask, "v:vError");

                if (errorTask.getClientTaskExpression() != null
                        && errorTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            ITaskDescriptor checkErrorTask = validatorDescriptor
                    .getOnCheckErrorTask();
            if (checkErrorTask != null) {
                writeTaskDescriptor(htmlWriter, checkErrorTask, "v:vCheckError");

                if (checkErrorTask.getClientTaskExpression() != null
                        && checkErrorTask.getServerTask() == null) {
                    internalValue = false;
                }
            }

            String converter = validatorDescriptor.getConverter();
            if (converter != null) {
                htmlWriter.writeAttribute("v:converter", converter);
            }

            if (internalValue) {
                IParameter ps[] = new IParameter[params.size() / 2];
                int cnt = 0;
                for (Iterator it = params.iterator(); it.hasNext();) {
                    final String name = (String) it.next();
                    final String value = (String) it.next();

                    ps[cnt++] = new IParameter() {

                        public String getName() {
                            return name;
                        }

                        public String getValue() {
                            return value;
                        }
                    };
                }

                computeInternalValue(htmlWriter, validatorDescriptor, ps);
            }
        }
    }

    private void computeInternalValue(IHtmlWriter htmlWriter,
            IClientValidatorDescriptor validatorDescriptor,
            IParameter parameters[]) throws WriterException {
        TextEntryComponent textEntryComponent = (TextEntryComponent) htmlWriter
                .getComponentRenderContext().getComponent();

        FacesContext facesContext = htmlWriter.getComponentRenderContext()
                .getFacesContext();

        String text = convertValue(facesContext, textEntryComponent);

        IClientValidatorContext context = new ClientValidatorContext(htmlWriter
                .getComponentRenderContext(), parameters, text);

        if (text != null && text.length() > 0) {
            IFilterTask filterTask = null;
            ITranslatorTask translatorTask = null;

            ITaskDescriptor filterTaskDescriptor = validatorDescriptor
                    .getFilterTask();
            if (filterTaskDescriptor != null) {
                filterTask = (IFilterTask) filterTaskDescriptor.getServerTask();
            }

            ITaskDescriptor translatorTaskDescriptor = validatorDescriptor
                    .getTranslatorTask();
            if (translatorTaskDescriptor != null) {
                translatorTask = (ITranslatorTask) translatorTaskDescriptor
                        .getServerTask();
            }

            if (filterTask != null || translatorTask != null) {
                char chs[] = text.toCharArray();

                char out[] = new char[chs.length];
                int newLen = 0;

                boolean modified = false;

                for (int i = 0; i < chs.length; i++) {
                    char ch = chs[i];

                    if (filterTask != null
                            && filterTask.applyFilter(context, ch) == false) {
                        modified = true;
                        continue;
                    }

                    if (translatorTask != null) {
                        char newCh = translatorTask
                                .applyTranslator(context, ch);

                        if (newCh != ch) {
                            modified = true;
                            ch = newCh;
                        }
                    }

                    out[newLen++] = ch;
                }

                if (modified) {
                    String newText = new String(out, 0, newLen);

                    context.setInputValue(newText);
                    context.setOutputValue(newText);
                }
            }
        }

        IOnErrorTask errorTask = null;

        ITaskDescriptor onErrorTaskDescriptor = validatorDescriptor
                .getOnErrorTask();
        if (onErrorTaskDescriptor != null) {
            // errorTask = (IOnErrorTask) onErrorTaskDescriptor.getServerTask();
            // Pas besoin ?
        }

        boolean onError = false;

        ITaskDescriptor checkerTaskDescriptor = validatorDescriptor
                .getCheckerTask();
        if (checkerTaskDescriptor != null && text != null) {
            ICheckerTask checkerTask = (ICheckerTask) checkerTaskDescriptor
                    .getServerTask();

            if (checkerTask != null) {
                String newText = checkerTask.applyChecker(context, context
                        .getOutputValue());

                if (newText != null) {
                    context.setOutputValue(newText);
                    context.setInputValue(newText);

                    if (errorTask != null) {
                        errorTask.performError(context,
                                IOnErrorTask.CHECK_TASK, false);
                    }

                } else {
                    // On error !
                    onError = true;

                    if (errorTask != null) {
                        errorTask.performError(context,
                                IOnErrorTask.CHECK_TASK, true);
                    }

                }
            }
        }

        if (onError == false) {
            ITaskDescriptor formatterTaskDescriptor = validatorDescriptor
                    .getFormatterTask();
            if (formatterTaskDescriptor != null && text != null) {
                IFormatterTask formatterTask = (IFormatterTask) formatterTaskDescriptor
                        .getServerTask();

                if (formatterTask != null) {
                    String newText = formatterTask.applyFormatter(context,
                            context.getOutputValue());

                    if (newText != null) {
                        context.setOutputValue(newText);

                        if (errorTask != null) {
                            errorTask.performError(context,
                                    IOnErrorTask.FORMATTER_TASK, false);
                        }

                    } else {
                        // On error !
                        onError = true;

                        if (errorTask != null) {
                            errorTask.performError(context,
                                    IOnErrorTask.FORMATTER_TASK, true);
                        }
                    }
                }
            }
        }

        if (onError == false) {
            // Normalement, meme avec une erreur on appelle le Behavior
            ITaskDescriptor behaviorTaskDescriptor = validatorDescriptor
                    .getBehaviorTask();
            if (behaviorTaskDescriptor != null) {
                IBehaviorTask behaviorTask = (IBehaviorTask) checkerTaskDescriptor
                        .getServerTask();

                if (behaviorTask != null) {
                    if (behaviorTask.applyBehavior(context, context
                            .getOutputValue())) {

                        if (errorTask != null) {
                            errorTask.performError(context,
                                    IOnErrorTask.BEHAVIOR_TASK, false);
                        }

                    } else {
                        // On error !
                        onError = true;

                        if (errorTask != null) {
                            errorTask.performError(context,
                                    IOnErrorTask.BEHAVIOR_TASK, true);
                        }
                    }
                }
            }
        }

        if (onError) {
            // Pas de raccourci s'il y a des erreurs !
            return;
        }

        htmlWriter.writeAttribute("v:internalValue", context.getInput());

        htmlWriter.getComponentRenderContext().setAttribute(VALUE_ATTRIBUTE,
                context.getOutputValue());
    }

    protected void writeTaskDescriptor(IHtmlWriter htmlWriter,
            ITaskDescriptor filter, String attributeName)
            throws WriterException {

        String expression = filter.getClientTaskExpression();
        if (expression == null || expression.length() < 1) {
            return;
        }

        htmlWriter.writeAttribute(attributeName, expression);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.renderkit.html.AbstractInputRenderer#getInputType()
     */
    protected String getInputType(UIComponent component) {
        return IHtmlWriter.TEXT_INPUT_TYPE;
    }

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

        if (newValue != null
                && textEntryComponent.isValueLocked(facesContext) == false) {
            textEntryComponent.setSubmittedExternalValue(newValue);
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

    protected final void writeClientValidator(IJavaScriptWriter jsWriter)
            throws WriterException {

        IComponentRenderContext componentRenderContext = jsWriter
                .getHtmlComponentRenderContext();

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
                    name = jsWriter.allocateString(name);
                }
                params.add(name);

                String value = parameter.getValue();
                // Value peut etre null !
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    value = jsWriter.allocateString(value);
                }
                params.add(value);
            }

            for (; i < vParameters.length; i++) {
                IParameter parameter = vParameters[i];

                String name = parameter.getName();
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    name = jsWriter.allocateString(name);
                }
                params.add(name);

                String value = parameter.getValue();
                // Value peut etre null !
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    value = jsWriter.allocateString(value);
                }
                params.add(value);
            }

        }

        jsWriter.write("var validator=").writeCall("f_clientValidator",
                "f_newInstance").write(jsWriter.getComponentVarName());

        if (params.isEmpty() == false) {
            int pred = 0;

            for (Iterator it = params.iterator(); it.hasNext();) {
                String value = (String) it.next();

                if (value == null) {
                    pred++;
                    continue;
                }

                for (; pred > 0; pred--) {
                    jsWriter.write(',').writeNull();
                }

                jsWriter.write(',');
                if (ALLOCATE_VALIDATOR_PARAMETERS) {
                    jsWriter.write(value);

                } else {
                    jsWriter.writeString(value);
                }
            }
        }

        jsWriter.writeln(");");

        ITaskDescriptor filter = validatorDescriptor.getFilterTask();
        if (filter != null) {
            writeTaskDescriptor(jsWriter, filter, "f_addFilter");
        }
        ITaskDescriptor translator = validatorDescriptor.getTranslatorTask();
        if (translator != null) {
            writeTaskDescriptor(jsWriter, translator, "f_addTranslator");
        }
        ITaskDescriptor checker = validatorDescriptor.getCheckerTask();
        if (checker != null) {
            writeTaskDescriptor(jsWriter, checker, "f_addChecker");
        }
        ITaskDescriptor formatter = validatorDescriptor.getFormatterTask();
        if (formatter != null) {
            writeTaskDescriptor(jsWriter, formatter, "f_addFormatter");
        }
        ITaskDescriptor behavior = validatorDescriptor.getBehaviorTask();
        if (behavior != null) {
            writeTaskDescriptor(jsWriter, behavior, "f_addBehavior");
        }
        ITaskDescriptor error = validatorDescriptor.getOnErrorTask();
        if (error != null) {
            writeTaskDescriptor(jsWriter, error, "f_addOnError");
        }
        ITaskDescriptor checkError = validatorDescriptor.getOnCheckErrorTask();
        if (checkError != null) {
            writeTaskDescriptor(jsWriter, checkError, "f_addOnCheckError");
        }
    }

    protected void writeTaskDescriptor(IJavaScriptWriter jsWriter,
            ITaskDescriptor filter, String command) throws WriterException {

        String call = filter.getClientTaskExpression();
        if (call == null) {
            return;
        }

        jsWriter.writeCall("validator", command).writeSymbol(call)
                .writeln(");");
    }

    protected String getJavaScriptClassName() {
        return JavaScriptClasses.TEXT_ENTRY;
    }

    public void addRequiredJavaScriptClassNames(IHtmlWriter htmlWriter,
            Set classes) {
        super.addRequiredJavaScriptClassNames(htmlWriter, classes);

        IJavaScriptRenderContext javaScriptRenderContext = htmlWriter
                .getHtmlComponentRenderContext().getHtmlRenderContext()
                .getJavaScriptRenderContext();

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
    }

    protected boolean hasComponenDecoratorSupport() {
        return true;
    }

    private void appendValidators(FacesContext facesContext,
            IHtmlWriter writer, Validator[] validators) throws WriterException {

        IClientValidatorsRegistry clientValidatorManager = writer
                .getComponentRenderContext().getRenderContext()
                .getProcessContext().getRcfacesContext()
                .getClientValidatorsRegistry();
        if (clientValidatorManager == null) {
            // throw new FacesException("Can not get validator registry from
            // faces context !");

            // Designer mode
            return;
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
