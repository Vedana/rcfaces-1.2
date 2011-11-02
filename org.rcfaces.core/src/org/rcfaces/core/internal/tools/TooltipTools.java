package org.rcfaces.core.internal.tools;

import java.util.Collections;
import java.util.List;

import javax.faces.component.UIComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.TooltipComponent;
import org.rcfaces.core.component.iterator.ITooltipIterator;
import org.rcfaces.core.internal.util.ComponentIterators;

/**
 * 
 * @author jbmeslin@vedana.com
 * 
 */
public class TooltipTools extends CollectionTools {
	
	private static final ITooltipIterator EMPTY_TOOLTIP_ITERATOR = new TooltipListIterator(
			Collections.EMPTY_LIST);
	
	private static final Log LOG = LogFactory.getLog(TooltipTools.class);
	
	
	public static ITooltipIterator listTooltipss (UIComponent component) {
		List list = ComponentIterators.list(component,
				TooltipComponent.class);
		if (list.isEmpty()) {
			return EMPTY_TOOLTIP_ITERATOR;
		}

		return new TooltipListIterator(list);
	}
	
	
	/**
	 * 
	 * @author Olivier Oeuillot (latest modification by $Author$)
	 * @version $Revision$ $Date$
	 */
	private static final class TooltipListIterator extends
			ComponentIterators.ComponentListIterator implements
			ITooltipIterator {

		protected TooltipListIterator(List list) {
			super(list);
		}

		public final TooltipComponent next() {
			return (TooltipComponent) nextComponent();
		}

		public TooltipComponent[] toArray() {
			return (TooltipComponent[]) toArray(new TooltipComponent[count()]);
		}
	}
}
