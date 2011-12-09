/*
 * $Id$
 */
package org.rcfaces.core.internal.capability;

import java.io.Serializable;

import javax.el.ValueExpression;

public interface IValueExpressionCapability {
    ValueExpression getValueExpression(Serializable name);
}
