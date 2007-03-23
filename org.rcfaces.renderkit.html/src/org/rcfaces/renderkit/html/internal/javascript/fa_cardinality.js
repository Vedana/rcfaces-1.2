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
var __static = {
	
	/**
	 * @field public static final number
	 */
	OPTIONAL_CARDINALITY: 1,

	/**
	 * @field public static final number
	 */
	ZEROMANY_CARDINALITY: 2,

	/**
	 * @field public static final number
	 */
	ONE_CARDINALITY: 3,

	/**
	 * @field public static final number
	 */
	ONEMANY_CARDINALITY: 4
}

new f_aspect("fa_cardinality", __static);
