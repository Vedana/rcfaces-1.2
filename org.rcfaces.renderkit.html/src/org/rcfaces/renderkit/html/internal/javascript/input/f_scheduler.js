/**
 * f_scheduler class
 * 
 * @class f_scheduler extends f_component, fa_items, fa_selectionManager
 * @author jb.meslin@vedana.com
 * @version $Revision: 1.0
 */
var __statics = {
	/**
	 * @field private static final String
	 */
	_PERIOD_STYLE: "f_scheduler_period",
	
	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return boolean
	 * @context object:scheduler
	 */
	_OnPeriodMouseOver : function(evt) {
		var scheduler = this._scheduler;
	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
	
		if (scheduler.f_getEventLocked(evt, false)) {
			return false;
		}
		
		var period = this._period;
		if(!period._selectable){
			return false;
		}
		if(period._hover == true){
			return false;
		}
		period._hover=true;
		scheduler.fa_updateItemStyle(period);
		return true;
	},

	/**
	 * @method private static
	 * @param Event
	 *            evt
	 * @return boolean
	 * @context object:scheduler
	 */
	_OnPeriodMouseOut : function(evt) {
		var scheduler = this._scheduler;
	
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		var period = this._period;

		if(period._hover == false){
			return false;
		}
		period._hover=false;
		scheduler.fa_updateItemStyle(period);
		return true;
	}

}

var __members = {

	f_scheduler : function() {
		this.f_super(arguments);
	},

	f_finalize : function() {

		this.f_super(arguments);
		// this._dateBegin=undefined;
		// this._top=undefined;
		// this._left=undefined;
		// this._minPerPx=undefined;
		// this._columnWidth=undefined;
	},

	f_addPeriod : function(item) {
		this.f_addItem(this, item);
	},

	f_update : function() {

		var columnNumber = f_core.GetNumberAttribute(this, "v:columnNumber");
		var dateBegin = f_core.GetAttribute(this, "v:dateBegin");
		if (dateBegin) {
			dateBegin = f_core.DeserializeDate(dateBegin);
		}
		var dayBegin = dateBegin.getDay();

		var minutesDayBegin = f_core.GetNumberAttribute(this,
				"v:minutesDayBegin");
		var minutesDayEnd = f_core.GetNumberAttribute(this, "v:minutesDayEnd");

		var minPerPx = parseFloat(f_core.GetAttribute(this, "v:minPerPx"));
		var columnWidth = f_core.GetNumberAttribute(this, "v:columnWidth");

		var div = this.ownerDocument.getElementById(this.id + "::periods");

		var items = this._items;
		for ( var i = 0; i < items.length; i++) {
			var period = items[i];

			if (!period._begin || !period._end) {
				continue;
			}
			var begin = f_core.DeserializeDate(period._begin);
			var end = f_core.DeserializeDate(period._end);
			var periodeDay = begin.getDay();
			if (begin.getDate() >= dateBegin.getDate()
					&& begin.getDate() <= (dateBegin.getDate() + (columnNumber - 1))) {

				var minutesPerdiodBegin = period._minutesBegin;
				if (minutesPerdiodBegin < minutesDayBegin){
					minutesPerdiodBegin = minutesDayBegin;
				}
				var minutesPerdiodEnd = period._minutesEnd;
				if (minutesPerdiodEnd > minutesDayEnd){
					minutesPerdiodEnd = minutesDayEnd;
				}
				var top = (minutesPerdiodBegin - minutesDayBegin) * minPerPx;

				var left = columnWidth * (periodeDay - dayBegin);
				var height = (minutesPerdiodEnd - minutesPerdiodBegin)
						* minPerPx - 1;
				var width = (columnWidth - 1);
				if (periodeDay - dayBegin == columnNumber - 1) {
					width -= 1;
				}
				
				var style = f_scheduler._PERIOD_STYLE;
				if (period._periodStyle){
					style +=" "+period._periodStyle;
				}
				var divNode = f_core.CreateElement(div, "div", {
					className : style,
					cssTop : top + "px",
					cssLeft : left + "px",
					cssWidth : width + "px",
					cssHeight : height + "px"

				});
				period._divNode = divNode;
				
				
				var labelNode = f_core.CreateElement(divNode, "label", {
					textnode : period._label

				});
				period._labelNode = labelNode;
				divNode._period = period;
				divNode._scheduler = this;
				//divNode.onmousedown=  f_setSelection(fa_getElementValue(period), true);
				//divNode.onmouseup=f_scheduler._OnPeriodMouseUp;
				divNode.onmouseover=f_scheduler._OnPeriodMouseOver;
				divNode.onmouseout=f_scheduler._OnPeriodMouseOut;
			}
		}

	},
	
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_updateItemStyle: function(period) {
		var style = f_scheduler._PERIOD_STYLE;
		var periodStyle;
		if (period._periodStyle){
			periodStyle  = period._periodStyle;
		}
		if(period._hover){
			style += " "+style+"_over"; 
			if (periodStyle){
				periodStyle += " "+periodStyle+"_over";
			}
		}
		
		if (periodStyle) {
			style += " "+periodStyle;
		}
		
		
//		var component=this.f_getUIItem(period);
//		
//		if (component.className!=style) {
//			component.className=style;
//		}
		
	},
	
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	f_setProperty:function(period) {
		
	},
	/**
	 * @method protected abstract
	 * @return void
	 */

	fa_destroyItems : function() {
		this._items = undefined;
	},
	
	fa_isElementSelected:  function(period) {
		return period._selected;
	},
	
	/**
	 * @method protected abstract
	 * @param any element
	 * @param boolean selected
	 * @return void 
	 */
	fa_setElementSelected:  function(period, selected) {
		period._selected = selected;
	},
	
	
	/**
	 * @method protected abstract
	 * @param any element
	 * @return Object 
	 */
	fa_getElementValue: function(period) {
		return period._value;
	}

}

new f_class("f_scheduler", {
	extend : f_component,
	aspects : [ fa_items, fa_selectionManager ],
	statics : __statics,
	members : __members
});
