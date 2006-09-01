package org.rcfaces.core.component;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.component.capability.IAsyncRenderModeCapability;
import org.rcfaces.core.component.capability.IPreferenceCapability;
import org.rcfaces.core.component.capability.ISelectionEventCapability;
import org.rcfaces.core.component.iterator.ICardIterator;
import org.rcfaces.core.internal.component.AbstractInputComponent;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.converter.AsyncRenderModeConverter;
import org.rcfaces.core.internal.tools.CardBoxTools;

public class CardBoxComponent extends AbstractInputComponent implements 
	ISelectionEventCapability,
	IAsyncRenderModeCapability,
	IPreferenceCapability {

	public static final String COMPONENT_TYPE="org.rcfaces.core.cardBox";


	public CardBoxComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public CardBoxComponent(String componentId) {
		this();
		setId(componentId);
	}

	public final ICardIterator listCards() {


		return CardBoxTools.listCards(this);
		
	}

	public final CardComponent getSelectedCard() {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public final CardComponent getSelectedCard(FacesContext facesContext) {


		return CardBoxTools.getSelectedCard(this);
		
	}

	public final void select(CardComponent card) {


			CardBoxTools.selectCard(this, card);
			
	}

	public final void setAsyncRenderMode(String asyncRenderMode) {


			setAsyncRenderMode(((Integer)AsyncRenderModeConverter.SINGLETON.getAsObject(null, null, asyncRenderMode)).intValue());
		
	}

	public final void addSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		addFacesListener(listener);
	}

	public final void removeSelectionListener(org.rcfaces.core.event.ISelectionListener listener) {
		removeFacesListener(listener);
	}

	public final javax.faces.event.FacesListener [] listSelectionListeners() {
		return getFacesListeners(org.rcfaces.core.event.ISelectionListener.class);
	}

	public final int getAsyncRenderMode() {
		return getAsyncRenderMode(null);
	}

	public final int getAsyncRenderMode(javax.faces.context.FacesContext facesContext) {
		return engine.getIntProperty(Properties.ASYNC_RENDER_MODE,0, facesContext);
	}

	public final void setAsyncRenderMode(int asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public final void setAsyncRenderMode(ValueBinding asyncRenderMode) {
		engine.setProperty(Properties.ASYNC_RENDER_MODE, asyncRenderMode);
	}

	public final org.rcfaces.core.preference.IComponentPreference getPreference() {
		return getPreference(null);
	}

	public final org.rcfaces.core.preference.IComponentPreference getPreference(javax.faces.context.FacesContext facesContext) {
		return (org.rcfaces.core.preference.IComponentPreference)engine.getProperty(Properties.PREFERENCE, facesContext);
	}

	public final void setPreference(org.rcfaces.core.preference.IComponentPreference preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public final void setPreference(ValueBinding preference) {
		engine.setProperty(Properties.PREFERENCE, preference);
	}

	public void release() {
		super.release();
	}
}
