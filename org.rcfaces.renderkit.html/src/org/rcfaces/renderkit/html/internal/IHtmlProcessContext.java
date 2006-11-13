/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal;

import org.rcfaces.core.internal.contentAccessor.IContentAccessor;
import org.rcfaces.core.internal.contentAccessor.IContentType;
import org.rcfaces.core.internal.renderkit.IProcessContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IHtmlProcessContext extends IProcessContext {

    String HTML_FLAT_IDENTIFIER_PARAMETER = Constants.getPackagePrefix()
            + ".FLAT_IDENTIFIER";

    String HTML_SEPARATOR_CHAR_PARAMETER = Constants.getPackagePrefix()
            + ".SEPARATOR_CHAR";

    String USE_META_CONTENT_SCRIPT_TYPE_PARAMETER = Constants
            .getPackagePrefix()

            + ".USE_META_CONTENT_SCRIPT_TYPE";

    String USE_META_CONTENT_STYLE_TYPE_PARAMETER = Constants.getPackagePrefix()
            + ".USE_META_CONTENT_STYLE_TYPE";

    String USE_SCRIPT_CDATA_PARAMETER = Constants.getPackagePrefix()
            + ".USE_SCRIPT_CDATA";

    String DEBUG_MODE_APPLICATION_PARAMETER = Constants.getPackagePrefix()
            + ".client.DEBUG_MODE";

    String PROFILER_MODE_APPLICATION_PARAMETER = Constants.getPackagePrefix()
            + ".client.PROFILER_MODE";

    boolean isFlatIdentifierEnabled();

    String getStyleSheetURI(String uri, boolean containsContextPath);

    IContentAccessor getStyleSheetContentAccessor(String uri,
            IContentType contentType);

    boolean useMetaContentScriptType();

    boolean useMetaContentStyleType();

    boolean useScriptCData();
}
