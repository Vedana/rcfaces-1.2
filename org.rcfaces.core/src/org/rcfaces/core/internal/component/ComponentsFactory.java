/*
 * $Id$
 *
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.9  2006/08/28 16:03:53  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.8  2006/06/19 17:22:17  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.7  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.6  2004/12/22 12:16:15  oeuillot
 * Refonte globale de l'arborescence des composants ....
 * Int�gration des corrections de Didier
 *
 * Revision 1.5  2004/08/20 12:08:30  oeuillot
 * *** empty log message ***
 *
 * Revision 1.4  2004/08/13 10:13:47  oeuillot
 * Ajout des evenements
 *
 * Revision 1.3  2004/08/10 15:27:54  oeuillot
 * Modifie l'heritage
 *
 * Revision 1.2  2004/08/06 14:07:06  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.3  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.2  2004/08/05 16:53:26  oeuillot
 * Integration de l'INPUT
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.internal.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ComponentsFactory implements IFactory {
    private static final String REVISION = "$Revision$";

    private static final ComponentsFactory SINGLETON = new ComponentsFactory();

    private ComponentsFactory() {
    }

    public String getName() {
        return "Camelia basic components factory";
    }

    public static final ComponentsFactory getCameliaFactory(
            FacesContext facesContext) {
        return SINGLETON;
    }

    public List createList(int size) {
        return new ArrayList(size);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rcfaces.core.internal.util.IContainerStrategies#createMap(int)
     */
    public Map createMap(int size) {
        return new HashMap(size);
    }

    public Object allocateObject(Class claz) {
        try {
            return claz.newInstance();

        } catch (Throwable th) {
            throw new FacesException("Can not allocate object from class '"
                    + claz + "'.", th);
        }
    }

    public IComponentEngine createComponentEngine() {
        return new BasicComponentEngine(this);
    }

    public IPropertiesManager createPropertiesManager(IComponentEngine engine) {

        BasicPropertiesManager pa = (BasicPropertiesManager) allocateObject(BasicPropertiesManager.class);

        pa.setCameliaFactory(this);

        return pa;
    }

}