/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.css.internal.CssSteadyStateParser;

public class UserAgentPropertyRule {

    private static final Log LOG = LogFactory
            .getLog(CssSteadyStateParser.class);

    private String prefix;

    private String name;

    private String version;

    private String ruleProcessorClassName;

    private IRuleProcessor ruleProcessor;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRuleProcessor() {
        return ruleProcessorClassName;
    }

    public void setRuleProcessor(String className) {
        this.ruleProcessorClassName = className;
    }

    @SuppressWarnings("unchecked")
    public IRuleProcessor getRuleProcessorImpl() {
        if (ruleProcessorClassName == null) {
            return ruleProcessor;
        }

        try {
            Class<IRuleProcessor> clazz = (Class<IRuleProcessor>) Class
                    .forName(getRuleProcessor());

            ruleProcessor = clazz.newInstance();

        } catch (Exception ex) {
            LOG.error("Can not load class '" + getRuleProcessor() + "'", ex);
        }

        ruleProcessorClassName = null;

        return ruleProcessor;
    }

}
