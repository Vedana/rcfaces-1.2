/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.javascript;

import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository;
import org.rcfaces.core.internal.webapp.IHierarchicalRepository.ISet;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptRepository extends IHierarchicalRepository {

    int CLASS_NAME_COLLECTION_TYPE = 10;

    String getBaseURI(IProcessContext context);

    IClass getClassByName(String className);

    public interface IClassFile extends IHierarchicalFile {
        IClass[] listClasses();
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IClass {
        String getName();

        IHierarchicalFile getFile();

        IClass[] listRequiredClasses(String requireId);

        IHierarchicalFile[] listRequiredResources(String requiredId);
    }
}
