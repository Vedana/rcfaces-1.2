/*
 * $Id$
 */
package org.rcfaces.core.model;

import java.util.Map;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class MapIndexesModel extends CollectionIndexesModel {

	private static final long serialVersionUID = -6123923382601149193L;

	private static final Object DEFAULT_VALUE = Boolean.TRUE;

	private final Map map;

	private final Object defaultValue;

	public MapIndexesModel(Map map) {
		this(map, DEFAULT_VALUE);
	}

	public MapIndexesModel(Map map, Object defaultValue) {
		super(map.keySet());

		this.map = map;
		this.defaultValue = defaultValue;
	}

	public boolean addIndex(int index) {
		if (commited) {
			throw new IllegalStateException("Already commited indexes model.");
		}

		map.put(getKey(index), getSelectedValue(index));

		return true;
	}

	protected Object getSelectedValue(int index) {
		return defaultValue;
	}

	public IIndexesModel copy() {
		return new MapIndexesModel(map, defaultValue);
	}

}
