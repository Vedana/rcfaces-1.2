/*
 * $Id$
 */

/**
 * Class fsvg_image.
 * 
 * @class fsvg_image extends f_image, fa_items
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {
	/**
	 * @method public
	 * @return Document
	 */
	f_getSVGDocument : function() {
		return this.contentDocument;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getRootElement: function(id) {
		return this.f_getSVGDocument().rootElement;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getElementById: function(id) {
		return this.f_getSVGDocument().getElementById(id);
	},
	/**
	 * @method public
	 * @return Element
	 */
	$: function(expr) {
		return this.f_query(expr);
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_query: function(expr) {
		return this.f_getSVGDocument().querySelector(expr);
	},
	/**
	 * @method public
	 * @return Element[]
	 */
	f_queryAll: function(expr) {
		return this.f_getSVGDocument().querySelectorAll(expr);
	},
	f_setItemColor: function(item, color) {
		
	},
	fa_updateItemStyle: function(item) {
		var elt=this._getItemElement(item);
		if (!elt) {
			return;
		}
		
		var itemStyle=item.style;
		
		var itemColor=item._color;
		if (itemColor!==undefined && itemStyle.color!=itemColor) {
			if (itemColor) {
				itemStyle.color=itemColor;
			} else {
				// remove style				
			}
		}
		
	},
	_getItemElement: function(item) {
		var valueToElement=this._valueToElement;
		if (!valueToElement) {
			valueToElement=new Object();
			this._valueToElement=valueToElement;
		}
		
		var value=this.f_getItemValue(item);
		if (value===undefined) {
			return undefined;
		}
		
		var element=valueToElement[value];
		if (typeof(element)!="undefined") {
			return element;
		}
		
		element=this.f_getElementById(value);
		valueToElement[value]=element;
		
		return element;
	},
}

new f_class("fsvg_svg", {
	extend : f_box,
	aspects : [ fa_items ],
	members : __members
});
