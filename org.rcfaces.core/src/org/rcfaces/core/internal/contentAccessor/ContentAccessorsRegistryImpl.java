/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.core.provider.AbstractProvider;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentAccessorsRegistryImpl extends AbstractProvider implements
        IContentAccessorRegistry {
    private static final String REVISION = "$Revision$";

    private static final IContentAccessorHandler[] CONTENT_ACCESSOR_HANDLER_EMPTY_ARRAY = new IContentAccessorHandler[0];

    private final Map contentAccessorsByType = new HashMap(8);

    private IContentAccessorHandler defaultContentAccessors[] = CONTENT_ACCESSOR_HANDLER_EMPTY_ARRAY;

    public ContentAccessorsRegistryImpl() {
        // Dans le constructeur car celui-ci est utilisé par d'autres registry
        RcfacesContext rcfacesContext = RcfacesContext.getCurrentInstance();

        if (rcfacesContext.getContentAccessorRegistry() == null) {
            rcfacesContext.setContentAccessorRegistry(this);
        }
    }

    public String getId() {
        return "ContentAccessorsRegistry";
    }

    public IContentAccessorHandler[] listContentAccessorHandlers(
            IContentFamily type) {
        IContentAccessorHandler contentAccessorHandlers[] = (IContentAccessorHandler[]) contentAccessorsByType
                .get(type);
        if (contentAccessorHandlers == null) {
            return defaultContentAccessors;
        }

        return contentAccessorHandlers;
    }

    public void declareContentAccessorHandler(IContentFamily contentFamily,
            IContentAccessorHandler contentAccessorHandler) {

        if (contentFamily == null) {
            // On fait l'ajout
            for (Iterator it = contentAccessorsByType.keySet().iterator(); it
                    .hasNext();) {

                contentFamily = (IContentFamily) it.next();

                declareContentAccessorHandler(contentFamily,
                        contentAccessorHandler);
            }

            // Puis on declare les defaults

            List l = new ArrayList(Arrays.asList(defaultContentAccessors));
            l.add(contentAccessorHandler);
            defaultContentAccessors = (IContentAccessorHandler[]) l
                    .toArray(new IContentAccessorHandler[l.size()]);

            return;
        }

        List l = new ArrayList();
        IContentAccessorHandler cah[] = listContentAccessorHandlers(contentFamily);
        if (cah.length > 0) {
            l.addAll(Arrays.asList(cah));

        } else if (defaultContentAccessors.length > 0) {
            l.addAll(Arrays.asList(defaultContentAccessors));
        }
        l.add(contentAccessorHandler);

        contentAccessorsByType.put(contentFamily, l
                .toArray(new IContentAccessorHandler[l.size()]));

    }

    public void configureRules(Digester digester) {
        super.configureRules(digester);

        // Il faut lire la config pour declarer les accesseurs
    }

}
