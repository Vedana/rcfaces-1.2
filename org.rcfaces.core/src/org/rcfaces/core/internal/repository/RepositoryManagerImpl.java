/*
 * $Id$
 */
package org.rcfaces.core.internal.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.rcfaces.core.internal.RcfacesContext;
import org.rcfaces.core.provider.AbstractProvider;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RepositoryManagerImpl extends AbstractProvider implements
        IRepositoryManager {

    private static final String REVISION = "$Revision$";

    private static final String ID = "org.rcfaces.core.REPOSITORY_MANAGER";

    private static final String[] STRING_EMPTY_ARRAY = new String[0];

    private final Map repositories = new HashMap(16);

    public String getId() {
        return ID;
    }

    public void startup(FacesContext facesContext) {
        super.startup(facesContext);

        RcfacesContext rcfacesContext = RcfacesContext
                .getInstance(facesContext);

        String families[] = listFamilies();
        for (int i = 0; i < families.length; i++) {
            String family = families[i];

            List l = (List) repositories.get(family);

            String r[] = (String[]) l.toArray(new String[l.size()]);

            repositories.put(family, r);
        }

        rcfacesContext.setRepositoryManager(this);
    }

    public String[] listFamilies() {
        Collection c = repositories.keySet();

        return (String[]) c.toArray(new String[c.size()]);
    }

    public String[] listRepositoryLocations(String family) {
        String rls[] = (String[]) repositories.get(family);
        if (rls == null) {
            return STRING_EMPTY_ARRAY;
        }

        return rls;
    }

    public void configureRules(Digester digester) {
        super.configureRules(digester);

        digester.addRule("rcfaces-config/repositories/repository", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                super.digester.push(new RepositoryBean());
            }

            public void end(String namespace, String name) throws Exception {
                RepositoryBean adapterBean = (RepositoryBean) super.digester
                        .pop();

                addRepositoryBean(adapterBean);
            }
        });

        digester.addBeanPropertySetter(
                "rcfaces-config/repositories/repository/location", "location");

        digester.addBeanPropertySetter(
                "rcfaces-config/repositories/repository/type", "type");
    }

    protected void addRepositoryBean(RepositoryBean adapterBean) {
        String type = adapterBean.getType();
        String location = adapterBean.getLocation();

        if (type == null || location == null) {
            throw new FacesException("Invalid repository parameters (type=\""
                    + type + "\", location=\"" + location + "\")");
        }

        List l = (List) repositories.get(type);
        if (l == null) {
            l = new ArrayList();

            repositories.put(type, l);
        }

        l.add(location);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class RepositoryBean {

        private static final String REVISION = "$Revision$";

        public String location;

        public String type;

        public final String getLocation() {
            return location;
        }

        public final void setLocation(String location) {
            this.location = location;
        }

        public final String getType() {
            return type;
        }

        public final void setType(String type) {
            this.type = type;
        }

    }
}
