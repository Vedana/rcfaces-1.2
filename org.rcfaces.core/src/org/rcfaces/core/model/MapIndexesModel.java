/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.2  2004/09/24 14:01:36  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.model;

import java.util.Map;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class MapIndexesModel extends CollectionIndexesModel {
	private static final String REVISION="$Revision$";
	
	private static final Object DEFAULT_VALUE=Boolean.TRUE;

	private final Map map;
	private final Object defaultValue;
	
	public MapIndexesModel(Map map) {
		this(map, DEFAULT_VALUE);
	}

	public MapIndexesModel(Map map, Object defaultValue) {
		super(map.keySet());
		
		this.map=map;
		this.defaultValue=defaultValue;
	}

	public void addIndex(int index) {
		map.put(getKey(index), getSelectedValue(index));
	}

	protected Object getSelectedValue(int index) {
		return defaultValue;
	}
}
