package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ToolTipComponent;
import org.rcfaces.core.component.iterator.IToolTipIterator;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * 
 * @author jbmeslin@vedana.com
 * 
 */
public class ToolTipTools extends CollectionTools {

	private static final IToolTipIterator EMPTY_TOOLTIP_ITERATOR = new ToolTipListIterator(
			Collections.EMPTY_LIST);

	private static final Log LOG = LogFactory.getLog(ToolTipTools.class);

	public static IToolTipIterator listToolTips(UIComponent component) {
		List list = ComponentIterators.list(component, ToolTipComponent.class);
		if (list.isEmpty()) {
			return EMPTY_TOOLTIP_ITERATOR;
		}

		return new ToolTipListIterator(list);
	}

	/**
	 * 
	 * @author Olivier Oeuillot (latest modification by $Author$)
	 * @version $Revision$ $Date$
	 */
	private static final class ToolTipListIterator extends
			ComponentIterators.ComponentListIterator implements
			IToolTipIterator {

		protected ToolTipListIterator(List list) {
			super(list);
		}

		public final ToolTipComponent next() {
			return (ToolTipComponent) nextComponent();
		}

		public ToolTipComponent[] toArray() {
			return (ToolTipComponent[]) toArray(new ToolTipComponent[count()]);
		}
	}
}
