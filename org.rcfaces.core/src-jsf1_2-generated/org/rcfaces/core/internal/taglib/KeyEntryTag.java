package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.KeyEntryComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class KeyEntryTag extends AbstractGridTag implements Tag {


	private static final Log LOG=LogFactory.getLog(KeyEntryTag.class);

	private ValueExpression emptyMessage;
	private ValueExpression emptyDataMessage;
	private ValueExpression selectionListeners;
	private ValueExpression disabled;
	private ValueExpression required;
	private ValueExpression readOnly;
	private ValueExpression border;
	private ValueExpression maxTextLength;
	private ValueExpression editable;
	private ValueExpression filterProperties;
	private ValueExpression suggestionDelayMs;
	private ValueExpression suggestionMinChars;
	private ValueExpression rowCountVar;
	private ValueExpression rowIndexVar;
	private ValueExpression valueColumnId;
	private ValueExpression labelColumnId;
	private ValueExpression selectedValue;
	private ValueExpression valueFormat;
	private ValueExpression forceValidation;
	private ValueExpression forLabel;
	private ValueExpression valueFormatLabel;
	private ValueExpression noValueFormatLabel;
	public String getComponentType() {
		return KeyEntryComponent.COMPONENT_TYPE;
	}

	public final void setEmptyMessage(ValueExpression emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public final void setEmptyDataMessage(ValueExpression emptyDataMessage) {
		this.emptyDataMessage = emptyDataMessage;
	}

	public final void setSelectionListener(ValueExpression selectionListeners) {
		this.selectionListeners = selectionListeners;
	}

	public final void setDisabled(ValueExpression disabled) {
		this.disabled = disabled;
	}

	public final void setRequired(ValueExpression required) {
		this.required = required;
	}

	public final void setReadOnly(ValueExpression readOnly) {
		this.readOnly = readOnly;
	}

	public final void setBorder(ValueExpression border) {
		this.border = border;
	}

	public final void setMaxTextLength(ValueExpression maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

	public final void setEditable(ValueExpression editable) {
		this.editable = editable;
	}

	public final void setFilterProperties(ValueExpression filterProperties) {
		this.filterProperties = filterProperties;
	}

	public final void setSuggestionDelayMs(ValueExpression suggestionDelayMs) {
		this.suggestionDelayMs = suggestionDelayMs;
	}

	public final void setSuggestionMinChars(ValueExpression suggestionMinChars) {
		this.suggestionMinChars = suggestionMinChars;
	}

	public final void setRowCountVar(ValueExpression rowCountVar) {
		this.rowCountVar = rowCountVar;
	}

	public final void setRowIndexVar(ValueExpression rowIndexVar) {
		this.rowIndexVar = rowIndexVar;
	}

	public final void setValueColumnId(ValueExpression valueColumnId) {
		this.valueColumnId = valueColumnId;
	}

	public final void setLabelColumnId(ValueExpression labelColumnId) {
		this.labelColumnId = labelColumnId;
	}

	public final void setSelectedValue(ValueExpression selectedValue) {
		this.selectedValue = selectedValue;
	}

	public final void setValueFormat(ValueExpression valueFormat) {
		this.valueFormat = valueFormat;
	}

	public final void setForceValidation(ValueExpression forceValidation) {
		this.forceValidation = forceValidation;
	}

	public final void setForLabel(ValueExpression forLabel) {
		this.forLabel = forLabel;
	}

	public final void setValueFormatLabel(ValueExpression valueFormatLabel) {
		this.valueFormatLabel = valueFormatLabel;
	}

	public final void setNoValueFormatLabel(ValueExpression noValueFormatLabel) {
		this.noValueFormatLabel = noValueFormatLabel;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (KeyEntryComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  emptyMessage='"+emptyMessage+"'");
			LOG.debug("  emptyDataMessage='"+emptyDataMessage+"'");
			LOG.debug("  disabled='"+disabled+"'");
			LOG.debug("  required='"+required+"'");
			LOG.debug("  readOnly='"+readOnly+"'");
			LOG.debug("  border='"+border+"'");
			LOG.debug("  maxTextLength='"+maxTextLength+"'");
			LOG.debug("  editable='"+editable+"'");
			LOG.debug("  filterProperties='"+filterProperties+"'");
			LOG.debug("  suggestionDelayMs='"+suggestionDelayMs+"'");
			LOG.debug("  suggestionMinChars='"+suggestionMinChars+"'");
			LOG.debug("  rowCountVar='"+rowCountVar+"'");
			LOG.debug("  rowIndexVar='"+rowIndexVar+"'");
			LOG.debug("  valueColumnId='"+valueColumnId+"'");
			LOG.debug("  labelColumnId='"+labelColumnId+"'");
			LOG.debug("  selectedValue='"+selectedValue+"'");
			LOG.debug("  valueFormat='"+valueFormat+"'");
			LOG.debug("  forceValidation='"+forceValidation+"'");
			LOG.debug("  forLabel='"+forLabel+"'");
			LOG.debug("  valueFormatLabel='"+valueFormatLabel+"'");
			LOG.debug("  noValueFormatLabel='"+noValueFormatLabel+"'");
		}
		if ((uiComponent instanceof KeyEntryComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'KeyEntryComponent'.");
		}

		super.setProperties(uiComponent);

		KeyEntryComponent component = (KeyEntryComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (emptyMessage != null) {
			if (emptyMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.EMPTY_MESSAGE, emptyMessage);

			} else {
				component.setEmptyMessage(emptyMessage.getExpressionString());
			}
		}

		if (emptyDataMessage != null) {
			if (emptyDataMessage.isLiteralText()==false) {
				component.setValueExpression(Properties.EMPTY_DATA_MESSAGE, emptyDataMessage);

			} else {
				component.setEmptyDataMessage(emptyDataMessage.getExpressionString());
			}
		}

		if (selectionListeners != null) {
			ListenersTools1_2.parseListener(facesContext, component, ListenersTools.SELECTION_LISTENER_TYPE, selectionListeners);
		}

		if (disabled != null) {
			if (disabled.isLiteralText()==false) {
				component.setValueExpression(Properties.DISABLED, disabled);

			} else {
				component.setDisabled(getBool(disabled.getExpressionString()));
			}
		}

		if (required != null) {
			if (required.isLiteralText()==false) {
				component.setValueExpression(Properties.REQUIRED, required);

			} else {
				component.setRequired(getBool(required.getExpressionString()));
			}
		}

		if (readOnly != null) {
			if (readOnly.isLiteralText()==false) {
				component.setValueExpression(Properties.READ_ONLY, readOnly);

			} else {
				component.setReadOnly(getBool(readOnly.getExpressionString()));
			}
		}

		if (border != null) {
			if (border.isLiteralText()==false) {
				component.setValueExpression(Properties.BORDER, border);

			} else {
				component.setBorder(getBool(border.getExpressionString()));
			}
		}

		if (maxTextLength != null) {
			if (maxTextLength.isLiteralText()==false) {
				component.setValueExpression(Properties.MAX_TEXT_LENGTH, maxTextLength);

			} else {
				component.setMaxTextLength(getInt(maxTextLength.getExpressionString()));
			}
		}

		if (editable != null) {
			if (editable.isLiteralText()==false) {
				component.setValueExpression(Properties.EDITABLE, editable);

			} else {
				component.setEditable(getBool(editable.getExpressionString()));
			}
		}

		if (filterProperties != null) {
			if (filterProperties.isLiteralText()==false) {
				component.setValueExpression(Properties.FILTER_PROPERTIES, filterProperties);

			} else {
				component.setFilterProperties(filterProperties.getExpressionString());
			}
		}

		if (suggestionDelayMs != null) {
			if (suggestionDelayMs.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_DELAY_MS, suggestionDelayMs);

			} else {
				component.setSuggestionDelayMs(getInt(suggestionDelayMs.getExpressionString()));
			}
		}

		if (suggestionMinChars != null) {
			if (suggestionMinChars.isLiteralText()==false) {
				component.setValueExpression(Properties.SUGGESTION_MIN_CHARS, suggestionMinChars);

			} else {
				component.setSuggestionMinChars(getInt(suggestionMinChars.getExpressionString()));
			}
		}

		if (rowCountVar != null) {
			if (rowCountVar.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'rowCountVar' does not accept binding !");
			}
				component.setRowCountVar(rowCountVar.getExpressionString());
		}

		if (rowIndexVar != null) {
			if (rowIndexVar.isLiteralText()==false) {
				throw new javax.faces.FacesException("Attribute 'rowIndexVar' does not accept binding !");
			}
				component.setRowIndexVar(rowIndexVar.getExpressionString());
		}

		if (valueColumnId != null) {
			if (valueColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_COLUMN_ID, valueColumnId);

			} else {
				component.setValueColumnId(valueColumnId.getExpressionString());
			}
		}

		if (labelColumnId != null) {
			if (labelColumnId.isLiteralText()==false) {
				component.setValueExpression(Properties.LABEL_COLUMN_ID, labelColumnId);

			} else {
				component.setLabelColumnId(labelColumnId.getExpressionString());
			}
		}

		if (selectedValue != null) {
			if (selectedValue.isLiteralText()==false) {
				component.setValueExpression(Properties.SELECTED_VALUE, selectedValue);

			} else {
				component.setSelectedValue(selectedValue.getExpressionString());
			}
		}

		if (valueFormat != null) {
			if (valueFormat.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_FORMAT, valueFormat);

			} else {
				component.setValueFormat(valueFormat.getExpressionString());
			}
		}

		if (forceValidation != null) {
			if (forceValidation.isLiteralText()==false) {
				component.setValueExpression(Properties.FORCE_VALIDATION, forceValidation);

			} else {
				component.setForceValidation(getBool(forceValidation.getExpressionString()));
			}
		}

		if (forLabel != null) {
			if (forLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.FOR_LABEL, forLabel);

			} else {
				component.setForLabel(forLabel.getExpressionString());
			}
		}

		if (valueFormatLabel != null) {
			if (valueFormatLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.VALUE_FORMAT_LABEL, valueFormatLabel);

			} else {
				component.setValueFormatLabel(valueFormatLabel.getExpressionString());
			}
		}

		if (noValueFormatLabel != null) {
			if (noValueFormatLabel.isLiteralText()==false) {
				component.setValueExpression(Properties.NO_VALUE_FORMAT_LABEL, noValueFormatLabel);

			} else {
				component.setNoValueFormatLabel(noValueFormatLabel.getExpressionString());
			}
		}
	}

	public void release() {
		emptyMessage = null;
		emptyDataMessage = null;
		selectionListeners = null;
		disabled = null;
		required = null;
		readOnly = null;
		border = null;
		maxTextLength = null;
		editable = null;
		filterProperties = null;
		suggestionDelayMs = null;
		suggestionMinChars = null;
		rowCountVar = null;
		rowIndexVar = null;
		valueColumnId = null;
		labelColumnId = null;
		selectedValue = null;
		valueFormat = null;
		forceValidation = null;
		forLabel = null;
		valueFormatLabel = null;
		noValueFormatLabel = null;

		super.release();
	}

}
