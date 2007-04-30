/*
 */
package org.rcfaces.core.internal.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IVariableScopeCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IConvertValueHolder;
import org.rcfaces.core.internal.capability.IRCFacesComponent;
import org.rcfaces.core.internal.capability.IStateChildrenList;
import org.rcfaces.core.internal.capability.ISubmittedExternalValue;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IRendererExtension;
import org.rcfaces.core.internal.tools.ComponentTools;

/**
 * @author Olivier Oeuillot
 */
public abstract class CameliaInputComponent extends javax.faces.component.UIInput implements
		IRCFacesComponent, IContainerManager, ITransientAttributesManager, IConvertValueHolder, ISubmittedExternalValue {
	private static final String REVISION = "$Revision$";

	private static final Log LOG = LogFactory.getLog(CameliaInputComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(Arrays.asList(new String[] {"value","converter"}));

	protected final transient IComponentEngine engine;

	private transient IStateChildrenList stateChildrenList;

	 private static final Object NULL_VALUE = org.rcfaces.core.internal.tools.ValuesTools.NULL_VALUE;


	protected CameliaInputComponent() {
		IFactory factory = Constants.getCameliaFactory();

		this.engine = factory.createComponentEngine();

		IInitializationState state=factory.createInitializationState();
        initializeComponent(state);	
    }

    protected void initializeComponent(IInitializationState state) {
    	if (Constants.TEMPLATE_ENGINE_SUPPORT) {
	        if (isTemplateComponent(state) && state.isConstructionState()) {
	        	if (LOG.isDebugEnabled()) {
	        		LOG.debug("Call construct template method.");
	        	}
	            constructTemplate(state);
	        }
	    }
	    if (Constants.COMPONENT_DEFAULT_PROPERTIES_SUPPORT) {
	        if (hasDefaultProperties(state) && state.isConstructionState()) {
	        	if (LOG.isDebugEnabled()) {
	        		LOG.debug("Call setDefaultProperties method.");
	        	}
	            setDefaultProperties(state);
	        }
	    }
	    
    }
 
    protected boolean isTemplateComponent(IInitializationState state) {
        return false;
    }

    protected void constructTemplate(IInitializationState state) {
    }

    protected boolean hasDefaultProperties(IInitializationState state) {
        return false;
    }

    protected void setDefaultProperties(IInitializationState state) {
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

	public final void setValueBinding(String name, ValueBinding binding) {
		if (getCameliaFields().contains(name)) {
			if (name.equals(getCameliaValueAlias())) {
				name="value";
			}

			engine.setProperty(name, binding);
			return;
		}
		
		super.setValueBinding(name, binding);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}

	protected String getCameliaValueAlias() {
		return null;
	}

	public final ValueBinding getValueBinding(String name) {
		if (getCameliaFields().contains(name)) {
			if (name.equals(getCameliaValueAlias())) {
				name="value";
			}

			return engine.getValueBindingProperty(name);
		}

		return super.getValueBinding(name);
	}

/*
	public void release() {
		if (engine != null) {
			engine.release();
		}
	}
*/
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

        ComponentTools.IVarScope varScope = null;
        if (this instanceof IVariableScopeCapability) {
            varScope=ComponentTools.processVariableScope(context, (IVariableScopeCapability)this);
        }

		engine.startDecodes(context);
		
		Renderer renderer = getRenderer(context);
        if ((renderer instanceof IRendererExtension) == false) {
            super.processDecodes(context);

        } else  {
	        CameliaComponents.processDecodes(context, this, renderer);
	    }
       
        if (varScope!=null) {
            varScope.popVar(context);
        }
	}

	public void processValidators(FacesContext context) {
		if (context == null) {
            throw new NullPointerException("Context is NULL to processValidators");
        }

        // Skip processing if our rendered flag is false
		if (isRendered()==false) {
            return;
        }

        ComponentTools.IVarScope varScope = null;
        if (this instanceof IVariableScopeCapability) {
            varScope=ComponentTools.processVariableScope(context, (IVariableScopeCapability)this);
        }

		super.processValidators(context);
       
        if (varScope!=null) {
            varScope.popVar(context);
        }
	}

    public void processUpdates(FacesContext context) {

 		if (isRendered()==false) {
            return;
        }

		ComponentTools.IVarScope varScope = null;
        if (this instanceof IVariableScopeCapability) {
            varScope=ComponentTools.processVariableScope(context, (IVariableScopeCapability)this);
        }

        engine.processUpdates(context);

        super.processUpdates(context);
        
        if (varScope!=null) {
            varScope.popVar(context);
        }
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
		
		Boolean visible=visibilityCapability.getVisibleState();
		if (visible==null || visible.booleanValue()) {
			return true;
		}
		
		if ((this instanceof IHiddenModeCapability)==false) {
			return false;
		}
		
		IHiddenModeCapability hiddenModeCapability=(IHiddenModeCapability)this;
		
		int hiddenMode=hiddenModeCapability.getHiddenMode();
		if (IHiddenModeCapability.SERVER_HIDDEN_MODE==hiddenMode) {
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
	
	public final void setRendered(ValueBinding binding) {
		setValueBinding("rendered", binding);
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
	
	public final void setConverter(String converterId) {


			 setConverter(null, converterId);
		
	}

	public final void setConverter(ValueBinding valueBinding) {


              this.setValueBinding("converter", valueBinding);
		
	}

	public final void setConverter(FacesContext facesContext, String converterId) {


			if (facesContext==null) {
				facesContext=FacesContext.getCurrentInstance();
			}
			Converter converter = facesContext.getApplication().createConverter(converterId);
            this.setConverter(converter);
		
	}

	public final void setValue(ValueBinding value) {


			engine.setValue(Properties.VALUE, value);
		
	}

	public final void setRequired(ValueBinding required) {


			setValueBinding("required", required);
		
	}

	public final void broadcast(FacesEvent event) {


				if (!Constants.MYFACES_INPUT_BROADCAST_BUG) {
					super.broadcast(event);
					return;
				}
				
				// Bug #1647203
				if (event instanceof ValueChangeEvent) {				
					super.broadcast(event);
					return;
				}
				
				FacesListener listeners[]=getFacesListeners(FacesListener.class);
				ComponentTools.broadcast(this, event, listeners);
				
			
	}

	public final Object getLocalValue() {


				Object value=super.getLocalValue();
				
				if (NULL_VALUE.equals(value)) {
					value=null;
				}
				
				return value;
			
	}

	protected Object getConvertedValue(FacesContext context, Object submittedValue) {

				
				if (NULL_VALUE.equals(submittedValue)) {
					submittedValue=null;
				}
				
				return super.getConvertedValue(context, submittedValue);
			
	}

	public final Object getSubmittedExternalValue() {


				Object value=super.getSubmittedValue();
				
				if (NULL_VALUE.equals(value)) {
					value=null;
				}
				
				return value;
			
	}

	public final void setSubmittedExternalValue(Object submittedValue) {


				if (submittedValue==null) {
					submittedValue=NULL_VALUE;
				}
				
				super.setSubmittedValue(submittedValue);
			
	}

	public final boolean isSubmittedValueSetted() {


				Object value=super.getSubmittedValue();
				
				if (NULL_VALUE.equals(value)) {
					return true;
				}
				
				return value!=null;
			
	}

	public final Object getValue() {


				Object value=super.getValue();
				
				if (NULL_VALUE.equals(value)) {
					value=null;
				}
				
				return value;
			
	}


}
