/*
 */
package org.rcfaces.core.internal.component;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import java.lang.String;
import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IValueLockedCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.CameliaComponents;
import org.rcfaces.core.internal.component.IComponentEngine;
import org.rcfaces.core.internal.component.IConvertValueHolder;
import org.rcfaces.core.internal.component.IFactory;
import org.rcfaces.core.internal.component.StateIdChildrenList;
import org.rcfaces.core.internal.component.TemplatesEngine;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IRendererExtension;



/**
 * @author Olivier Oeuillot
 */
public abstract class CameliaInputComponent extends javax.faces.component.UIInput implements
		IContainerManager, ITransientAttributesManager, IConvertValueHolder {
	private static final String REVISION = "$Revision$";

	protected final transient IComponentEngine engine;

	private transient StateIdChildrenList children;


	protected CameliaInputComponent() {
		IFactory factory = Constants.getCameliaFactory();

		this.engine = factory.createComponentEngine();

        initializeComponent();
    }

    protected void initializeComponent() {
    	if (Constants.TEMPLATE_SUPPORT) {
	        if (isTemplateComponent() && TemplatesEngine.isConstructPhase()) {
	            constructTemplate();
	        }
	    }
    }
    

    protected boolean isTemplateComponent() {
        return false;
    }

    protected void constructTemplate() {
    }

	public String getFamily() {
		return CameliaComponents.FAMILY;
	}

	public final String getRendererType() {
		String rendererType = super.getRendererType();

		if ((this instanceof ILookAndFeelCapability) == false) {
			return rendererType;
		}

		String lookId = ((ILookAndFeelCapability) this).getLookId();
		if (lookId == null) {
			return rendererType;
		}

		return rendererType + ":" + lookId;
	}

	public void restoreState(FacesContext context, Object state) {
		Object states[] = (Object[]) state;

		super.restoreState(context, states[0]);

		engine.restoreState(context, states[1]);
	}

	public Object saveState(FacesContext context) {
		Object states[] = new Object[2];

		states[0] = super.saveState(context);
		states[1] = engine.saveState(context);

		return states;
	}

	/*
	 *
	public final void setValueBinding(String name, ValueBinding binding) {
		engine.setProperty(name, binding);
	}
	*/

	public final ValueBinding getValueBinding(String name) {
		ValueBinding valueBinding = engine.getValueBindingProperty(name);
		if (valueBinding != null) {
			return valueBinding;
		}

		return super.getValueBinding(name);
	}

	public void release() {
		if (engine != null) {
			engine.release();
		}
	}

	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (isRendered()==false || isClientRendered()==false) {
			return;
		}

		engine.startDecodes(context);
		Renderer renderer = getRenderer(context);
        if ((renderer instanceof IRendererExtension) == false) {
            super.processDecodes(context);
            return;
        }

        CameliaComponents.processDecodes(context, this, renderer);
		
		// Attention !
		// Le fait de detourner le processDecodes peut poser de nombreux problemes,
		// nottament dans le cas d'un UIInput, UIData, ... !
	}

	public void processValidators(FacesContext context) {
		if (context == null) {
            throw new NullPointerException("Context is NULL to processValidators");
        }

        // Skip processing if our rendered flag is false
		if (isRendered()==false || isClientRendered()==false) {
            return;
        }

		if (this instanceof IValueLockedCapability) {
			IValueLockedCapability valueLocked = (IValueLockedCapability) this;

			if (valueLocked.isValueLocked()) {
				// Pas de validation !
				return;
			}
		}
		

		super.processValidators(context);
	}

    public void processUpdates(FacesContext context) {

 		if (isRendered()==false || isClientRendered()==false) {
            return;
        }
 
        engine.processUpdates(context);

        super.processUpdates(context);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.component.UIComponent#getChildren()
	 */
	public final List getChildren() {
		List list = super.getChildren();

		if (children == null) {
			children = new StateIdChildrenList();
		}

		children.setChildren(list);

		return children;
	}

	public final int getContainerStateId() {
		if (children == null) {
			return 0;
		}

		return children.getStateId();
	}

	public final Object getTransientAttribute(String name) {
		return engine.getTransientAttribute(name);
	}

	public final Object setTransientAttribute(String name, Object value) {
		return engine.setTransientAttribute(name, value);
	}

	protected final boolean isClientRendered() {
		if ((this instanceof IVisibilityCapability)==false) {
			return true;
		}
		
		IVisibilityCapability visibilityCapability=(IVisibilityCapability)this;
		
		Boolean visible=visibilityCapability.getVisible();
		if (visible==null || visible.booleanValue()) {
			return true;
		}
		
		int hiddenMode=visibilityCapability.getHiddenMode();
		if (IVisibilityCapability.SERVER_HIDDEN_MODE==hiddenMode) {
			return false;
		}
		
		return true;
	}
	
	public final IAsyncRenderer getAsyncRenderer(FacesContext facesContext) {
		Renderer renderer=getRenderer(facesContext);
		if (renderer instanceof IAsyncRenderer) {
			return (IAsyncRenderer) renderer;
		}
		
		return null;
	}

   public void queueEvent(FacesEvent e) {
// Un keyPress doit pouvoir activer l'immediate !
// Oui mais le code d'appel ne fait r�f�rence qu'a des ActionEvent
		if (e instanceof ActionEvent) {
	   		if (this instanceof IImmediateCapability) {
	   			IImmediateCapability immediateCapability=(IImmediateCapability)this;
	   			
			    if (immediateCapability.isImmediate()) {
					e.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
					
			    } else {
					e.setPhaseId(PhaseId.INVOKE_APPLICATION);
			    }
			}
		}
		
		super.queueEvent(e);
    }
	
	public final Converter getConverter() {


		return getConverter(null);
		
	}

	public final Converter getConverter(FacesContext facesContext) {


		return engine.getConverter(facesContext);
		
	}

	public final void setConverter(String converterId) {


		engine.setConverterId(converterId);
		
	}

	public final void setConverter(ValueBinding converter) {


			engine.setConverterId(converter);
		
	}

	public final void setConverter(Converter converter) {


		engine.setConverter(converter);
		
	}

	public final void setValue(ValueBinding value) {


			engine.setValue(Properties.VALUE, value);
		
	}

	public final void setRequired(ValueBinding required) {


			setValueBinding("required", required);
		
	}


}
