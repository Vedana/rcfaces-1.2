/**
 * @class f_tooltip extends f_component, fa_asyncRender
 * @author jbmeslin@vedana.com
 * @version $Revision$ $Date$
 */
var __members = {

		
	/**
	 * @method public
	 * @param Boolean visible
	 * @param Boolean aSync
	 * @return void
	 */
	f_setVisible : function(visible, aSync) {
		
		this.f_super(arguments, visible);
		
		if (visible) {
			this.style.left = this._x + 5 + "px";
			this.style.top =  tooltip._elementContainer.offsetTop + tooltip._elementContainer.offsetHeight +"px";
		} else {
			//this.style.visibility = "hidden";
			this.style.top =  "-5000px";
			this.style.left = "-5000px";
		}
	},

	
	/**
	 * @method hidden
	 * @return HTMLElement
	 */
	fa_getInteractiveParent: function() {
		return this; //div tooltip
	}

};

new f_class("f_tooltip", {
	extend : f_component,
	aspects: [ fa_asyncRender ],
	members : __members
});
