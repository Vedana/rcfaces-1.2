package org.rcfaces.core.internal.taglib;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.tools.ListenersTools;
import javax.servlet.jsp.tagext.Tag;
import org.rcfaces.core.internal.tools.ListenersTools1_2;
import javax.el.ValueExpression;
import org.apache.commons.logging.LogFactory;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.Log;
import javax.faces.component.UIViewRoot;
import org.rcfaces.core.component.TreeNodeComponent;
import javax.faces.component.UIComponent;
import javax.faces.application.Application;

public class TreeNodeTag extends ExpandableItemTag implements Tag {


	private static final Log LOG=LogFactory.getLog(TreeNodeTag.class);

	private ValueExpression groupName;
	private ValueExpression styleClass;
	private ValueExpression menuPopupId;
	private ValueExpression inputType;
	private ValueExpression dragEffects;
	private ValueExpression dragTypes;
	private ValueExpression draggable;
	private ValueExpression dropEffects;
	private ValueExpression dropTypes;
	private ValueExpression droppable;
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

	public final void setDragEffects(ValueExpression dragEffects) {
		this.dragEffects = dragEffects;
	}

	public final void setDragTypes(ValueExpression dragTypes) {
		this.dragTypes = dragTypes;
	}

	public final void setDraggable(ValueExpression draggable) {
		this.draggable = draggable;
	}

	public final void setDropEffects(ValueExpression dropEffects) {
		this.dropEffects = dropEffects;
	}

	public final void setDropTypes(ValueExpression dropTypes) {
		this.dropTypes = dropTypes;
	}

	public final void setDroppable(ValueExpression droppable) {
		this.droppable = droppable;
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
			LOG.debug("  dragEffects='"+dragEffects+"'");
			LOG.debug("  dragTypes='"+dragTypes+"'");
			LOG.debug("  draggable='"+draggable+"'");
			LOG.debug("  dropEffects='"+dropEffects+"'");
			LOG.debug("  dropTypes='"+dropTypes+"'");
			LOG.debug("  droppable='"+droppable+"'");
		}
		if ((uiComponent instanceof TreeNodeComponent)==false) {
			if (uiComponent instanceof UIViewRoot) {
				throw new IllegalStateException("The first component of the page must be a UIViewRoot component !");
			}
			throw new IllegalStateException("Component specified by tag is not instanceof of 'TreeNodeComponent'.");
		}

		super.setProperties(uiComponent);

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

		if (dragEffects != null) {
			if (dragEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAG_EFFECTS, dragEffects);

			} else {
				component.setDragEffects(dragEffects.getExpressionString());
			}
		}

		if (dragTypes != null) {
			if (dragTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAG_TYPES, dragTypes);

			} else {
				component.setDragTypes(dragTypes.getExpressionString());
			}
		}

		if (draggable != null) {
			if (draggable.isLiteralText()==false) {
				component.setValueExpression(Properties.DRAGGABLE, draggable);

			} else {
				component.setDraggable(getBool(draggable.getExpressionString()));
			}
		}

		if (dropEffects != null) {
			if (dropEffects.isLiteralText()==false) {
				component.setValueExpression(Properties.DROP_EFFECTS, dropEffects);

			} else {
				component.setDropEffects(dropEffects.getExpressionString());
			}
		}

		if (dropTypes != null) {
			if (dropTypes.isLiteralText()==false) {
				component.setValueExpression(Properties.DROP_TYPES, dropTypes);

			} else {
				component.setDropTypes(dropTypes.getExpressionString());
			}
		}

		if (droppable != null) {
			if (droppable.isLiteralText()==false) {
				component.setValueExpression(Properties.DROPPABLE, droppable);

			} else {
				component.setDroppable(getBool(droppable.getExpressionString()));
			}
		}
	}

	public void release() {
		groupName = null;
		styleClass = null;
		menuPopupId = null;
		inputType = null;
		dragEffects = null;
		dragTypes = null;
		draggable = null;
		dropEffects = null;
		dropTypes = null;
		droppable = null;

		super.release();
	}

}
