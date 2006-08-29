/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.3  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.2  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
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

import org.rcfaces.core.component.capability.ILocalizedAttributesCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.converter.LocaleConverter;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public final class ContextTools {
    private static final String REVISION = "$Revision$";

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
            if ((component instanceof ILocalizedAttributesCapability) == false) {
                continue;
            }

            locale = ((ILocalizedAttributesCapability) component)
                    .getAttributesLocale();
            if (locale != null) {
                return locale;
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

    public static void setAttributesLocale(ExternalContext externalContext,
            Locale locale) {

        externalContext.getRequestMap().put(ATTRIBUTES_LOCALE_PROPERTY_NAME,
                locale);
    }

    public static Locale getUserLocale(FacesContext facesContext) {
        if (facesContext == null) {
            facesContext = FacesContext.getCurrentInstance();
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null) {
            Locale locale = viewRoot.getLocale();
            if (locale != null) {
                return locale;
            }
        }

        return facesContext.getApplication().getViewHandler().calculateLocale(
                facesContext);
    }
}
