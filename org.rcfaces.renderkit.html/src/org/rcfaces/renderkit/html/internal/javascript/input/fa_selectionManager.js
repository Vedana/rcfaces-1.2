/*
 * $Id$
 */
 
/**
 * Aspect SelectionManager
 *
 * @aspect public fa_selectionManager extends fa_itemsManager, fa_selectionProvider
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {

	/** 
	 * @field hidden static final number 
	 */
	EXCLUSIVE_SELECTION: 1,

	/** 
	 * @field hidden static final number 
	 */
	APPEND_SELECTION: 2,

	/** 
	 * @field hidden static final number 
	 */
	RANGE_SELECTION: 4,

	/** 
	 * @field hidden static final number 
	 */
	ACTIVATE_SELECTION: 8,

	/** 
	 * @field hidden static final number 
	 */
	STARTRANGE_SELECTION: 16,

	
	/**
	 * @method hidden static
	 */
	ComputeMouseSelection: function(evt) {
		var selection=0;
		
		if (f_core.IsAppendRangeMode(evt)) {
			selection|=fa_selectionManager.RANGE_SELECTION;
		}
			
		if (f_core.IsAppendMode(evt)) {
			selection|=fa_selectionManager.APPEND_SELECTION;
		}
	
		if (!selection) {
			selection|=fa_selectionManager.EXCLUSIVE_SELECTION;
		}

		selection |= fa_selectionManager.ACTIVATE_SELECTION;
		
		if (!(selection & fa_selectionManager.RANGE_SELECTION)) {
			selection |= fa_selectionManager.STARTRANGE_SELECTION;		
		}
		
		return selection;
	},

	
	/**
	 * @method hidden static
	 */
	ComputeKeySelection: function(evt) {
		var keySelection=0;

		if (f_core.IsAppendRangeMode(evt)) {
			keySelection |= fa_selectionManager.RANGE_SELECTION;
			//keySelection |= fa_selectionManager.STARTRANGE_SELECTION;

		} else if (!f_core.IsAppendMode(evt)) {
			keySelection |= fa_selectionManager.EXCLUSIVE_SELECTION;
			keySelection |= fa_selectionManager.STARTRANGE_SELECTION;
		
		} else {
			// Nous sommes en mode CONTROL
		}
		
		var code=evt.keyCode;
		switch(code) {
		case f_key.VK_RETURN:
		case f_key.VK_ENTER:
			keySelection |= fa_selectionManager.ACTIVATE_SELECTION;

		case f_key.VK_SPACE:
			if (!(keySelection & fa_selectionManager.RANGE_SELECTION)) {
				keySelection |= fa_selectionManager.STARTRANGE_SELECTION;
			}
			if (evt.ctrlKey) {
				keySelection|=fa_selectionManager.APPEND_SELECTION;
			}
			break;
		}
		
		return keySelection;
	},
	/**
	 * @hidden
	 */
	SetSelectionCardinality: function(object, cardinality, selectionFullState) {
		object._selectionCardinality=cardinality;
		object._selectionFullState=(selectionFullState)?(new Array):null;
	}	
}
var __prototype = {
	fa_selectionManager: function() {
		if (this._selectionCardinality===undefined) {
			var v_selectionCardinality=f_core.GetNumberAttribute(this, "v:selectionCardinality", undefined);
	
			if (v_selectionCardinality===undefined) {
				return;
			}
			this._selectionCardinality=v_selectionCardinality;
							
			if (f_core.GetAttribute(this, "v:clientSelectionFullState")) {
				this._selectionFullState=new Array;
			}
		}
		
		this._selectable=true;
		
		this._selectedElementValues=new Array;
		this._deselectedElementValues=new Array;
		this._currentSelection=new Array;
	},
	f_finalize: function() {
		this._currentSelection=undefined; // HtmlElement[]
		this._lastSelectedElement=undefined; // HtmlElement

		// this._selectedElementValues=undefined; // string[] or number[]
		// this._deselectedElementValues=undefined; // string[] or number[]
		// this._clearAllSelectedElements=undefined; // boolean

		// this._selectionFullState=undefined; // string[] or number[]

		//	this._selectable=undefined;  // boolean
		//	this._selectionCardinality=undefined; // boolean
		//  this._clientSelectionFullState=undefined // boolean
	},
	f_serialize: {
		before: function() {
			if (!this._selectable) {
				return;
			}
			
			var selectedElementValues=this._selectedElementValues;
			if (selectedElementValues.length) {
				this.f_setProperty(f_prop.SELECTED_ITEMS, selectedElementValues, true);
			}
			
			if (this._clearAllSelectedElements) {
				this.f_setProperty(f_prop.DESELECTED_ITEMS, f_prop.ALL_VALUE);

			} else {
				var deselectedElementValues=this._deselectedElementValues;
				if (deselectedElementValues.length) {
					this.f_setProperty(f_prop.DESELECTED_ITEMS, deselectedElementValues, true);
				}
			}
		}
	},
	/**
	 * @method protected
	 */
	f_moveCursor: function(element, show, evt, selection) {
		f_core.Assert(element && element.tagName, "fa_selectionManager.f_moveCursor: Invalid parameter to move cursor !");
		
		var old=this._cursor;
		
		if (element!=old) {
			this._cursor=element;
			
			if (old) {
				this.fa_updateElementStyle(old);
			}
			
			if (element) {
				this.fa_updateElementStyle(element);
			}
		}
		
		if (!element) {
			return;
		}

		f_core.Debug(fa_selectionManager, "f_moveCursor: Move cursor to element '"+this.fa_getElementValue(element)+"'"+((selection)?" selection=0x"+selection.toString(16):"")+" disabled="+this.fa_isElementDisabled(element));
		
		if (selection) {
			if (this._performElementSelection(element, show, evt, selection)) {
				show=false;
			}
		}
		
		if (show) {
			this.fa_showElement(element);
		}
		
		if (!this._selectable) {
			return;
		}
		
		if ((selection & fa_selectionManager.STARTRANGE_SELECTION) 
				&& !this.fa_isElementDisabled(element)) {
			f_core.Debug(fa_selectionManager, "f_moveCursor: Set lastSelectedElement to '"+this.fa_getElementValue(element)+"'.");
			this._lastSelectedElement=element;
		}
		
		if (false && f_core.IsDebugEnabled(fa_selectionManager)) {
			var s="SelectedValues=";
			var selectedElementValues=this._selectedElementValues;
			if (!selectedElementValues.length) {
				s+="EMPTY";
			} else {
				s+=selectedElementValues.join(",");
			}
			
			s+="\ndeselectedValues=";
			var deselectedElementValues=this._deselectedElementValues;
			if (!deselectedElementValues.length) {
				s+="EMPTY";
				
			} else {
				s+=deselectedElementValues.join(",");
			}
			
			if (this._clearAllSelectedElements) {
				s+=" CLEAR ALL";
			}
			
			s+="\nselection=";
			var currentSelection=this._currentSelection;
			if (!currentSelection.length) {
				s+="EMPTY";
				
			} else {
				s+=currentSelection.join(",");
			}		
		
			f_core.Debug("fa_selectionManager", s);
		}
	},
	/**
	 * @method protected
	 * @param HtmlElement element
	 * @param boolean selected
	 * @return boolean
	 */
	f_updateElementSelection: function(element, selected) {
		// Suivant l'état enregistré, on recalcule l'état !

		var value=this.fa_getElementValue(element);
	
		selected=this._isElementValueSelected(value, selected);
		this.fa_setElementSelected(element, selected);
		
		if (!selected) {
			return false;
		}
		
		this._currentSelection.push(element);
		
		return true;
	},
	_selectElement: function(element, value, show) {
		if (this.fa_isElementSelected(element)) {
			return;
		}
		
		this.fa_setElementSelected(element, true);
		this.fa_updateElementStyle(element);
		
		this._currentSelection.push(element);
		
		if (value===undefined) {
			value=this.fa_getElementValue(element);
		}
		
		if (!this._deselectedElementValues.f_removeElement(value)) {
			this._selectedElementValues.f_addElement(value);
		}
		
		if (show) {
			this.fa_showElement(element);
		}
	},
	_deselectElement: function(element, value) {
		if (!this.fa_isElementSelected(element)) {
			return false;
		}

		this.fa_setElementSelected(element, false);
		this.fa_updateElementStyle(element);
		
		this._currentSelection.f_removeElement(element);
		
		if (value===undefined) {
			value=this.fa_getElementValue(element);
		}
		
		if (this._selectedElementValues.f_removeElement(value)) {
			return true;
		}
		
		if (this._clearAllSelectedElements) {
			return false;
		}
		
		return this._deselectedElementValues.f_addElement(value);
	},
	_deselectAllElements: function() {		
		var currentSelection=this._currentSelection;
		if (currentSelection.length) {
			this._currentSelection=new Array;

			for(var i=0;i<currentSelection.length;i++) {
				var element=currentSelection[i];
				
				this.fa_setElementSelected(element, false);
				this.fa_updateElementStyle(element);
			}
		}
		
		this._clearAllSelectedElements=true;
		this._deselectedElementValues=new Array;
		this._selectedElementValues=new Array;
	},
	_selectRange: function(first, last, appendSelection) {
		// on deselectionne tout ... puis on selectionne le range !
		
		f_core.Debug(fa_selectionManager, "_selectRange: Select range from '"+this.fa_getElementValue(first)+"'=>'"+this.fa_getElementValue(last)+"' appendMode="+appendSelection);
		
		var elements=this.fa_listVisibleElements();
		if (!elements) {
			return;
		}
		
		var l=new Array;
		var append=false;
		for(var i=0;i<elements.length;i++) {
			var element=elements[i];
			
			var elementValue=this.fa_getElementValue(element);
			if (append && !this.fa_isElementDisabled(element)) {
				l.push(elementValue);
			}
			
			if (element!=first && element!=last) {
				continue;
			}
					
			if (append) {
				append=false;
				break;
			}
			
			if (!this.fa_isElementDisabled(element)) {
				l.push(elementValue);
			}
			
			if (first==last) {
				break;
			}

			append=true;			
		}	
	
		if (append || !l.length) {
			// Y a un probleme !
			// Ou on selectionne un truc non selectionnable !
			return;
		}
		
		return this._selectElementsRange(l, appendSelection, false, elements);
	},
	_selectElementsRange: function(l, appendSelection, show, elements) {
		if (f_core.IsDebugEnabled(fa_selectionManager)) {
			var s="Range select: "+l.length+" elements: ";

			if (!l.length) {
				s+=" EMPTY ???";
				
			} else {
				s+=l.join(",");
			}
						
			f_core.Debug("fa_selectionManager", s);
		}

		var elementByValue=new Object;
		if (elements===undefined) {
			elements=this.fa_listVisibleElements();
		}
		
		for(var i=0;i<elements.length;i++) {
			var element=elements[i];
			
			elementByValue[this.fa_getElementValue(element)]=element;
		}
			
		var selectedElementValues=this._selectedElementValues;
		for(var i=0;i<selectedElementValues.length;) {
			var selectedElementValue=selectedElementValues[i];
				
			var found=false;
			for(var j=0;j<l.length;j++) {
				if (selectedElementValue!=l[j]) {
					continue;
				}
				
				// On le laisse selectionné, on le retire de notre liste "à selectionner" !
				l.splice(j, 1);
				found=true;
				break;
			}
			
			if (found || appendSelection) {
				i++;
				continue;
			}

			var element=elementByValue[selectedElementValue];
			
			if (element) {
				this._deselectElement(element);
				continue;
			}

			// Pas dans les visibles, on supprime directement du tableau.
			selectedElementValues.splice(i, 1);
		}
		
		if (!this._clearAllSelectedElements) {
			var deselectedElementValues=this._deselectedElementValues;

			for(var i=0;i<deselectedElementValues.length;) {
				var deselectedElementValue=deselectedElementValues[i];
				
				var found=false;
				for(var j=0;j<l.length;j++) {
					if (deselectedElementValue!=l[j]) {
						continue;
					}
					
					// On le retire de la deselection !
					found=true;
					l.splice(j, 1);
					break;
				}
					
				if (!found) {
					i++;
					continue;
				}
				
				// On le retire de la liste des "déselectionnés" et on le reselectionne !
				var element=elementByValue[deselectedElementValue];
				if (element) {
					this._selectElement(element, deselectedElementValue, show);
					show=false;
					continue;
				}

				deselectedElementValues.splice(i, 1);
			}			
		}
		
		for(var i=0;i<l.length;i++) {
			var value=l[i];
			var element=elementByValue[value];
			if (!element) {
				// La valeur n'est pas affichée !
				
				selectedElementValues.push(value);
				continue;
			}
			
			this._selectElement(element, value, show);
			show=false;
		}		
	},
	
	_performElementSelection: function(element, show, evt, selection) {
		var cardinality=this._selectionCardinality;
		if (!cardinality) {
			return false;
		}
		
		f_core.Debug(fa_selectionManager, "_performElementSelection: "+
			" exclusive='"+((selection & fa_selectionManager.EXCLUSIVE_SELECTION)>0)+"'"+
			" append='"+((selection & fa_selectionManager.APPEND_SELECTION)>0)+"'"+
			" range='"+((selection & fa_selectionManager.RANGE_SELECTION)>0)+"'  disabled="+this.fa_isElementDisabled(element));
	
		if (this.fa_isElementDisabled(element)) {
			return false;
		}

		var rangeMode=(selection & fa_selectionManager.RANGE_SELECTION);
		
//		alert("Select="+this._selectionCardinality+"/"+node._value+"/"+li._node._selected);
		
		var elementSelected=this.fa_isElementSelected(element);
		var elementValue=this.fa_getElementValue(element);
		
		switch(cardinality) {
		case fa_cardinality.ONE_CARDINALITY:
			if (elementSelected) {
				return false;
			}
			
			// On continue ....
			
		case fa_cardinality.OPTIONAL_CARDINALITY:
			if (elementSelected) {
				// Deselection seulement !
				
				if (selection & fa_selectionManager.APPEND_SELECTION) {
					this._deselectAllElements();
				}
				break;
			}
			
			// On deselectionne tout: 1 seul doit rester selectionner 
			this._deselectAllElements();
				
			this._selectElement(element, elementValue, show);
			break;
			
		case fa_cardinality.ONEMANY_CARDINALITY:
			if (elementSelected && !rangeMode) {
				if (this._currentSelection.length<2) {
					// Un seul selectionné: on arrete tout !
					return false;
				}
			}

			// On continue ...

		case fa_cardinality.ZEROMANY_CARDINALITY:
			if (rangeMode) {
				var lastSelectedElement=this._lastSelectedElement;
				if (!lastSelectedElement) {
					f_core.Debug(fa_selectionManager, "_performElementSelection: No lastSelectedElement set to '"+this.fa_getElementValue(element)+"'.");

					this._lastSelectedElement=element;
					lastSelectedElement=element;
				}
				
				// Nous sommes en range mode .....
				this._selectRange(element, lastSelectedElement, (selection & fa_selectionManager.APPEND_SELECTION));
				
			} else if (elementSelected) {				
				if (selection & fa_selectionManager.APPEND_SELECTION) {
					// On est juste en ajout: pas de déselection complete !
					this._deselectElement(element, elementValue);				
					break;
				}
				
				// On deselectionne tout !
				this._deselectAllElements();

			} else if (selection & fa_selectionManager.EXCLUSIVE_SELECTION) {
				// On deselectionne tout !
				this._deselectAllElements();
			}
			

			this._selectElement(element, elementValue, show);
			break;
		}
		
		var detail=0;
		if (selection & fa_selectionManager.ACTIVATE_SELECTION) {
			detail|=f_event.ACTIVATE_DETAIL;
		}
		if (selection) {	
			detail|=1;
		}
	
		var item=this.fa_getElementItem(element);
	
		this.fa_fireSelectionChangedEvent(evt, detail, item, elementValue);
		
		return true;
	},
	
	/**
	 * @method hidden
	 * @return void
	 */
	f_setSelectionStates: function(selectionFullState) {
		this._selectionFullState=selectionFullState;
	},
	/**
	 * @method public
	 * @return Object[] An array of selected values.
	 */
	f_getSelection: function() {
		var ret=new Array;
		if (!this._selectable) {
			return ret;
		}
		
		if (this._clientSelectionFullState) {
			if (!this._clearAllSelectedElements) {
				var selectionFullState=this._selectionFullState;
				if (selectionFullState && selectionFullState.length) {
					ret.push.apply(ret, selectionFullState);
				}
			}	
			
			var selectedElementValues=this._selectedElementValues;
			if (selectedElementValues.length) {
				ret.f_addElements.apply(ret, selectedElementValues);
			}

			var deselectedElementValues=this._deselectedElementValues;
			if (deselectedElementValues.length) {
				ret.f_removeElements.apply(ret, deselectedElementValues);
			}
			
			return ret;
		}
		
		// Nous ne sommes pas en fullstate, on ne renvoit que ce que l'on voit !
		var currentSelection=this._currentSelection;
		for(var i=0;i<currentSelection.length;i++) {
			var element=currentSelection[i];
			
			var value=this.fa_getElementValue(element);
			if (value===undefined) {
				continue;
			}

			ret.push(value);
		}
		
		return ret;
	},
	_isElementValueSelected: function(value, defaultValue) {
		var selected=defaultValue;
		
		var selectionFullState=this._selectionFullState;
		if (!selected && selectionFullState) {
			selected=selectionFullState.f_contains(value);
		}
	
		if (selected && !this._clearAllSelectedElements) {
			// On recherche s'il n'a pas été deselectionné !
			if (this._deselectedElementValues.f_contains(value)) {
				// Il a été deselectionné !
				return false;
			}
		
			// Il n'a pas été deselectionné !
			return true;
		}
		
		// Tout a été deselectionné, ou c'etait pas sélectionné à la création du composant!
		
		return this._selectedElementValues.f_contains(value);
	},
	/**
	 * @method public
	 * @param Object[] selection The new selection.
	 * @param optional boolean show Show the first new selected element.
	 * @return void
	 */
	f_setSelection: function(selection, show) {
		f_core.Debug(fa_selectionManager, "f_setSelection: Set selection to '"+selection+"' show='"+show+"'.");
		
		if (!selection) {
			this._deselectAllElements();
			this.fa_fireSelectionChangedEvent();
			return;
		}
		
		this._selectElementsRange(selection, show);
		
		this.fa_fireSelectionChangedEvent();
	},
	/**
	 * @method protected
	 */
	fa_fireSelectionChangedEvent: function(evt, detail, item, elementValue) {
		
		this.f_fireEvent(f_event.SELECTION, evt, item, elementValue, this, detail);
	},

	/**
	 * @method protected abstract
	 */
	fa_isElementSelected: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 */
	fa_setElementSelected: f_class.ABSTRACT
}

new f_aspect("fa_selectionManager", __static, __prototype, fa_itemsManager, fa_selectionProvider);
