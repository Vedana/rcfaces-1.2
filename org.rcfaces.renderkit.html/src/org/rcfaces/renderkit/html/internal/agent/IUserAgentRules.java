/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.agent;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IUserAgentRules {
    boolean accepts(IUserAgent proposal);

    void textForm(StringBuilder sb, String separator);

    int rulesCount();

    IUserAgentRules merge(IUserAgentRules featureAgentRules);

    IUserAgent reduce(IUserAgent userAgent);
}
