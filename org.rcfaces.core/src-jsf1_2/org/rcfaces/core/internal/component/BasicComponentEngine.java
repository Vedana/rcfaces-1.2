/*
 * $Id$
 */
package org.rcfaces.core.internal.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IStateChildrenList;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BasicComponentEngine extends AbstractComponentEngine {
	private static final String REVISION = "$Revision$";

	private static final Log LOG = LogFactory
			.getLog(BasicComponentEngine.class);

	private static final boolean debugEnabled = LOG.isDebugEnabled();

	private static final String VALIDATORS_KEY = "camelia.validators";

	private static final String CONVERTER_ID_PROPERTY = "camalia.converterId";

	static final Class BOOLEAN_CLASS = Boolean.class;

	static final Class STRING_CLASS = String.class;

	private static final Class INTEGER_CLASS = Integer.class;

	private static final Integer INTEGER_0 = new Integer(0);

	private static final Class DOUBLE_CLASS = Double.class;

	private static final Double DOUBLE_0 = new Double(0);

	private static final String[] STRING_EMPTY_ARRAY = new String[0];

	private static final int DATA_ACCESSORS_INIT_SIZE = 2;

	private transient boolean enableDelta;

	private Converter converter;

	private boolean converterSetted = false;

	private Map transientAttributes;

	private Map dataAccessorsByName;

	private IPropertiesManager propertiesManager;

	public BasicComponentEngine(IFactory factory) {
		super(factory);
	}

	protected BasicComponentEngine(BasicComponentEngine original) {
		this(original.factory);

		if (debugEnabled) {
			LOG.debug("Clone BasicComponentEngine  source=" + original);
		}

		if (original.propertiesManager != null) {
			propertiesManager = original.propertiesManager.copyOriginalState();
		}

		if (original.dataAccessorsByName != null
				&& original.dataAccessorsByName.isEmpty() == false) {

			dataAccessorsByName = new HashMap(DATA_ACCESSORS_INIT_SIZE);

			for (Iterator it = original.dataAccessorsByName.entrySet()
					.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				BasicDataAccessor originalDataAccessor = (BasicDataAccessor) entry
						.getValue();

				BasicDataAccessor newDataAccessor = (BasicDataAccessor) originalDataAccessor
						.copyOriginalState();

				dataAccessorsByName.put(entry.getKey(), newDataAccessor);
			}
		}
	}

	public final Boolean getBooleanProperty(String propertyName,
			FacesContext facesContext) {
		return (Boolean) getInternalProperty(propertyName, BOOLEAN_CLASS,
				facesContext);
	}

	public final Integer getIntegerProperty(String propertyName,
			FacesContext facesContext) {
		return (Integer) getInternalProperty(propertyName, INTEGER_CLASS,
				facesContext);
	}

	public final boolean getBoolProperty(String propertyName,
			boolean defaultValue, FacesContext facesContext) {
		Object object = getBooleanProperty(propertyName, facesContext);

		if (object == null) {
			return defaultValue;
		}

		if (object == Boolean.FALSE) {
			return false;
		}

		if (object == Boolean.TRUE) {
			return true;
		}

		return ((Boolean) object).booleanValue();
	}

	public final String getStringProperty(String propertyName,
			FacesContext facesContext) {
		return (String) getInternalProperty(propertyName, STRING_CLASS,
				facesContext);
	}

	public final Object getProperty(String propertyName,
			FacesContext facesContext) {
		return getInternalProperty(propertyName, null, facesContext);
	}

	public final int getIntProperty(String propertyName, int defaultValue,
			FacesContext facesContext) {
		Integer i = (Integer) getInternalProperty(propertyName, INTEGER_CLASS,
				facesContext);
		if (i == null) {
			return defaultValue;
		}
		return i.intValue();
	}

	public final double getDoubleProperty(String propertyName,
			double defaultValue, FacesContext facesContext) {
		Double i = (Double) getInternalProperty(propertyName, DOUBLE_CLASS,
				facesContext);
		if (i == null) {
			return defaultValue;
		}
		return i.doubleValue();
	}

	final Object getLocalProperty(String propertyName) {

		if (debugEnabled) {
			LOG.debug("getLocalProperty(\"" + propertyName + "\") ...");
		}

		IPropertiesAccessor propertiesAccessor = getPropertiesAccessor(false);
		if (propertiesAccessor == null) {
			if (debugEnabled) {
				LOG.debug("getLocalProperty(\"" + propertyName
						+ "\") returns null (propertiesAccessor="
						+ propertiesAccessor + ")");
			}
			return null;
		}

		Object value = propertiesAccessor.getProperty(propertyName);

		if (debugEnabled) {
			LOG.debug("getLocalProperty(\"" + propertyName + "\") returns "
					+ value + "  (propertiesAccessor=" + propertiesAccessor
					+ ")");
		}

		return value;
	}

	public final Object getInternalProperty(String propertyName,
			Class requestedClass, FacesContext facesContext) {

		if (debugEnabled) {
			LOG.debug("getInternalProperty(\""
					+ propertyName
					+ "\" ["
					+ ((requestedClass != null) ? requestedClass.getName()
							: "no class") + "]) ... (enableDelta="
					+ enableDelta + ")");
		}

		Object object = getLocalProperty(propertyName);
		if (object == null) {

			if (debugEnabled) {
				LOG.debug("getInternalProperty(\""
						+ propertyName
						+ "\" ["
						+ ((requestedClass != null) ? requestedClass.getName()
								: "no class") + "]) returns null");
			}
			return null;
		}

		if (object instanceof ValueExpression) {
			ValueExpression valueExpression = (ValueExpression) object;

			if (facesContext == null) {
				facesContext = getFacesContext();
			}

			object = valueExpression.getValue(facesContext.getELContext());

			if (debugEnabled) {
				LOG.debug("getInternalProperty(\""
						+ propertyName
						+ "\" ["
						+ ((requestedClass != null) ? requestedClass.getName()
								: "no class") + "]) returns a value binding ("
						+ valueExpression + ") => " + object);
			}
			return object;
		}

		if (debugEnabled) {
			LOG.debug("getInternalProperty(\""
					+ propertyName
					+ "\" ["
					+ ((requestedClass != null) ? requestedClass.getName()
							: "no class") + "]) returns " + object);
		}

		return object;
	}

	protected FacesContext getFacesContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			return facesContext;
		}

		throw new FacesException("FacesContext is not initialized !", null);
	}

	protected final void setInternalProperty(String propertyName, Object value) {

		if (debugEnabled) {
			LOG.debug("setInternalProperty(\"" + propertyName + "\", " + value
					+ ") ...");
		}

		IPropertiesAccessor pa = getPropertiesAccessor(true);

		pa.setProperty(null, propertyName, value);

		if (debugEnabled) {
			LOG.debug("setInternalProperty(\"" + propertyName + "\", " + value
					+ ") propertiesAccessor=" + pa);
		}
	}

	protected final void setInternalProperty(String propertyName,
			ValueExpression value) {

		if (debugEnabled) {
			LOG.debug("setInternalProperty(\"" + propertyName + "\", " + value
					+ ") ...");
		}

		IPropertiesAccessor pa = getPropertiesAccessor(true);

		pa.setProperty(null, propertyName, value);

		if (debugEnabled) {
			LOG.debug("setInternalProperty(\"" + propertyName + "\", " + value
					+ ") propertiesAccessor=" + pa);
		}
	}

	public final void setProperty(String propertyName, boolean value) {
		if (value) {
			setInternalProperty(propertyName, Boolean.TRUE);
			return;
		}

		setInternalProperty(propertyName, Boolean.FALSE);
	}

	public final void setProperty(String propertyName, Boolean bool) {
		if (bool != null) {
			// On s'assure que l'instance de l'objet BOOLEAN est bien static !
			if (bool.booleanValue()) {
				bool = Boolean.TRUE;

			} else {
				bool = Boolean.FALSE;
			}
		}

		setInternalProperty(propertyName, bool);
	}

	public final void setProperty(String propertyName, Integer value) {
		if (value != null) {
			if (value.intValue() == 0) {
				value = INTEGER_0;
			}
		}

		setInternalProperty(propertyName, value);
	}

	public final void setProperty(String propertyName, Double value) {
		if (value != null) {
			if (value.doubleValue() == 0.0) {
				value = DOUBLE_0;
			}
		}

		setInternalProperty(propertyName, value);
	}

	public final void setProperty(String propertyName, double value) {
		if (value == 0.0) {
			setInternalProperty(propertyName, DOUBLE_0);
			return;
		}

		setInternalProperty(propertyName, new Double(value));
	}

	public final void setProperty(String propertyName, int value) {
		if (value == 0) {
			setInternalProperty(propertyName, INTEGER_0);
			return;
		}

		setInternalProperty(propertyName, new Integer(value));
	}

	public final void setProperty(String propertyName, Object value) {
		setInternalProperty(propertyName, value);
	}

	public final void setProperty(String propertyName, ValueExpression value) {
		setInternalProperty(propertyName, value);
	}

	final IPropertiesAccessor getPropertiesAccessor(boolean forceDelta) {

		if (debugEnabled) {
			LOG.debug("getPropertiesAccessor  enableDelta=" + enableDelta
					+ " forceDelta=" + forceDelta + " ...");
		}

		if (propertiesManager != null) {
			return propertiesManager.getPropertiesAccessor(enableDelta,
					forceDelta);
		}

		if (forceDelta == false) {
			return null;
		}

		propertiesManager = factory.createPropertiesManager(this);

		if (debugEnabled) {
			LOG.debug("Create propertiesManager='" + propertiesManager
					+ " for this='" + this + "' enableDelta=" + enableDelta
					+ " forceDelta=" + forceDelta);
		}

		return propertiesManager.getPropertiesAccessor(enableDelta, forceDelta);
	}

	public IDataMapAccessor getDataMapAccessor(FacesContext context,
			String name, boolean forceDelta) {
		IDataMapAccessor dataAccessor;
		if (dataAccessorsByName != null) {
			dataAccessor = (IDataMapAccessor) dataAccessorsByName.get(name);
			if (dataAccessor != null) {
				return dataAccessor;
			}
		}

		if (forceDelta == false) {
			return null;
		}

		dataAccessor = createDataAccessor(context, name);
		if (dataAccessorsByName == null) {
			dataAccessorsByName = new HashMap(DATA_ACCESSORS_INIT_SIZE);
		}

		dataAccessorsByName.put(name, dataAccessor);

		return dataAccessor;
	}

	protected BasicDataAccessor createDataAccessor(FacesContext context,
			String name) {
		return new BasicDataAccessor(name);
	}

	public void restoreState(FacesContext context, Object state) {
		Object states[] = (Object[]) state;

		Object props = states[0];
		if (props != null) {
			propertiesManager = factory.createPropertiesManager(this);
			propertiesManager.restoreManagerState(context, props);

		} else {
			propertiesManager = null;
		}

		Object datas = states[1];
		if (datas != null) {
			Object ds[] = (Object[]) datas;

			dataAccessorsByName = new HashMap(ds.length / 2);
			for (int i = 0; i < ds.length;) {
				String name = (String) ds[i++];

				BasicDataAccessor dataAccessor = createDataAccessor(context,
						name);
				dataAccessor.setCameliaFactory(factory);

				dataAccessor.restoreDataState(context, ds[i++]);

				dataAccessorsByName.put(name, dataAccessor);
			}
		} else {
			dataAccessorsByName = null;
		}

		Object converter = states[2];
		if (converter != null) {
			this.converterSetted = true;
			if (Boolean.FALSE.equals(converter)) {
				this.converter = null;

			} else {
				this.converter = (Converter) UIComponentBase
						.restoreAttachedState(context, converter);
			}

		} else {
			this.converterSetted = false;
			this.converter = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext
	 * )
	 */
	public Object saveState(FacesContext context) {
		Object states[] = new Object[3];

		if (propertiesManager != null) {
			states[0] = propertiesManager.saveManagerState(context);
		}

		if (dataAccessorsByName != null
				&& dataAccessorsByName.isEmpty() == false) {
			List l = new ArrayList(dataAccessorsByName.size() * 2);

			for (Iterator it = dataAccessorsByName.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				BasicDataAccessor dataAccessor = (BasicDataAccessor) entry
						.getValue();

				Object serializedForm = dataAccessor.saveDataState(context);

				if (serializedForm == null) {
					continue;
				}

				l.add(entry.getKey());
				l.add(serializedForm);
			}

			if (l.isEmpty() == false) {
				states[1] = l.toArray();
			}
		}

		if (converterSetted) {
			Object savedConverter = Boolean.FALSE;
			if (converter != null) {
				savedConverter = UIComponentBase.saveAttachedState(context,
						converter);

				if (savedConverter == null) {
					savedConverter = Boolean.FALSE;
				}
			}

			states[2] = savedConverter;
		}

		return states;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.component.UIComponent#setValueExpression(java.lang.String,
	 * javax.faces.el.ValueExpression)
	 */
	public final void setValue(String valueName, Object value) {
		setInternalProperty(valueName, value);
	}

	public final void setValueExpression(String valueName, ValueExpression value) {
		setProperty(valueName, value);
	}

	public final Object getValue(String valueName, FacesContext context) {
		return getInternalProperty(valueName, null, context);
	}

	public final Object getLocalValue(String valueName) {
		return getLocalProperty(valueName);
	}

	public final ValueExpression getValueExpressionProperty(String name) {
		Object object = getLocalProperty(name);
		if (object instanceof ValueExpression) {
			return (ValueExpression) object;
		}

		return null;
	}

	public void release() {
		if (dataAccessorsByName != null
				&& dataAccessorsByName.isEmpty() == false) {
			for (Iterator it = dataAccessorsByName.values().iterator(); it
					.hasNext();) {
				BasicDataAccessor dataAccessor = (BasicDataAccessor) it.next();

				dataAccessor.releaseDatas();
			}

			dataAccessorsByName = null;
		}

		if (propertiesManager != null) {
			propertiesManager.releaseManager();
			propertiesManager = null;
		}

		enableDelta = false;
		converter = null;
		converterSetted = false;
	}

	/*
	 * public final String getConverterId(FacesContext facesContext) { return
	 * getStringProperty(CONVERTER_ID_PROPERTY, facesContext); }
	 * 
	 * public final void setConverterId(String converterId) {
	 * setProperty(CONVERTER_ID_PROPERTY, converterId); this.converter = null;
	 * this.converterSetted = false; }
	 * 
	 * public final void setConverterId(ValueExpression converterId) {
	 * setProperty(CONVERTER_ID_PROPERTY, converterId); this.converter = null;
	 * this.converterSetted = false; }
	 */

	public final Converter getConverter(FacesContext facesContext) {
		if (converterSetted) {
			return converter;
		}
		converterSetted = true;

		if (facesContext == null) {
			facesContext = getFacesContext();
		}

		Object converterValue = getProperty(CONVERTER_ID_PROPERTY, facesContext);
		if (converterValue == null) {
			return null;
		}

		if (converterValue instanceof Converter) {
			converter = (Converter) converterValue;

			return converter;
		}

		String converterId = String.valueOf(converterValue);
		if (converterId == null) {
			return null;
		}

		converter = facesContext.getApplication().createConverter(converterId);

		return converter;
	}

	public final void setConverter(Converter converter) {
		this.converter = converter;
		this.converterSetted = true;

		if (debugEnabled) {
			LOG.debug("Set converter: " + converter);
		}
	}

	public Object getTransientAttribute(String name) {
		if (transientAttributes == null) {
			return null;
		}

		Object value = transientAttributes.get(name);

		if (debugEnabled) {
			LOG.debug("Get transient attribute '" + name + "' => " + value);
		}

		return value;
	}

	public Object setTransientAttribute(String name, Object value) {
		if (transientAttributes == null) {
			transientAttributes = factory.createMap(4);
		}

		if (debugEnabled) {
			LOG.debug("Set transient attribute '" + name + "'=" + value);
		}

		return transientAttributes.put(name, value);
	}

	public boolean isPropertySetted(String propertyName) {

		if (debugEnabled) {
			LOG.debug("Is property setted '" + propertyName + "' ...");
		}

		IPropertiesAccessor propertiesAccessor = getPropertiesAccessor(false);
		if (propertiesAccessor == null) {

			if (debugEnabled) {
				LOG.debug("Is property setted '" + propertyName
						+ "' => FALSE  (propertiesAccessor='"
						+ propertiesAccessor + "')");
			}
			return false;
		}

		boolean setted = propertiesAccessor.isPropertySetted(propertyName);

		if (debugEnabled) {
			LOG.debug("Is property setted '" + propertyName + "' => " + setted
					+ "  (propertiesAccessor='" + propertiesAccessor + "')");
		}

		return setted;
	}

	public void processValidation(FacesContext context) {

		if (debugEnabled) {
			LOG.debug("Process validation, enableDelta=" + enableDelta);
		}

		if (enableDelta == false) {
			return;
		}

		enableDelta = false;
	}

	public void processUpdates(FacesContext context) {

		if (debugEnabled) {
			LOG.debug("Process update, enableDelta=" + enableDelta);
		}

		enableDelta = false;

		if (enableDelta == true) {
			throw new FacesException(
					"Invalid update state without validation phase !");
		}

		if (dataAccessorsByName != null
				&& dataAccessorsByName.isEmpty() == false) {
			for (Iterator it = dataAccessorsByName.values().iterator(); it
					.hasNext();) {
				BasicDataAccessor dataAccessor = (BasicDataAccessor) it.next();

				dataAccessor.commitDatas(context);
			}
		}

		if (propertiesManager != null) {
			propertiesManager.commitManager(context);
		}
	}

	public void startDecodes(FacesContext context) {
		enableDelta = true;

		if (debugEnabled) {
			LOG.debug("Start decode component ");
		}
	}

	/**
	 * 
	 * @author Olivier Oeuillot (latest modification by $Author$)
	 * @version $Revision$ $Date$
	 */
	protected class BasicDataAccessor extends BasicPropertiesManager implements
			IDataMapAccessor {
		private static final String REVISION = "$Revision$";

		private final String name;

		private IPropertiesManager propertiesManager;

		public BasicDataAccessor(String name) {
			this.name = name;
		}

		final IPropertiesAccessor getDataPropertiesAccessor(boolean forceDelta) {
			if (propertiesManager != null) {
				return propertiesManager.getPropertiesAccessor(enableDelta,
						forceDelta);
			}

			if (forceDelta == false) {
				return null;
			}

			propertiesManager = createPropertiesManager();

			return propertiesManager.getPropertiesAccessor(enableDelta,
					forceDelta);
		}

		public IPropertiesManager copyOriginalState() {
			BasicDataAccessor copy = new BasicDataAccessor(name);
			copy.propertiesManager = copy;
			copy.setCameliaFactory(factory);
			copy.originalPropertiesAccessor = originalPropertiesAccessor;

			return copy;
		}

		protected IPropertiesManager createPropertiesManager() {
			return this;
		}

		public Object removeData(String name, FacesContext facesContext) {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(true);

			return propertiesAccessor.removeProperty(facesContext, name);
		}

		public Object getData(String key, FacesContext facesContext) {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(false);
			if (propertiesAccessor == null) {
				return null;
			}

			Object object = propertiesAccessor.getProperty(key);
			if (object == null) {
				return null;
			}

			if (object instanceof ValueExpression) {
				ValueExpression valueBinding = (ValueExpression) object;

				if (facesContext == null) {
					facesContext = getFacesContext();
				}

				object = valueBinding.getValue(facesContext.getELContext());
				if (object == null) {
					return null;
				}
			}

			return object;
		}

		public Object setData(String name, Object value,
				FacesContext facesContext) {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(true);

			return propertiesAccessor.setProperty(facesContext, name, value);
		}

		public void setData(String name, ValueExpression value,
				FacesContext facesContext) {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(true);

			propertiesAccessor.setProperty(facesContext, name, value);
		}

		public int getDataCount() {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(false);
			if (propertiesAccessor == null) {
				return 0;
			}

			return propertiesAccessor.size();
		}

		public String[] listDataKeys(FacesContext facesContext) {
			IPropertiesAccessor propertiesAccessor = getDataPropertiesAccessor(false);
			if (propertiesAccessor == null) {
				return STRING_EMPTY_ARRAY;
			}

			Collection c = propertiesAccessor.keySet();
			if (c.isEmpty()) {
				return STRING_EMPTY_ARRAY;
			}

			return (String[]) c.toArray(new String[c.size()]);
		}

		public void restoreDataState(FacesContext context, Object datas) {
			propertiesManager = factory
					.createPropertiesManager(BasicComponentEngine.this);
			propertiesManager.restoreManagerState(context, datas);
		}

		public Object saveDataState(FacesContext context) {
			if (propertiesManager == null) {
				return null;
			}

			return propertiesManager.saveManagerState(context);
		}

		public void releaseDatas() {
			if (propertiesManager == null) {
				return;
			}

			propertiesManager.releaseManager();
			propertiesManager = null;
		}

		public void commitDatas(FacesContext context) {
			if (propertiesManager == null) {
				return;
			}

			propertiesManager.commitManager(context);
		}

		public Map getDataMap(FacesContext facesContext) {
			String keys[] = listDataKeys(facesContext);
			if (keys.length < 1) {
				return Collections.EMPTY_MAP;
			}

			Map map = new HashMap(keys.length);

			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];

				Object value = getData(key, facesContext);

				// On accepte les <null>
				map.put(key, value);
			}

			return map;
		}
	}

	public IStateChildrenList createStateChildrenList() {
		return new BasicStateChildrenList();
	}

	public IComponentEngine copyOriginalState() {

		BasicComponentEngine newComponentEngine = new BasicComponentEngine(this);

		return newComponentEngine;
	}

	public String toString() {
		String s = "";

		if (propertiesManager != null) {
			s += propertiesManager.toString();
		}

		if (transientAttributes != null
				&& transientAttributes.isEmpty() == false) {
			if (s.length() > 0) {
				s += ",";
			}
			s += "transient=" + transientAttributes;
		}

		if (converter != null) {
			if (s.length() > 0) {
				s += ",";
			}
			s += "converter='" + converter + "'";
		}

		if (dataAccessorsByName != null
				&& dataAccessorsByName.isEmpty() == false) {
			for (Iterator it = dataAccessorsByName.entrySet().iterator(); it
					.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();

				if (s.length() > 0) {
					s += ",";
				}
				s += "dataAccessor[" + entry.getKey() + "]='"
						+ entry.getValue() + "'";
			}
		}

		return s;
	}

}