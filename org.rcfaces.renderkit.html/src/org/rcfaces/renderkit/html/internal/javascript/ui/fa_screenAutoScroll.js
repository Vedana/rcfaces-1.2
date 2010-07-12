/*
 * $Id$
 */

/**
 * Aspect
 *
 * @aspect public abstract fa_screenAutoScroll extends fa_autoScroll
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	/**
	 * @method protected
	 * @return Boolean
	 */
	fa_isContainerAutoScroll: function(scrollableComponent) {
		
		return false;
	},
	
	/**
	 * @method protected
	 * @param HTMLElement scrollableComponent
	 * @param Object componentPosition Component position
	 * @param Object mousePosition Mouse position
	 * @return void
	 */
	fa_updateAutoScroll: function(scrollableComponent, componentPosition, mousePosition) {
		
	}

};

new f_aspect("fa_screenAutoScroll", {
	extend: [ fa_autoScroll ],
	members: __members
});
