/*
 * $Id$
 */
package org.rcfaces.core.internal.taglib;

import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.NumberConverter;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.ConverterTag;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

import org.rcfaces.core.converter.AbstractNumberConverter;
import org.rcfaces.core.internal.converter.LocaleConverter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ConvertNumberTag extends ConverterTag {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = 9211078836220394550L;

    private static final String DEFAULT_NUMBER_TYPE = "number";

    private String currencyCode;

    private String currencySymbol;

    private String groupingUsed;

    private String integerOnly;

    private String maxFractionDigits;

    private String maxIntegerDigits;

    private String minFractionDigits;

    private String minIntegerDigits;

    private String locale;

    private String pattern;

    private String type;

    private String defaultValue;

    public ConvertNumberTag() {
        initializeFields();
    }

    public void release() {
        super.release();
        initializeFields();
    }

    private void initializeFields() {
        currencyCode = null;
        currencySymbol = null;
        groupingUsed = null;
        integerOnly = null;
        maxFractionDigits = null;
        maxIntegerDigits = null;
        minFractionDigits = null;
        locale = null;
        pattern = null;
        type = DEFAULT_NUMBER_TYPE;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public void setGroupingUsed(String groupingUsed) {
        this.groupingUsed = groupingUsed;
    }

    public void setIntegerOnly(String integerOnly) {
        this.integerOnly = integerOnly;
    }

    public void setMaxFractionDigits(String maxFractionDigits) {
        this.maxFractionDigits = maxFractionDigits;
    }

    public void setMaxIntegerDigits(String maxIntegerDigits) {
        this.maxIntegerDigits = maxIntegerDigits;
    }

    public void setMinFractionDigits(String minFractionDigits) {
        this.minFractionDigits = minFractionDigits;
    }

    public void setMinIntegerDigits(String minIntegerDigits) {
        this.minIntegerDigits = minIntegerDigits;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setType(String type) {
        this.type = type;
    }

    public final void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int doStartTag() throws JspException {
        setConverterId(getDefaultConverterId());

        return super.doStartTag();
    }

    protected String getDefaultConverterId() {
        return "org.rcfaces.Number";
    }

    protected Converter createConverter() throws JspException {

        NumberConverter result = (NumberConverter) super.createConverter();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (currencyCode != null) {
            if (UIComponentTag.isValueReference(currencyCode)) {
                currencyCode = (String) evaluateValueExpression(facesContext,
                        currencyCode);
            }

            result.setCurrencyCode(currencyCode);
        }

        if (currencySymbol != null) {
            if (UIComponentTag.isValueReference(currencySymbol)) {
                currencySymbol = (String) evaluateValueExpression(facesContext,
                        currencySymbol);
            }

            result.setCurrencySymbol(currencySymbol);
        }

        if (pattern != null) {
            if (UIComponentTag.isValueReference(pattern)) {
                pattern = (String) evaluateValueExpression(facesContext,
                        pattern);
            }

            result.setPattern(pattern);
        }

        if (type != null) {
            if (UIComponentTag.isValueReference(type)) {
                type = (String) evaluateValueExpression(facesContext, type);
            }

            result.setType(type);
        }

        if (groupingUsed != null) {
            boolean gu;
            if (UIComponentTag.isValueReference(groupingUsed)) {
                Boolean b = (Boolean) evaluateValueExpression(facesContext,
                        groupingUsed);
                gu = b.booleanValue();

            } else {
                gu = Boolean.valueOf(groupingUsed).booleanValue();
            }

            result.setGroupingUsed(gu);
        }

        if (integerOnly != null) {
            boolean io;

            if (UIComponentTag.isValueReference(integerOnly)) {
                Boolean b = (Boolean) evaluateValueExpression(facesContext,
                        integerOnly);
                io = b.booleanValue();
            } else {
                io = new Boolean(integerOnly).booleanValue();
            }

            result.setIntegerOnly(io);
        }

        if (maxFractionDigits != null) {
            int mfd;

            if (UIComponentTag.isValueReference(maxFractionDigits)) {
                Integer i = (Integer) evaluateValueExpression(facesContext,
                        maxFractionDigits);
                mfd = i.intValue();
            } else {
                mfd = Integer.parseInt(maxFractionDigits);
            }

            result.setMaxFractionDigits(mfd);
        }

        if (maxIntegerDigits != null) {
            int mid;
            if (UIComponentTag.isValueReference(maxIntegerDigits)) {
                Integer i = (Integer) evaluateValueExpression(facesContext,
                        maxIntegerDigits);
                mid = i.intValue();
            } else {
                mid = Integer.parseInt(maxIntegerDigits);
            }

            result.setMaxIntegerDigits(mid);
        }

        if (minFractionDigits != null) {
            int mfd;
            if (UIComponentTag.isValueReference(minFractionDigits)) {
                Integer i = (Integer) evaluateValueExpression(facesContext,
                        minFractionDigits);
                mfd = i.intValue();
            } else {
                mfd = Integer.parseInt(minFractionDigits);
            }

            result.setMinFractionDigits(mfd);
        }

        if (minIntegerDigits != null) {
            int mid;
            if (UIComponentTag.isValueReference(minIntegerDigits)) {
                Integer i = (Integer) evaluateValueExpression(facesContext,
                        minIntegerDigits);
                mid = i.intValue();
            } else {
                mid = Integer.parseInt(minIntegerDigits);
            }

            result.setMinIntegerDigits(mid);
        }

        if (locale != null) {
            Locale loc;

            if (UIComponentTag.isValueReference(locale)) {
                loc = (Locale) evaluateValueExpression(facesContext, locale);

            } else {
                loc = (Locale) LocaleConverter.SINGLETON.getAsObject(
                        facesContext, null, locale);
            }

            result.setLocale(loc);
        }

        if (result instanceof AbstractNumberConverter) {
            if (defaultValue != null) {
                Object defValue = defaultValue;

                if (UIComponentTag.isValueReference(defaultValue)) {
                    defValue = evaluateValueExpression(facesContext,
                            defaultValue);
                }

                ((AbstractNumberConverter) result).setDefaultValue(String
                        .valueOf(defValue));
            }
        }

        return result;
    }

    private Object evaluateValueExpression(FacesContext facesContext,
            String value) {
        Application application = facesContext.getApplication();

        ValueBinding vb = application.createValueBinding(value);

        return vb.getValue(facesContext);
    }
}