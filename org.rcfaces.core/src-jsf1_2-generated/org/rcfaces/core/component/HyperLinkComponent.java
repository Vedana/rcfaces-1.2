package org.rcfaces.core.component;

import javax.faces.convert.Converter;
import org.rcfaces.core.internal.capability.IConvertValueHolder;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import java.util.HashSet;
import java.lang.String;
import java.util.Arrays;
import java.util.Set;
import javax.faces.context.FacesContext;

/**
 * <p>The hyperLink Component translates into a classic hyperlink and is a <A href="/comps/buttonComponent.html">button</A>.</p>
 * <p>The hyperLink Component has the following capabilities :
 * <ul>
 * <li>Position &amp; Size</li>
 * <li>Foreground &amp; Background Color</li>
 * <li>Text, font &amp; image</li>
 * <li>Help</li>
 * <li>Visibility, Read-Only, Disabled</li>
 * <li>Events Handling</li>
 * </ul>
 * </p>
 */
public class HyperLinkComponent extends ButtonComponent implements 
	IConvertValueHolder {

	public static final String COMPONENT_TYPE="org.rcfaces.core.hyperLink";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(ButtonComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"converter"}));
	}

	public HyperLinkComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public HyperLinkComponent(String componentId) {
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
