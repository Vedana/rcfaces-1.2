package org.rcfaces.core.internal.listener;


import org.rcfaces.core.event.ExpandEvent;
import org.rcfaces.core.event.IExpandListener;

/**
 * @author meslin.jb@vedana.com
 */
public class ExpandActionListener extends AbstractActionListener implements
		IExpandListener {
	private static final String REVISION = "$Revision: 1.0";

	private static final Class actionParameters[] = { ExpandEvent.class };

	public ExpandActionListener(String expression) {
		super(expression);
	}

	public ExpandActionListener() {
	}

	protected Class[] listParameterClasses() {
		return actionParameters;
	}

	@Override
	public void processExpand(ExpandEvent event) {
		process(event);
	}
}
