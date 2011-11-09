/**
 * @class f_toolTip extends f_component, fa_asyncRender
 * @author jbmeslin@vedana.com
 * @version $Revision$ $Date$
 */

var __members = {

	f_finalize: function() {
		this._elementContainer=undefined; // HTMLElement

		this.f_super(arguments);
	},
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
			this.style.top =  this._elementContainer.offsetTop + this._elementContainer.offsetHeight +"px";
		} else {
			//this.style.visibility = "hidden";
			this.style.top =  "-5000px";
			this.style.left = "-5000px";
		}
	},

	
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	
	/**
	 * @method hidden
	 * @return HTMLElement
	 */
	fa_getInteractiveParent: function() {
		return this; //div tooltip
	},
	
	/**
	 * @method hidden 
	 * @param HTMLElement elementContainer
	 * @return void
	 */
	f_setElementContainer: function(elementContainer) {
		this._elementContainer=elementContainer;
	},
	/**
	 * @method hidden 
	 * @return HTMLElement
	 */
	f_getElementContainer: function() {
		return this._elementContainer;
	},
	/**
	 * @method hidden
	 * @return void
	 */
	f_clear: function() {
		this._elementContainer=undefined;
	}

};

new f_class("f_toolTip", {
	extend : f_component,
	aspects: [ fa_asyncRender ],
	members : __members
});
