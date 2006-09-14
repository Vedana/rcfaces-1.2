/*
 * $Id$
 */

/**
 * 
 * @class public f_dateChooser extends f_imageButton, fa_calendarPopup
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype={

	f_dateChooser: function() {
		this.f_super(arguments);

		var className=f_core.GetAttribute(this, "v:className");
		if (!className) {
			className=this.className;
		}

		this._className=className;
		
		this._forComponent=f_core.GetAttribute(this, "v:for");
		if (this._forComponent) {
			this._forValueFormat=f_core.GetAttribute(this, "v:forValueFormat");
		}
	},
	f_finalize: function() {
		this._initialSelection=undefined; // any ?
		// this._className=undefined; // string
	
		this.f_super(arguments);
	},
	_onSelect: function(event) {
		f_core.Debug("f_dateChooser", "_onSelect: "+event+" detail="+event.f_getDetail());

		if (this.f_isDisabled()) {
			return false;
		}
		
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly()) {
			return false;
		}

		return this._openCalendarPopup(event);
	},
	f_setDomEvent: function(type, target) {
		if (type==f_event.CHANGE) {
			return;
		}
		return this.f_super(arguments, type, target);
	},
	f_clearDomEvent: function(type, target) {
		if (type==f_event.CHANGE) {
			return;
		}
		return this.f_super(arguments, type, target);
	},
	_onDateSelected: function(date, jsEvent) {
		f_core.Debug(f_dateChooser, "Selected date: "+date);
		
		return this.f_fireEvent(f_event.CHANGE, jsEvent, null, date);
	}
}
 
var f_dateChooser=new f_class("f_dateChooser", null, __static, __prototype, f_imageButton, fa_calendarPopup);
