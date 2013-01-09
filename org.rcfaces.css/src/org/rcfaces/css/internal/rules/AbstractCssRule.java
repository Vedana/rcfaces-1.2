/*
 * $Id$
 */
package org.rcfaces.css.internal.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractCssRule {
    private final List<String> names = new ArrayList<String>();

    private final List<UserAgentPropertyRule> agents = new ArrayList<UserAgentPropertyRule>();

    public void addName(String name) {
        names.add(name);
    }

    public void addAgent(UserAgentPropertyRule rule) {
        agents.add(rule);
    }

    public List<String> listAgentNames() {
        return names;
    }

    public List<UserAgentPropertyRule> listAgentRules() {
        return agents;
    }
}
