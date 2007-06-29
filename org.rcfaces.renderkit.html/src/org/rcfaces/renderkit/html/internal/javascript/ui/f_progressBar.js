/*
 * $Id$
 */

/**
 * Classe ProgressBar
 *
 * @class public f_progressBar extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$) 
 * @version $Revision$ $Date$
 */

var __members = {
	f_progressBar: function() {
		this.f_super(arguments);

		var smin=f_core.GetAttribute(this, "v:min");
		this._min=(smin)?parseFloat(smin):0;
		
		var smax=f_core.GetAttribute(this, "v:max");
		this._max=(smax)?parseFloat(smax):0;

		var svalue=f_core.GetAttribute(this, "v:value");
		this._value=(svalue)?parseFloat(svalue):0;
		
		var cursor=this.ownerDocument.createElement("div");
		
		var suffix="_cursor";
		if (true) {
			suffix+="_smooth";
		}
		
		cursor.className=this.f_computeStyleClass(suffix);
		this._cursor=cursor;
		var tds=this.getElementsByTagName("td");
		
		f_core.Assert(tds.length==3, "f_progressBar.f_progressBar: TD.length!=3. ("+tds.length+")");
		
		tds[1].appendChild(cursor);
	},
	f_finalize: function() {
		// this._min=undefined; // number
		// this._max=undefined; // number
		// this._value=undefined; // number
		// this._step=undefined; // number
		// this._noPropertyUpdates=undefined; // boolean
		
		this._cursor=undefined; // HTMLElement
	
		this.f_super(arguments);
	},
	f_update: function() {
		this._updateBar();

		this.f_super(arguments);
	},
	/** 
	 * Returns the minimum value.
	 *
	 * @method public
	 * @return number Minimum value
	 */
	f_getMin: function() {
		return this._min;
	},
	/** 
	 * Set the minimum value.
	 *
	 * @method public
	 * @param number min The minimum value to set.
	 * @return void
	 */
	f_setMin: function(min) {
		f_core.Assert(typeof(min)=="number", "f_setMin: Min parameter must be a number. ("+min+")");
		
		this._min=min;
		
		this.f_setProperty(f_prop.MIN, min);
		
		this._updateBar();
	},
	/** 
	 * Returns the maximum value.
	 *
	 * @method public
	 * @return number Maximum value
	 */
	f_getMax: function() {
		return this._max;
	},
	/** 
	 * Set the maximum value.
	 *
	 * @method public
	 * @param number max The maximum value to set.
	 * @return void
	 */
	f_setMax: function(max) {
		f_core.Assert(typeof(max)=="number", "f_setMax: Max parameter must be a number. ("+max+")");
		
		this._max=max;
		this.f_setProperty(f_prop.MAX, max);
		
		this._updateBar();
	},
	/** 
	 * Returns the value of the progression.
	 *
	 * @method public
	 * @return number Value of the progression
	 */
	f_getValue: function() {
		return this._value;
	},
	/** 
	 * Set the value of the progression.
	 *
	 * @method public
	 * @param number value The value of the progression.
	 * @return void
	 */
	f_setValue: function(value) {
		f_core.Assert(typeof(value)=="number", "f_setValue: Value parameter must be a number. ("+value+")");
		this._value=value;

		this.f_setProperty(f_prop.VALUE, value);
		
		this._updateBar();
	},
	/** 
	 * Set the indeterminate state.
	 *
	 * @method public
	 * @param boolean indeterminate State of indeterminate.
	 * @return void
	 */
	f_setIndeterminate: function(indeterminate) {
		this._indeterminate=indeterminate;
	},
	/** 
	 * Returns the indeterminate state.
	 *
	 * @method public
	 * @return boolean State of indeterminate.
	 */
	f_isIndeterminate: function() {
		return this._indeterminate;
	},
	_updateBar: function() {
		var min=this._min;
		var max=this._max;
		var cursor=this._cursor;
	
		if (!cursor) {
			return;
		}
	
		var parentNode=cursor.parentNode;
		var parentWidth=parentNode.offsetWidth;
		
		f_core.Debug(f_progressBar, "_updateBar: min="+min+" max="+max+" value="+this._value+" parentWidth="+parentWidth);
		if (parentWidth<1) {
			return;
		}
		
		var cursorStyle=cursor.style;
		var value=this._value;

		if (min>=max || value<=min) {
			if (cursorStyle.visibility!="hidden") {
				cursorStyle.visibility="hidden";
			}
			return;
		}
		
		if (value>max) {
			value=max;
		}
		
		var width=Math.floor((value-min)/(max-min)*parentWidth);
		
		var step=this._step;
		if (step && value<max) {
			width-=(width % step);
		}
	
		cursorStyle.width=width+"px";
		if (cursorStyle.visibility!="visible") {
			cursorStyle.visibility="visible";
		}
	}
}
 
new f_class("f_progressBar", {
	extend: f_component,
	members: __members
});