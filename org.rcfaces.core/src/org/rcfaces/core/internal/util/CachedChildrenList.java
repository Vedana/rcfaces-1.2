/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:28  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/01/04 13:02:51  oeuillot
 * Amelioration des tables. (Ajout des tris, scrollbars ...)
 *
 * Revision 1.1  2004/08/06 14:03:57  jmerlin
 * Vedana Faces
 *
 * Revision 1.2  2004/08/06 09:35:14  oeuillot
 * *** empty log message ***
 *
 * Revision 1.1  2004/08/04 16:28:11  oeuillot
 * Premier jet !
 *
 */
package org.rcfaces.core.internal.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.rcfaces.core.internal.AbstractReleasable;
import org.rcfaces.core.internal.manager.IContainerManager;
import org.rcfaces.core.internal.manager.ITransientAttributesManager;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public final class CachedChildrenList extends AbstractReleasable {
	private static final String REVISION = "$Revision$";

	private static final UIComponent[] UI_COMPONENT_EMPTY_ARRAY = new UIComponent[0];

	private static final Iterator EMPTY_ITERATOR = new Iterator() {
		private static final String REVISION = "$Revision$";

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			return null;
		}

		public void remove() {
		}
	};

	// private static final Object LOCK = new Object();

	private static final Map emptyArrays = new HashMap(32);

	private Class elementClass;

	private int containerStateId;

	private UIComponent componentsArray[];

	private List components;

	private void set(IContainerManager container, Class elementClass) {
		this.elementClass = elementClass;
		containerStateId = -1; // getContainerStateId(container);
	}

	private int getContainerStateId(IContainerManager container) {
		return container.getChildrenListState();
	}

	private void verifyList(IContainerManager container) {
		int currentContainerStateId = getContainerStateId(container);

		if (components != null && currentContainerStateId == containerStateId) {
			return;
		}

		if (components == null) {
			components = new ArrayList(container.getChildCount());

		} else {
			components.clear();
		}

		componentsArray = null;

		for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
			Object obj = it.next();

			if (obj == null || elementClass.isInstance(obj) == false) {
				continue;
			}

			components.add(obj);
		}

		if (components instanceof ArrayList) {
			((ArrayList) components).trimToSize();
		}

		containerStateId = currentContainerStateId;
	}

	private UIComponent[] getArray(IContainerManager container) {
		verifyList(container);

		if (componentsArray != null) {
			return componentsArray;
		}

		componentsArray = getEmptyArray(elementClass);

		if (components.size() > 0) {
			componentsArray = (UIComponent[]) components
					.toArray(componentsArray);
		}

		return componentsArray;
	}

	private int getCount(IContainerManager container) {
		verifyList(container);

		return components.size();
	}

	private Iterator getIterator(IContainerManager container) {
		if (getCount(container) == 0) {
			return EMPTY_ITERATOR;
		}

		return components.iterator();
	}

	private List getList(IContainerManager container) {
		if (getCount(container) == 0) {
			return Collections.EMPTY_LIST;
		}

		return components;
	}

	public static UIComponent[] getArray(IContainerManager elementContainer,
			Class elementClass) {
		CachedChildrenList cachedChildrenList = getCachedChildrenList(
				elementContainer, elementClass);

		return cachedChildrenList.getArray(elementContainer);
	}

	public static List getList(IContainerManager elementContainer,
			Class elementClass) {
		CachedChildrenList cachedChildrenList = getCachedChildrenList(
				elementContainer, elementClass);

		return cachedChildrenList.getList(elementContainer);
	}

	public static Iterator getIterator(IContainerManager elementContainer,
			Class elementClass) {
		CachedChildrenList cachedChildrenList = getCachedChildrenList(
				elementContainer, elementClass);

		return cachedChildrenList.getIterator(elementContainer);
	}

	public static int getCount(IContainerManager elementContainer,
			Class elementClass) {
		CachedChildrenList cachedChildrenList = getCachedChildrenList(
				elementContainer, elementClass);

		return cachedChildrenList.getCount(elementContainer);
	}

	private static CachedChildrenList getCachedChildrenList(
			IContainerManager elementContainer, Class elementClass) {
		ITransientAttributesManager transientAttributes = (ITransientAttributesManager) elementContainer;

		String key = elementClass.getName();

		CachedChildrenList cachedChildrenList = (CachedChildrenList) transientAttributes
				.getTransientAttribute(key);
		if (cachedChildrenList != null) {
			return cachedChildrenList;
		}

		cachedChildrenList = allocateCachedChildrenList();

		cachedChildrenList.set(elementContainer, elementClass);

		transientAttributes.setTransientAttribute(key, cachedChildrenList);

		return cachedChildrenList;
	}

	private static CachedChildrenList allocateCachedChildrenList() {
		return new CachedChildrenList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vedana.adonis.Releasable#release()
	 */
	public void release() {
		elementClass = null;
		if (components != null) {
			components.clear();
		}
		componentsArray = null;
		containerStateId = -1;

		super.release();
	}

	public static UIComponent[] getEmptyArray(Class clz) {
		synchronized (emptyArrays) {
			UIComponent array[] = (UIComponent[]) emptyArrays.get(clz);
			if (array != null) {
				return array;
			}

			array = (UIComponent[]) Array.newInstance(clz, 0);

			emptyArrays.put(clz, array);

			return array;
		}
	}
}