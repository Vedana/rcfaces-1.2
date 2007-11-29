/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.LengthValidator;
import javax.faces.validator.LongRangeValidator;
import javax.faces.validator.Validator;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.renderkit.IRenderContext;
import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.tools.BindingTools;
import org.rcfaces.core.internal.util.ClassLocator;
import org.rcfaces.core.internal.util.Convertor;
import org.rcfaces.core.internal.validator.IClientValidatorDescriptor;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.validator.IServerConverter;
import org.rcfaces.core.internal.validator.ITaskDescriptor;
import org.rcfaces.core.validator.IClientValidatorTask;
import org.rcfaces.core.validator.IParameter;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientValidatorsRegistryImpl extends AbstractRenderKitRegistryImpl
        implements IClientValidatorsRegistry {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(ClientValidatorsRegistryImpl.class);

    private static final IParameter[] PARAMETER_EMPTY_ARRAY = new IParameter[0];

    public IClientValidatorDescriptor getClientValidatorById(
            FacesContext facesContext, String validatorId, Locale locale,
            TimeZone timeZone) {

        RenderKit renderKit = (RenderKit) getRenderKit(facesContext, null);
        if (renderKit == null) {
            return null;
        }

        return renderKit.getValidatorById(validatorId, locale, timeZone);
    }

    /*
     * public IStringAdapterDescriptor getStringFormatterById( FacesContext
     * facesContext, String formatterId) {
     * 
     * RenderKit renderKit = (RenderKit) getRenderKit(facesContext, null); if
     * (renderKit == null) { return null; }
     * 
     * return renderKit.getStringFormatterById(formatterId); }
     */

    public void configureRules(Digester digester) {

        digester.addRule("rcfaces-config/clientValidators/render-kit",
                new Rule() {
                    private static final String REVISION = "$Revision$";

                    public void begin(String namespace, String name,
                            Attributes attributes) throws Exception {

                        String renderKitId = attributes
                                .getValue("render-kit-id");

                        RenderKit renderKit = (RenderKit) allocate(renderKitId);

                        super.digester.push(renderKit);
                    }

                    public void end(String namespace, String name)
                            throws Exception {
                        super.digester.pop();
                    }
                });

        digester.addObjectCreate(
                "rcfaces-config/clientValidators/render-kit/clientValidator",
                ClientValidator.class);
        digester.addSetProperties(
                "rcfaces-config/clientValidators/render-kit/clientValidator",
                "id", "id");
        digester.addSetProperties(
                "rcfaces-config/clientValidators/render-kit/clientValidator",
                "package", "packageName");

        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/filter",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator.setFilter(new TaskDescriptor(
                                        attributes));
                            }
                        });

        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/translator",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator
                                        .setTranslator(new TaskDescriptor(
                                                attributes));
                            }
                        });

        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/checker",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator.setChecker(new TaskDescriptor(
                                        attributes));
                            }
                        });
        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/formatter",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator
                                        .setFormatter(new TaskDescriptor(
                                                attributes));
                            }
                        });
        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/behavior",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator.setBehavior(new TaskDescriptor(
                                        attributes));
                            }
                        });
        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/onerror",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator.setOnerror(new TaskDescriptor(
                                        attributes));
                            }
                        });
        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/oncheckerror",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator
                                        .setOncheckerror(new TaskDescriptor(
                                                attributes));
                            }
                        });
        digester
                .addRule(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/processor",
                        new Rule() {
                            private static final String REVISION = "$Revision$";

                            public void begin(String namespace, String name,
                                    Attributes attributes) throws Exception {
                                ClientValidator clientValidator = (ClientValidator) getDigester()
                                        .peek();

                                clientValidator
                                        .setProcessor(new TaskDescriptor(
                                                attributes));
                            }
                        });
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/converter",
                        "object", "converter");

        digester
                .addObjectCreate(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/parameter",
                        Parameter.class);
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/parameter",
                        "name", "name");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/parameter",
                        "value", "value");
        digester
                .addSetNext(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/parameter",
                        "addParameter");

        digester
                .addObjectCreate(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter",
                        ServerConverter.class);
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter",
                        "id", "id");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter",
                        "class", "className");

        digester
                .addObjectCreate(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter/parameter",
                        Parameter.class);
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter/parameter",
                        "name", "name");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter/parameter",
                        "value", "value");
        digester
                .addSetNext(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter/parameter",
                        "addParameter");

        digester
                .addSetNext(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/server-converter",
                        "setServerConverter");

        digester.addSetNext(
                "rcfaces-config/clientValidators/render-kit/clientValidator",
                "addclientValidator");

    }

    protected AbstractRenderKitRegistryImpl.RenderKit createRenderKit() {
        return new RenderKit();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class RenderKit extends
            AbstractRenderKitRegistryImpl.RenderKit {
        private static final String REVISION = "$Revision$";

        private final Map clientValidatorsById = new HashMap(128);

        private final Map stringAdaptersById = new HashMap(128);

        public RenderKit() {
        }

        /*
         * public final IStringAdapterDescriptor getStringFormatterById( String
         * formatterId) { return (IStringAdapterDescriptor) stringAdaptersById
         * .get(formatterId); }
         */

        /*
         * (non-Javadoc)
         * 
         * @see org.rcfaces.core.internal.validator.IDescriptorManager#getValidatorById(java.lang.String)
         */
        public final IClientValidatorDescriptor getValidatorById(
                String validatorId, Locale locale, TimeZone timeZone) {

            StringAppender sa = new StringAppender(validatorId, 24);
            if (locale != null) {
                sa.append('$');
                sa.append(locale.toString());

                if (timeZone != null) {
                    sa.append('$');
                    sa.append(timeZone.toString());
                }

                IClientValidatorDescriptor clientValidatorDescriptor = (IClientValidatorDescriptor) clientValidatorsById
                        .get(sa.toString());
                if (clientValidatorDescriptor != null) {
                    return clientValidatorDescriptor;
                }

                sa.setLength(0);
                sa.append(validatorId);

                if (timeZone != null) {
                    sa.append('$');
                    sa.append(locale.toString());

                    clientValidatorDescriptor = (IClientValidatorDescriptor) clientValidatorsById
                            .get(sa.toString());
                    if (clientValidatorDescriptor != null) {
                        return clientValidatorDescriptor;
                    }

                    sa.setLength(0);
                    sa.append(validatorId);
                }
            } else if (timeZone != null) {
                sa.append('$');
                sa.append(timeZone.toString());

                IClientValidatorDescriptor clientValidatorDescriptor = (IClientValidatorDescriptor) clientValidatorsById
                        .get(sa.toString());
                if (clientValidatorDescriptor != null) {
                    return clientValidatorDescriptor;
                }

                sa.setLength(0);
                sa.append(validatorId);
            }

            IClientValidatorDescriptor clientValidatorDescriptor = (IClientValidatorDescriptor) clientValidatorsById
                    .get(sa.toString());
            return clientValidatorDescriptor;
        }

        public final void addclientValidator(ClientValidator validator) {
            validator.commitParameters();

            LOG.trace("addclientValidator(" + validator.getId() + ", "
                    + validator + ")");

            clientValidatorsById.put(validator.getId(), validator);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ClientValidator extends ParametersContainer implements
            IClientValidatorDescriptor {
        private static final String REVISION = "$Revision$";

        private String id;

        private String requiredClasses[];

        private ITaskDescriptor filter;

        private ITaskDescriptor translator;

        private ITaskDescriptor checker;

        private ITaskDescriptor formatter;

        private ITaskDescriptor behavior;

        private ITaskDescriptor processor;

        private ITaskDescriptor onerror;

        private ITaskDescriptor oncheckerror;

        private String converter;

        private IServerConverter serverConverter;

        public final ITaskDescriptor getBehaviorTask() {
            return behavior;
        }

        public final ITaskDescriptor getCheckerTask() {
            return checker;
        }

        public final ITaskDescriptor getFilterTask() {
            return filter;
        }

        public final ITaskDescriptor getFormatterTask() {
            return formatter;
        }

        public final ITaskDescriptor getOnCheckErrorTask() {
            return oncheckerror;
        }

        public final ITaskDescriptor getOnErrorTask() {
            return onerror;
        }

        public final String[] listRequiredClasses() {
            return requiredClasses;
        }

        public final ITaskDescriptor getTranslatorTask() {
            return translator;
        }

        public ITaskDescriptor getProcessorTask() {
            return processor;
        }

        public void setProcessor(ITaskDescriptor processor) {
            this.processor = processor;
        }

        public final void setBehavior(ITaskDescriptor behavior) {
            this.behavior = behavior;
        }

        public final void setChecker(ITaskDescriptor checker) {
            this.checker = checker;
        }

        public final void setFilter(ITaskDescriptor filter) {
            this.filter = filter;
        }

        public final void setFormatter(ITaskDescriptor formatter) {
            this.formatter = formatter;
        }

        public final void setOncheckerror(ITaskDescriptor oncheckerror) {
            this.oncheckerror = oncheckerror;
        }

        public final void setOnerror(ITaskDescriptor onerror) {
            this.onerror = onerror;
        }

        public final void setRequiredClasses(String requiredClasses[]) {
            this.requiredClasses = requiredClasses;
        }

        public final void setTranslator(ITaskDescriptor translator) {
            this.translator = translator;
        }

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }

        public final String getConverter() {
            return converter;
        }

        public final void setConverter(String converter) {
            this.converter = converter;
        }

        public final IServerConverter getServerConverter() {
            return serverConverter;
        }

        public final void setServerConverter(ServerConverter serverConverter) {
            serverConverter.commitParameters();

            this.serverConverter = serverConverter;
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class TaskDescriptor implements ITaskDescriptor {
        private static final String REVISION = "$Revision$";

        private final String clientTaskExpression;

        private final IClientValidatorTask clientValidatorTask;

        public TaskDescriptor(Attributes attributes) {
            clientTaskExpression = attributes.getValue("call");

            String serverTaskClassName = attributes.getValue("class");

            if (serverTaskClassName != null) {
                LOG.debug("Instanciate filter '" + serverTaskClassName + "'.");
                
                Class clazz;
                try {
                    clazz = ClassLocator.load(serverTaskClassName, null,
                            FacesContext.getCurrentInstance());

                } catch (Throwable th) {
                    LOG.error("Can not load client validator task class '"
                            + serverTaskClassName + "'.", th);

                    throw new FacesException(
                            "Can not initialize server filter.", th);
                }

                try {
                    clientValidatorTask = (IClientValidatorTask) clazz
                            .newInstance();

                } catch (Throwable th) {
                    LOG.error("Can not instanciate client validator task '"
                            + clazz + "'.", th);

                    throw new FacesException(
                            "Can not initialize server filter.", th);
                }

            } else {
                clientValidatorTask = null;
            }
        }

        public String getClientTaskExpression() {
            return clientTaskExpression;
        }

        public String getClientTaskExpressionType() {
            return null;
        }

        public IClientValidatorTask getServerTask() {
            return clientValidatorTask;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ParametersContainer {
        private static final String REVISION = "$Revision$";

        private IParameter[] parameters;

        private List parametersList;

        public IParameter[] listParameters() {
            return parameters;
        }

        public void commitParameters() {
            if (parametersList == null) {
                parameters = PARAMETER_EMPTY_ARRAY;
                return;
            }

            parameters = (IParameter[]) parametersList
                    .toArray(new IParameter[parametersList.size()]);
            parametersList = null;
        }

        public void addParameter(Parameter parameter) {
            if (parameters != null) {
                throw new IllegalStateException();
            }

            if (parametersList == null) {
                parametersList = new ArrayList(8);
            }

            parametersList.add(parameter);
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class Parameter implements IParameter {
        private static final String REVISION = "$Revision$";

        private String name;

        private String value;

        public final String getName() {
            return name;
        }

        public final void setName(String name) {
            this.name = name;
        }

        public final String getValue() {
            return value;
        }

        public final void setValue(String value) {
            this.value = value;
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ServerConverter extends ParametersContainer implements
            IServerConverter {
        private String id;

        private String className;

        public final String getClassName() {
            return className;
        }

        public final void setClassName(String className) {
            this.className = className;
        }

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }

        public Converter getInstance(FacesContext facesContext) {

            Converter converter = null;

            boolean setParameters = false;

            String id = getId();
            if (id != null && id.length() > 0) {
                Application application = facesContext.getApplication();

                if (BindingTools.isBindingExpression(id)) {
                    converter = (Converter) BindingTools.evalBinding(
                            facesContext, id, Converter.class);

                } else {
                    converter = application.createConverter(id);
                    setParameters = true;
                }
            }

            if (converter == null) {
                String className = getClassName();
                if (className != null) {
                    try {
                        Class clazz = ClassLocator.load(className, null,
                                facesContext);
                        converter = (Converter) clazz.newInstance();

                        setParameters = true;

                    } catch (Throwable th) {
                        LOG.error("Can not instanciate converter class='"
                                + className + "'.", th);

                        throw new FacesException(th);
                    }
                }
            }

            if (converter == null) {
                return converter;
            }

            IParameter parameters[] = listParameters();

            if (setParameters == false) {
                if (parameters.length > 1) {
                    throw new FacesException(
                            "Can not set parameters to a 'value binding' Converter.");
                }
                return converter;
            }

            BeanInfo beanInfo;
            try {
                beanInfo = Introspector.getBeanInfo(converter.getClass());

            } catch (IntrospectionException e) {
                throw new FacesException(
                        "Can not introspect bean from validator id='" + getId()
                                + "' className='" + getClassName() + "'.", e);
            }

            PropertyDescriptor propertyDescriptors[] = beanInfo
                    .getPropertyDescriptors();

            for (int i = 0; i < parameters.length; i++) {
                IParameter parameter = parameters[i];

                String name = parameter.getName();

                for (int j = 0; j < propertyDescriptors.length; j++) {
                    PropertyDescriptor propertyDescriptor = propertyDescriptors[j];

                    if (propertyDescriptor.getName().equals(name) == false) {
                        continue;
                    }

                    Class propertyType = propertyDescriptor.getPropertyType();

                    Object value = Convertor.convert(parameter.getValue(),
                            propertyType);

                    try {
                        propertyDescriptor.getWriteMethod().invoke(converter,
                                new Object[] { value });

                    } catch (Throwable th) {
                        LOG.error("Can not set property '"
                                + propertyDescriptor.getPropertyType()
                                + "' to " + value, th);
                    }

                    break;
                }
            }

            return converter;
        }
    }

    public String convertFromValidatorToExpression(
            IRenderContext renderContext, Validator validator) {

        if (validator instanceof LongRangeValidator) {
            return getLongRangeValidatorExpression(renderContext,
                    (LongRangeValidator) validator);
        }

        if (validator instanceof DoubleRangeValidator) {
            return getDoubleRangeValidatorExpression(renderContext,
                    (DoubleRangeValidator) validator);
        }

        if (validator instanceof LengthValidator) {
            return getLengthValidatorExpression(renderContext,
                    (LengthValidator) validator);
        }

        return null;
    }

    private String getLengthValidatorExpression(IRenderContext renderContext,
            LengthValidator validator) {
        StringAppender sa = new StringAppender(64);

        return convertValidatorExpression(renderContext, "f_validator",
                "LengthValidator", sa.toString());
    }

    private String getDoubleRangeValidatorExpression(
            IRenderContext renderContext, DoubleRangeValidator validator) {
        StringAppender sa = new StringAppender(64);

        return convertValidatorExpression(renderContext, "f_validator",
                "DoubleRangeValidator", sa.toString());
    }

    private String getLongRangeValidatorExpression(
            IRenderContext renderContext, LongRangeValidator validator) {
        StringAppender sa = new StringAppender(64);

        return convertValidatorExpression(renderContext, "f_validator",
                "LongRangeValidator", sa.toString());
    }

    private String convertValidatorExpression(IRenderContext renderContext,
            String clazz, String method, String params) {

        IScriptRenderContext scriptRenderContext = renderContext
                .getScriptRenderContext();

        StringAppender sa = new StringAppender(clazz.length() + 1
                + method.length() + params.length());

        if (clazz != null) {
            if (scriptRenderContext != null) {
                clazz = scriptRenderContext.convertSymbol(null, clazz);
            }

            sa.append(clazz);
            sa.append('.');
        }

        if (scriptRenderContext != null) {
            method = scriptRenderContext.convertSymbol(clazz, method);
        }
        sa.append(method);

        sa.append('(');
        sa.append(params);
        sa.append(')');

        return sa.toString();
    }
}
