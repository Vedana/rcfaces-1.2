/*
 * $Id$
 */
package org.rcfaces.core.internal.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
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
import org.rcfaces.core.internal.validator.IClientValidatorDescriptor;
import org.rcfaces.core.internal.validator.IClientValidatorsRegistry;
import org.rcfaces.core.internal.validator.IParameter;
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
            FacesContext facesContext, String validatorId) {

        RenderKit renderKit = (RenderKit) getRenderKit(facesContext, null);
        if (renderKit == null) {
            return null;
        }

        return renderKit.getValidatorById(validatorId);
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
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/filter",
                        "call", "filter");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/translator",
                        "call", "translator");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/checker",
                        "call", "checker");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/formatter",
                        "call", "formatter");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/behavior",
                        "call", "behavior");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/onerror",
                        "call", "onerror");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/oncheckerror",
                        "call", "oncheckerror");
        digester
                .addSetProperties(
                        "rcfaces-config/clientValidators/render-kit/clientValidator/processor",
                        "call", "processor");
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
                String validatorId) {
            return (IClientValidatorDescriptor) clientValidatorsById
                    .get(validatorId);
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
    public static class ClientValidator implements IClientValidatorDescriptor {
        private static final String REVISION = "$Revision$";

        private String id;

        private String requiredClasses[];

        private String filter;

        private String translator;

        private String checker;

        private String formatter;

        private String behavior;

        private String processor;

        private String onerror;

        private String oncheckerror;

        private List parametersList;

        private String converter;

        private IParameter[] parameters;

        public final String getBehaviorCall() {
            return behavior;
        }

        public final String getCheckerCall() {
            return checker;
        }

        public final String getFilterCall() {
            return filter;
        }

        public final String getFormatterCall() {
            return formatter;
        }

        public final String getOnCheckErrorCall() {
            return oncheckerror;
        }

        public final String getOnErrorCall() {
            return onerror;
        }

        public final String[] listRequiredClasses() {
            return requiredClasses;
        }

        public final String getTranslatorCall() {
            return translator;
        }

        public String getProcessorCall() {
            return processor;
        }

        public void setProcessor(String processor) {
            this.processor = processor;
        }

        public final void setBehavior(String behavior) {
            this.behavior = behavior;
        }

        public final void setChecker(String checker) {
            this.checker = checker;
        }

        public final void setFilter(String filter) {
            this.filter = filter;
        }

        public final void setFormatter(String formatter) {
            this.formatter = formatter;
        }

        public final void setOncheckerror(String oncheckerror) {
            this.oncheckerror = oncheckerror;
        }

        public final void setOnerror(String onerror) {
            this.onerror = onerror;
        }

        public final void setRequiredClasses(String requiredClasses[]) {
            this.requiredClasses = requiredClasses;
        }

        public final void setTranslator(String translator) {
            this.translator = translator;
        }

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }

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

        public final String getConverter() {
            return converter;
        }

        public final void setConverter(String converter) {
            this.converter = converter;
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
                String sclazz = scriptRenderContext.convertSymbol(clazz);
                if (sclazz != null) {
                    clazz = sclazz;
                }
            }

            sa.append(clazz);
            sa.append('.');
        }

        if (scriptRenderContext != null) {
            String smethod = scriptRenderContext.convertSymbol(method);
            if (smethod != null) {
                method = smethod;
            }
        }
        sa.append(method);

        sa.append('(');
        sa.append(params);
        sa.append(')');

        return sa.toString();
    }
}
