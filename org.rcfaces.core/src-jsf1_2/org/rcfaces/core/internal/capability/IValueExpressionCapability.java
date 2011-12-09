/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import javax.el.ValueExpression;

public interface IValueExpressionCapability {
    ValueExpression getValueExpression(String name);
}
