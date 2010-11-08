/*
 * $Id$
 */
 
/**
 * Aspect SelectionProvider
 *
 * @aspect public abstract fa_selectionProvider<T>
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	
	/**
	 * Returns selected objects.
	 *
	 * @method public abstract
	 * @return T An Object, an array of Object, it depends of the type of component.
	 */
	f_getSelection: f_class.ABSTRACT,
	
	/**
	 * Set selected objects.
	 *
	 * @method public abstract
	 * @param T selection An Object, an array of Object, it depends of the type of component.
	 * @return optional Boolean show
	 * @return void
	 */
	f_setSelection: f_class.ABSTRACT
}

new f_aspect("fa_selectionProvider", {
	members: __members
});
	