/*
 * $Id$
 */

/**
 * Aspect
 *
 * @aspect public abstract fa_autoOpen
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {

	/**
	 * @method public
	 * @return Object 
	 */
	fa_findAutoOpenElement: f_class.ABSTRACT,
	
	/**
	 * @method protected
	 * @param Object 
	 * @return void 
	 */
	fa_performAutoOpenElement: f_class.ABSTRACT,
	
	
	/**
	 * @method protected
	 * @param Object el1
	 * @param Object elt2
	 * @return Boolean
	 */
	fa_isSameAutoOpenElement: f_class.ABSTRACT
};

new f_aspect("fa_autoOpen", {
	members: __members
});
