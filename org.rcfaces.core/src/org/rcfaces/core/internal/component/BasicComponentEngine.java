/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.4  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.3  2006/07/18 17:06:29  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.2  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.1  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.13  2006/04/27 13:49:46  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.12  2006/03/15 13:53:04  oeuillot
 * Stabilisation
 * Ajout des bundles pour le javascript
 * R�organisation de l'arborescence de GridData qui n'herite plus de UIData
 *
 * Revision 1.11  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.10  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.9  2005/12/27 16:08:17  oeuillot
 * Gestion imageButtonWriter
 * Ajout de fa_images
 * Preparation de f_imageCombo
 *
 * Revision 1.8  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.7  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.5  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2004/09/02 17:44:30  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.internal.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class BasicComponentEngine extends AbstractComponentEngine {
    private static final String REVISION = "$Revision$";

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
        IPropertiesAccessor propertiesAccessor = getPropertiesAccessor(false);
        if (propertiesAccessor == null) {
            return null;
        }

        return propertiesAccessor.getProperty(propertyName);
    }

    public final Object getInternalProperty(String propertyName,
            Class requestedClass, FacesContext facesContext) {

        Object object = getLocalProperty(propertyName);
        if (object == null) {
            return null;
        }

        if (object instanceof ValueBinding) {
            ValueBinding valueBinding = (ValueBinding) object;

            if (facesContext == null) {
                facesContext = getFacesContext();
            }

            return valueBinding.getValue(facesContext);
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
        IPropertiesAccessor pa = getPropertiesAccessor(true);

        pa.setProperty(null, propertyName, value);
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

    public final void setProperty(String propertyName, ValueBinding value) {
        setInternalProperty(propertyName, value);
    }

    final IPropertiesAccessor getPropertiesAccessor(boolean forceDelta) {
        if (propertiesManager != null) {
            return propertiesManager.getPropertiesAccessor(enableDelta,
                    forceDelta);
        }

        if (forceDelta == false) {
            return null;
        }

        propertiesManager = factory.createPropertiesManager(this);

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
        if (Boolean.FALSE.equals(converter)) {
            this.converterSetted = false;
            this.converter = null;

        } else {
            this.converterSetted = true;
            this.converter = (Converter) converter;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
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
            states[2] = converter;

        } else {
            states[2] = Boolean.FALSE;
        }

        return states;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.faces.component.UIComponent#setValueBinding(java.lang.String,
     *      javax.faces.el.ValueBinding)
     */
    public final void setValue(String valueName, Object value) {
        setInternalProperty(valueName, value);
    }

    public final void setValueBinding(String valueName, ValueBinding value) {
        setProperty(valueName, value);
    }

    public final Object getValue(String valueName, FacesContext context) {
        return getInternalProperty(valueName, null, context);
    }

    public final Object getLocalValue(String valueName) {
        return getLocalProperty(valueName);
    }

    public final ValueBinding getValueBindingProperty(String name) {
        Object object = getLocalProperty(name);
        if (object instanceof ValueBinding) {
            return (ValueBinding) object;
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

    public final String getConverterId(FacesContext facesContext) {
        return getStringProperty(CONVERTER_ID_PROPERTY, facesContext);
    }

    public final void setConverterId(String converterId) {
        setProperty(CONVERTER_ID_PROPERTY, converterId);
        this.converter = null;
        this.converterSetted = false;
    }

    public final void setConverterId(ValueBinding converterId) {
        setProperty(CONVERTER_ID_PROPERTY, converterId);
        this.converter = null;
        this.converterSetted = false;
    }

    public final Converter getConverter(FacesContext facesContext) {
        if (converterSetted) {
            return converter;
        }
        converterSetted = true;

        if (facesContext == null) {
            facesContext = getFacesContext();
        }

        String converterId = getConverterId(facesContext);
        if (converterId == null) {
            return null;
        }

        converter = facesContext.getApplication().createConverter(converterId);

        return converter;
    }

    public final void setConverter(Converter converter) {
        this.converter = converter;
        this.converterSetted = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.component.IComponentEngine#getTransientAttribute(java.lang.String)
     */
    public Object getTransientAttribute(String name) {
        if (transientAttributes == null) {
            return null;
        }

        return transientAttributes.get(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.component.IComponentEngine#setTransientAttribute(java.lang.String,
     *      java.lang.Object)
     */
    public Object setTransientAttribute(String name, Object value) {
        if (transientAttributes == null) {
            transientAttributes = factory.createMap(4);
        }

        return transientAttributes.put(name, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.component.IComponentEngine#isPropertySetted(java.lang.String)
     */
    public boolean isPropertySetted(String propertyName) {
        return getPropertiesAccessor(false).isPropertySetted(propertyName);
    }

    public void processUpdates(FacesContext context) {

        if (enableDelta == false) {
            return;
        }
        enableDelta = false;

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
    }

    /**
     * 
     * @author Olivier Oeuillot
     * @version $Revision$
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

            if (object instanceof ValueBinding) {
                ValueBinding valueBinding = (ValueBinding) object;

                if (facesContext == null) {
                    facesContext = getFacesContext();
                }

                object = valueBinding.getValue(facesContext);
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

        public void setData(String name, ValueBinding value,
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
}