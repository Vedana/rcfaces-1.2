package org.rcfaces.core.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.convert.Converter;
import org.rcfaces.core.internal.capability.IConvertValueHolder;
import javax.faces.context.FacesContext;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.rcfaces.core.component.AbstractCommandComponent;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

/**
 * Technical component, used as a basis for building new RCFaces components.
 */
public abstract class AbstractConverterCommandComponent extends AbstractCommandComponent implements 
	IConvertValueHolder {

	private static final Log LOG = LogFactory.getLog(AbstractConverterCommandComponent.class);

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(AbstractCommandComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"converter"}));
	}


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


        	engine.setProperty("converter", converter);
		
	}

	public Converter getConverter() {


        	return (Converter)engine.getProperty("converter", null);
		
	}

	public Converter getConverter(FacesContext facesContext) {


        	return (Converter)engine.getProperty("converter", facesContext);
		
	}

	public Object getLocalValue() {


		return engine.getLocalValue(Properties.VALUE);
		
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
