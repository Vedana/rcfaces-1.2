/*
 * $Id$
 */
package org.rcfaces.core.internal.facelets;

import java.util.Locale;

import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;

import org.rcfaces.core.converter.AbstractNumberConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.jsf.ConvertHandler;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class CameliaConverterHandler extends ConvertHandler {
    private static final String REVISION = "$Revision$";

    private final TagAttribute localeAttribute;

    private final TagAttribute defaultNumberAttribute;

    public CameliaConverterHandler(TagConfig config) {
        super(config);

        this.localeAttribute = this.getAttribute("locale");
        this.defaultNumberAttribute = this.getAttribute("defaultNumber");
    }

    protected Converter createConverter(FaceletContext ctx) {
        return ctx.getFacesContext().getApplication().createConverter(
                getConverterId());
    }

    protected abstract String getConverterId();

    protected void setAttributes(FaceletContext ctx, Object converter) {
        super.setAttributes(ctx, converter);

        if (localeAttribute == null && (converter instanceof NumberConverter)) {
            Locale locale = null;
            Object localeValue = localeAttribute.getObject(ctx);
            if (localeValue instanceof Locale) {
                locale = (Locale) localeValue;

            } else if (localeValue instanceof String) {
                locale = (Locale) LocaleConverter.SINGLETON.getAsObject(ctx
                        .getFacesContext(), null, (String) localeValue);
            }

            if (locale != null) {
                ((NumberConverter) converter).setLocale(locale);
            }
        }

        if (defaultNumberAttribute != null
                && (converter instanceof AbstractNumberConverter)) {
            Object defaultValue = defaultNumberAttribute.getObject(ctx);

            if (defaultValue != null) {
                Object defValue = defaultValue;

                ((AbstractNumberConverter) converter).setDefaultValue(String
                        .valueOf(defValue));
            }
        }
    }

    protected MetaRuleset createMetaRuleset(Class type) {
        return super.createMetaRuleset(type).ignore("locale").ignore(
                "defaultNumber");
    }
}
