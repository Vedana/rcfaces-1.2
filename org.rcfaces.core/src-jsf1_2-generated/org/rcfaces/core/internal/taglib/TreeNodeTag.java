package org.rcfaces.core.internal.taglib;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import org.apache.commons.logging.Log;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TreeNodeComponent;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.faces.context.FacesContext;

public class TreeNodeTag extends ExpandableItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TreeNodeTag.class);

	private ValueExpression groupName;
	private ValueExpression styleClass;
	private ValueExpression menuPopupId;
	private ValueExpression inputType;
	public String getComponentType() {
		return TreeNodeComponent.COMPONENT_TYPE;
	}

	public final void setGroupName(ValueExpression groupName) {
		this.groupName = groupName;
	}

	public final void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	public final void setMenuPopupId(ValueExpression menuPopupId) {
		this.menuPopupId = menuPopupId;
	}

	public final void setInputType(ValueExpression inputType) {
		this.inputType = inputType;
	}

	protected void setProperties(UIComponent uiComponent) {
		if (LOG.isDebugEnabled()) {
			if (TreeNodeComponent.COMPONENT_TYPE==getComponentType()) {
				LOG.debug("Component id='"+getId()+"' type='"+getComponentType()+"'.");
			}
			LOG.debug("  groupName='"+groupName+"'");
			LOG.debug("  styleClass='"+styleClass+"'");
			LOG.debug("  menuPopupId='"+menuPopupId+"'");
			LOG.debug("  inputType='"+inputType+"'");
		}
		super.setProperties(uiComponent);

		if ((uiComponent instanceof TreeNodeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TreeNodeComponent'.");
		}

		TreeNodeComponent component = (TreeNodeComponent) uiComponent;
		FacesContext facesContext = getFacesContext();

		if (groupName != null) {
			if (groupName.isLiteralText()==false) {
				component.setValueExpression(Properties.GROUP_NAME, groupName);

			} else {
				component.setGroupName(groupName.getExpressionString());
			}
		}

		if (styleClass != null) {
			if (styleClass.isLiteralText()==false) {
				component.setValueExpression(Properties.STYLE_CLASS, styleClass);

			} else {
				component.setStyleClass(styleClass.getExpressionString());
			}
		}

		if (menuPopupId != null) {
			if (menuPopupId.isLiteralText()==false) {
				component.setValueExpression(Properties.MENU_POPUP_ID, menuPopupId);

			} else {
				component.setMenuPopupId(menuPopupId.getExpressionString());
			}
		}

		if (inputType != null) {
			if (inputType.isLiteralText()==false) {
				component.setValueExpression(Properties.INPUT_TYPE, inputType);

			} else {
				component.setInputType(inputType.getExpressionString());
			}
		}
	}

	public void release() {
		groupName = null;
		styleClass = null;
		menuPopupId = null;
		inputType = null;

		super.release();
	}

}
