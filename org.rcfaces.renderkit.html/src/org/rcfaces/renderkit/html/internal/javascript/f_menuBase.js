/*
 * $Id$
 */

/**
 * Class MenuBase
 *
 * @class f_menuBase extends f_component, fa_menuCore
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {
	f_menuBase: function() {
		this.f_super(arguments);
		
		var tabIndex=this.tabIndex;
		if (tabIndex<=0) {
			tabIndex=0;
		}
		
		this._tabIndex=tabIndex;
		this.tabIndex=-1;
	}
	
	/*
	f_finalize: function() {
		this._tabIndex=undefined; // number
	
		this.f_super(arguments);
	}
	*/
}

var f_menuBase=new f_class("f_menuBase", null, null, __prototype, f_component, fa_menuCore);
