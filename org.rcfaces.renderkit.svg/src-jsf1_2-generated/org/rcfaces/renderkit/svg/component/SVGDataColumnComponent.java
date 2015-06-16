package org.rcfaces.renderkit.svg.component;

import javax.faces.convert.Converter;
import java.lang.Object;
import org.rcfaces.renderkit.svg.component.Properties;
import javax.el.ValueExpression;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.Set;
import org.rcfaces.core.internal.component.CameliaValueColumnComponent;
import javax.faces.context.FacesContext;

public class SVGDataColumnComponent extends CameliaValueColumnComponent {

	private static final Log LOG = LogFactory.getLog(SVGDataColumnComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.SVGDataColumn";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(CameliaValueColumnComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"value"}));
	}

	public SVGDataColumnComponent() {
		setRendererType(null);
	}

	public SVGDataColumnComponent(String componentId) {
		this();
		setId(componentId);
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

	public Object getLocalValue() {


				return engine.getLocalValue(Properties.VALUE);
			
	}

	public Object getValue() {


				return getValue(null);
			
	}

	public Object getValue(FacesContext context) {


				return engine.getValue(Properties.VALUE, context);
			
	}

	public void setValue(Object value) {


				engine.setValue(Properties.VALUE, value);
			
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
