/**
 * Class Tooltip Manager
 * 
 * @class public f_tooltipManager extends f_object
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {

	/**
	 * @field private static
	 */
	_Instance : undefined,

	/**
	 * @method public static
	 * @param hidden
	 *            boolean create
	 * @return f_tooltipManager
	 */
	Get : function() {
		var instance = f_tooltipManager._Instance;
		if (!instance) {
			instance = f_tooltipManager.f_newInstance();
			f_tooltipManager._Instance = instance;
		}

		return instance;
	},
	
	
	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer : function() {
		f_tooltipManager._Instance = undefined; // f_tooltipManager
	}
};

var __members = {

	f_tooltipManager : function() {
		this.f_super(arguments);

		
		f_core.AddEventListener(document.body, "mousemove", function(evt) {
			if (window._rcfacesExiting) {
				 return false;
			}

			initFct(evt.target);
		}, document.body);
		
		
		f_core.AddEventListener(document.body, "mouseenter", function(evt) {
			if (window._rcfacesExiting) {
				 return false;
			}

			initFct(evt.target);
		}, document.body);
		
		f_core.AddEventListener(document.body, "mouseout", function(evt) {
			if (window._rcfacesExiting) {
				 return false;
			}

			initFct(evt.target);
		}, document.body);

	},

	f_finalize : function() {

		this.f_super(arguments);
	}

};

new f_class("f_tooltipManager", {
	extend : f_object,
	statics : __statics,
	members : __members
});
