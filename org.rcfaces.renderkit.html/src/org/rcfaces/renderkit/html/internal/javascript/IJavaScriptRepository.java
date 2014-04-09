/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.javascript;

import java.util.List;
import java.util.Map;

import org.rcfaces.core.internal.renderkit.IProcessContext;
import org.rcfaces.core.internal.repository.IHierarchicalRepository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptRepository extends IHierarchicalRepository {

    int CLASS_NAME_COLLECTION_TYPE = 10;

    String getBaseURI(IProcessContext context);

    IClass getClassByName(String className);

    List<ISymbolFile> listSymbolsFiles();

    public interface IClassFile extends IHierarchicalFile {
        IClass[] listClasses();

        String convertSymbols(Map<String, String> symbols, String code);
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

        String getResourceBundleName();
    }

    public interface ISymbolFile {
        String getSymbolsPath();

        String getBaseDirectory();
    }
}
