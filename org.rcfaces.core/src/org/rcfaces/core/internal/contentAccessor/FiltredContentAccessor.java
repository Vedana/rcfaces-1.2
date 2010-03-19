package org.rcfaces.core.internal.contentAccessor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FiltredContentAccessor extends AbstractContentAccessor implements
        IFiltredContentAccessor {
    private static final String REVISION = "$Revision$";

    private final String filter;

    public FiltredContentAccessor(String filter, IContentAccessor parentAccessor) {
        super(parentAccessor);
        this.filter = filter;

        setPathType(IContentPath.FILTER_PATH_TYPE);
    }

    public Object getContentRef() {
        return null;
    }

    public String getPath() {
        return null;
    }

    public String getFilter() {
        return filter;
    }

    public String toString() {
        return "[FiltredContentAccessor filter='" + filter + "' contentType="
                + getContentFamily() + " pathType="
                + BasicContentPath.getPathTypeName(getPathType())
                + " versionHandler=" + getContentVersionHandler() + " root="
                + getParentAccessor() + "]";
    }

}