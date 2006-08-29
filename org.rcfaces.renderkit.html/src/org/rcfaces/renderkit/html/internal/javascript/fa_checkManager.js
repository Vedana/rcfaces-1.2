/*
 * $Id$
 */
 
/**
 * Aspect CheckManager
 *
 * @aspect public fa_checkManager extends fa_selectionManager
 * @author Olivier Oeuillot
 * @version $Revision$
 */

var __prototype = {
	fa_checkManager: function() {
		var v_checkCardinality=f_core.GetAttribute(this, "v:checkCardinality");
		if (!v_checkCardinality) {
			return;
		}
		
		if (f_core.GetAttribute(this, "v:clientCheckFullState")) {
			this._checkFullState=new Array;
		}
		
		this._checkCardinality=parseInt(v_checkCardinality);
		this._checkable=true;
		
		this._checkedElementValues=new Array;
		this._uncheckedElementValues=new Array;
		this._currentChecks=new Array;	
	},

	f_finalize: function() {
		//this._checkedElementValues=undefined; // string[] or number[]
		//this._uncheckedElementValues=undefined; // string[] or number[]
		this._currentChecks=undefined; // any[]
		// this._clearAllCheckedElements=undefined; // boolean

		//this._checkFullState=undefined; // string[] or number[]
		
		//this._checkCardinality=undefined; // number
		//this._checkable=undefined; // boolean
	},

	f_serialize: {
		before: function() {
			if (!this._checkable) {
				return;
			}	
			
			var checkedElementValues=this._checkedElementValues;
			if (checkedElementValues.length) {
				this.f_setProperty(f_prop.CHECKED_ITEMS, checkedElementValues, true);
			}
			
			if (this._clearAllCheckedElements) {
				this.f_setProperty(f_prop.UNCHECKED_ITEMS, f_prop.ALL_VALUE);

			} else {
				var uncheckedElementValues=this._uncheckedElementValues;
				if (uncheckedElementValues.length) {
					this.f_setProperty(f_prop.UNCHECKED_ITEMS, uncheckedElementValues, true);
				}
			}
		}
	},
	
	_setCheckStates: function(checkFullState) {
		this._checkFullState=checkFullState;
	},
	_checkElement: function(element, value, show) {
		if (this._isElementChecked(element)) {
			return;
		}
		
		this._setElementChecked(element, true);
		this._updateElementStyle(element);
		
		this._currentChecks.push(element);
		
		if (value===undefined) {
			value=this._getElementValue(element);
		}
		
		if (!this._uncheckedElementValues.f_removeElement(value)) {
			this._checkedElementValues.f_addElement(value);
		}
		
		if (show) {
			this._showElement(element);
		}
	},
	_uncheckElement: function(element, value) {
		if (!this._isElementChecked(element)) {
			return false;
		}

		this._setElementChecked(element, false);
		this._updateElementStyle(element);
		
		this._currentChecks.f_removeElement(element);
		
		if (value===undefined) {
			value=this._getElementValue(element);
		}
		
		if (this._checkedElementValues.f_removeElement(value)) {
			return true;
		}
	
		if (this._clearAllCheckedElements) {
			return false;
		}
			
		return this._uncheckedElementValues.f_addElement(value);
	},
	_uncheckAllElements: function() {		
		var currentChecks=this._currentChecks;
		if (currentChecks.length>0) {
			this._currentChecks=new Array;

			for(var i=0;i<currentChecks.length;i++) {
				var element=currentChecks[i];
				
				this._setElementChecked(element, false);
				this._updateElementStyle(element);
			}
		}
		
		this._clearAllCheckedElements=true;
		this._uncheckedElementValues=new Array;
		this._checkedElementValues=new Array;
	},

	_updateElementCheck: function(element, checked) {
		var value=this._getElementValue(element);
	
		checked=this._isElementValueChecked(value, checked);
		this._setElementChecked(element, checked);
		
		if (!checked) {
			return false;
		}
		
		this._currentChecks.push(element);
		
		return true;
	},
	
	_performElementCheck: function(element, show, evt, checked) {
		var cardinality=this._checkCardinality;
		if (!cardinality) {
			return false;
		}
		
		f_core.Debug("fa_checkManager", "performElementCheck '"+this._getElementValue(element)+"' disabled="+this._isElementDisabled(element)+" cardinality="+cardinality);
	
		if (this._isElementDisabled(element)) {
			return false;
		}
		
		var elementChecked=this._isElementChecked(element);
		var elementValue=this._getElementValue(element);
		
		switch(cardinality) {
		case fa_cardinality.ONE_CARDINALITY:
			if (elementChecked) {
				return false;
			}
			
			// On continue ....
			
		case fa_cardinality.OPTIONAL_CARDINALITY:			
			// On décoche tout: 1 seul doit rester selectionner 
			this._uncheckAllElements();
				
			if (checked) {
				this._checkElement(element, elementValue, show);
			}
			break;
			
		case fa_cardinality.ONEMANY_CARDINALITY:
			if (elementChecked) {
				if (this._currentChecks.length<2) {
					// Un seul décoché: on arrete tout !
					return false;
				}
			}

			// On continue ...

		case fa_cardinality.ZEROMANY_CARDINALITY:
			if (elementChecked) {
				this._uncheckElement(element, elementValue);
				break;
			}

			this._checkElement(element, elementValue, show);
			break;
		}
	
		var detail=0;
		if (checked) {
			detail|=1;
		}
	
		this._fireCheckChangedEvent(evt, detail, element, elementValue);
		
		return true;
	},
	_fireCheckChangedEvent: function(evt, detail, element, elementValue) {

		return this.f_fireEvent(f_event.CHECK, evt, element, elementValue, null, detail);
	},
	_isElementValueChecked: function(value, defaultValue) {
		var checked=defaultValue;
		
		var checkFullState=this._checkFullState;
		if (!checked && checkFullState) {
			checked=checkFullState.f_contains(value);
		}
	
		if (checked && !this._clearAllCheckedElements) {
			// On recherche s'il n'a pas été décoché !
			if (this._uncheckedElementValues.f_contains(value)) {
				// Il a été décoché !
				return false;
			}
		
			// Il n'a pas été décoché !
			return true;
		}
		
		// Tout a été décoché, ou c'etait pas coché à la création du composant!
		
		return this._checkedElementValues.f_contains(value);
	},
	/**
	 * @method public
	 * @return Object[] An array of checked values.
	 */
	f_getCheckedValues: function() {
		var ret=new Array;
		if (!this._checkable) {
			return ret;
		}

		if (this._checkFullState) {
			if (!this._clearAllCheckedElements) {
				var checkFullState=this._checkFullState;
				if (checkFullState && checkFullState.length>0) {
					ret.push.apply(ret, checkFullState);
				}
			}	
			
			var checkedElementValues=this._checkedElementValues;
			if (checkedElementValues.length>0) {
				ret.f_addElements.apply(ret, checkedElementValues);
			}

			var uncheckedElementValues=this._uncheckedElementValues;
			if (uncheckedElementValues.length>0) {
				ret.f_removeElements.apply(ret, uncheckedElementValues);
			}
			
			return ret;
		}
		
		// Nous ne sommes pas en fullstate, on ne renvoit que ce que l'on voit !
		var currentChecks=this._currentChecks;
		for(var i=0;i<currentChecks.length;i++) {
			var element=currentChecks[i];
			
			var value=this._getElementValue(element);
			if (value===undefined) {
				continue;
			}

			ret.push(value);
		}
		
		return ret;
	},

	_isElementChecked: f_class.ABSTRACT,
	
	_setElementChecked: f_class.ABSTRACT
	
}



var fa_checkManager=new f_aspect("fa_checkManager", null, __prototype, fa_selectionManager);
