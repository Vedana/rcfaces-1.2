/*
 * $Id$
 */

/**
 * <p><strong>f_columnSortDialog</strong> represents columns Sort popup modal window.
 *
 * @class public final f_columnSortDialog extends f_dialog
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __statics = {
	/**
	 * @field private static final
	 */
	_EVENTS: {
			selection: f_event.SELECTION
	},
	
	/**
	 * @field private static final String
	 */
	 LIB_ASCENDANT: "ascendant",
	
	/**
	 * @field private static final String
	 */
	 LIB_DESCENDANT: "descendant",
	
     /**
     * @method private static
     * @param Event evt the event
     * @return boolean
     */
    _OnClick: function(evt) {
    	var button=this;
    	if (this._button) {
    		// called by the form
    		button=this._button;
    	}
		var base=button._base;
		var messageBox=base._messageBox;
		
		f_core.Debug(f_columnSortDialog, "_OnClick: entering");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (messageBox.f_getEventLocked(evt, true)) {
			f_core.Debug(f_columnSortDialog, "_OnClick : messageBox.f_getEventLocked(true)");
			return false;
		}
		
		f_core.Debug(f_columnSortDialog, "_OnClick: before messageBox.f_buttonOnClick(button);");
		messageBox.f_buttonOnClick(button, evt);
		
		return f_core.CancelJsEvent(evt);
    },
    
     /**
     * @method private static
     * @param Event evt the event
     * @return boolean
     */
    _SelectOnChange: function(evt) {
    	var select=this;
		var base=select._base;
		var docBase = select.ownerDocument;
		var number = select._number
		
		f_core.Debug(f_columnSortDialog, "_SelectOnChange: entering");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
	    var firstSelectComp;
	    var secondSelectComp;
	    var thirdSelectComp;

		if (number == 0) {
		    firstSelectComp = base._selects[0];
		    secondSelectComp = base._selects[1];
		    thirdSelectComp = base._selects[2];
		} else {
		    firstSelectComp = base._selects[1];
		    secondSelectComp = base._selects[2];
		}
	    
	    f_columnSortDialog.EmptySelect(secondSelectComp);
	    if (thirdSelectComp) {
	        f_columnSortDialog.EmptySelect(thirdSelectComp);
	    }
	    if (firstSelectComp.selectedIndex == 0) {
	        firstSelectComp.value = "";
	        return;
	    }
	    var options = firstSelectComp.options;
	    var value = firstSelectComp.value;
	    for (var i = 0; i<options.length; i++) {
	        if (!options[i].selected) {
	            f_columnSortDialog.AddOption(docBase, secondSelectComp, options[i]._column)
	        }
	    } 
		secondSelectComp.value = "";
		
		return f_core.CancelJsEvent(evt);
    },

    /**
     * <p>js listener example</p>
     * dans le tag : SelectionListener="return ListenerExample(event);"
     *
     * @method public static
     * @param f_event evt
     * @return boolean
     */
    ListenerExample: function(evt) {
    	var value = evt.f_getValue();
    	return true;
    },
    
    /**
     * @method public static
     * @param HTMLDocument docBase document
     * @param HTMLElement selectComp Select
     * @param object column
     * @return option
     */
    AddOption: function(docBase, selectComp, column) {
            var newOpt = docBase.createElement("option");
            var text = "(aucune)";
            if (column) {
	            // oops
    	        text = column._dataGrid.f_getColumnName(column);
    	    }
            newOpt.value = text;
            newOpt.appendChild(docBase.createTextNode(text));
            newOpt._column = column;
            
            return selectComp.appendChild(newOpt);
  	},
  	
    /**
     * @method public static
     * @param HTMLElement selectComp Select
     * @return void
     */
	EmptySelect: function(selectComp) {
		selectComp.value = "";
		var j=selectComp.options.length;
		while (j > 0) {
			selectComp.options(j)._column = undefined;
			selectComp.remove(--j);
		} 
	}
    
}

var __members = {

	/**
	 * @field private String
	 */
	_title: undefined,
	/**
	 * @field private f_grid
	 */
	_grid: undefined,

	/**
	 * <p>Construct a new <code>f_columnSortDialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param grid
	 */
	f_columnSortDialog: function(grid) {
		this.f_super(arguments, f_shell.PRIMARY_MODAL_STYLE);
		
		this._title="Tri de colonnes";
		this._grid=grid;

		this.f_setPriority(0);
		this.f_setCssClassBase("f_columnSortDialog");
		this.f_setBackgroundMode("greyed");
		
		this.f_setWidth(500);
		this.f_setHeight(300);
	},

	/**
	 * <p>Destruct a new <code>f_columnSortDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {		
		// this._title=undefined; // string
		this._grid=undefined; // f_grid

		this.f_super(arguments);
	},

	/**
	 *  <p>Return the title.</p>
	 *
	 * @method public 
	 * @return String The title
	 */
	f_getTitle: function() {
		return this._title;
	},
	
	/**
	 *  <p>Sets the title.</p>
	 *
	 * @method public 
	 * @param String title the title
	 */
	f_setTitle: function(title) {
    	f_core.Assert((typeof(title)=="string"), "f_columnSortDialog.f_setTitle: Invalid parameter '"+title+"'.");
		this._title = title;
	},
	
	/**
	 *  <p>get the columns.</p>
	 *
	 * @method private 
	 */
	f_getColumns: function() {
		var grid = this._grid;
		if (!grid) {
			f_core.Error(f_columnSortDialog, "f_geColumns: grid is undefined !");
		}
		return grid.f_getColumns();
	},
	
	/**
	 *  <p>draw a message box. 
	 *  The first parameter is a callback that must take a String as a first parameter and a f_columnSortDialog as second parameter.
	 *  the callback can be null;
	 *  </p>
	 *
	 * @method public 
	 * @param Function callback The callback function to be called when the messageBox is closed
	 * @return void
	 */
	f_openSortPopup: function(callback) {
		f_core.Assert(!arguments.length || typeof(callback) == "function", "f_columnSortDialog.f_openSortPopup: Invalid Callback parameter ("+callback+")");
		
     	f_core.Debug(f_columnSortDialog, "f_openSortPopup: entering ("+callback+")");
		
		// If a callback is passed : clean the selection listeners and add this callback to the listeners
		if (callback) {
			var actionList=this.f_getActionList(f_event.SELECTION);
			if (actionList) {
				actionList.f_clearActions();
			}
			
			this.f_addEventListener(f_event.SELECTION, callback);
		}
		
		this.f_openDialog(this._open, null);
		
	},
	/**
	 *  <p>draw a message box.
	 *  </p>
	 *
	 * @method private 
	 * @param HTMLElement the base html element to construct the dialog
	 * @return void
	 */
	_open: function(base) {
     	f_core.Debug(f_columnSortDialog, "_open: entering ("+base+")");
		
		if (!base) {
			var iframe = this.f_getIframe();
			base = iframe.contentWindow.document.body;
		}
		
		//Hide Selects
		f_shell.HideSelect();
		
		var cssClassBase = this.f_getCssClassBase();
		if (!cssClassBase) {
			cssClassBase = "f_columnSortDialog";
			this.f_setCssClassBase(cssClassBase);
		}

		var docBase = base.ownerDocument;
		
		// form to catch the return
		var actForm = docBase.createElement("form");
		
		// Creation de la table
		var table = docBase.createElement("table");
		var baseMem = table;

		table.className = cssClassBase+"_dialog";
		
		// Memorisation de la call-back et de l'instance de f_columnSortDialog
		baseMem._popup=this;

		//set size and pos
		table.style.top=0;
		table.style.left=0;
		table.style.width=this.f_getWidth()+"px";
		table.style.height=this.f_getHeight()+"px";
		table.cellPadding=0;
		table.cellSpacing=0;
		table.width=this.f_getWidth()+"px";
		table.tabIndex=1;

		var tbod = docBase.createElement("tbody");
		
		// Creation de la ligne de titre
		var ligne = docBase.createElement("tr");
		ligne.className = cssClassBase+"_title_tr";
		ligne.style.height = "30px";
		
		var cell = docBase.createElement("td");
		cell.tabIndex=1;
		
		// HandleToMove : to recognize a movable item
		cell.className = cssClassBase+"_title_td handleToMove";
		//Handle for Mouse Moves
//		cell.onmousedown = f_dialog._OnMouseDown;
//		cell.onmouseup = f_dialog._OnMouseUp;
//		cell.onmousemove = f_dialog._OnMouseMove;
		
		var zone = docBase.createElement("span");
		zone.className = cssClassBase+"_title_text";
		
		var text=this._title;
		if (text) {
			zone.appendChild(docBase.createTextNode(text));
		}

		cell.appendChild(zone);
		ligne.appendChild(cell);
		tbod.appendChild(ligne);
		
		// Creation du corps de la popup
		ligne = docBase.createElement("tr");
		ligne.className = cssClassBase+"_corps_tr";
		ligne.style.height = (this.f_getHeight() -60 -30)+"px";
		
		// cell for body
		cell = docBase.createElement("td");
		cell.className = cssClassBase+"_corps_td";
		cell.tabIndex=1;
		
		cell.align = "center";
		
		var tableCorps = docBase.createElement("table");
		tableCorps.style.top=0;
		tableCorps.style.left=0;
		tableCorps.cellPadding=0;
		tableCorps.cellSpacing=0;
		var tbodCorps = docBase.createElement("tbody");
		
		// Corps de la popup : 3 combos et des radios

		// Creation de la ligne de libellé Trier par
		var ligneCorps = docBase.createElement("tr");
		
		var cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne à la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		var selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		
		// Remplissage
		f_columnSortDialog.AddOption(docBase, selectComp);
		selectComp.selectedIndex = 0;
		selectComp._sort = 1;
		var grid = this._grid;
		var cols = grid.f_getColumns();
		var sortedCols = new Array();
		for (var i = 0; i<cols.length; i++) {
			var option = f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
			if (sortedCols.length == 0) {
				var sort = grid.f_getColumnOrderState(cols[i]);
				if (sort != 0) {
					sortedCols.push(cols[i]); 
					selectComp._sort = sort;
					selectComp._column = cols[i];
					selectComp.selectedIndex = i+1;
				}
			}
		}
		
		selectComp._base = baseMem;
		selectComp._number = 0;
		baseMem._selects = new Array();
		baseMem._selects.push(selectComp);
		selectComp.onchange = f_columnSortDialog._SelectOnChange;
		
		cellCorps.appendChild(selectComp);

		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");
		
		var tableRadio = this._createTableRadio(docBase, "sort0", selectComp);

		cellCorps.appendChild(tableRadio);
		ligneCorps.appendChild(cellCorps);
		
		tbodCorps.appendChild(ligneCorps);

		// Creation de la ligne de libellé Puis Trier par (2)
		ligneCorps = docBase.createElement("tr");
		
		cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Puis trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne à la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		selectComp._sort = 1;
		
		// Remplissage si la précédente est déjà sélectionnée
		if (sortedCols.length == 1) {
			selectComp.selectedIndex = 0;
			selectComp.value = "";
			f_columnSortDialog.AddOption(docBase, selectComp);
			for (var i = 0; i<cols.length; i++) {
				if (cols[i] != sortedCols[0]) {
					var option = f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
					if (sortedCols.length == 1) {
						var sort = grid.f_getColumnOrderState(cols[i]);
						if (sort != 0) {
							sortedCols.push(cols[i]); 
							selectComp._sort = sort;
							selectComp._column = cols[i];
							selectComp.selectedIndex = i+1;
						}
					}
				}
			}
		}
		
		selectComp._base = baseMem;
		selectComp._number = 1;
		baseMem._selects.push(selectComp);
		selectComp.onchange = f_columnSortDialog._SelectOnChange;
		
		cellCorps.appendChild(selectComp);

		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");
		
		var tableRadio = this._createTableRadio(docBase, "sort1", selectComp);

		cellCorps.appendChild(tableRadio);
		ligneCorps.appendChild(cellCorps);
		
		tbodCorps.appendChild(ligneCorps);

		// Creation de la ligne de libellé Puis Trier par (3)
		ligneCorps = docBase.createElement("tr");
		
		cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Puis trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne à la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		selectComp._sort = 1;
		
		// Remplissage si la précédente est déjà sélectionnée
		if (sortedCols.length == 2) {
			selectComp.selectedIndex = 0;
			selectComp.value = "";
			f_columnSortDialog.AddOption(docBase, selectComp);
			for (var i = 0; i<cols.length; i++) {
				if (cols[i] != sortedCols[0] && cols[i] != sortedCols[1]) {
					var option = f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
					if (sortedCols.length == 2) {
						var sort = grid.f_getColumnOrderState(cols[i]);
						if (sort != 0) {
							sortedCols.push(cols[i]); 
							selectComp._sort = sort;
							selectComp._column = cols[i];
							selectComp.selectedIndex = i+1;
						}
					}
				}
			}
		}
		
		selectComp._base = baseMem;
		selectComp._number = 2;
		baseMem._selects.push(selectComp);
		
		cellCorps.appendChild(selectComp);

		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");
		
		var tableRadio = this._createTableRadio(docBase, "sort2", selectComp);

		cellCorps.appendChild(tableRadio);
		ligneCorps.appendChild(cellCorps);
		
		tbodCorps.appendChild(ligneCorps);


		tableCorps.appendChild(tbodCorps);

	//fin de la table de corps

		cell.appendChild(tableCorps);

		ligne.appendChild(cell);
		tbod.appendChild(ligne);

		// Creation de la ligne de boutons
		ligne = docBase.createElement("tr");
		ligne.className = cssClassBase+"_actions_tr";
		ligne.style.height = "60px";
		
		cell = docBase.createElement("td");
		cell.colSpan = 2;

		cell.className = cssClassBase+"_actions_td";
		cell.align = "center";
		cell.tabIndex=1;
		
		var actTable = docBase.createElement("table");
		var actTbod = docBase.createElement("tbody");
		var actTr = docBase.createElement("tr");
		
		var noFocus=function(evt) {
			f_core.Debug(f_columnSortDialog, "_open: noFocus on button "+this.value);
			if (!evt) {
				evt = f_core.GetJsEvent(this);
			}
			return f_core.CancelJsEvent(evt);
			
		};
		
		// Bouton OK
		var cellb = docBase.createElement("td");
		var button = docBase.createElement("input");
			
		button.type="submit";

		actForm.onsubmit=f_columnSortDialog._OnClick;
		actForm._button=button;

		button.className=cssClassBase+"_button";
		button.value="Valider";
		button.onclick=f_columnSortDialog._OnClick;
		button.onClick=null;
		button._base = baseMem;
		button.onfocusin=noFocus;
		baseMem._buttons = new Array();
		baseMem._buttons.push(button);
			
		cellb.appendChild(button);
		actTr.appendChild(cellb);

		// Bouton Appliquer
		cellb = docBase.createElement("td");
		button = docBase.createElement("input");
		
		button.type="button";
		button.className=cssClassBase+"_button";
		button.value="Appliquer";
		button.onclick=f_columnSortDialog._OnClick;
		button.onClick=null;
		button._base = baseMem;
		button.onfocusin=noFocus;
		baseMem._buttons.push(button);
			
		cellb.appendChild(button);
		actTr.appendChild(cellb);

		// Bouton Annuler
		cellb = docBase.createElement("td");
		button = docBase.createElement("input");
		
		button.type="button";
		button.className=cssClassBase+"_button";
		button.value="Annuler";
		button.onclick=f_columnSortDialog._OnClick;
		button.onClick=null;
		button._base = baseMem;
		button.onfocusin=noFocus;
		baseMem._buttons.push(button);
			
		cellb.appendChild(button);
		actTr.appendChild(cellb);

			
		actTbod.appendChild(actTr);
		actTable.appendChild(actTbod);

		cell.appendChild(actTable);
		ligne.appendChild(cell);
		tbod.appendChild(ligne);
		
		table.appendChild(tbod);
		actForm.appendChild(table);

		base.appendChild(actForm);
		
		// Hide the select
		f_shell.HideSelect();
		
	},
	
	/**
	 * @method private
	 * @param HTMLDocument docBase document
	 * @param string name
	 * @param number sort
	 * @return HTMLElement table with radios
	 */
	_createTableRadio: function(docBase, name, selectComp) {
		var tableRadio = docBase.createElement("table");
		//set size and pos
		tableRadio.cellPadding=0;
		tableRadio.cellSpacing=0;
		tableRadio.tabIndex=1;
		
		var tbodyRadio = docBase.createElement("tbody");

		var ligneRadio = docBase.createElement("tr");
		var cellRadio = docBase.createElement("td");
		
		var radioComp;
		if (f_shell._IE) {
			var tag = "<input type='radio' name='"+name+"'";
			if (selectComp._sort != -1) {
				tag = tag + " checked='true'";
			}
			tag = tag + "/>";
			radioComp = docBase.createElement(tag);	
		} else {
			radioComp = docBase.createElement("input");	
			radioComp.type = "radio";
			radioComp.name = name;
			if (selectComp._sort != -1) {
				radioComp.checked = "true";
			}
		}
		
		var cssClassBase = this.f_getCssClassBase();
		radioComp.className = cssClassBase+"_radio_text";
        radioComp.onclick = function() {
        	selectComp._sort = 1;
        };
        
        cellRadio.appendChild(radioComp);
        var zone = docBase.createElement("span");
        zone.className = cssClassBase+"_radio_text";
        zone.appendChild(docBase.createTextNode(f_columnSortDialog.LIB_ASCENDANT));
        cellRadio.appendChild(zone);
        ligneRadio.appendChild(cellRadio);
		tbodyRadio.appendChild(ligneRadio);
		
		ligneRadio = docBase.createElement("tr");
		cellRadio = docBase.createElement("td");
		
		if (f_shell._IE) {
			var tag = "<input type='radio' name='"+name+"'";
			if (selectComp._sort == -1) {
				tag = tag + " checked='true'";
			}
			tag = tag + "/>";
			radioComp = docBase.createElement(tag);	
		} else {
			radioComp = docBase.createElement("input");	
			radioComp.type = "radio";
			radioComp.name = name;
			if (selectComp._sort == -1) {
				radioComp.checked = "true";
			}
		}
		radioComp.className = cssClassBase+"_radio_text";
        radioComp.onclick = function() {
        	selectComp._sort = -1;
        };
        
        cellRadio.appendChild(radioComp);
        zone = docBase.createElement("span");
        zone.className = cssClassBase+"_radio_text";
        zone.appendChild(docBase.createTextNode(f_columnSortDialog.LIB_DESCENDANT));
        cellRadio.appendChild(zone);
        ligneRadio.appendChild(cellRadio);
		tbodyRadio.appendChild(ligneRadio);

		tableRadio.appendChild(tbodyRadio);
		
		return tableRadio;
	},
	
	/**
	 *  <p>callBack that will call the user provided callBack</p>
	 *
	 * @method protected 
	 * @param HTMLInputElement selectedButton The button that was pushed
	 * @param Event jsEvent
	 * @return void
	 */
	f_buttonOnClick: function(selectedButton, jsEvent) {
     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: entering ("+selectedButton+")");
	
		var base=selectedButton._base;
		var messageBox=base._messageBox;
		var buttons=base._buttons;
		var value=selectedButton._value;
	
		// Buttons cleaning
		for (var i=0; i<buttons.length; i++) {
			var button = buttons[i];
			button._base=undefined;
			button._value=undefined;
			button.onclick=null;
			button.onfocusin=null;
			
			f_core.VerifyProperties(button);
		}
		
		// Table cleaning
		base._messageBox=undefined;
		base._buttons=undefined;
			
		f_core.VerifyProperties(base);
		
		// Deletion of the base HTMLElement
		var parent = base.parentNode.parentNode;
		parent.removeChild(base.parentNode);

//		var msgDialog=this;
//		var toto=function() {
//			f_dialog.ClearTimeoutId();
//			var ret = msgDialog.f_fireEvent(f_event.SELECTION, jsEvent, null, value);
//			if (ret) {
//				f_dialog.ShowNextDialogStored();
//				return;
//			}
	
			//delete the iFrame
//			msgDialog.f_delModIFrame();
//		};

		// Need to desynchronize the call to the next thing to do
//		f_dialog.SetTimeoutId(window.setTimeout(toto, 50));

		var ret = this.f_fireEvent(f_event.SELECTION, jsEvent, null, value);
		if (ret) {
			f_dialog.ShowNextDialogStored();
			return;
		}

		//delete the iFrame
		this.f_delModIFrame();
	},
	
	
	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		var ts = this.f_super(arguments);
		ts = ts + "\n[f_columnSortDialog title='"+this._title+"' text='"+this._text+"' defaultValue='"+this._defaultValue+"']";
		return ts;
	}
}

new f_class("f_columnSortDialog", null, __statics, __members, f_dialog);
