/*
 * $Id$
 */
 
/**
 * Aspect Cardinality
 *
 * @aspect fa_cardinality
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
	
	/**
	 * @field public static final Number
	 */
	OPTIONAL_CARDINALITY: 1,

	/**
	 * @field public static final Number
	 */
	ZEROMANY_CARDINALITY: 2,

	/**
	 * @field public static final Number
	 */
	ONE_CARDINALITY: 3,

	/**
	 * @field public static final Number
	 */
	ONEMANY_CARDINALITY: 4
};

new f_aspect("fa_cardinality", __statics);
