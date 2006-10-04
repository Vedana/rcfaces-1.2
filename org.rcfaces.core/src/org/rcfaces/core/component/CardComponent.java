package org.rcfaces.core.component;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.component.AbstractOutputComponent;
import org.rcfaces.core.component.capability.ITextAlignmentCapability;
import org.rcfaces.core.component.capability.ILoadEventCapability;
import org.rcfaces.core.component.capability.IVerticalAlignmentCapability;

public class CardComponent extends AbstractOutputComponent implements 
	ITextAlignmentCapability,
	IVerticalAlignmentCapability,
	ILoadEventCapability,
	IAsyncRenderComponent {

	public static final String COMPONENT_TYPE="org.rcfaces.core.card";


	public CardComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final CardBoxComponent getCardBox() {


				return CardBoxTools.getCardBox(this);
			
	}

	public final java.lang.String getTextAlignment() {
		return getTextAlignment(null);
	}

	public final java.lang.String getTextAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.TEXT_ALIGNMENT, facesContext);
	}

	public final void setTextAlignment(java.lang.String textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final void setTextAlignment(ValueBinding textAlignment) {
		engine.setProperty(Properties.TEXT_ALIGNMENT, textAlignment);
	}

	public final java.lang.String getVerticalAlignment() {
		return getVerticalAlignment(null);
	}

	public final java.lang.String getVerticalAlignment(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.VERTICAL_ALIGNMENT, facesContext);
	}

	public final void setVerticalAlignment(java.lang.String verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public final void setVerticalAlignment(ValueBinding verticalAlignment) {
		engine.setProperty(Properties.VERTICAL_ALIGNMENT, verticalAlignment);
	}

	public final void addLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		addFacesListener(listener);
	}

	public final void removeLoadListener(org.rcfaces.core.event.ILoadListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listLoadListeners() {
		return getFacesListeners(org.rcfaces.core.event.ILoadListener.class);
	}

	public void release() {
		super.release();
	}
}
