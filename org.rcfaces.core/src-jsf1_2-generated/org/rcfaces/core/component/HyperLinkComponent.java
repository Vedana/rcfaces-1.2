package org.rcfaces.core.component;

import javax.faces.convert.Converter;
import org.rcfaces.core.internal.capability.IConvertValueHolder;
import org.rcfaces.core.internal.component.Properties;
import org.rcfaces.core.component.ButtonComponent;
import org.apache.commons.logging.Log;
import java.util.HashSet;
import java.lang.String;
import org.apache.commons.logging.LogFactory;
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
 * 
 * 
 * <p>The default <a href="/apidocs/index.html?org/rcfaces/core/component/HyperLinkComponent.html">hyperLink</a> renderer is link to the <a href="/jsdocs/index.html?f_hyperLink.html" target="_blank">f_hyperLink</a> javascript class. f_hyperLink extends f_input, fa_immediate, fa_value</p>
 * 
 * 
 * <p>Table of component style classes : </p>
 * <table border="1" cellpadding="3" cellspacing="0" width="100%">
 * <tbody >
 * <tr style="text-align:left">
 * <td bgcolor="#eeeeee"  width="33%">Style Name</td>
 * <td bgcolor="#eeeeee"  width="50%">Description</td>
 * </tr>
 * <tr id="pwsif0" style="text-align:left">
 * <td bgcolor="#ffffff"  width="33%">f_hyperLink</td>
 * <td  width="50%">Defines styles for the wrapper element of the component</td>
 * </tr>
 * </tbody>
 * </table>
 */
public class HyperLinkComponent extends ButtonComponent implements 
	IConvertValueHolder {

	private static final Log LOG = LogFactory.getLog(HyperLinkComponent.class);

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
