/*
 * $Id$
 */

/**
 * 
 * @aspect public fa_calendarPopup extends fa_itemsWrapper, fa_selectionProvider
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics={
	/** 
	 * @field private static final String 
	 */
	_CALENDAR_KEY_SCOPE_ID: "#calendarPopup",
	
	/** 
	 * @field private static final boolean 
	 */
	_CACHE_IE_POPUP: false,

	/**
	 * @method private static
	 */
	_ClosePopup: function(dateChooser, jsEvt) {	
		var calendar=dateChooser.f_getCalendarObject();

		f_core.Debug(fa_calendarPopup, "_ClosePopup: Close the popup of dateChooser='"+dateChooser.id+"'.");

		if (!dateChooser._popupOpened) {
			return;
		}
		dateChooser._popupOpened=undefined;

		f_popup.UnregisterWindowClick(dateChooser);		
					
		f_key.ExitScope(fa_calendarPopup._CALENDAR_KEY_SCOPE_ID);
		
		var popup=dateChooser._popupCalendar;
		
		if (!popup) {
			return; 
		}
		
		if (!dateChooser._iePopup) {
			popup.style.visibility="hidden";
			return;
		}	
		
		f_popup.Ie_closePopup(popup);
		dateChooser._popupCalendar=undefined;
		
		if (!fa_calendarPopup._CACHE_IE_POPUP) {
			calendar.f_destroyComponent();
		}
	},

	/**
	 * @method private static
	 * @return void
	 */
	_OpenPopup: function(dateChooser, position, offsetX, offsetY, offsetWidth, autoSelect) {
		var calendar=dateChooser.f_getCalendarObject();
		
		f_core.Debug(fa_calendarPopup, "_OpenPopup: Open popup for date chooser '"+dateChooser.id+"'. (popupOpened='"+dateChooser._popupOpened+"')");
		
		if (dateChooser._popupOpened) {
			return;
		}
		
		var popup=dateChooser._popupCalendar;
		if (!popup) {
			var body=null;
			
			if (dateChooser._iePopup) {
				var doc=dateChooser.ownerDocument;
				
				if (fa_calendarPopup._CACHE_IE_POPUP) {
					popup=f_popup.Ie_GetCurrentPopupByKey(doc, dateChooser.id);
				}
				
				if (!popup) {
					if (fa_calendarPopup._CACHE_IE_POPUP) {				
						popup=f_popup.Ie_GetPopup(doc, dateChooser.id, function() {
							calendar.f_destroyComponent();		
						});
					} else {
						popup=f_popup.Ie_GetPopup(doc);
					}
				
					var pdoc=popup.document;
					
					pdoc.body.innerHTML="";
					
					body=pdoc.createElement("div");
					body.className="f_dateChooser_popup";
					body.style.visibility="inherit";
	
					pdoc.body.appendChild(body);
				}
								
			} else {
				popup=dateChooser.ownerDocument.createElement("div");
				popup.className="f_dateChooser_popup";
	
				popup.onclick=f_core.CancelJsEventHandlerTrue;
				popup.onmousedown=f_core.CancelJsEventHandlerTrue;
	
				body=popup;
				
				var parent=dateChooser;
				for(;;parent=parent.parentNode) {
					var tagName=parent.tagName.toLowerCase();
					
					if (tagName=="input" || tagName=="span") {
						continue;
					}
					break;
				}
				
				parent.appendChild(popup);
			}
			dateChooser._popupCalendar=popup;
			
			if (body) {
				body._popupObject=true;

				calendar.f_constructComponent(body);
				f_core.Debug(fa_calendarPopup, "_OpenPopup: Create new popup for dateChooser='"+dateChooser.id+"'.");
				
				if (this._initialSelection) {
					calendar.f_setSelection(this._initialSelection);
					this._initialSelection=undefined;
				}
				
			} else {
				calendar.f_refreshComponent();
			}
		}

		if (f_popup.RegisterWindowClick({
				/**
				 * @method public
				 */
				exit: dateChooser._clickOutside,
				/**
				 * @method public
				 */
				keyDown: function(evt) {
					f_core.Debug(fa_calendarPopup, "_OpenPopup.keyDown: popup keyDown: "+evt.keyCode);
					/*if (menu._filterKey("down", evt)===true) {
						return true;
					}
				
					switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return true;
					}
					
					return fa_menuCore.OnKeyDown(menu, evt);
					*/
					return true;
				},
				/**
				 * @method public
				 */
				keyUp: function(evt) {
					f_core.Debug(fa_calendarPopup, "_OpenPopup.keyUp: popup keyUp: "+evt.keyCode);
					/*return menu._filterKey("up", evt);*/
					return true;
				},
				/**
				 * @method public
				 */
				keyPress: function(evt) {
					f_core.Debug(fa_calendarPopup, "_OpenPopup.keyPress: popup keyPress: "+evt.keyCode);
					/*switch(evt.keyCode) {
					case f_key.VK_RETURN:
				 	case f_key.VK_ENTER:
				 		return fa_menuCore.OnKeyDown(menu, evt);
					}
					*/
					return true;
				}
			}, dateChooser, popup)==false) {
			
			f_core.Debug(fa_calendarPopup, "_OpenPopup: Register refused to open the popup of dateChooser='"+dateChooser.id+"'.");
			return;
		}
		
		f_core.Debug(fa_calendarPopup, "_OpenPopup: Open popup "+popup+" of dateChooser='"+dateChooser.id+"'.");
		if (popup) {
			f_key.EnterScope(fa_calendarPopup._CALENDAR_KEY_SCOPE_ID);

			if (dateChooser._iePopup) {
				f_popup.Ie_openPopup(popup, {
					component: dateChooser, 
					position: f_popup.BOTTOM_LEFT_COMPONENT });
			
			} else {
				var p1=f_core.GetAbsolutePosition(position);
				var parentPos=f_core.GetAbsolutePosition(popup.offsetParent);
			
				f_core.Debug(fa_calendarPopup, "_OpenPopup: Popup absolute pos x="+p1.x+" y="+p1.y+" offsetX="+offsetX+" offsetY="+offsetY+" parentX="+parentPos.x+" parentY="+parentPos.y);
			
				var x=p1.x+offsetX-parentPos.x;
				var y=p1.y+offsetY-parentPos.y;

				x+=0; // Les bordures ....
				y+=3;
			
				var pos={ x: x, y: y };
				
				f_core.ComputePopupPosition(popup, pos);

				f_core.Debug(fa_calendarPopup, "_OpenPopup: Computed pos x="+p1.x+" y="+p1.y+" offsetX="+offsetX+" offsetY="+offsetY);
					
				popup.style.left=pos.x+"px";
				popup.style.top=pos.y+"px";
			
				if (offsetWidth) {
					popup.style.width=offsetWidth+"px";
					
				} else if (offsetWidth!==false) {
					popup.style.width="auto";
				}
			
				popup.style.visibility="inherit";
			}			
		}
	
		dateChooser._popupOpened=true;
	},

	/**
	 * @method private static
	 */
	_DateSelectedEvent: function(evt) {
		if (evt.f_getDetail()!=f_calendarObject.DAY_SELECTION_DETAIL) {
			return true;
		}
		
		var calendar=evt.f_getComponent();
		f_core.Assert(typeof(calendar)=="object", "fa_calendarPopup._DateSelectedEvent: Component is not a calendarObject object '"+calendar+"'.");
		
		var dateChooser=calendar.f_getTargetComponent();
		f_core.Assert(typeof(dateChooser)=="object", "fa_calendarPopup._DateSelectedEvent: TargetComponent is not a dateChooser object '"+dateChooser+"'.");
		
		var value=evt.f_getValue();

		if (value && value.length) {
			value=value[0];
			
			if (value && value.length) {
				value=value[0];
			}
		}
		
		var jsEvt=evt.f_getJsEvent();
		
		if (dateChooser._onDateSelected(value, jsEvt)===false) {
			return false;
		}
		
		fa_calendarPopup._ClosePopup(dateChooser, jsEvt);
		
		var forComponent=dateChooser._forComponent;
		if (forComponent) {
			var component=dateChooser.f_findComponent(forComponent);
			
			if (component) {
				try {
					fa_calendarPopup._SetDateToComponent(component, value, dateChooser._forValueFormat, calendar);
					
				} catch (x) {
					f_core.Error(fa_calendarPopup, "_DateSelectedEvent: Set date of component '"+component.id+"' throws exception.", x);
				}
				
			} else {
				f_core.Info(fa_calendarPopup, "_DateSelectedEvent: Can not find componentId '"+forComponent+"' to set date !");
			}
		}
		
		return true;
	},

	/**
	 * @method private static
	 */
	_SetDateToComponent: function(component, date, format, calendarObject) {
		if (component.f_setDate) {
			f_core.Debug(fa_calendarPopup, "_SetDateToComponent: call f_setDate of component '"+component.id+"' with date '"+date+"' and format '"+format+"'.");
			component.f_setDate(date);
			return;
		}

		if (component.f_setValue) {
			f_core.Debug(fa_calendarPopup, "_SetDateToComponent: call f_setValue of component '"+component.id+"' with date '"+date+"' and format '"+format+"'.");
			if (component.f_setValue(date)!==false) {
				return;
			}
		}
		
		var value=calendarObject.f_formatDate(date, format);
		
		if (component.f_setText) {
			f_core.Debug(fa_calendarPopup, "_SetDateToComponent: call f_setText of component '"+component.id+"' with value '"+value+"' and format '"+format+"'.");
			component.f_setText(value);
			return;
		}
		
		if (component.tagName.toLowerCase()=="input") {
			f_core.Debug(fa_calendarPopup, "_SetDateToComponent: set field 'value' of component '"+component.id+"' with value '"+value+"' and format '"+format+"'.");
			component.value=value;
			return;
		}

		f_core.Debug(fa_calendarPopup, "_SetDateToComponent: set field 'value' of component '"+component.id+"' with value '"+value+"' and format '"+format+"'.");
		f_core.SetTextNode(component, value);
	},

	/**
	 * @method private static
	 * @return Date
	 */
	_GetDateFromComponent: function(component, format, calendarObject) {
		if (component.f_getDate) {
			f_core.Debug(fa_calendarPopup, "_GetDateToComponent: call f_getDate of component '"+component.id+"'.");
			return component.f_getDate();
		}
		
		var value;
		if (component.f_getText) {
			f_core.Debug(fa_calendarPopup, "_GetDateToComponent: call f_getText of component '"+component.id+"'.");
			value=component.f_getText();

		} else if (component.tagName.toLowerCase()=="input") {
			f_core.Debug(fa_calendarPopup, "_GetDateToComponent: get field 'value' of component '"+component.id+"'.");
			value=component.value;

		} else {
			f_core.Debug(fa_calendarPopup, "_GetDateToComponent: Invalid type of component id='"+component.id+"' tagName='"+component.tagName+"'.");
			return null;		
		}

		if (!value) {
			return null;
		}

		return calendarObject.f_parseDate(value, format);
	}
}

var __members={

	fa_calendarPopup: function() {
		var calendar=f_calendarObject.CreateCalendarFromComponent(this,
			f_calendarObject.MONTH_CURSOR_LAYOUT | 
			f_calendarObject.DAY_LIST_LAYOUT |
			f_calendarObject.HOME_DATE_LAYOUT);
		this._calendar=calendar;
		
		calendar.f_insertEventListenerFirst(f_event.SELECTION, fa_calendarPopup._DateSelectedEvent);
		calendar.f_enablePopupMode();
		
		this._iePopup=f_popup.Ie_enablePopup();
	},		

	f_finalize: {
		after: function() {
			this._iePopup=undefined;
		
			var calendar=this._calendar;
			if (calendar) {
				this._calendar=undefined;
				f_classLoader.Destroy(calendar);
			}
				
			var popup=this._popupCalendar;
			if (popup) {
				this._popupCalendar=undefined;
				
				popup._popupParent=undefined;
				if (popup.nodeType) {
					popup.onclick=null;
					popup.onmousedown=null;
				}
							
				f_core.VerifyProperties(popup);
			}
		}
	},
	/**
	 * @method public
	 * @param f_event event
	 * @return boolean
	 */
	f_openCalendarPopup: function(event) {
		f_core.Debug(fa_calendarPopup, "f_openCalendarPopup: "+event+" detail="+event.f_getDetail());
		
		// Ouverture du calendrier !
		var forComponent=this._forComponent;
		if (forComponent) {
			var component=this.f_findComponent(forComponent);
			
			if (component) {
				try {
					var forValueFormat=this._forValueFormat;					
				
					var date=fa_calendarPopup._GetDateFromComponent(component, forValueFormat, this._calendar);
					
					if (date) {
						this.f_setSelection(date);
					}
					
				} catch (x) {
					f_core.Debug(fa_calendarPopup, "f_openCalendarPopup: Get date of component '"+component.id+"' throws exception.", x);
					this.f_setSelection(date);
				}
				
			} else {
				f_core.Info(fa_calendarPopup, "f_openCalendarPopup: Can not find componentId '"+forComponent+"' to get date !");
			}
		}
		
		if (this.f_fireEvent(f_event.MENU, event.f_getJsEvent())===false) {
			return false;
		}
		
		var offsetX=0;
		var offsetY=this.offsetHeight;
		
		fa_calendarPopup._OpenPopup(this, this, 0, this.offsetHeight, false, false);
		
		return false;
	},
	/**
	 * @method hidden
	 */
	f_appendDateItem: function(date, label, disabled, styleClass) {
		var calendar=this._calendar;
		calendar.f_appendDateItem.apply(calendar, arguments);
	},
	/**
	 * @method public
	 * @return f_calendarObject
	 */
	f_getCalendarObject: function() {
		return this._calendar;
	},
	_clickOutside: function(jsEvt) {
		f_core.Debug(fa_calendarPopup, "_clickOutside: popup click outside");
		
		fa_calendarPopup._ClosePopup(this, jsEvt);
		return false;
	},
	/**
	 * @method public
	 * @return Date
	 */
	f_getSelection: function() {
		var calendar=this._calendar;
		if (!calendar) {
			return this._initialSelection;
		}
		
		var selection=calendar.f_getSelection();
		if (!selection || !selection.length) {
			return null;
		}
		
		return selection[0][0];
	},
	/**
	 * @method public
	 * @param Date selection
	 * @return void
	 */
	f_setSelection: function(selection) {
		var calendar=this._calendar;
		if (!calendar) {
			this._initialSelection=selection;
			return;
		}
		
		calendar.f_setSelection(selection);
	}
}
 
new f_aspect("fa_calendarPopup", __statics, __members, fa_itemsWrapper, fa_selectionProvider);
