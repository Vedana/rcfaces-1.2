/*
 * $Id$
 */

/**
 * 
 * @class public f_calendar extends f_component, fa_readOnly, fa_disabled, fa_itemsWrapper
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {
	
	f_calendar: function() {
		this.f_super(arguments);

		this._calendar=f_calendarObject.CreateCalendarFromComponent(this, 
			f_calendarObject.YEAR_CURSOR_LAYOUT | 
			f_calendarObject.MONTH_LIST_LAYOUT |
			f_calendarObject.DAY_LIST_LAYOUT |
			f_calendarObject.UNIT_CURSOR_LAYOUT);
	},
	
	f_finalize: function() {
		var calendar=this._calendar;
		if (calendar) {
			this._calendar=undefined;
			
			f_classLoader.Destroy(calendar);
		}
	
		this.f_super(arguments);
	},
	
	f_update: function() {		
		this.f_updateComponent();
		
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 */
	f_updateComponent: function() {
		var doc=this.ownerDocument;
		this._calendar.f_constructComponent(this);		
	},
	/**
	 * @method hidden
	 */
	f_appendDateItem: function(date, label, disabled, styleClass) {
		this._calendar.f_appendDateItem.apply(this._calendar, arguments);
	},
	
	/**
	 * @method protected
	 */
	fa_getItemsWrapper: function() {
		return this._calendar;
	},
	/**
	 * @method public
	 * @return f_calendarObject
	 */
	f_getCalendarObject: function() {
		return this._calendar;
	}
}
 
new f_class("f_calendar", null, null, __prototype, f_component, fa_readOnly, fa_disabled, fa_itemsWrapper);
