/*
 * $Id$
 */

/**
 * 
 * @class public f_calendarObject extends f_object, fa_eventTarget, fa_items, fa_selectionProvider, fa_commands
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __static = {

	/**
	 * @field private static final boolean
	 */
	_COMPUTE_TOOLTIP: true,

	/**
	 * @field private static final string
	 */
	_BLANK_IMAGE_URL: "/blank.gif",

	/**
	 * @field private static final number
	 */
	_HOME_DELTA: -999999,

	/**
	 * @field private static final number
	 */
	_END_DELTA:   999999,

	/**
	 * @field private static final number
	 */
	_PAGE_UP_DELTA: -999990,

	/**
	 * @field private static final number
	 */
	_PAGE_DOWN_DELTA:   999990,
	
	/**
	 * @field public static final number
	 */
	DAY_SELECTION_DETAIL: 0x01,

	/**
	 * @field public static final number
	 */
	WEEK_SELECTION_DETAIL: 0x02,

	/**
	 * @field public static final number
	 */
	DAYOFWEEK_SELECTION_DETAIL: 0x03,

	/**
	 * @field public static final number
	 */
	MONTH_SELECTION_DETAIL: 0x04,

	/**
	 * @field public static final number
	 */
	YEAR_SELECTION_DETAIL: 0x05,

	/**
	 * @field public static final number
	 */
	DATE_MODE: 0,

	/**
	 * @field public static final number
	 */
	PERIOD_MODE: 1,

	/**
	 * @field public static final number
	 */
	PERIODS_MODE: 2,

	/**
	 * @field hidden static final number
	 */
	YEAR_CURSOR_LAYOUT: 0x1,

	/**
	 * @field hidden static final number
	 */
	MONTH_LIST_LAYOUT: 0x2,

	/**
	 * @field hidden static final number
	 */
	MONTH_CURSOR_LAYOUT: 0x4,

	/**
	 * @field hidden static final number
	 */
	DAY_LIST_LAYOUT: 0x8,

	/**
	 * @field hidden static final number
	 */
	HOME_DATE_LAYOUT: 0x10,

	/**
	 * @field hidden static final number
	 */
	UNIT_CURSOR_LAYOUT: 0x20,

	/**
	 * @field private static final number
	 */
	_DEFAULT_UNIT: 0,

	/**
	 * @field private static final number
	 */
	_WEEK_UNIT: 0,

	/**
	 * @field private static final number
	 */
	_DAY_UNIT: 1,

	/**
	 * @field private static final number
	 */
	_DAYOFWEEK_UNIT: 2,  // Passe au lundi suivant ...

	/**
	 * @field private static final number
	 */
	_MONTH_UNIT: 3,

	/**
	 * @field private static final number
	 */
	_YEAR_UNIT: 4,

	/**
	 * @field private static final number
	 */
	_WEEKDAY_UNIT: 5, // Passe du lundi au mardi

	/**
	 * @method hidden static final
	 */
	CreateCalendarFromComponent: function(component, layout) {
		var className="f_calendar";
		//f_core.GetAttribute(component, "v:className");
		//if (!className) {
		//	className=component.className;
		//}
			
		var calendar=f_calendarObject.f_newInstance(className, component, layout);
		
		var maxDate=f_core.GetAttribute(component, "v:maxDate");
		if (maxDate) {
			calendar.f_setMaxDate(maxDate);
		}		

		var minDate=f_core.GetAttribute(component, "v:minDate");
		if (minDate) {
			calendar.f_setMinDate(minDate);
		}
		
		var twoDigitYearStart=f_core.GetAttribute(component, "v:twoDigitYearStart");
		if (twoDigitYearStart) {
			calendar.f_setTwoDigitYearStart(twoDigitYearStart);
		}

		var homeDate=f_core.GetAttribute(component, "v:homeDate");
		if (homeDate) {
			var homeDateLabel=f_core.GetAttribute(component, "v:homeDateLabel");
			
			calendar.f_setHomeDate(homeDate, homeDateLabel);
		}

		var clientDatesStrategy=f_core.GetAttribute(component, "v:clientDatesStrategy");
		if (clientDatesStrategy) {
			calendar._clientDatesStrategy=parseInt(clientDatesStrategy, 10);
		}

		var disabledWeekDays=f_core.GetAttribute(component, "v:disabledWeekDays");
		if (disabledWeekDays) {
			calendar.f_setDisabledWeekDays(disabledWeekDays);
		}
		
		var firstDayOfWeek=f_core.GetAttribute(component, "v:firstDayOfWeek");
		if (firstDayOfWeek) {
			calendar._firstDayOfWeek=parseInt(firstDayOfWeek, 10);
		}

		var mode=f_core.GetAttribute(component, "v:mode");
		if (mode) {
			calendar._mode=parseInt(mode, 10);
		}
		
		var date=f_core.GetAttribute(component, "v:value");
		if (date) {
			calendar.f_setSelection(date);
		}
		
		return calendar;
	},
	
	/**
	 * @method private static
	 */
	_ParseDates: function(value) {
		var ds=value.split(",");
		var ret=new Array;
		
		for(var i=0;i<ds.length;i++) {
			var d=f_dateFormat.ParseStringDate(ds[i]);
			if (!d) {
				continue;
			}
			
			ret.push(d);
		}
		
		return ret;
	},
	
	/**
	 * @method private static
	 */
	_ParsePeriods: function(value) {
		var ds=value.split(",");
		var ret=new Array;
		
		for(var i=0;i<ds.length;i++) {
			var ds2=ds[i].split(":");
			
			var d=f_dateFormat.ParseStringDate(ds2[0]);
			if (!d) {
				continue;
			}
			
			if (ds2.length<2) {
				ret.push([d, d]);
				continue;
			}
			
			var d2=f_dateFormat.ParseStringDate(ds2[0]);
			if (!d2) {
				continue;
			}
			
			ret.push([d, d2]);
		}
		
		return ret;
	},
	
	/**
	 * @method private static
	 */
	_OnHomeDateClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		evt.cancelBubble=true;
		
		var date=this._date;
		if ((date instanceof Date)==false) {
			return false;
		}
		
		calendar._onDayClick(evt, this, date);
	},
	
	/**
	 * @method private static
	 */
	_OnDayClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		evt.cancelBubble=true;
		
		var date=this._date;
		if ((date instanceof Date)==false) {
			return false;
		}
		
		calendar._onDayClick(evt, this, date);
	},
	
	/**
	 * @method private static
	 */
	_OnDayKey: function(evt) {
		var calendar=this._calendar;
	
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
				
		var delta=f_calendarObject._GetDeltaKey(evt, 1, 7, true, true);
		
		if (!delta) {
			return f_core.CancelEvent(evt);
		}
		
		switch(delta) {
		case f_calendarObject._PAGE_UP_DELTA: 
			var date=this._date;
			if (!date) {
				return f_core.CancelEvent(evt);
			}

			var nbd=date.getDate();  // Nombre de jour à reculer
			var d2=new Date(date.getTime());
			d2.setDate(0);
			
			var nbjm=d2.getDate() // Nombre jour/mois
			
			if (nbjm>date.getDate()) {
				d2.setDate(date.getDate());
			}
			
			d2=calendar._searchValidDate(d2, 1, date);
			if (!d2 || d2.getTime()==date.getTime()) {
				return f_core.CancelEvent(evt);
			}

			delta=nbjm-d2.getDate()+nbd;
			
			calendar._onUnitClick(evt, this, -delta, f_calendarObject._DAY_UNIT);
			break;

		case f_calendarObject._PAGE_DOWN_DELTA: 
			var date=this._date;
			if (!date) {
				return f_core.CancelEvent(evt);
			}

			var d2=new Date(date.getTime());
			d2.setDate(1);
			d2.setMonth(d2.getMonth()+1);
			d2.setDate(0);
			
			var nbd=d2.getDate()-date.getDate(); // Nombre de joura à avancer !
			
			d2.setDate(1);
			d2.setMonth(d2.getMonth()+2);
			d2.setDate(0);
			
			// On regarde si le positionnement au 31 est valide !
			if (d2.getDate()>date.getDate()) {
				d2.setDate(date.getDate());
			}
			
			d2=calendar._searchValidDate(d2, -1, date);
			if (!d2 || d2.getTime()==date.getTime()) {
				return f_core.CancelEvent(evt);
			}

			delta=d2.getDate()+nbd;
			
			calendar._onUnitClick(evt, this, delta, f_calendarObject._DAY_UNIT);
			break;
			
		case f_calendarObject._HOME_DELTA: 
			var date=this._date;
			if (!date) {
				return f_core.CancelEvent(evt);
			}
			
			var d2=new Date(date.getTime());
			d2.setDate(1);
			d2=calendar._searchValidDate(d2, 1, date);
			if (!d2) {
				return true;
			}

			var deltaDate=date.getDate()-d2.getDate();			
			
			if (deltaDate<0) {
				return f_core.CancelEvent(evt);

			} else if (deltaDate==0) {
				// On change de mois !
				var nbd=date.getDate();  // Nombre de jour à reculer
				d2.setDate(1);
				d2.setMonth(d2.getMonth());
				d2.setDate(0);
				
				var nbjm=d2.getDate() // Nombre jour/mois
				
				d2.setDate(1);
				
				d2=calendar._searchValidDate(d2, 1, date);
				if (!d2 || d2.getTime()==date.getTime()) {
					return f_core.CancelEvent(evt);
				}

				deltaDate=nbjm-d2.getDate()+nbd;
			}
			
			calendar._onUnitClick(evt, this, -deltaDate, f_calendarObject._DAY_UNIT);
			
			break;

		case f_calendarObject._END_DELTA: 
			var date=this._date;
			
			if (!date) {
				return f_core.CancelEvent(evt);
			}
			
			var d2=new Date(date.getTime());
			d2.setDate(1);
			d2.setMonth(d2.getMonth()+1);
			d2.setDate(0); // d2= fin du mois
			var nbd=d2.getDate()-date.getDate(); // Nombre de jour avant la fin du mois
			
			var d1=d2;
			d2=calendar._searchValidDate(d2, -1, date);
			if (!d2) {
				// f_core.Debug(f_calendarObject, "Day.EndDelta: invalid date '"+d2+"' from '"+d1+"'=>'"+date+"'.");
				return f_core.CancelEvent(evt);
			}

			var delta=d2.getDate()-date.getDate();			
			
			if (delta<0) {
				return f_core.CancelEvent(evt);
			}
			
			if (delta==0) {
				// On change de mois !
				d2.setDate(1);
				d2.setMonth(d2.getMonth()+2);
				d2.setDate(0);
				
				d2=calendar._searchValidDate(d2, -1, date);
				if (!d2 || d2.getTime()==date.getTime()) {
					return f_core.CancelEvent(evt);
				}

				delta=nbd+d2.getDate();
			}
			
			calendar._onUnitClick(evt, this, delta, f_calendarObject._DAY_UNIT);
			break;

		default:
			calendar._onUnitClick(evt, this, delta, f_calendarObject._DAY_UNIT);
		}

		var f=null;
		if (calendar._dates.length>0) {
			f=calendar._dates[0][0];
		}
		if (!f) {
			f=calendar._showDate;
		}
		
		f_calendarObject._SearchButton(calendar._dayButtons, f, true);
		
		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_OnMonthClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		evt.cancelBubble=true;
		
		var date=this._date;
		if ((date instanceof Date)==false) {
			return false;
		}
		
		calendar._onMonthClick(evt, this, date);		
	},
	
	/**
	 * @method private static
	 */
	_OnMonthKey: function(evt) {
		var calendar=this._calendar;

		if (calendar.f_getEventLocked2()) {
			return null;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
			
		var delta=f_calendarObject._GetDeltaKey(evt, 1, 6, true);
		
		if (!delta) {
			return f_core.CancelEvent(evt);
		}
		
		switch(delta) {
		case f_calendarObject._HOME_DELTA: 
			var date=this._date;
			if (!date) {
				return true;
			}
			
			var m=date.getMonth();
			
			delta=-((m==0)?12:m);
			
			break;

		case f_calendarObject._END_DELTA: 
			var date=this._date;
			if (!date) {
				return true;
			}
			
			var m=date.getMonth();
			
			delta=(m==11)?12:(11-m);
			
			break;
		}

		calendar._onUnitClick(evt, this, delta, f_calendarObject._MONTH_UNIT);
		
		if (calendar._dates.length>0) {
			var ds=calendar._dates[0][0];
			f_calendarObject._Focus(calendar._monthButtons[ds.getMonth()]);
		}
		
		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_GetDeltaKey: function(evt, hDelta, vDelta, supportHome, supportPage) {
		switch(evt.keyCode) {
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
			return hDelta;
			
		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
			return -hDelta;
			
		case f_key.VK_UP: 
			return -vDelta;
			
		case f_key.VK_DOWN:
			return vDelta;
			
		case f_key.VK_HOME:
			if (supportHome) {
				return f_calendarObject._HOME_DELTA;
			}
			break;
			
		case f_key.VK_END:
			if (supportHome) {
				return f_calendarObject._END_DELTA;
			}
			break;
			
		case f_key.VK_PAGE_UP:
			if (supportPage) {
				return f_calendarObject._PAGE_UP_DELTA;
			}
			break;
			
		case f_key.VK_PAGE_DOWN:
			if (supportPage) {
				return f_calendarObject._PAGE_DOWN_DELTA;
			}
			break;
		}
		
		return 0;
	},
	
	/**
	 * @method private static
	 */
	_OnYearClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		evt.cancelBubble=true;
		
		var date=this._date;
		if ((date instanceof Date)==false) {
			return false;
		}
		
		calendar._onYearClick(evt, this, date);		
	},
	
	/**
	 * @method private static
	 */
	_OnYearKey: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
			
		var delta=f_calendarObject._GetDeltaKey(evt, 1, 1, true);
		
		if (!delta) {
			return true;
		}

		var date=calendar._yearButtons[1]._date;
		
		switch(delta) {
		case f_calendarObject._HOME_DELTA: 
			var minDate=calendar._minDate;
			if (!date || !minDate) {
				return true;
			}
			
			delta=minDate.getFullYear()-date.getFullYear();
			break;

		case f_calendarObject._END_DELTA:
			var maxDate=calendar._maxDate;
			if (!date || !maxDate) {
				return true;
			}
			
			delta=maxDate.getFullYear()-date.getFullYear();			
			break;
		}

		if (!delta) {
			return true;
		}
		
		calendar._onUnitClick(evt, this, delta, f_calendarObject._YEAR_UNIT);
		
		f_calendarObject._Focus(calendar._yearButtons[(delta<0)?0:2]);
		
		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_OnWeekClick: function(evt) {
		try {
			var calendar=this._calendar;
			if (calendar.f_getEventLocked2()) {
				return false;
			}
			if (!evt) {
				evt = f_core.IeGetEvent(this);
			}
			evt.cancelBubble=true;
			
			var date=this._date;
			if ((date instanceof Date)==false) {
				return false;
			}
			
			calendar._onWeekClick(evt, this, date);		
		} catch (x) {
			f_core.Error(f_calendarObject, "_OnWeekClick throws exception.",x);
		}
	},
	
	/**
	 * @method private static
	 */
	_OnWeekKey: function(evt) {
		var calendar=this._calendar;

		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}		
		
		var delta=f_calendarObject._GetDeltaKey(evt, 10, 1);
		
		if (!delta || delta==-10) {
			return f_core.CancelEvent(evt);
		}
		
		if (delta==10) {
			// Focus le bouton du jour !
			
			var date=calendar._showDate;
			if (date) {
				f_calendarObject._SearchButton(calendar._dayButtons, date, true);
			}
			
			return f_core.CancelEvent(evt);
		}
		
		calendar._onUnitClick(evt, this, delta, f_calendarObject._WEEK_UNIT);
		if (calendar._dates.length>0) {
			var ds=new Date(calendar._dates[0][0].getTime());
			var dday=(ds.getDay()-calendar._firstDayOfWeek) % 7;
			if (dday<0) {
				dday=7-dday;
			}
			ds.setDate(ds.getDate()-dday); // Reviens en debut de semaine !
			
			f_calendarObject._SearchButton(calendar._weekButtons, ds, true);
		}		
		
		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_SearchButton: function(buts, date, focus) {
		f_core.Assert(buts instanceof Array, "Bad buts parameter ("+buts+")");
		f_core.Assert(date instanceof Date, "Not a date parameter="+date);
		
		var ds=date.getTime();
		for(var i=0;i<buts.length;i++) {
			var d=buts[i]._date;
			if (!d || d.getTime()!=ds) {
				continue;
			}
			
			if (focus) {
				f_calendarObject._Focus(buts[i]);
			}
			return buts[i];
		}			
		
		return null;
	},
	
	/**
	 * @method private static
	 */
	_OnWeekDayClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		evt.cancelBubble=true;
		
		var date=this._date;
		if ((date instanceof Date)==false) {
			return false;
		}
		
		calendar._onWeekDayClick(evt, this, date);		
	},
	
	/**
	 * @method private static
	 */
	_OnWeekDayKey: function(evt) {
		var calendar=this._calendar;

		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}		
	
		var delta=f_calendarObject._GetDeltaKey(evt, 1, 10);
		
		if (!delta || delta==-10) {
			return f_core.CancelEvent(evt);
		}
		if (delta==10) {
			// Focus le bouton du jour !
			
			var date=calendar._showDate;
			if (date) {
				f_calendarObject._SearchButton(calendar._dayButtons, date, true);
			}
			
			return f_core.CancelEvent(evt);
		}
	
		calendar._onUnitClick(evt, this, delta, f_calendarObject._DAYOFWEEK_UNIT);

		var dates=calendar._dates;

		var day;
		if (dates.length>0) {
			day=dates[0][0].getDay();

		} else {		
			day=calendar._showDate.getDay();
		}
		
		f_calendarObject._Focus(calendar._weekDayButtons[(day + 7 - calendar._firstDayOfWeek) % 7]);
				
		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_OnNextUnitClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}

		evt.cancelBubble=true;
		
		calendar._onUnitClick(evt, this, 1);
	},
	
	/**
	 * @method private static
	 */
	_OnPrevUnitClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		
		evt.cancelBubble=true;
		
		calendar._onUnitClick(evt, this, -1);
	},
	
	/**
	 * @method private static
	 */
	_OnUnitKey: function(evt) {
		var calendar=this._calendar;

		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}		

		switch(evt.keyCode) {
		case f_key.VK_RIGHT: // FLECHE VERS LA DROITE
			var buttons=calendar._unitButtons;
			if (buttons && buttons[1]) {
				buttons[1].focus();
			}
			break;

		case f_key.VK_LEFT: // FLECHE VERS LA GAUCHE
			var buttons=calendar._unitButtons;
			if (buttons && buttons[0]) {
				buttons[0].focus();
			}
			break;
			
		case f_key.VK_ENTER:
		case f_key.VK_RETURN:
		case f_key.VK_SPACE:
			var delta=this._date; // C'est -1 ou 1 dans ce cas de bouton "UNIT"
			calendar._onUnitClick(evt, this, delta);
			break;			
		}

		return f_core.CancelEvent(evt);
	},
	
	/**
	 * @method private static
	 */
	_OnNextYearClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}

		evt.cancelBubble=true;
		
		calendar._onUnitClick(evt, this, 1, f_calendarObject._YEAR_UNIT);
	},
	
	/**
	 * @method private static
	 */
	_OnPrevYearClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		
		evt.cancelBubble=true;

		calendar._onUnitClick(evt, this, -1, f_calendarObject._YEAR_UNIT);
	},
	
	/**
	 * @method private static
	 */
	_OnNextMonthClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		
		evt.cancelBubble=true;

		calendar._onUnitClick(evt, this, 1, f_calendarObject._MONTH_UNIT);
	},
	
	/**
	 * @method private static
	 */
	_OnPrevMonthClick: function(evt) {
		var calendar=this._calendar;
		if (calendar.f_getEventLocked2()) {
			return false;
		}
		if (!evt) {
			evt = f_core.IeGetEvent(this);
		}
		
		evt.cancelBubble=true;

		calendar._onUnitClick(evt, this, -1, f_calendarObject._MONTH_UNIT);
	},
	
	/**
	 * @method private static
	 */
	_DestroyButtons: function(buttons) {
		if (!buttons) {
			return;
		}
		
		for(var i=0;i<buttons.length;i++) {
			var button=buttons[i];

			button.onclick=null;
			button.onkeydown=null;
			button._calendar=undefined; // f_calendarObject

			// button._date=undefined;  // Date
			
			f_core.VerifyProperties(button);
		}
	},
	
	/**
	 * @method private static
	 */
	_LongFormatMonth: function(date, locale) {
		return f_core.UpperCaseFirstChar(locale.f_getMonthName(date.getMonth(), f_locale.LONG))
			+" "
			+date.getFullYear();
	},
	
	/**
	 * @method private static
	 */
	_LongFormatWeek: function(idx) {
		var txt=f_resourceBundle.Get(f_calendarObject).f_get("WEEK");

		return txt+idx;
	},
	
	/**
	 * @method private static
	 */
	_FindByDicho: function(array, value) {
		 var first = 0; // début du tableau
		 var last = array.length - 1; // fin du tableau

		 for (;;) {
		    var index = (first + last) >> 1;

		    if (array[index] == value) {
		    	return true;
		    }
		    
		    if (last - first <= 1) {
		    	return false;
		    }
		    
		    if (array[index] > value) {
		    	last = index;
		    	continue;
		    }
		    
		    first = index;
		}
	},
	
	/**
	 * @method private static
	 */
	_FindDateItem: function(array, value) {
		 var first = 0; // début du tableau
		 var last = array.length - 1; // fin du tableau

		 for (;;) {
		    var index = (first + last) >> 1;

			var v=array[index];
		    if (v._time == value) {
		    	return v._item;
		    }
		    
		    if (last - first <= 1) {
		    	return null;
		    }
		    
		    if (v._time > value) {
		    	last = index;
		    	continue;
		    }
		    
		    first = index;
		}
	},
	
	/**
	 * @method private static
	 */
	_Focus: function(button) {
		try {
			button.focus();
			
		} catch (x) {
			// Sous IE le popup est déjà caché, et le focus plante !
		}
	}
} 
var __prototype = {
	f_calendarObject: function(className, calendarContainer, layout) {
		this.f_super(arguments);

		if (!className) {
			className="f_calendarObject";
		}
		this._className=className;
		this._calendarContainer=calendarContainer;
		this._layout=layout;
		
		this._locale=f_locale.Get();
		
		this._mode=f_calendarObject.DATE_MODE;
	
		this._firstDayOfWeek=this._locale.f_getFirstDayOfWeek();

		this._dates=new Array;
	},
	f_finalize: function() {
		this._calendarContainer=undefined;
		
		// this._layout=undefined; // number
		
		this._locale=undefined; // f_locale;
		
		// this._className=undefined; // string
		// this._mode=undefined; // number
	
		// this._firstDayOfWeek=undefined; // number
		// this._dates=undefined; // date[]
		// this._showDate=undefined; // date
		// this._disabledWeekDays=undefined; // number
		// this._maxDate=undefined; // date
		// this._maxTime=undefined; //number
		// this._minDate=undefined; // date
		// this._minTime=undefined; //number
		// this._twoDigitYearStart=undefined; // date
		// this._focusSelection=undefined; // ? boolean
		// this._disabledDates=undefined; // date[]
		this._itemDates=undefined;
		
		// this._popupMode=undefined; // boolean
	
		if (this._parentComponent) {
			this.f_destroyComponent();
		}
	
		this.f_super(arguments);
	},
	/**
	 * @method protected
	 */	
	f_destroyComponent: function() {
		this._parentComponent=undefined;
		
		if (this._yearButtons) {
			f_calendarObject._DestroyButtons(this._yearButtons);
			this._yearButtons=undefined;

			this._previousYearImage=undefined;	// HtmlImageElement
			this._nextYearImage=undefined;	// HtmlImageElement		
			this._yearText=undefined; // TextNode
		}		
		
		if (this._monthButtons) {
			f_calendarObject._DestroyButtons(this._monthButtons);
			this._monthButtons=undefined;

			this._previousMonthImage=undefined;	// HtmlImageElement
			this._nextMonthImage=undefined;	// HtmlImageElement
			this._monthText=undefined; // TextNode
		}
				
		f_calendarObject._DestroyButtons(this._weekDayButtons);
		this._weekDayButtons=undefined;
				
		f_calendarObject._DestroyButtons(this._dayButtons);
		this._dayButtons=undefined;
		
		if (this._weekButtons) {
			f_calendarObject._DestroyButtons(this._weekButtons);
			this._weekButtons=undefined;
		}
		
		if (this._unitButtons) {
			f_calendarObject._DestroyButtons(this._unitButtons);
			this._unitButtons=undefined;
					
			this._unitLabel=undefined; // TextNode
			
			// this._lastUnit=undefined; //  number
			// this._lastUnitDate=undefined; // date			
 		}

		var todayButton=this._todayButton;
		if (todayButton) {
			this._todayButton=undefined;
			
			todayButton.onclick=null;
			// todayButton._date=undefined; // date
			todayButton._calendar=undefined;
		}
		
	},
	/**
	 * @method protected
	 */
	f_constructComponent: function(parent) {			
		if (!this._showDate) {
			var date=new Date();
			this._showDate=new Date(date.getFullYear(), date.getMonth(), date.getDate());
		}

		if (!this._dayButtons) {
			this.f_createCalendar(parent.ownerDocument, parent, this._className);
		}
		
		// XXX Restituer la selection
	},
	f_getTargetComponent: function() {
		return this._calendarContainer;
	},
	/**
	 * @method hidden
	 * @return void
	 */	
	f_enablePopupMode: function() {
		this._popupMode=true;
	},
	/**
	 * @method public
	 * @param Date maxDate
	 * @return void
	 */
	f_setMaxDate: function(maxDate) {
		if (typeof(maxDate)=="string") {
			maxDate=f_dateFormat.ParseStringDate(maxDate);
		}
		this._maxDate=maxDate;
		this._maxTime=(maxDate)?maxDate.getTime():undefined;
	},
	/**
	 * @method public
	 * @param Date maxDate
	 * @return void
	 */
	f_setMinDate: function(minDate) {
		if (typeof(minDate)=="string") {
			minDate=f_dateFormat.ParseStringDate(minDate);
		}
		this._minDate=minDate;
		this._minTime=(minDate)?minDate.getTime():undefined;
	},
	/**
	 * @method public
	 * @param Date twoDigitYearStart
	 * @return void
	 */
	f_setTwoDigitYearStart: function(twoDigitYearStart) {
		switch(typeof(twoDigitYearStart)) {
		case "string":
			twoDigitYearStart=f_dateFormat.ParseStringDate(twoDigitYearStart);
			break;
			
		case "number":
			twoDigitYearStart=new Date(1, 0, twoDigitYearStart);
			break;
		}
		this._twoDigitYearStart=twoDigitYearStart;
	},
	/**
	 * @method public
	 * @param Date homeDate
	 * @param string homeDateLabel
	 * @return void
	 */
	f_setHomeDate: function(homeDate, homeDateLabel) {
		if (typeof(homeDate)=="string") {
			homeDate=f_dateFormat.ParseStringDate(homeDate);
		}
		this._homeDate=homeDate;
		this._homeDateLabel=homeDateLabel;
	},
	/*
	 * @method public
	 *
	f_setFocusSelection: function(focusSelection) {
		this._focusSelection=focusSelection;
	},
	*/
	/**
	 * @method public
	 * @return void
	 */
	f_setDisabledWeekDays: function(disabledWeekDays) {
		if (typeof(disabledWeekDays)=="string") {
			disabledWeekDays=parseInt(disabledWeekDays, 10);
		}
		this._disabledWeekDays=disabledWeekDays;
	},
	/**
	 * @method public
	 * @return any selection
	 */
	f_getSelection: function() {
		return this._dates;
	},
	/**
	 * @method public
	 * @param any selection
	 * @return void
	 */
	f_setSelection: function(selection) {
		if (typeof(selection)=="string") {
			selection=f_calendarObject._ParsePeriods(selection);

		} else if (selection instanceof Date) {
			selection=[[selection, selection]];
			
		} else if (selection instanceof Array) {
			// On laisse ...
			
		} else  {
			selection=[];
		}
	
		this._dates=selection;
		
		var nextShowDate=this._showDate;
		if (selection && selection.length>0) {
			var ds=selection[0];
			if (ds && ds.length>0) {
				f_core.Assert(ds[0] instanceof Date, "f_calendarObject.f_setSelection: Invalid selected date '"+ds[0]+"' into selection '"+selection+"'.");

				nextShowDate=ds[0];
			}
		}
		
		if (!this._dayButtons) {
			this._showDate=nextShowDate;
			return;
		}
		
		this._updateShowDate(nextShowDate, true);
	},
	/**
	 * @method public
	 * @param Date date The date to select.
	 * @return void
	 */
	f_setCurrentDate: function(date) {
		this._showDate=new Date(date.getFullYear(), date.getMonth(), date.getDate());
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_createCalendar: function(doc, component, className) {
		this._parentComponent=component;
	
		var blankImageURL=f_env.GetStyleSheetBase()+f_calendarObject._BLANK_IMAGE_URL;
		f_imageRepository.PrepareImage(blankImageURL);

		var layout=this._layout;
		/* --- L'année -- */
		if (layout & f_calendarObject.YEAR_CURSOR_LAYOUT) {
			this._createYearCursor(doc, component, className, blankImageURL);
		}
				
		/* --- Les mois -- */
		if (layout & f_calendarObject.MONTH_LIST_LAYOUT) {
			this._createMonthList(doc, component, className, blankImageURL);	
		}
		
		if (layout & f_calendarObject.MONTH_CURSOR_LAYOUT) {
			this._createMonthCursor(doc, component, className, blankImageURL);		
		}
		
		/* --- Les jours -- */
		if (layout & f_calendarObject.DAY_LIST_LAYOUT) {
			this._createDayList(doc, component, className, blankImageURL);
		}
		
		if ((layout & f_calendarObject.HOME_DATE_LAYOUT) && this._homeDate) {
			this._createHomeDate(doc, component, className, blankImageURL);
		}
		
		/* -- navigation par unité (suivant derniere selection) -- */
		if (layout & f_calendarObject.UNIT_CURSOR_LAYOUT) {
			this._createUnitCursor(doc, component, className, blankImageURL);	
		}
		
		this.f_refreshComponent();
	},
	_createHomeDate: function(doc, component, className, blankImageURL) {
		f_core.Assert(this._homeDate instanceof Date, "f_calendarObject._createHomeDate: homeDate is not a date ('"+this._homeDate+"').");
		
		var button=doc.createElement("A");
		button.className=className+"_today";
		button.onclick=f_calendarObject._OnHomeDateClick;
		button.href=f_core.JAVASCRIPT_VOID;
		button._date=this._homeDate;
		button._calendar=this;
		
		var message=this._homeDateLabel;
		if (!message) {						
			message=f_resourceBundle.Get(f_calendarObject).f_get("GOTO_DATE");
			
			if (!message) {
				message="";
				
			} else {
				message+=" ";
			}
			message+=this._longFormatDate(this._homeDate, this._locale);
		}
		f_core.SetTextNode(button, message);
	
		component.appendChild(button);
		
		this._todayButton=button;
	},
	_createUnitCursor: function(doc, component, className, blankImageURL) {
		var unitButtons=new Array();
		this._unitButtons=unitButtons

		var div=doc.createElement("DIV");
		
		var table=doc.createElement("TABLE");
		table.align="center";
		table.cellpadding=0;
		table.cellspacing=0;
		table.border=0;
		div.appendChild(table);

		var tbody=doc.createElement("TBODY");
		table.appendChild(tbody);

		var tr=doc.createElement("TR");
		tbody.appendChild(tr);

		var td=doc.createElement("TD");
		tr.appendChild(td);
		
		var link=doc.createElement("A");
		link.className=className+"_prevUnit";
		link.href=f_core.JAVASCRIPT_VOID;
		td.appendChild(link);
		unitButtons.push(link);
		link.onclick=f_calendarObject._OnPrevUnitClick;
		link.onkeydown=f_calendarObject._OnUnitKey;
		link._calendar=this;
		link._date=-1;
		
		var prevUnitLabel=f_resourceBundle.Get(f_calendarObject).f_get("PREVIOUS_UNIT");
		var name=doc.createTextNode(prevUnitLabel);
		link.appendChild(name);
		
		var img=doc.createElement("IMG");
		img.width=16;
		img.height=16;
		img.src=blankImageURL;
		img.alt="<";
		img.className=className+"_prevUnit";
		link.appendChild(img);
		
		var td=doc.createElement("TD");		
		tr.appendChild(td);
		
		var divName=doc.createElement("SPAN");
		td.appendChild(divName);
		divName.className=className+"_unitLabel";
		
		var name=doc.createTextNode("");
		this._unitLabel=name;
		divName.appendChild(name);

		var img=doc.createElement("IMG");
		img.width=1;
		img.height=16;
		img.src=blankImageURL;
		img.className=className+"_unitLabel";
		divName.appendChild(img);

		var td=doc.createElement("TD");
		tr.appendChild(td);
			
		var link=doc.createElement("A");
		link.className=className+"_nextUnit";
		link.href=f_core.JAVASCRIPT_VOID;
		td.appendChild(link);
		unitButtons.push(link);
		link.onclick=f_calendarObject._OnNextUnitClick;
		link.onkeydown=f_calendarObject._OnUnitKey;
		link._calendar=this;
		link._date=1;
				
		var img=doc.createElement("IMG");
		img.width=16;
		img.height=16;
		img.src=blankImageURL;
		img.alt=">";
		img.className=className+"_nextUnit";
		link.appendChild(img);
		
		var nextUnitLabel=f_resourceBundle.Get(f_calendarObject).f_get("NEXT_UNIT");
		var name=doc.createTextNode(nextUnitLabel);
		link.appendChild(name);
		
		component.appendChild(div);
	},	
	_createDayList: function(doc, component, className, blankImageURL) {
		var table=doc.createElement("TABLE");
		table.align="center";
		table.cellpadding=1;
		table.cellspacing=1;
		table.border=0;

		var tbody=doc.createElement("TBODY");
		table.appendChild(tbody);

		var tr=doc.createElement("TR");
		tbody.appendChild(tr);

		if (!this._popupMode) {
			var td=doc.createElement("TD");
			tr.appendChild(td);
		}
		
		this._weekDayButtons=new Array;

		var dof=this._firstDayOfWeek;
		for(var j=0;j<7;j++) {
			var td=doc.createElement("TD");
			td.align="center";
			tr.appendChild(td);
				
			var link;
			if (!this._popupMode) {
				link=doc.createElement("A");
				link.href=f_core.JAVASCRIPT_VOID;
				link.onclick=f_calendarObject._OnWeekDayClick;
				link.onkeydown=f_calendarObject._OnWeekDayKey;
				link._calendar=this;
				
			} else {
				link=doc.createElement("SPAN");
			}
			
			td.appendChild(link);
			link.className=className+"_weekDay";
			this._weekDayButtons.push(link);
			
			var d=(j+dof) % 7;
			
			var dayName=this._locale.f_getDayName(d, f_locale.SHORT);
			var name=doc.createTextNode(dayName);
			
			var dayLongName=this._locale.f_getDayName(d, f_locale.LONG);
			if (dayLongName) {
				link.title=f_core.UpperCaseFirstChar(dayLongName);
			}
			
			link.appendChild(name);
		}
		
		this._dayButtons=new Array;
		this._weekButtons=new Array;
		
		var idx=0;
		for(var j=0;j<42;j++) {
			if ((j % 7)==0) {
				tr=doc.createElement("TR");
				tr.className=className+"_rday";
				tbody.appendChild(tr);

				if (!this._popupMode) {
					var td=doc.createElement("TD");
					td.align="center";
					tr.appendChild(td);
					td.className=className+"_cweek";
	
					var but=doc.createElement("A");
					td.appendChild(but);
					but.className=className+"_week";
					but.href=f_core.JAVASCRIPT_VOID;
					this._weekButtons.push(but);
					but.onclick=f_calendarObject._OnWeekClick;
					but.onkeydown=f_calendarObject._OnWeekKey;
					but._calendar=this;
	
					but.tabIndex=this._tabIndex;
	
					var weekSelect=doc.createElement("IMG");
					but.appendChild(weekSelect);
					weekSelect.className=className+"_weekImage";
					weekSelect.width=16;
					weekSelect.height=16;
					weekSelect.src=blankImageURL;
				}
			}
			
			var td=doc.createElement("TD");
			td.align="center";
			tr.appendChild(td);
			td.className=className+"_cday";
			
			var day=doc.createElement("A");
			td.appendChild(day);
// C'est une classe utilisateur !
//			day.className=className+"_day";
			day.href=f_core.JAVASCRIPT_VOID;
			this._dayButtons.push(day);
			day.onclick=f_calendarObject._OnDayClick;
			day.onkeydown=f_calendarObject._OnDayKey;
			day._calendar=this;
				
			var name=doc.createTextNode("");
			day.appendChild(name);
			
			day._index=j;
		}
	
		component.appendChild(table);
	},
	_createMonthList: function(doc, component, className, blankImageURL) {
		this._monthButtons=new Array;

		var table=doc.createElement("TABLE");
		
		table.align="center";
		table.cellpadding=1;
		table.cellspacing=1;
		table.border=0;

		var tbody=doc.createElement("TBODY");
		table.appendChild(tbody);

		var tr;
		var idx=0;
		for(var j=0;j<12;j++) {
			if ((j % 6)==0) {
				tr=doc.createElement("TR");
				tbody.appendChild(tr);
			}
			
			var td=doc.createElement("TD");
			td.align="center";
			tr.appendChild(td);
			td.className=className+"_cmonth";
			
			var month=doc.createElement("A");
			td.appendChild(month);
			month.className=className+"_month";
			month.href=f_core.JAVASCRIPT_VOID;
			this._monthButtons.push(month);
			
			month.onclick=f_calendarObject._OnMonthClick;
			month.onkeydown=f_calendarObject._OnMonthKey;
			month._calendar=this;
			
			var monthName=this._locale.f_getMonthName(j, f_locale.MEDIUM);
			var name=doc.createTextNode(monthName);
			month.appendChild(name);
			month._index=j;
		}
	
		component.appendChild(table);
	},
	_createYearCursor: function(doc, component, className, blankImageURL) {			
		this._yearButtons=new Array;
		
		var table=doc.createElement("TABLE");
		table.align="center";
		table.cellpadding=1;
		table.cellspacing=0;
		table.border=0;
		
		var tbody=doc.createElement("TBODY");
		table.appendChild(tbody);
		
		var tr=doc.createElement("TR");
		tbody.appendChild(tr);
		
		var td=doc.createElement("TD");
		tr.appendChild(td);
		
		var link=doc.createElement("A");
		link.className=className+"_prevYear";
		td.appendChild(link);
		link.href=f_core.JAVASCRIPT_VOID;
		this._yearButtons.push(link);
		link.onclick=f_calendarObject._OnPrevYearClick;
		link.onkeydown=f_calendarObject._OnYearKey;
		link._calendar=this;		
		
		var img=doc.createElement("IMG");
		link.appendChild(img);
		img.className=className+"_prevYear";
		img.width=16;
		img.height=16;
		img.src=blankImageURL;		
		this._previousYearImage=img;
		
		var td=doc.createElement("TD");
		tr.appendChild(td);

		var year=doc.createElement("A");
		td.appendChild(year);
		year.className=className+"_year";
		year.href=f_core.JAVASCRIPT_VOID;
		year.onclick=f_calendarObject._OnYearClick;
		year.onkeydown=f_calendarObject._OnYearKey;		
		this._yearButtons.push(year);
		year._calendar=this;
		
		var name=doc.createTextNode("");
		year.appendChild(name);
		this._yearText=name;
		
		var td=doc.createElement("TD");
		tr.appendChild(td);
		
		var link=doc.createElement("A");
		link.className=className+"_nextYear";
		td.appendChild(link);
		link.href=f_core.JAVASCRIPT_VOID;
		this._yearButtons.push(link);
		link.onclick=f_calendarObject._OnNextYearClick;
		link.onkeydown=f_calendarObject._OnYearKey;
		link._calendar=this;
		
		var img=doc.createElement("IMG");
		link.appendChild(img);
		img.className=className+"_nextYear";
		img.src=blankImageURL;
		img.width=16;
		img.height=16;
		this._nextYearImage=img;
		
		component.appendChild(table);
	},
	_createMonthCursor: function(doc, component, className, blankImageURL) {			
		this._monthButtons=new Array;
		
		var table=doc.createElement("TABLE");
		table.align="center";
		table.cellpadding=1;
		table.cellspacing=0;
		table.border=0;
		
		var tbody=doc.createElement("TBODY");
		table.appendChild(tbody);
		
		var tr=doc.createElement("TR");
		tbody.appendChild(tr);
		
		var td=doc.createElement("TD");
		tr.appendChild(td);
		
		var link=doc.createElement("A");
		link.className=className+"_prevMonth";
		td.appendChild(link);
		link.href=f_core.JAVASCRIPT_VOID;
		this._monthButtons.push(link);
		link.onclick=f_calendarObject._OnPrevMonthClick;
		link.onkeydown=f_calendarObject._OnMonthKey;
		link._calendar=this;		
		
		var img=doc.createElement("IMG");
		link.appendChild(img);
		img.className=className+"_prevMonth";
		img.width=16;
		img.height=16;
		img.src=blankImageURL;		
		this._previousMonthImage=img;
		
		var td=doc.createElement("TD");
		tr.appendChild(td);
		td.align="center";

		var month=doc.createElement("DIV");
		td.appendChild(month);
		month.className=className+"_month";
		
		var name=doc.createTextNode("");
		month.appendChild(name);
		this._monthText=name;
		
		var td=doc.createElement("TD");
		tr.appendChild(td);
		
		var link=doc.createElement("A");
		link.className=className+"_nextMonth";
		td.appendChild(link);
		link.href=f_core.JAVASCRIPT_VOID;
		this._monthButtons.push(link);
		link.onclick=f_calendarObject._OnNextMonthClick;
		link.onkeydown=f_calendarObject._OnMonthKey;
		link._calendar=this;
		
		var img=doc.createElement("IMG");
		link.appendChild(img);
		img.className=className+"_nextMonth";
		img.src=blankImageURL;
		img.width=16;
		img.height=16;
		this._nextMonthImage=img;
		
		component.appendChild(table);
	},
	_updateCells: function(date) {
		
		var time=date.getTime();
		
		var yearButtons=this._yearButtons;
		if (yearButtons) {
			var d=new Date(time);
			d.setMonth(0);
			d.setDate(1);
			yearButtons[1]._date=d;
	
			d=new Date(d.getTime());
			d.setFullYear(d.getFullYear()-1);
			yearButtons[0]._date=d;
	
			d=new Date(d.getTime());
			d.setFullYear(d.getFullYear()+2);
			yearButtons[2]._date=d;

			this._yearText.data=date.getFullYear();
		}
		
		var monthButtons=this._monthButtons;
		if (monthButtons) {
			if (this._popupMode) {
				var d=new Date(time);
				d.setDate(1);
				// Pas de title , y a rien a y mettre !				
				var monthName=this._locale.f_getMonthName(d.getMonth(), f_locale.LONG);
				this._monthText.data=f_core.UpperCaseFirstChar(monthName)+" "+date.getFullYear();
		
				d=new Date(d.getTime());
				d.setMonth(d.getMonth()-1);
				monthButtons[0]._date=d;
				monthButtons[0].title=f_core.UpperCaseFirstChar(this._locale.f_getMonthName(d.getMonth(), f_locale.LONG))+" "+d.getFullYear();
		
				d=new Date(d.getTime());
				d.setMonth(d.getMonth()+2);
				monthButtons[1]._date=d;
				monthButtons[1].title=f_core.UpperCaseFirstChar(this._locale.f_getMonthName(d.getMonth(), f_locale.LONG))+" "+d.getFullYear();
	
				
			} else {
				var d=new Date(time);
				for(var i=0;i<monthButtons.length;i++) {
					var but=monthButtons[i];
		
					d.setDate(1);
					d.setMonth(i);
		
					var t=d.getTime();
					but._date=new Date(t);
		
					if (f_calendarObject._COMPUTE_TOOLTIP) {
						but.title=f_calendarObject._LongFormatMonth(d, this._locale);
					}
				}
			}
		}
		
		var d=new Date(time);
		d.setDate(1);
		
		var diffDay=(7+d.getDay()-this._firstDayOfWeek) % 7;
		// diffDay est le nombre de jour du mois précedent présent dans le calendrier !

		d.setDate(-diffDay+1);
		
		var weekDayButtons=this._weekDayButtons;
		for(var i=0;i<weekDayButtons.length;i++) {
			var but=weekDayButtons[i];

			var t=d.getTime();
			but._date=new Date(t);

			d.setDate(d.getDate()+1);			
		}
		
		d=new Date(time);
		d.setDate(-diffDay+1);
		
		var t=d.getTime();
		d.setDate(1);
		d.setMonth(0);
		
		var weekIdx=Math.floor((t-d.getTime())/(1000*60*60*24*7))+1;
		
		d=new Date(time);
		d.setDate(-diffDay+1);
		var weekButtons=this._weekButtons;
		for(var i=0;i<weekButtons.length;i++) {
			var but=weekButtons[i];

			var t=d.getTime();
			but._date=new Date(t);

			if (f_calendarObject._COMPUTE_TOOLTIP) {
				but.title=f_calendarObject._LongFormatWeek(weekIdx);
			}
			d.setDate(d.getDate()+7);
			weekIdx=(weekIdx % 52)+1;
		}
		
		var d=new Date(time);
		d.setDate(-diffDay+1);
		
		var totalDay=42;
		if (!this._popupMode) {
			var d2=new Date(time);
			d2.setDate(1);
			d2.setMonth(date.getMonth()+1);
			d2.setDate(0);
			totalDay=(d2.getDate()+diffDay);
			
			if ((totalDay % 7)>0) {
				totalDay+=7-(totalDay % 7);
			}
		}
				
		var dayButtons=this._dayButtons;
		var itemDates=this._getItemDates();
		var day=0;
		var defaultStyleClass=this.className+"_day";
		for(;day<totalDay;day++) {
			var but=dayButtons[day];
			
			var t=d.getTime();
			but._date=new Date(t);
			
			but.firstChild.data=d.getDate();

			var tooltip=null;
			var styleClass=null;
			
			if (itemDates) {
				var item=f_calendarObject._FindDateItem(itemDates, t);
				
				if (item) {
					tooltip=item._label;
					
					if (item._styleClass) {
						styleClass=item._styleClass;
					}
				}
			}
			if (!styleClass) {
				styleClass=defaultStyleClass;
			}

			if (f_calendarObject._COMPUTE_TOOLTIP && !tooltip) {
				tooltip=this._longFormatDate(d);
			}
			
			if (tooltip) {
				but.title=tooltip;
			}
			
			if (but.className!=styleClass) {
				but.className=styleClass;
			}
			
			d.setDate(d.getDate()+1);	
		}

		var lastWeekDisplay="inherit";
		var lastWeekTabIndex=this.tabIndex;
		if (day<42) {
			for(;day<42;day++) {
				var but=dayButtons[day];
				but._date=undefined;			
				but.firstChild.data="";
				but.title="";
				but.tabIndex=-1;
			}
			lastWeekDisplay="hidden";
			lastWeekTabIndex=-1;
		}
		
		var lastWeekButton=this._weekButtons[5];
		if (lastWeekButton) {
			lastWeekButton.style.visibility=lastWeekDisplay;
			lastWeekButton.tabIndex=lastWeekTabIndex;
		}
	},
	_updateUnit: function(lastUnit, lastDate) {
		if (!this._unitButtons) {
			return;
		}	
		
		if (lastUnit===undefined) {
			lastUnit=f_calendarObject._DEFAULT_UNIT;
		}

		this._lastUnit=lastUnit;
		this._lastUnitDate=lastDate;
		
		var unitNames=f_resourceBundle.Get(f_calendarObject).f_get("UNIT_NAMES");
		
		var text=unitNames[lastUnit];
/*		if (lastUnit==f_calendarObject._DAYOFWEEK_UNIT && lastDate!==undefined) {
		
			text=this._locale.f_getDayName(lastDate.getDay(), f_locale.LONG);
		}
	*/	
		this._unitLabel.data=text;
	},
	_updateSelection: function(showDate) {
		var firstDate;
		var lastDate;
		
		var minTime=this._minTime;
		var minDate=this._minDate;
		
		var maxTime=this._maxTime;
		var maxDate=this._maxDate;
				
		var dates=this._dates;
		var dtimes;
		if (dates.length>0) {
			dtimes=new Array;

			for(var j=0;j<dates.length;j++) {
				var sd=dates[j];

				dtimes.push(sd[0].getTime());		
				dtimes.push(sd[1].getTime());
			}
		}
		
		var monthButtons=this._monthButtons;
		if (monthButtons && !this._popupMode) {
			for(var i=0;i<monthButtons.length;i++) {
				var but=monthButtons[i];
	
				var cl=this._className+"_month";
				
				var m=but._date.getMonth();
				
				var d1=but._date.getTime();
				var d2=new Date(d1);
				d2.setMonth(m+1); // Recherche fin du mois
				d2.setDate(0);
				d2=d2.getTime();
				
				if (m==showDate.getMonth()) {
					cl+="_cursor";
				}
				
				if (dtimes) {
					for(var j=0;j<dtimes.length;) {
						var t1=dtimes[j++];
						var t2=dtimes[j++];
						
						if (t2>=d1 && t1<=d2) {
							cl+="_selected";
							break;
						}
					}
				}
				
				but.tabIndex=this._tabIndex;
				
				if ((minDate && d2<minTime) || 
					(maxDate && d1>=maxTime)) {
					cl+="_disabled";
					but.tabIndex=-1;
				}
				
				if (but.className!=cl) {
					but.className=cl;
				}
			}
		}
		
		var disabledWeekDays=this._disabledWeekDays;
		var disabledDates=this._getDisabledDates();
		
		var dayButtons=this._dayButtons;
		var defaultClassName=this._className+"_cday";
		for(var i=0;i<dayButtons.length;i++) {
			var but=dayButtons[i];

			var cl=defaultClassName;
			
			var bd=but._date;
			if (bd===undefined) {
				cl+="_hidden";

			} else {	
				var d=bd.getTime();
				if (dtimes) {
					for(var j=0;j<dtimes.length;) {
						var t1=dtimes[j++];
						var t2=dtimes[j++];
						
						if (d>=t1 && d<=t2) {
							cl+="_selected";
							break;
						}
					}
				}
							
				if (showDate.getMonth()!=bd.getMonth()) {
					cl+="_outside";
				}
								
				if ((minDate && d<minTime) || 
					(maxDate && d>maxTime) || 
					(disabledWeekDays && ((1<<bd.getDay()) & disabledWeekDays)>0) ||
					(disabledDates && f_calendarObject._FindByDicho(disabledDates, d))) {
					cl+="_disabled";
					but.tabIndex=-1;
				}
			}
								
			var parent=but.parentNode;	
			if (parent.className!=cl) {
				parent.className=cl;
			}
		}
	},
	_select: function(jsEvent, dates, selectionDetail, canRefocus, nextShowDate) {
		var ds=new Array;
	
		var minDate=this._minDate;
		var maxDate=this._maxDate;
		var minTime=this._minTime;
		var maxTime=this._maxTime;
	
		for(var i=0;i<dates.length;) {
			var start=dates[i++];
			var end=dates[i++];

//			f_core.Debug(f_calendarObject, "select start="+start+" end="+end);	

			if (minDate) {
				if (minTime>start) {
					start=minDate;
				}
				if (minTime>end) {
					continue;
				}
			}
			
			if (maxDate) {
				if (maxTime<start) {
					continue;
				}
				if (maxTime<end) {
					end=maxDate;
				}
			}
		

			var vstart=start;
			var vend;
			for(;;) {
				vstart=this._searchValidDate(vstart, 1, end);
				
				if (!vstart) {
//					f_core.Debug(f_calendarObject, "Can find start for vstart='"+vstart+"'.");
					break;
				}
	
//				f_core.Debug(f_calendarObject, "First valid date="+vstart);
				
				vend=this._searchLastValidDate(vstart, 1, end);
				if (!vend) {
//					f_core.Debug(f_calendarObject, "Can not find end ! vstart='"+vstart+"' end='"+end+"'.");
					vend=vstart;
				}
	
//				f_core.Debug(f_calendarObject, "Select date vstart='"+vstart+"' vend='"+vend+"'.");
	
				// Il faut peut etre découpé !	
				ds.push([vstart, vend]);
				
				if (this._mode!=f_calendarObject.PERIODS_MODE) {
					break;
				}
				
				vstart=new Date(vend.getTime());
				vstart.setDate(vstart.getDate()+1);
			}

			if (ds.length>0 && this._mode!=f_calendarObject.PERIODS_MODE) {
				break;
			}
		}

		this._dates = ds;
		if (ds.length>0 && !nextShowDate) {
			nextShowDate=ds[0][0];
		}
		
		if (nextShowDate) {
			this._updateShowDate(nextShowDate, canRefocus);
		}

		f_core.Debug(f_calendarObject, "Fire selection for '"+ds+"' detail='"+selectionDetail+"'.");
		this.f_fireEvent(f_event.SELECTION, jsEvent, null, ds, null, selectionDetail);
	},
	_updateShowDate: function(showDate, canRefocus) {
		var refocus=false;
		
		var oldShowDate=this._showDate;
		this._showDate=showDate;
		
		if (oldShowDate.getMonth()!=showDate.getMonth() || 
				oldShowDate.getFullYear()!=showDate.getFullYear()) {		
				
			// Pas le meme mois, ni la meme année !	
			this._updateCells(showDate);
			
			refocus=canRefocus;
		}
		
		if (refocus) {
			// On recherche le focus de la date !
			
			var dayButtons=this._dayButtons;
			
			var t=showDate.getTime();
			for(var i=0;i<dayButtons.length;i++) {
				var but=dayButtons[i];
				var d=but._date;
				if (!d) {
					continue;
				}
				if (d.getTime()!=t) {
					continue;
				}
				
				f_calendarObject._Focus(but);
				break;
			}			
		}
		
		this._updateSelection(showDate);
		
		return true;
	},
	_isValidDate: function(date, t) {
		f_core.Assert(date instanceof Date, "Parameter is invalid: "+date);
		
		if (!t) {
			t=date.getTime();
		}
		
		if ((this._minDate && this._minTime>t) || 
			(this._maxDate && this._maxTime<t)) {
			return false;
		}
		
		var disabledWeekDays=this._disabledWeekDays;
		if (disabledWeekDays && ((1<<date.getDay()) & disabledWeekDays)>0) {
			return false;
		}
		
		var disabledDates=this._getDisabledDates();
		if (disabledDates) {
			if (f_calendarObject._FindByDicho(disabledDates, t)) {
				return false;
			}
		}
		
		return true;
	},
	_searchValidDate: function(date, increment, limit) {
		var limitMax;
		var limitMin;
		
		if (limit) {
			var l=limit.getTime();
			if (increment<0) {
				limitMin=l;
				
			} else {
				limitMax=l;
			}
		}
		// Le limit est ACCEPTE
	
		var t=date.getTime();
		if (this._isValidDate(date, t)) {
			if ((limitMin!==undefined && limitMin>t) || 
				(limitMax!==undefined && limitMax<t)) {
				return null;
			}
			
			return date;
		}
		
		date=new Date(t);		
		for(;;) {
			if ((limitMin!==undefined && limitMin>t) || 
				(limitMax!==undefined && limitMax<t)) {
				return null;
			}
			
			if (this._isValidDate(date, t)) {
				return date;
			}
	
			date.setDate(date.getDate()+increment);
			t=date.getTime();
		}		
	},
	_searchLastValidDate: function(date, increment, limit) {
		var limitTime=(limit)?limit.getTime():0;
		// Le limit est ACCEPTE
	
//		if (!this._isValidDate(date)) {
//			return null;
//		}

		var lastValidTime=date.getTime();
		if (limit && limitTime<lastValidTime) {
			return null;
		}
		
		date=new Date(lastValidTime);
		
		for(;;) {
			date.setDate(date.getDate()+increment);
			var t=date.getTime();

			if ((!limit || limitTime>=t) && this._isValidDate(date, t)) {
 				lastValidTime=t;
 				continue;
			}
			
			return new Date(lastValidTime);
		}		
	},
	_isValidPeriod: function(start, end) {
		if ((this._minDate && end<this._minTime) || 
			(this._maxDate && start>this._maxTime)) {
			return false;
		}
		
		return true;
	},
	_onDayClick: function(evt, dayButton, date) {
		if (!this._isValidDate(date)) {
			return;
		}	
		
		this._select(evt, [ date, date ], f_calendarObject.DAY_SELECTION_DETAIL, true, null);
		this._updateUnit(f_calendarObject._DAY_UNIT, date);
	},
	_onMonthClick: function(evt, monthButton, date) {
		var d;
		var next;
		
		switch(this._mode) {
		case f_calendarObject.DATE_MODE:
			// On reste sur le meme jour mais un mois different !
	
			var dates=this._dates;
			if (dates.length<1) {
				// On change le showDate !
				// Car il n'y a pas encore de selection, on bascule tout de suite !
				this._showDate=date;
				this._updateCells(date);
				this._updateSelection(date);
				return;
			}
			
			d=new Date(dates[0][0].getTime());
			d.setFullYear(date.getFullYear());
			d.setMonth(date.getMonth());
			if (d.getMonth()!=date.getMonth()) {
				// On est tombé sur un 31/30 non supporté par le mois, nous sommes au mois suivant !
				
				d.setDate(0); // On repasse au mois précedent !
			}

			break;
			
		case f_calendarObject.PERIOD_MODE:
		case f_calendarObject.PERIODS_MODE:
			// On selectionne tout le mois !
			
			d=new Date(date.getTime());
			next=new Date(date.getTime());
			next.setMonth(next.getMonth()+1);
			next.setDate(0);

			if (!this._isValidPeriod(d, next)) {
				return;
			}
			
			break;
			
		default:
			return;
		}

		if (next===undefined) {
			next=d;
		}
		
		this._select(evt, [d, next], f_calendarObject.MONTH_SELECTION_DETAIL);
		this._updateUnit(f_calendarObject._MONTH_UNIT, d);
	},
	_onYearClick: function(evt, yearButton, date) {
		var d;
		var next;
		switch(this._mode) {
		case f_calendarObject.DATE_MODE:
			return;
			
		case f_calendarObject.PERIOD_MODE:
		case f_calendarObject.PERIODS_MODE:
			d=date;
			next=new Date(date.getTime());
			next.setFullYear(next.getFullYear()+1);
			next.setMonth(0);
			next.setDate(0);

			break;
			
		default:
			return;
		}

		if (next===undefined) {
			next=d;
		}
		
		this._select(evt, [d, next], f_calendarObject.YEAR_SELECTION_DETAIL);
		this._updateUnit(f_calendarObject._YEAR_UNIT, d);
	},
	_onWeekClick: function(evt, weekButton, date) {
		
		f_core.Debug(f_calendarObject, "_onWeekClick: weekButton="+weekButton+" date="+date+" mode="+this._mode+" dates="+this._dates.length);

		var d;
		var next;
		
		switch(this._mode) {
		case f_calendarObject.DATE_MODE:
			// On reste sur le meme jour de la semaine, mais une semaine differente !
			if (this._dates.length<1) {
				return;
			}
			d=new Date(this._dates[0][0].getTime());

			var dt1=d.getDay(); 
			var dt2=date.getDay(); // Debut de la semaine 
			if (dt1<dt2) {
				dt1+=7;
			}
							
			d=new Date(date.getTime());
			// On se la joue incremental, car il peut y avoir des passages de "mois"
			for(;dt2<dt1;dt2++) {
				d.setDate(d.getDate()+1);
			}
			break;
			
		case f_calendarObject.PERIOD_MODE: 
		case f_calendarObject.PERIODS_MODE:
			var d=new Date(date.getTime());
			var fd=d.getDay()-this._firstDayOfWeek;
			if (fd<0) {
				fd+=6;
			}
			
			for(var i=0;i<fd;i++) {
				d.setDate(d.getDate()-1);
			}
			
			var next=new Date(d.getTime());
			next.setDate(next.getDate()+6);
			break;
			
		default:
			f_core.Error(f_calendarObject, "_onWeekClick: Unknown mode ? "+this._mode+" (period="+f_calendarObject.PERIOD_MODE+")");
			return;
		}		

		if (next===undefined) {
			next=d;
		}
	
		f_core.Debug(f_calendarObject, "_onWeekClick: select: "+d+" to "+next);
		
		this._select(evt, [d, next], f_calendarObject.WEEK_SELECTION_DETAIL);
		this._updateUnit(f_calendarObject._WEEK_UNIT, d);
	},
	_onWeekDayClick: function(evt, weekDayButton, date) {
		var l;
		
		switch(this._mode) {
		case f_calendarObject.DATE_MODE:
		case f_calendarObject.PERIOD_MODE:
			// On reste sur la meme semaine, mais on change le jour
			if (this._dates.length<1) {
				return;
			}
			var d=new Date(this._dates[0][0].getTime());

			var dt1=d.getDay(); 
			var dt2=date.getDay(); // Jour de la semaine a rechercher
				
			if (this._firstDayOfWeek) {
				if (dt2<this._firstDayOfWeek) {
					dt2+=7;
				}
				if (dt1<this._firstDayOfWeek) {
					dt1+=7;
				}
			}
		
			// On se la joue incremental, car il peut y avoir des passages de "mois"
			var delta=(dt1<dt2)?1:-1;
			for(;dt1!=dt2;dt1+=delta) {
				d.setDate(d.getDate()+delta);
			}
			
			var next=d;
			if (this._mode==f_calendarObject.PERIOD_MODE) {
				next=new Date(d.getTime());
				next.setDate(next.getDate()+6);
			}
			
			l=[d, next];
			break;
			
		case f_calendarObject.PERIODS_MODE:
			// On selectionne tous le memes jours de la semaine du meme mois
			var d=new Date(this._showDate.getTime());
			var mt=d.getMonth();
			d.setDate(1);
			
			var diff=date.getDay()-d.getDay();
			if (diff<0) {
				diff+=7;
			}

			d.setDate(d.getDate()+diff);
			
			var l=new Array;
			for(;d.getMonth()==mt;) {
				var dt=new Date(d.getTime());
				l.push(dt, dt);
				
				d.setDate(d.getDate()+7);
			}
			break;
			
		default:
			return;									
		}		
		
		this._select(evt, l, f_calendarObject.DAYOFWEEK_SELECTION_DETAIL);
		this._updateUnit(f_calendarObject._DAYOFWEEK_UNIT, l[0]);
	},
	_onUnitClick: function(evt, button, delta, lastUnit, canRefocus) {
		if (lastUnit===undefined) {
			lastUnit=this._lastUnit;
			if (lastUnit===undefined) {
				return;
			}
		} else {
			this._updateUnit(lastUnit);
		}
		
		var showDate=this._showDate;
		if (!showDate) {
			return;
		}

		var l;
		var d;
		var next;
		
		var selectionDetail=0;
		
		switch(lastUnit) {
		case f_calendarObject._WEEK_UNIT:
			selectionDetail=f_calendarObject.WEEK_SELECTION_DETAIL;
			
			d=new Date(showDate.getTime());
			d.setDate(d.getDate()+delta*7);
			
			switch(this._mode) {
			case f_calendarObject.PERIOD_MODE:
			case f_calendarObject.PERIODS_MODE:
				next=new Date(d.getTime());
				next.setDate(next.getDate()+6);

				if (!this._isValidPeriod(d, next)) {
					return;
				}

				break;								
			}

			break;
			
		case f_calendarObject._DAY_UNIT:
			selectionDetail=f_calendarObject.DAY_SELECTION_DETAIL;

			d=new Date(showDate.getTime());
			d.setDate(d.getDate()+delta);
			
			canRefocus=true;
			break;
			
		case f_calendarObject._DAYOFWEEK_UNIT:
			selectionDetail=f_calendarObject.DAYOFWEEK_SELECTION_DETAIL;

			switch(this._mode) {
			case f_calendarObject.DATE_MODE:
				d=new Date(showDate.getTime());
				d.setDate(d.getDate()+delta);
			
				break;
				
			case f_calendarObject.PERIOD_MODE:
				d=new Date(showDate.getTime());
				d.setDate(d.getDate()+delta);
				next=new Date(d.getTime());
				next.setDate(next.getDate()+6);
				
				break;

			case f_calendarObject.PERIODS_MODE:
				// On selectionne tous le memes jours de la semaine du meme mois
				d=new Date(showDate.getTime());
				d.setDate(d.getDate()+delta+(delta<0?7:0));
				next=new Date(d.getTime());
				var mt=d.getMonth();
				next.setDate(1);
				
				var diff=d.getDay()-next.getDay();
				if (diff<0) {
					diff+=7;
				}
	
				next.setDate(next.getDate()+diff);
				
				l=new Array;
				for(;next.getMonth()==mt;) {
					var dt=new Date(next.getTime());
					l.push(dt, dt);
					
					next.setDate(next.getDate()+7);
				}
				break;
			}
			break;
			
		case f_calendarObject._YEAR_UNIT:
			selectionDetail=f_calendarObject.YEAR_SELECTION_DETAIL;
			d=new Date(showDate.getTime());
			
			d.setFullYear(d.getFullYear()+delta);

			switch(this._mode) {
			case f_calendarObject.PERIOD_MODE:
			case f_calendarObject.PERIODS_MODE:
			
				// On verifie qu'au moins un element peut être selectionné !
				d.setMonth(0);
				d.setDate(1);					
				
				next=new Date(d.getTime());
				next.setFullYear(next.getFullYear()+1);
				next.setMonth(0);
				next.setDate(0);
				
				if (!this._isValidPeriod(d, next)) {
					return;
				}
				
				break;
			}
			break;
			
		case f_calendarObject._MONTH_UNIT:
			selectionDetail=f_calendarObject.MONTH_SELECTION_DETAIL;
			d=new Date(showDate.getTime());
			
			d.setMonth(d.getMonth()+delta);

			switch(this._mode) {
			case f_calendarObject.PERIOD_MODE:
			case f_calendarObject.PERIODS_MODE:
				d.setDate(1);
				
				next=new Date(d.getTime());
				next.setMonth(next.getMonth()+1);
				next.setDate(0);

				if (!this._isValidPeriod(d, next)) {
					return;
				}
			}
							
			break;
		}
	
		if (this._popupMode) {
			// showDate;
			this._updateShowDate(d, canRefocus);
			return;
		}
	
		if (next===undefined) {
			next=d;
		}
		
		if (!l) {
			if (!d) {
				l=[];
			} else {
				l=[d, next];
			}
		}
		
		this._select(evt, l, selectionDetail, canRefocus, d);
	},
	/**
	 * @method public
	 * @return number
	 */
	f_getMode: function() {
		return this._mode;
	},
	/**
	 * @method hidden
	 */
	f_appendDateItem: function(date, label, disabled, styleClass, clientDatas) {
		var item=new Object;
		item._label=label;
		item._value=date;
		item._dates=f_calendarObject._ParseDates(date);
		item._styleClass=styleClass;
		item._disabled=disabled;
		
		if (arguments.length>4) {
			var cd=new Object;
			
			for(var i=5;i+1<arguments.length;) {
				cd[arguments[i++]]==arguments[i++];
			}
			
			this.f_setItemClientData(item, cd);
		}
		
		this.f_addItem(this, item);

		f_core.Debug(f_calendarObject, "Add date item '"+item._dates+"' label="+label);

		this._disabledDates=undefined;
		this._itemDates=undefined;
	},
	fa_updateItemStyle: function() {
		this._disabledDates=undefined;
		this._itemDates=undefined;
	},
	fa_destroyItems: function() {
		this._disabledDates=undefined;
		this._itemDates=undefined;
	},
	_getDisabledDates: function() {
		var disabledDates=this._disabledDates;
		if (disabledDates===false) {
			return null;
		}
		if (disabledDates!==undefined) {
			return disabledDates;
		}
		
		this._prepareItems();
		
		return this._getDisabledDates();
	},
	_getItemDates: function() {
		var itemDates=this._itemDates;
		if (itemDates===false) {
			return null;
		}
		if (itemDates!==undefined) {
			return itemDates;
		}
		
		this._prepareItems();
		
		return this._getItemDates();
	},
	_prepareItems: function() {	
		this._disabledDates=false;
		this._itemDates=false;
		
		var items=this._items;
		if (!items || items.length<1) {
			return;
		}
		
		var disabledDates;
		var itemDates;
		
		for(var i=0;i<items.length;i++) {
			var item=items[i];
			
			if (item._disabled) {
				if (!disabledDates) {
					disabledDates=new Array;
				}
			
				var ds=item._dates;
				
				for(var j=0;j<ds.length;j++) {
					disabledDates.push(ds[j].getTime());
				}
			}
			
			if (item._styleClass || item._label) {
				if (!itemDates) {
					itemDates=new Array;
				}
			
				var ds=item._dates;
				
				for(var j=0;j<ds.length;j++) {
					itemDates.push({ 
						_time: ds[j].getTime(),
						_item: item
					});
				}				
			}
		}
		
		if (disabledDates) {
			disabledDates.sort(function(x, y) {
				return x-y;
			});
			
			this._disabledDates=disabledDates;
		}
	
		if (itemDates) {
			itemDates.sort(function(x, y) {
				return x._time-y._time;
			});
			
			this._itemDates=itemDates;
		}
	},
	/** 
	 * @method hidden
	 */
	f_parseDate: function(text, format) {
		return f_dateFormat.ParseDate(text, format, this._twoDigitYearStart, this._locale);
	},
	/** 
	 * @method hidden
	 */
	f_formatDate: function(date, format) {
		return f_dateFormat.FormatDate(date, format, this._twoDigitYearStart, this._locale);
	},
	/** 
	 * @method protected
	 */
	f_getEventLocked2: function(showAlert, mask) {

		if (this._popupMode) {
			if (!mask) {
				mask=0;
			}	
			
			mask|=f_event.POPUP_LOCK;
		}

		return this.f_getEventLocked(showAlert, mask);
	},
	_longFormatDate: function(date) {
		var format=this._locale.f_getDateFormat(f_locale.LONG);
		if (!format) {
			return String(date);
		}
		
		return f_core.UpperCaseFirstChar(this.f_formatDate(date, format));
	},
	f_refreshComponent: function() {		
		this._updateCells(this._showDate);
		
		this._updateUnit(f_calendarObject._DEFAULT_UNIT);
		
		this._updateSelection(this._showDate);
	},
	/**
	 * @method private
	 */
	_callServer: function(minDate, maxDate) {
		if (!window.f_httpRequest) {
			f_core.Error(f_calendarObject, "Class f_httpRequest is not defined !");
			return;
		}
		
		/*
		var w=this.style.width;
		if (!w || w=="auto") {
			this._oldWidth="auto";
			
			w=this.offsetWidth;
			if (w<f_combo._MIN_WIDTH) {
				w=f_combo._MIN_WIDTH;
			}
			this.style.width=w+"px";
		}
		this.className=this._className+"_loading";
		*/
		
	
		var params=new Object;
		params.componentId=this.id;
		params.minDate=minDate.getTime();
		params.maxDate=maxDate.getTime();

		var url=f_env.GetViewURI();	
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var calendarObject=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 		 	var waiting=calendarObject._waiting;
	 			if (!waiting) {
		 			
		 			calendarObject._waiting=waiting;
		 		}
		 		
		 		if (waiting) {
					// pas de f_core.SetTextNode  : ca marche pas !
		 			waiting.innerHTML=f_core.EncodeHtml(f_waiting.GetLoadingMessage());
		 			waiting.disabled=true;
		 		}
	 		},	 		
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_calendarObject, "Bad status: "+request.f_getStatus());
	 			
				if (calendarObject.f_processNextCommand()) {
					return;
				}
	 		
				calendarObject._loading=false;		
				
				var waiting=calendarObject._waiting;
				if (waiting) {
					calendarObject._waiting=undefined;
					calendarObject.removeChild(waiting);
				}
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 			var waiting=calendarObject._waiting;
				if (waiting) {
					// pas de f_core.SetTextNode  : ca marche pas !
					waiting.innerHTML=f_core.EncodeHtml(f_waiting.GetReceivingMessage());
					waiting.disabled=true;
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (calendarObject.f_processNextCommand()) {
					return;
				}
	 			
	 			var waiting=calendarObject._waiting;
				calendarObject._waiting=undefined;
				
				try {
					if (waiting) {
						calendarObject.removeChild(waiting);
					}

					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_calendarObject, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
	
					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
						f_core.Error(f_calendarObject, "Unsupported content type: "+responseContentType);
						return;
					}

					var ret=request.f_getResponse();
					try {
						eval(ret);
						
					} catch (x) {
						f_core.Error(f_calendarObject, "Can not eval response '"+ret+"'.", x);
					}

				} finally {
					calendarObject._loading=undefined;	
				}
				
				/* A voir ! @TODO
				var event=new f_event(combo, f_event.CHANGE);
				try {
					combo.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
				*/
	 		}
		});

		this._loading=true;
		request.f_setRequestHeader("X-Camelia", "dates.request");
		request.f_doFormRequest(params);
	}
}
 
var f_calendarObject=new f_class("f_calendarObject", null, __static, __prototype, f_object, fa_eventTarget, fa_items, fa_selectionProvider, fa_commands);
