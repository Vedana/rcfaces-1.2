/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.repository.AbstractCriteria;
import org.rcfaces.core.internal.repository.IRepository.ICriteria;
import org.rcfaces.core.internal.webapp.URIParameters;
import org.rcfaces.renderkit.html.internal.Constants;
import org.rcfaces.renderkit.html.internal.agent.IUserAgent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class UserAgentCriteria extends AbstractCriteria {

    private IUserAgent userAgent;

    /**
     * For serialisation ONLY !
     */
    public UserAgentCriteria() {
    }

    protected UserAgentCriteria(ICriteria parent, IUserAgent userAgent) {
        setParent(parent);

        this.userAgent = userAgent;
    }

    @Override
    public void appendSuffix(URIParameters uriParameters, boolean recursive) {

        if (userAgent.getBrowserType() != null) {
            StringAppender sa = new StringAppender(128);
            sa.append(userAgent.getBrowserType().shortName());
            if (userAgent.getMajorVersion() != null) {
                sa.append('.').append(userAgent.getMajorVersion());

                if (userAgent.getMinorVersion() != null) {
                    sa.append('.').append(userAgent.getMinorVersion());

                    if (userAgent.getReleaseVersion() != null) {
                        sa.append('.').append(userAgent.getReleaseVersion());
                    }
                }
            }

            uriParameters.appendAgent(sa.toString());
        }

        super.appendSuffix(uriParameters, recursive);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter, Object parameter) {
        if (adapter == IUserAgent.class) {
            return (T) userAgent;
        }

        return super.getAdapter(adapter, parameter);
    }

    public static ICriteria get(ICriteria parent, IUserAgent userAgent) {

        if (Constants.REDUCE_USER_AGENT_TO_BROWSER_TYPE) {
            return new UserAgentCriteria(parent,
                    userAgent.reduceByBrowserType());
        }

        return new UserAgentCriteria(parent, userAgent.reduce());
    }

    public static IUserAgent getUserAgent(ICriteria criteria) {
        return criteria.getAdapter(IUserAgent.class, null);
    }

    @Override
    public Object saveState(FacesContext context) {
        Object[] ret = new Object[2];

        ret[0] = super.saveState(context);
        ret[1] = UIComponentBase.saveAttachedState(context, userAgent);

        return ret;
    }

    @Override
    public void restoreState(FacesContext context, Object state) {
        Object[] array = (Object[]) state;

        super.restoreState(context, array[0]);
        userAgent = (IUserAgent) UIComponentBase.restoreAttachedState(context,
                array[1]);
    }

    public ICriteria merge(ICriteria criteria) {

        if (equals(criteria)) {
            return this;
        }

        IUserAgent mergeUserAgent = getUserAgent(criteria);
        if (mergeUserAgent == null) {
            return get(criteria, userAgent);
        }

        if (mergeUserAgent.equals(userAgent) == false) {
            throw new IllegalStateException("Can not merge userAgent '"
                    + userAgent + "' with '" + mergeUserAgent + "'");
        }

        return criteria;
    }
}
