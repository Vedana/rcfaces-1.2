/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.contentAccessor.BasicGenerationResourceInformation;
import org.rcfaces.renderkit.html.internal.util.FileItemSource;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FilesCollectorGenerationInformation extends
        BasicGenerationResourceInformation {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(FilesCollectorGenerationInformation.class);

    private static final String FILE_ITEM_SOURCES = "org.rcfaces.renderkit.html.FILE_ITEM_SOURCES";

    public FilesCollectorGenerationInformation(FileItemSource sources[]) {
        setAttribute(FILE_ITEM_SOURCES, sources);
    }

    public FileItemSource[] listSources() {
        return (FileItemSource[]) getAttribute(FILE_ITEM_SOURCES);
    }
}
