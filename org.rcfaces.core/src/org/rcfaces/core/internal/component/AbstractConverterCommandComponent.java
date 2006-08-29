package org.rcfaces.core.internal.component;

import java.lang.String;
import org.rcfaces.core.internal.component.Properties;
import javax.faces.convert.Converter;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import org.rcfaces.core.internal.component.IConvertValueHolder;
import org.rcfaces.core.internal.component.AbstractCommandComponent;

public abstract class AbstractConverterCommandComponent extends AbstractCommandComponent implements 
	IConvertValueHolder {



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

	public final Converter getConverter() {


		return getConverter(null);
		
	}

	public final Object getLocalValue() {


		return engine.getLocalValue(Properties.VALUE);
		
	}

	public void release() {
		super.release();
	}
}
