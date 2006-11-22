/*
 */
package org.rcfaces.core.internal.component;

import java.util.List;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.render.Renderer;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.component.IRCFacesComponent;
import org.rcfaces.core.internal.component.CameliaComponents;
import org.rcfaces.core.internal.component.TemplatesEngine;
import org.rcfaces.core.internal.component.IComponentEngine;
import org.rcfaces.core.internal.component.IFactory;
import org.rcfaces.core.internal.component.IStateChildrenList;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IRendererExtension;

/**
 * @author Olivier Oeuillot
 */
public abstract class CameliaBaseComponent extends javax.faces.component.UIComponentBase implements
		IRCFacesComponent, IContainerManager, ITransientAttributesManager {
	private static final String REVISION = "$Revision$";

	private static final Log LOG = LogFactory.getLog(CameliaBaseComponent.class);

	protected final transient IComponentEngine engine;

	private transient IStateChildrenList stateChildrenList;


	protected CameliaBaseComponent() {
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
		if (LOG.isTraceEnabled()) {
			LOG.trace("Restoring state of component '"+getId()+"'.");
		}
		
		Object states[] = (Object[]) state;

		super.restoreState(context, states[0]);

		engine.restoreState(context, states[1]);

		if (LOG.isTraceEnabled()) {
			LOG.trace("State of component '"+getId()+"' restored.");
		}
	}

	public Object saveState(FacesContext context) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Saving state of component '"+getId()+"'.");
		}

		Object states[] = new Object[2];

		states[0] = super.saveState(context);
		states[1] = engine.saveState(context);

		if (LOG.isTraceEnabled()) {
			LOG.trace("State of component '"+getId()+"' saved.");
		}

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

/*
    public void encodeBegin(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException();
		}

		if (isRendered()==false || isClientRendered()==false) {
			return;
		}

    	super.encodeBegin(context);    	
	}
	
    public void encodeChildren(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException();
		}

		if (isRendered()==false || isClientRendered()==false) {
			return;
		}

    	super.encodeChildren(context);    	
	}
	
    public void encodeEnd(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException();
		}

		if (isRendered()==false || isClientRendered()==false) {
			return;
		}

    	super.encodeEnd(context);    	
	}
	*/
	
	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException();
		}

		if (isRendered()==false) {
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
		if (isRendered()==false) {
            return;
        }

		super.processValidators(context);
	}

    public void processUpdates(FacesContext context) {

 		if (isRendered()==false) {
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
		if (Constants.STATED_COMPONENT_CHILDREN_LIST==false) {
			return super.getChildren();
		}
		
		List list = super.getChildren();

		if (stateChildrenList == null) {
			stateChildrenList = engine.createStateChildrenList();
		}

		stateChildrenList.setChildren(list);

		return stateChildrenList;
	}

	public final int getChildrenListState() {
		if (Constants.STATED_COMPONENT_CHILDREN_LIST==false) {
			throw new UnsupportedOperationException("STATED_COMPONENT_CHILDREN_LIST=false");
		}

		if (stateChildrenList == null) {
			return 0;
		}

		return stateChildrenList.getState();
	}

	public final Object getTransientAttribute(String name) {
		return engine.getTransientAttribute(name);
	}

	public final Object setTransientAttribute(String name, Object value) {
		return engine.setTransientAttribute(name, value);
	}

	public final boolean isClientRendered() {
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
	
	public final boolean isRendered() {
		if (super.isRendered()==false) {
			return false;
		}
		
		return isClientRendered();
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
// Oui mais le code d'appel ne fait référence qu'a des ActionEvent
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
	

}
