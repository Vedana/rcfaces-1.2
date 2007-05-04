/*
 * $Id$
 */
 
/**
 * Aspect SelectionProvider
 *
 * @aspect public fa_selectionProvider
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
	
	/**
	 * Returns selected objects.
	 *
	 * @method public abstract
	 * @return any An Object, an array of Object, it depends of the type of component.
	 */
	f_getSelection: f_class.ABSTRACT,
	
	/**
	 * Set selected objects.
	 *
	 * @method public abstract
	 * @param any selection An Object, an array of Object, it depends of the type of component.
	 * @param optional boolean show
	 * @return void
	 */
	f_setSelection: f_class.ABSTRACT
}

new f_aspect("fa_selectionProvider", null, __prototype);
	