/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.15  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.14  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.13  2006/04/27 13:49:45  oeuillot
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
 * Revision 1.10  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.9  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.core.internal.component;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;

import org.rcfaces.core.internal.IReleasable;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IComponentEngine extends IReleasable,
        ITransientAttributesManager {

    void restoreState(FacesContext context, Object object);

    Object saveState(FacesContext context);

    boolean getBoolProperty(String propertyName, boolean defaultValue,
            FacesContext facesContext);

    void setProperty(String propertyName, boolean value);

    void setProperty(String propertyName, int value);

    void setProperty(String propertyName, double value);

    void setProperty(String propertyName, Integer value);

    void setProperty(String propertyName, Boolean value);

    void setProperty(String propertyName, Object value);

    void setProperty(String propertyName, ValueBinding dataSource);

    Boolean getBooleanProperty(String propertyName, FacesContext facesContext);

    Integer getIntegerProperty(String propertyName, FacesContext facesContext);

    int getIntProperty(String propertyName, int defaultValue,
            FacesContext facesContext);

    String getStringProperty(String propertyName, FacesContext facesContext);

    Object getProperty(String propertyName, FacesContext facesContext);

    ValueBinding getValueBindingProperty(String propertyName);

    Converter getConverter(FacesContext facesContext);

    void setConverter(Converter converter);

    void setConverterId(ValueBinding converter);

    void setConverterId(String converterId);

    boolean isPropertySetted(String propertyName);

    Object getValue(String valueName, FacesContext context);

    Object getLocalValue(String valueName);

    void setValue(String valueName, Object value);

    void setValueBinding(String valueName, ValueBinding valueBinding);

    void startDecodes(FacesContext context);

    void processUpdates(FacesContext context);

    IDataMapAccessor getDataMapAccessor(FacesContext context, String name,
            boolean modify);

    double getDoubleProperty(String propertyName, double value, FacesContext facesContext);
}