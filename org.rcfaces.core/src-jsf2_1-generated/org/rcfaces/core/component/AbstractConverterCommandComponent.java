package org.rcfaces.core.component;

import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.internal.capability.IConvertValueHolder;
import org.rcfaces.core.component.capability.IUnlockedClientAttributesCapability;
import org.apache.commons.logging.LogFactory;
import java.lang.String;
import org.rcfaces.core.component.AbstractCommandComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.el.ValueExpression;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import java.util.Collection;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractConverterCommandComponent extends AbstractCommandComponent implements 
	IUnlockedClientAttributesCapability,
	IConvertValueHolder {

	private static final Log LOG = LogFactory.getLog(AbstractConverterCommandComponent.class);

	protected static final Collection<String> BEHAVIOR_EVENT_NAMES=AbstractCommandComponent.BEHAVIOR_EVENT_NAMES;


	public void setConverter(String converterId) {


			 setConverter(null, converterId);
		
	}

	public void setConverter(FacesContext facesContext, String converterId) {


			if (facesContext==null) {
				facesContext=FacesContext.getCurrentInstance();
			}
			Converter converter = facesContext.getApplication().createConverter(converterId);
            this.setConverter(converter);
		
	}

	public void setConverter(Converter converter) {


        	getStateHelper().put("converter", converter);
		
	}

	public Converter getConverter() {


        	return (Converter)getStateHelper().eval("converter", null);
		
	}

	public Converter getConverter(FacesContext facesContext) {


        	return (Converter)getStateHelper().eval("converter", facesContext);
		
	}

	public Object getLocalValue() {


		return getComponentEngine().getLocalValue(Properties.VALUE);
		
	}

}
