/*
 * $Id$
 */
 
/**
 * Hidden value component.
 *
 * @class public f_hiddenValue extends f_eventTarget, HTMLElement, fa_serializable, fa_clientData, fa_value
 */
 
 var __prototype = {
	/**
	 * Returns the value.
	 *
	 * @method public
	 * @return String Or <code>null</code> if not defined !
	 */
	f_getValue: function() {
		return this.value;
	},
	/**
	 * Set the value.
	 *
	 * @method public
	 * @param optional String value
	 * @return void
	 */
	f_setValue: function(value) {
		if (value===undefined) {
			value="";
		}
	
		var oldValue=this.value;	
		this.value=value;
		
		if (this.f_performPropertyChange) {
			this.f_performPropertyChange(f_prop.VALUE, value, oldValue);
		}
	}
}
 
 
var f_hiddenValue = new f_class("f_hiddenValue", null, null, __prototype, f_eventTarget, fa_serializable, fa_clientData);
