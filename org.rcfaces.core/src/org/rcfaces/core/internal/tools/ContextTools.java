/*
 * $Id$
 * 
 */
package org.rcfaces.core.internal.tools;

import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LocaleConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ContextTools {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(ContextTools.class);

    private static final String ATTRIBUTES_LOCALE_PROPERTY_NAME = "org.rcfaces.core.ATTRIBUTES_LOCALE";

    private static final String ATTRIBUTES_LOCALE_PARAMETER = Constants
            .getPackagePrefix()
            + ".ATTRIBUTES_LOCALE";

    public static final Object resolveAttribute(FacesContext facesContext,
            String attributeName) {

        ExternalContext externalContext = facesContext.getExternalContext();

        Object value = externalContext.getRequestMap().get(attributeName);
        if (value != null) {
            return value;
        }

        Map session = externalContext.getSessionMap();
        if (session != null) {
            value = session.get(attributeName);
            if (value != null) {
                return value;
            }
        }

        value = externalContext.getApplicationMap().get(attributeName);
        if (value != null) {
            return value;
        }

        return null;
    }

    public static final String resolveText(FacesContext facesContext,
            String bundleVar, String attributeName) {

        Object bundle = ContextTools.resolveAttribute(facesContext, bundleVar);
        if (bundle instanceof Map) {
            String rtext = (String) ((Map) bundle).get(attributeName);
            if (rtext != null) {
                return rtext;
            }

            return "???" + attributeName + " (key not found)???";
        }

        return "???" + attributeName + " (bundle not found)???";
    }

    public static Locale getAttributesLocale(UIComponent component) {

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

            if (component instanceof UIViewRoot) {
                UIViewRoot viewRoot = (UIViewRoot) component;

                locale = (Locale) viewRoot.getAttributes().get(
                        ATTRIBUTES_LOCALE_PROPERTY_NAME);
                if (locale != null) {
                    return locale;
                }
            }
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance()
                .getExternalContext();

        Map requestMap = externalContext.getRequestMap();
        locale = (Locale) requestMap.get(ATTRIBUTES_LOCALE_PROPERTY_NAME);
        if (locale != null) {
            return locale;
        }

        Map applicationMap = externalContext.getApplicationMap();
        Object object = applicationMap.get(ATTRIBUTES_LOCALE_PROPERTY_NAME);
        if (object != null) {
            if (object instanceof Locale) {
                locale = (Locale) object;

                requestMap.put(ATTRIBUTES_LOCALE_PROPERTY_NAME, locale);

                return locale;
            }

            // Ca peut etre Boolean.FALSE, dans ce cas on recherche le locale
            // par defaut.

        } else {
            Map applicationInitMap = externalContext.getInitParameterMap();
            String value = (String) applicationInitMap
                    .get(ATTRIBUTES_LOCALE_PARAMETER);
            if (value != null) {
                locale = (Locale) LocaleConverter.SINGLETON.getAsObject(null,
                        null, value);
                if (locale != null) {
                    applicationMap.put(ATTRIBUTES_LOCALE_PROPERTY_NAME, locale);
                    requestMap.put(ATTRIBUTES_LOCALE_PROPERTY_NAME, locale);
                    return locale;
                }

                throw new FacesException(
                        "Unknown locale name '"
                                + value
                                + "' defined into application init parameters. (web.xml)");
            }

            applicationMap.put(ATTRIBUTES_LOCALE_PROPERTY_NAME, Boolean.FALSE);
        }

        // On prend pas le UIViewRoot car ce serait lié à la config du
        // browser du client.
        // Les attributs spécifiés en parametre n'ont pas de liens avec la
        // config du client.
        // return Locale.getDefault();

        throw new FacesException(
                "You must specify a default locale for attributes !");
    }

    public static void setAttributesLocale(FacesContext facesContext,
            Locale locale) {

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null) {
            viewRoot.getAttributes().put(ATTRIBUTES_LOCALE_PROPERTY_NAME,
                    locale);
        }

        facesContext.getExternalContext().getRequestMap().put(
                ATTRIBUTES_LOCALE_PROPERTY_NAME, locale);
    }

    public static Locale getUserLocale(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null) {
            Locale locale = viewRoot.getLocale();
            if (locale != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Get locale from viewRoot: " + locale);
                }
                return locale;
            }
        }

        Locale locale = facesContext.getApplication().getViewHandler()
                .calculateLocale(facesContext);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Calculate locale from view handler: " + locale);
        }

        return locale;
    }
}
