/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.faces.model.SelectItem;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractFilteredList<T> extends
		AbstractFilteredCollection<T> implements List<T> {

	public static final List EMPTY_LIST;
	static {
		EMPTY_LIST = new AbstractFilteredList(Collections.EMPTY_LIST) {
			protected boolean accept(IFilterProperties filter, Object selectItem) {
				return false;
			}
		};
	}

	public AbstractFilteredList() {
		super(new ArrayList<T>());
	}

	public AbstractFilteredList(List<T> list) {
		super(list);
	}

	public AbstractFilteredList(SelectItem selectItem[]) {
		super(selectItem);
	}

	public T get(int index) {
		return ((List<T>) collection).get(index);
	}

	public T remove(int index) {
		return ((List<T>) collection).remove(index);
	}

	public void add(int index, T element) {
		((List<T>) collection).add(index, element);
	}

	public int indexOf(Object o) {
		return ((List<T>) collection).indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return ((List<T>) collection).lastIndexOf(o);
	}

	public boolean addAll(int index, Collection<? extends T> c) {
		return ((List<T>) collection).addAll(index, c);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return ((List<T>) collection).subList(fromIndex, toIndex);
	}

	public ListIterator<T> listIterator() {
		return ((List<T>) collection).listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return ((List<T>) collection).listIterator(index);
	}

	public T set(int index, T element) {
		return ((List<T>) collection).set(index, element);
	}

}
