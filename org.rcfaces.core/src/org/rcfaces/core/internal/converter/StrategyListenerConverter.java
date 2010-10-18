package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.rcfaces.core.model.AbstractConverter;

/**
 * 
 * @author jbmeslin@vedana.com
 */
public class StrategyListenerConverter extends AbstractConverter {

    public static final Converter SINGLETON = new StrategyListenerConverter();

    private final static int DEFAULT= 0x01;
    
    private final static int CLEAN_ALL= 0x02;
    
    private final static int CLEAN_BYCLASS = 0x04;
    
    private static final Map STRATEGY = new HashMap(8);
    static {
    	STRATEGY.put("DEFAULT", new Integer(DEFAULT));
    	STRATEGY.put("CLEAN_ALL", new Integer(CLEAN_ALL));
    	STRATEGY.put("CLEAN_BYCLASS", new Integer(CLEAN_BYCLASS));
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        if (value == null) {
            return DEFAULT;
        }
        
        Integer strategy = (Integer)  STRATEGY.get(value.trim().toUpperCase());
        if (strategy == null){
        	return DEFAULT;
        }
        
        return strategy;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        throw new FacesException("Not implemented !");
    }
}
