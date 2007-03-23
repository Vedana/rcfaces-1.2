/*
 * $Id$
 */
 
/**
 * Aspect ItemsManager
 *
 * @aspect public fa_itemsManager extends fa_cardinality
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {

	/**
	 * @method protected abstract
	 * @param String value
	 * @return Object An item
	 */
	fa_getElementItem: f_class.ABSTRACT,

	/**
	 * @method protected abstract
	 * @param Object The item
	 * @return String the value
	 */
	fa_getElementValue: f_class.ABSTRACT,

	/**
	 * @method protected abstract
	 */
	fa_isElementDisabled: f_class.ABSTRACT,

	/**
	 * @method protected abstract
	 */
	fa_listVisibleElements: f_class.ABSTRACT,

	/**
	 * @method protected abstract
	 */
	fa_showElement: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 */
	fa_updateElementStyle: f_class.ABSTRACT
}

new f_aspect("fa_itemsManager", null, __prototype, fa_cardinality);
