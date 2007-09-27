/*
 * $Id$
 */

/**
 * <p><strong>f_dialog</strong> represents popup modal window.
 *
 * @class public final f_dialog extends f_shell
 * @author Fred Lefevere-Laoide Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
}

var __members = {

	/**
	 * <p>Construct a new <code>f_dialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param number style the style of control to construct
	 */
	f_dialog: function(style) {
		this.f_super(arguments, style | f_shell.COPY_STYLESHEET);
	}

	/*
	 * <p>Destruct a new <code>f_dialog</code>.</p>
	 *
	 * @method public
	 *
	f_finalize: function() {
		this.f_super(arguments);
		//this._imageURL=undefined; // String
	},
	*/

}

new f_class("f_dialog", {
	extend: f_shell,
	statics: __statics,
	members: __members
});

