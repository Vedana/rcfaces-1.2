/*
 * $Id$
 */
package org.rcfaces.core.internal.style;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.content.IOperationContentLoader;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.util.IPath;
import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IStyleParser {
    String getParserName();

    String normalizeBuffer(String styleSheetURL, String styleSheetBuffer,
            IStyleParser.IParserContext parserContext) throws IOException;

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public interface IParserContext {
        FacesContext getFacesContext();

        String getCharset();

        long getLastModifiedDate();

        void setLastModifiedDate(long lastModifiedDate);

        IPath processVersioning(IPath base, IPath path,
                IContentFamily contentFamily);

        boolean isVersioningEnabled();

        void enableProcessRules();

        boolean isProcessRulesEnabled();

        void enableMergeImports();

        boolean isMergeImportsEnabled();

        IResourceLoaderFactory getResourceLoaderFactory();

        IOperationContentLoader getOperationContentLoader();

        boolean isResourceURLConversionEnabled();
    }
}