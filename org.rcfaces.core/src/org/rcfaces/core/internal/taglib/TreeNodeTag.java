package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.el.ValueBinding;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.TreeNodeComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TreeNodeTag extends ExpandableItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TreeNodeTag.class);

	private String groupName;
	private String styleClass;
	private String menuPopupId;
	private String inputType;
	public String getComponentType() {
		return TreeNodeComponent.COMPONENT_TYPE;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final String getStyleClass() {
		return styleClass;
	}

	public final void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public final String getMenuPopupId() {
		return menuPopupId;
	}

	public final void setMenuPopupId(String menuPopupId) {
		this.menuPopupId = menuPopupId;
	}

	public final String getInputType() {
		return inputType;
	}

	public final void setInputType(String inputType) {
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
		Application application = facesContext.getApplication();

		if (groupName != null) {
			if (isValueReference(groupName)) {
				ValueBinding vb = application.createValueBinding(groupName);
				component.setValueBinding(Properties.GROUP_NAME, vb);

			} else {
				component.setGroupName(groupName);
			}
		}

		if (styleClass != null) {
			if (isValueReference(styleClass)) {
				ValueBinding vb = application.createValueBinding(styleClass);
				component.setValueBinding(Properties.STYLE_CLASS, vb);

			} else {
				component.setStyleClass(styleClass);
			}
		}

		if (menuPopupId != null) {
			if (isValueReference(menuPopupId)) {
				ValueBinding vb = application.createValueBinding(menuPopupId);
				component.setValueBinding(Properties.MENU_POPUP_ID, vb);

			} else {
				component.setMenuPopupId(menuPopupId);
			}
		}

		if (inputType != null) {
			if (isValueReference(inputType)) {
				ValueBinding vb = application.createValueBinding(inputType);
				component.setValueBinding(Properties.INPUT_TYPE, vb);

			} else {
				component.setInputType(inputType);
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
