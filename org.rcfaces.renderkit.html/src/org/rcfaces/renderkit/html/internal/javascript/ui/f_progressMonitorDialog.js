/*
 * $Id$
 */

/**
 * <p><strong>f_progressMonitorDialog</strong> represents popup modal.
 *
 * @class public f_progressMonitorDialog extends f_dialog
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {

	/**
	 * <p>Construct a new <code>f_viewDialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 */
	f_progressMonitorDialog: function() {
		this.f_super(arguments, f_shell.PRIMARY_MODAL_STYLE);
	},

	/*
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {

		this.f_super(arguments);		
	},
	
	f_fillBody: function(base) {
		
		var ul=f_core.CreateElement(base, "ul", { 
			styleClass: "f_progressMonitorDialog_container"
		});
		
		var title=f_core.CreateElement(ul, "li", {
			styleClass: "f_progressMonitorDialog_title"
		});
		
		var body=f_core.CreateElement(ul, "li", {
			styleClass: "f_progressMonitorDialog_body"
		});
		
		var buttons=f_core.CreateElement(ul, "li", {
			styleClass: "f_progressMonitorDialog_buttons"
		});
		
		this._titleText=f_core.CreateELement(title, "label", {
			styleClass: "f_progressMonitorDialog_titleText"			
		});
		
		this._messageText1=f_core.CreateELement(body, "label", {
			styleClass: "f_progressMonitorDialog_bodyText1"			
		});
		
		this._messageText2=f_core.CreateELement(body, "label", {
			styleClass: "f_progressMonitorDialog_bodyText2"			
		});
		
		this._progressBar=f_progressBar.Create(body);
	},

	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_viewDialog viewURL='"+this._viewURL+"']";
	}
}

new f_class("f_progressMonitorDialog", {
	extend: f_progressMonitorDialog,
	members: __members
});
