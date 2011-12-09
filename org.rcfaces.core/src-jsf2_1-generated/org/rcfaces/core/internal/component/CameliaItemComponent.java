/*
 */
package org.rcfaces.core.internal.component;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.io.IOException;
import java.io.Serializable;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.component.StateHelper;
import javax.faces.component.behavior.ClientBehaviorHolder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import org.rcfaces.core.component.capability.IAsyncDecodeModeCapability;
import org.rcfaces.core.component.capability.IHiddenModeCapability;
import org.rcfaces.core.component.capability.IImmediateCapability;
import org.rcfaces.core.component.capability.ILookAndFeelCapability;
import org.rcfaces.core.component.capability.IValidationEventCapability;
import org.rcfaces.core.component.capability.IVisibilityCapability;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.RcfacesContext;

import org.rcfaces.core.internal.capability.IVariableScopeCapability;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IComponentLifeCycle;
import org.rcfaces.core.internal.capability.IListenerStrategy;
import org.rcfaces.core.internal.capability.IRCFacesComponent;
import org.rcfaces.core.internal.capability.IStateChildrenList;
import org.rcfaces.core.internal.component.CameliaComponents;
import org.rcfaces.core.internal.component.ComponentEngine;
import org.rcfaces.core.internal.component.RestoreViewPhaseListener;
import org.rcfaces.core.internal.component.IInitializationState;
import org.rcfaces.core.internal.component.IComponentEngineAccessor;
import org.rcfaces.core.internal.component.DataMapAccessor;
import org.rcfaces.core.internal.converter.StrategyListenerConverter;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;
import org.rcfaces.core.internal.renderkit.IAsyncRenderer;
import org.rcfaces.core.internal.renderkit.IRendererExtension;
import org.rcfaces.core.internal.tools.ComponentTools;
import org.rcfaces.core.internal.tools.BindingTools;
import org.rcfaces.core.event.IValidationListener;
import org.rcfaces.core.event.ValidationEvent;

import org.rcfaces.core.internal.capability.IValueExpressionCapability;

/**
 * @author Olivier Oeuillot
 */
public abstract class CameliaItemComponent extends javax.faces.component.UISelectItem implements
		IRCFacesComponent, IContainerManager, IComponentLifeCycle, IValueExpressionCapability, IComponentEngineAccessor, ClientBehaviorHolder, ITransientAttributesManager {
	private static final String REVISION = "$Revision$";

	private static final Log LOG = LogFactory.getLog(CameliaItemComponent.class);

	private transient Map<String, Object> transientAttributes=null;

	private transient IComponentEngine componentEngine = null;
	
    protected static final Collection<String> BEHAVIOR_EVENT_NAMES = Collections.emptySet();


	protected CameliaItemComponent() {
//		IInitializationState state=factory.createInitializationState();
 //       initializeComponent(state);	
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
	    
	    constructPhase(state.getFacesContext());	    
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

    @Override
 	public String getFamily() {
		return CameliaComponents.FAMILY;
	}

    @Override
 	public final String getRendererType() {
		String rendererType = super.getRendererType();
		if (rendererType == null) {
        	if (LOG.isTraceEnabled()) {
        		LOG.trace("RendererType is null for component id='"+getId()+"' class='"+getClass()+"'");
        	}
			return null;
		}

		if ((this instanceof ILookAndFeelCapability) == false) {
			return rendererType;
		}

		String lookId = ((ILookAndFeelCapability) this).getLookId();
		if (lookId == null) {
			return rendererType;
		}

		return rendererType + ":" + lookId;
	}

    @Override
 	public void restoreState(FacesContext context, Object state) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Restoring state of component '"+getId()+"'.");
		}
		
		try {
			super.restoreState(context, state);
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not restore state of component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }

		if (LOG.isTraceEnabled()) {
			LOG.trace("State of component '"+getId()+"' restored.");
		}
	}

    @Override
 	public Object saveState(FacesContext context) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Saving state of component '"+getId()+"'.");
		}

		Object states;
		try {	
			states = super.saveState(context);
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not save state of component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }

		if (LOG.isTraceEnabled()) {
			LOG.trace("State of component '"+getId()+"' saved.");
		}

		return states;
	}

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException("FacesContext is null");
		}

		try {	
	    	super.encodeBegin(context);
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not encode-begin component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
	}
	
    @Override
    public void encodeChildren(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException("FacesContext is null");
		}

		try {
	    	super.encodeChildren(context);    	
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not encode children of component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
	}
	
    @Override
    public void encodeEnd(FacesContext context) throws IOException {
		if (context == null) {
			throw new NullPointerException("FacesContext is null");
		}

		try {	
	    	super.encodeEnd(context);    	
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not encode-end component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
	}
	
	protected boolean verifyAsyncDecode(FacesContext context, PhaseId phaseId) {
		return ComponentTools.verifyAsyncDecode(context, (IAsyncDecodeModeCapability) this, phaseId);
	}
	
    @Override
	public void processDecodes(FacesContext context) {
		if (context == null) {
			throw new NullPointerException();
		}
		
		try {
			if (isRendered()==false) {
				return;
			}
			
			if (this instanceof IAsyncDecodeModeCapability) {
			    if (verifyAsyncDecode(context, PhaseId.APPLY_REQUEST_VALUES)==false) {
			        return;
			    }
			}			
	
	        ComponentTools.IVarScope varScope = null;
	        if (this instanceof IVariableScopeCapability) {
	            varScope=BindingTools.processVariableScope(context, (IVariableScopeCapability)this, PhaseId.APPLY_REQUEST_VALUES);
	        }
	
			getComponentEngine().processDecodes(context);
			
			Renderer renderer = getRenderer(context);
	        if ((renderer instanceof IRendererExtension) == false) {
	            super.processDecodes(context);
	
	        } else  {
		        CameliaComponents.processDecodes(context, this, renderer);
		    }
			
            boolean immediate=false;
	  		if (this instanceof IValidationEventCapability) {
	            if (this instanceof IImmediateCapability) {
	                immediate=((IImmediateCapability)this).isImmediate();
	            }
			}

 			if (immediate) {
				if (ComponentTools.hasValidationServerListeners(getFacesListeners(IValidationListener.class))) {
					this.broadcast(new ValidationEvent(this));
				}
				processEngineValidators(context);
			}
	       
	        if (varScope!=null) {
	            varScope.popVar(context);
	        }
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not decode component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
	}

    @Override
	public void processValidators(FacesContext context) {
		if (context == null) {
            throw new NullPointerException("Context is NULL to processValidators");
        }

		try {
	        // Skip processing if our rendered flag is false
			if (isRendered()==false) {
	            return;
	        }
			
			if (this instanceof IAsyncDecodeModeCapability) {
			    if (verifyAsyncDecode(context, PhaseId.PROCESS_VALIDATIONS)==false) {
			        return;
			    }
			}			
	
	        ComponentTools.IVarScope varScope = null;
	        if (this instanceof IVariableScopeCapability) {
	            varScope=BindingTools.processVariableScope(context, (IVariableScopeCapability)this, PhaseId.PROCESS_VALIDATIONS);
	        }
	
			super.processValidators(context);
	
            boolean immediate=false;
	 		if (this instanceof IValidationEventCapability) {
	            if (this instanceof IImmediateCapability) {
	                immediate=((IImmediateCapability)this).isImmediate();
	            }
			}
	 			
			if (immediate==false) {
				if (ComponentTools.hasValidationServerListeners(getFacesListeners(IValidationListener.class))) {
					this.broadcast(new ValidationEvent(this));
				}
			
				processEngineValidators(context);
			}
			       
	        if (varScope!=null) {
	            varScope.popVar(context);
	        }
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not valid component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
	}
  
	protected void processEngineValidators(FacesContext context) {
		getComponentEngine().processValidation(context);
	}
 
    @Override
    public void processUpdates(FacesContext context) {
		try {
	
	 		if (isRendered()==false) {
	            return;
	        }        
			
			if (this instanceof IAsyncDecodeModeCapability) {
			    if (verifyAsyncDecode(context, PhaseId.UPDATE_MODEL_VALUES)==false) {
			        return;
			    }
			}			
	
			ComponentTools.IVarScope varScope = null;
	        if (this instanceof IVariableScopeCapability) {
	            varScope=BindingTools.processVariableScope(context, (IVariableScopeCapability)this, PhaseId.UPDATE_MODEL_VALUES);
	        }
	
			processEngineUpdates(context);
	
	        super.processUpdates(context);
	        
	        if (varScope!=null) {
	            varScope.popVar(context);
	        }
	    	
	    } catch (RuntimeException ex) {
	    	LOG.error("Can not update component '"+getId()+"'.", ex);
	    	
	    	throw ex;
	    }
    }
    
	protected void processEngineUpdates(FacesContext context) {
		getComponentEngine().processUpdates(context);			
	}
    
	public final Object getTransientAttribute(String name) {
		if (transientAttributes==null) {
			return null;
		}
		return transientAttributes.get(name);
	}

	public final Object setTransientAttribute(String name, Object value) {
		if (transientAttributes==null) {
			transientAttributes=new HashMap<String, Object>();
		}
		return transientAttributes.put(name, value);
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
	
    @Override
	public boolean isRendered() {
		if (super.isRendered()==false) {
			return false;
		}
		
		return isClientRendered();
	}
	
	public void setRendered(ValueExpression binding) {
		setValueExpression("rendered", binding);
	}
	
	
	public final IAsyncRenderer getAsyncRenderer(FacesContext facesContext) {
		Renderer renderer=getRenderer(facesContext);
		if (renderer instanceof IAsyncRenderer) {
			return (IAsyncRenderer) renderer;
		}
		
		return null;
	}

    @Override
   public void queueEvent(FacesEvent e) {
// Un keyPress doit pouvoir activer l'immediate !
// Oui mais le code d'appel ne fait r�f�rence qu'a des ActionEvent
		if ((e instanceof ActionEvent) && e.getComponent()==this) {
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
    
    public void clearListeners() {
    	FacesListener fcs[]=getFacesListeners(FacesListener.class);
    	for(int i=0;i<fcs.length;i++) {
    		removeFacesListener(fcs[i]);
    	}
    }
    
    public void constructPhase(FacesContext facesContext) {
    }
    
    public void initializePhase(FacesContext facesContext,  boolean reused) {
        if (reused) {
        
       		RcfacesContext rcfacesContext = RcfacesContext
					.getInstance(facesContext);
			int faceletListenerMode = rcfacesContext
					.getListenerManagerStrategy();

			if ((IListenerStrategy.CLEAN_ALL & faceletListenerMode) > 0) {
				clearListeners();
			}
        }
    }

    public void decodePhase(FacesContext facesContext) {
    }

    public void validationPhase(FacesContext facesContext) {
    }

    public void updatePhase(FacesContext facesContext) {
    }

    public void renderPhase(FacesContext facesContext) {
    }   
    
    public boolean confirmListenerAppend(FacesContext facesContext, Class facesListenerClass){
    	
    	RcfacesContext rcfacesContext = RcfacesContext
				.getInstance(facesContext);
		int faceletListenerMode = rcfacesContext.getListenerManagerStrategy();

		if (((IListenerStrategy.ADD_IF_NEW | IListenerStrategy.CLEAN_BY_CLASS) & faceletListenerMode) == 0) {
			return true;
		}

		FacesListener fcs[] = getFacesListeners(facesListenerClass);
		if ((IListenerStrategy.ADD_IF_NEW & faceletListenerMode) > 0) {
			return fcs.length == 0;
		}

		if ((IListenerStrategy.CLEAN_BY_CLASS & faceletListenerMode) > 0) {
			for (int i = 0; i < fcs.length; i++) {
				removeFacesListener(fcs[i]);
			}

			return true;
		}

		return true;
    } 
      
      
    @Override
    protected StateHelper getStateHelper(boolean create) {
    	if (componentEngine!=null) {
    		return componentEngine.getStateHelper();
    	}
    	if (create==false) {
    		return null;
    	}
     
    	componentEngine=new ComponentEngine(this);
    	
    	return componentEngine.getStateHelper();
	}    
 
	/**
	 * INTERNAL USE ONLY
	 */
    public final IComponentEngine getComponentEngine() {
    	return (IComponentEngine) getStateHelper(true);
    }

	/**
	 * INTERNAL USE ONLY
	 */
	public final void cloneComponentEngine() {
		IComponentEngine origin = getComponentEngine();
		
		componentEngine=origin.copy();
	}
 
	protected final IDataMapAccessor getDataMapAccessor(FacesContext context, String name,
            boolean modify) {
        StateHelper stateHelper=getStateHelper(modify);
        if (stateHelper==null) {
        	return null;
        } 
           
    	return new DataMapAccessor(this, stateHelper, name);
    }
    
    protected final boolean isPropertySetted(java.io.Serializable key) {
    	if (getStateHelper().get(key)!=null) {
    		return true;
    	}
    	
    	if (getValueExpression(key)!=null) {
    		return true;
    	}
    	
    	return false;	
    }
 	
	public final void setValueExpression(Serializable property, ValueExpression expression) {
		setValueExpression(String.valueOf(property), expression);
	}
	
    @Override
 	public String toString() {
		String name=getClass().getName();
		name=name.substring(name.lastIndexOf('.')+1);
		
		String s=name+"[id='"+getId()+"'";
		
		if (isRendered()==false) {
			s+=",rendered=false";
		}
		
		// s+=","+engine.toString();
		
		s+=",rendererId='"+getRendererType()+"'";
		
		if (getFamily()!=CameliaComponents.FAMILY) {
			s+=",family='"+getFamily()+"'";
		}
		
		return s+"]";
	}
	
	public final ValueExpression getValueExpression(Serializable name) {
		String propertyName=String.valueOf(name.toString());
		
		ValueExpression valueExpression=getValueExpression(propertyName);
		
		return valueExpression;
	}

    @Override
    public Collection<String> getEventNames() {
        return BEHAVIOR_EVENT_NAMES;   
	}
	

}
