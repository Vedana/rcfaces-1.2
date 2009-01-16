/*
 * $Id$
 */
 
/**
 * Aspect abstractAppender.
 *
 * @aspect public abstract fa_abstractAppender
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {

	/**
	 * @method public abstract
	 * @param Object event
	 * @return void
	 */
	f_doAppend: f_class.ABSTRACT
}

new f_aspect("fa_abstractAppender", {
	members: __members
});	
