/*
 * $Id$
 */

/**
 * @class f_keyLabel extends f_filtredComponent, fa_value
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	/**
	 * @method hidden
	 * @param Object...
	 *            itemN Items
	 * @return void
	 */
	_updateItems: function(itemN) {
		for(;;) {
			var firstChild=this.firstChild;
			if (!firstChild) {
				break;
			}
			
			this.removeChild(firstChild);
		}
		
		for(var i=0;i<arguments.length;i++) {
			var item=arguments[i];
			
			var li=f_core.CreateElement(this, "label", {
				textnode: item._label,
				className: item._computedStyleClass
			});
			
			if (item._value) {
				li._value=item._value;
			}
			if (item._disabled) {
				li._disabled=item._disabled;
			}
		}
	},
	/**
	 * @method protected
	 */
	f_getServiceId: function() {
		return "keyLabel.request";
	}
};

new f_class("f_keyLabel", {
	extend: f_filtredComponent,
	aspects: [ fa_value ],
	members: __members
});
