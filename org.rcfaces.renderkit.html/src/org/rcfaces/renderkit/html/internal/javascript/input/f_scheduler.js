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
		
		var divNode = this;
		var period = divNode._period;
		if(!period._selectable){
			return false;
		}
		if(divNode._hover == true){
			return false;
		}
		divNode._hover=true;
		scheduler.fa_updateElementStyle(divNode);
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
		var divNode = this;
		//var period = divNode._period;

		if(divNode._hover == false){
			return false;
		}
		divNode._hover=false;
		scheduler.fa_updateElementStyle(divNode);
		return true;
	},
	
	/**
	 * @method private static 
	 * @param Event
	 * @return boolean
	 * @context object:scheduler
	 */
	_OnPeriodMouseDown: function(evt) {
		var scheduler=this._scheduler;
		if (!evt) {
			evt=f_core.GetJsEvent(this);
		}
		if (scheduler.f_getEventLocked(evt)) {
			return false;
		}
		var divNode = this;
		var period = divNode._period;
		if(!period._selectable){
			return false;
		}
		
		var selection=fa_selectionManager.ComputeMouseSelection(evt);
		scheduler.f_moveCursor(divNode, true, evt, selection);
		
		return true;
	}

}

var __members = {

	f_scheduler : function() {
		this.f_super(arguments);
		this._selectionCardinality=fa_cardinality.OPTIONAL_CARDINALITY;
	},

	f_finalize : function() {

		this.f_super(arguments);
		
	},

	f_addPeriod : function(item) {
		item._begin = f_core.DeserializeDate(item._begin);
		item._end = f_core.DeserializeDate(item._end);
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
			var begin = period._begin;
			var end = period._end;
			var periodeDay = begin.getDay();
			if (begin.getDate() >= dateBegin.getDate()
					&& begin.getDate() <= (dateBegin.getDate() + (columnNumber - 1))) {

				
				f_core.Debug(f_scheduler, "f_scheduler  periodBegin "+
						begin.getHours()+"h" +begin.getMinutes());
				var minutesPerdiodBegin = begin.getHours()*60+begin.getMinutes();
				if (minutesPerdiodBegin < minutesDayBegin){
					minutesPerdiodBegin = minutesDayBegin;
				}
				
				f_core.Debug(f_scheduler, "f_scheduler periodEnd "+
						end.getHours()+"h" +end.getMinutes());
				var minutesPerdiodEnd = end.getHours()*60+end.getMinutes();
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
					cssHeight : height + "px",
					title : period._toolTip
				});
				period._divNode = divNode;
				
				
				var labelNode = f_core.CreateElement(divNode, "label", {
					textnode : period._label,
					className : period._periodStyle+"_label"

				});
				period._labelNode = labelNode;
				divNode._period = period;
				divNode._scheduler = this;
				divNode.onmousedown= f_scheduler._OnPeriodMouseDown;
				divNode.onmouseover=f_scheduler._OnPeriodMouseOver;
				divNode.onmouseout=f_scheduler._OnPeriodMouseOut;
			}
		}

		return this.f_super(arguments);
	},
	
	
	/**
	 * @method public
	 * @param Object
	 * @return Date
	 */
	f_getItemDateBegin: function(period) {
		return   period._begin;
	},
	
	/**
	 * @method public
	 * @param Object
	 * @return Date
	 */
	f_getItemDateEnd: function(period) {
		return period._end;
	},
	
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_showElement: function(divNode){
	
	},
	
	/**
	 * @method abstract protected
	 * @return boolean
	 */
	
	fa_isElementDisabled: function(divNode) {
		return false;
	},
	
	
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_updateElementStyle: function(divNode) {
		var period = divNode._period;
		var style = f_scheduler._PERIOD_STYLE;
		var periodStyle;
		if (period._periodStyle){
			periodStyle  = period._periodStyle;
		}
		if (periodStyle) {
			style += " "+periodStyle;
		}
		if(divNode._hover){
			style += " "+style+"_over"; 
			if (periodStyle){
				periodStyle += " "+periodStyle+"_over";
			}
		}
		
		if (divNode.className!=style) {
			divNode.className=style;
		}
		
	},
	
	/**
	 * @method abstract protected
	 * @return item
	 */
	fa_getElementItem: function(divNode) {
		return divNode._period;
	},
	
	/**
	 * @method abstract protected
	 * @return void
	 */
	fa_updateItemStyle: function(period) {
		this.fa_updateElmentStyle(period._divNode);
	},
	
	/**
	 * @method protected
	 * @return void
	 */
	fa_destroyItems : function(items) {
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			this.f_destroyItem(item);
		}
	},	
	/**
	 * @method protected
	 * @param Object item
	 * @return void
	 */
	 f_destroyItem: function(item) {
		item._labelNode= undefined;

		var component=item._divNode;
		if (component) {
			item._divNode=undefined;
			component._period = undefined;
			component._scheduler = undefined;

			component.onmouseover=null;		
			component.onmouseout=null;
			component.onmousedown=null;
		
			f_core.VerifyProperties(component);
		}
	},
	
	fa_isElementSelected:  function(divNode) {
		return divNode._selected;
	},
	
	/**
	 * @method protected abstract
	 * @param any element
	 * @param boolean selected
	 * @return void 
	 */
	fa_setElementSelected:  function(divNode, selected) {
		divNode._selected = selected;
	},
	
	/**
	 * @method protected abstract
	 * @param any element
	 * @return Object 
	 */
	fa_getElementValue: function(divNode) {
		return divNode._period._value;
	},
	
	f_setDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			return;
		}
		
		this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		switch(type) {
		case f_event.SELECTION: 
			return;
		}
		
		this.f_super(arguments, type, target);
	}

}

new f_class("f_scheduler", {
	extend : f_component,
	aspects : [ fa_items, fa_selectionManager ],
	statics : __statics,
	members : __members
});
