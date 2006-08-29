package org.rcfaces.core.component;

import org.rcfaces.core.component.CardBoxComponent;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.component.AbstractOutputComponent;
import org.rcfaces.core.internal.tools.CardBoxTools;
import org.rcfaces.core.internal.component.IAsyncRenderComponent;
import org.rcfaces.core.component.capability.ILoadEventCapability;

public class CardComponent extends AbstractOutputComponent implements 
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
