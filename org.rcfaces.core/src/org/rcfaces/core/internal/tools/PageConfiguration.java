/*
 * $Id$
 */
package org.rcfaces.core.internal.tools;

import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.IPageConfigurator;
import org.rcfaces.core.internal.converter.LocaleConverter;
import org.rcfaces.core.internal.renderkit.IProcessContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class PageConfiguration {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(PageConfiguration.class);

    private static final String SCRIPT_TYPE_PROPERTY = "org.rcfaces.core.internal.PageConfiguration.SCRIPT_TYPE";

    private static final String ATTRIBUTES_LOCALE_PROPERTY = "org.rcfaces.core.internal.PageConfiguration.ATTRIBUTES_LOCALE";

    private static final String ATTRIBUTES_LOCALE_PARAMETER = Constants
            .getPackagePrefix()
            + ".ATTRIBUTES_LOCALE";

    public static void setPageConfigurator(FacesContext facesContext,
            IPageConfigurator pageConfigurator) {

        String pageScriptType = pageConfigurator.getPageScriptType();
        if (pageScriptType != null) {
            setAttribute(facesContext, SCRIPT_TYPE_PROPERTY, pageScriptType);
        }

        Locale locale = pageConfigurator.getAttributesLocale();
        if (locale != null) {
            setAttribute(facesContext, ATTRIBUTES_LOCALE_PROPERTY, locale);
        }
    }

    private static void setAttribute(FacesContext facesContext, String name,
            Object value) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();
        requestMap.put(name, value);

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null) {
            Map viewAttributes = viewRoot.getAttributes();

            viewAttributes.put(name, value);
        }
    }

    private static Object getAttribute(FacesContext facesContext, String name) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        Map requestMap = facesContext.getExternalContext().getRequestMap();
        Object value = requestMap.get(name);
        if (value != null) {
            return value;
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null) {
            Map viewAttributes = viewRoot.getAttributes();

            value = viewAttributes.get(name);
            if (value != null) {
                return value;
            }
        }

        return null;
    }

    private static final IPageConfigurator getPageConfiguration(
            FacesContext facesContext) {
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null) {
            return null;
        }

        return (IPageConfigurator) ComponentTools.findComponent(viewRoot,
                IPageConfigurator.class);
    }

    public static final String getScriptType(FacesContext facesContext) {

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        String scriptType = (String) getAttribute(facesContext,
                SCRIPT_TYPE_PROPERTY);

        IPageConfigurator scriptTypeConfigurator = getPageConfiguration(facesContext);
        if (scriptTypeConfigurator != null) {
            scriptType = scriptTypeConfigurator.getPageScriptType();

            if (scriptType != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Script type detected = " + scriptType);
                }

                setAttribute(facesContext, SCRIPT_TYPE_PROPERTY, scriptType);
                return scriptType;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("No script type detected !");
        }

        return null;
    }

    public static Locale getAttributesLocale(IProcessContext processContext,
            UIComponent component) {

        Locale locale = null;
        for (; component != null; component = component.getParent()) {

            if (component instanceof ILocalizedAttributesCapability) {

                locale = ((ILocalizedAttributesCapability) component)
                        .getAttributesLocale();
                if (locale != null) {
                    return locale;
                }

                continue;
            }
        }

        FacesContext facesContext = null;

        if (processContext != null) {
            locale = processContext.getDefaultAttributesLocale();
            if (locale != null) {
                return locale;
            }
            facesContext = processContext.getFacesContext();
        }

        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        locale = (Locale) getAttribute(facesContext, ATTRIBUTES_LOCALE_PROPERTY);
        if (locale != null) {
            return locale;
        }

        locale = getDefaultAttributesLocale(facesContext);
        if (locale != null) {
            setAttribute(facesContext, ATTRIBUTES_LOCALE_PROPERTY, locale);
            return locale;
        }

        throw new FacesException(
                "You must specify a default locale for attributes !");
    }

    public static Locale getDefaultAttributesLocale(FacesContext facesContext) {
        IPageConfigurator scriptTypeConfigurator = getPageConfiguration(facesContext);
        if (scriptTypeConfigurator != null) {
            Locale locale = scriptTypeConfigurator.getAttributesLocale();

            if (locale != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Default locale detected = " + locale);
                }

                return locale;
            }
        }

        Map applicationInitMap = facesContext.getExternalContext()
                .getInitParameterMap();
        String value = (String) applicationInitMap
                .get(ATTRIBUTES_LOCALE_PARAMETER);
        if (value == null) {
            return null;
        }

        Locale locale = (Locale) LocaleConverter.SINGLETON.getAsObject(null,
                null, value);
        if (locale != null) {
            return locale;
        }

        throw new FacesException("Unknown locale name '" + value
                + "' defined into application init parameters. (web.xml)");
    }
}
