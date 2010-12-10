package org.rcfaces.core.internal.jasper;

import java.lang.reflect.Field;

import javax.el.ValueExpression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jasper.el.JspValueExpression;
import org.rcfaces.core.internal.component.IUnproxifyValueExpression;

public class JasperUnproxifyValueExpression implements
		IUnproxifyValueExpression {

	private static final Log LOG = LogFactory
			.getLog(JasperUnproxifyValueExpression.class);

	private static final String TARGET_FIELD = "target";

	public ValueExpression process(ValueExpression valueExpression) {
		if (valueExpression instanceof JspValueExpression) {
			try {
				Field field = valueExpression.getClass().getDeclaredField(
						TARGET_FIELD);
				field.setAccessible(true);
				return (ValueExpression) field.get(valueExpression);
				
			} catch (Throwable e) {
				LOG.error("Can not get target from JspValueExpression="
						+ valueExpression, e);
				return null;
			}
		}

		return valueExpression;

	}

}
