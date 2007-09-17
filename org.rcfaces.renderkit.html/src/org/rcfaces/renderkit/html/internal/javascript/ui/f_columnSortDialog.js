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
		var popup=base._popup;
		
		f_core.Debug(f_columnSortDialog, "_OnClick: entering");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (popup.f_getEventLocked(evt, true)) {
			f_core.Debug(f_columnSortDialog, "_OnClick : popup.f_getEventLocked(true)");
			return false;
		}
		
		f_core.Debug(f_columnSortDialog, "_OnClick: before popup.f_buttonOnClick(button);");
		popup.f_buttonOnClick(button, evt);
		
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
		} else if (number == 1) {
		    firstSelectComp = base._selects[1];
		    secondSelectComp = base._selects[2];
		} else if (number == 2) {
		    firstSelectComp = base._selects[2];
		}
	    
	    f_columnSortDialog.EmptySelect(secondSelectComp);
        f_columnSortDialog.EmptySelect(thirdSelectComp);

	    var sel = firstSelectComp.selectedIndex;
	    if (sel <= 0) {
	        firstSelectComp.value = "";
	        firstSelectComp._column = undefined;
   	        firstSelectComp._sort = 0;
			return f_core.CancelJsEvent(evt);
	    }
	    
	    var options = firstSelectComp.options;
	    firstSelectComp._column = options[sel]._column;
		if (secondSelectComp) {
		    for (var i = 0; i<options.length; i++) {
		        if (i != sel) {
		            f_columnSortDialog.AddOption(docBase, secondSelectComp, options[i]._column)
		        }
		    } 
			secondSelectComp.value = "";
		}
		
		return f_core.CancelJsEvent(evt);
    },

    /**
     * <p>js listener example</p>
     * dans le tag : SelectionListener="return ListenerExample(event);"
     *
     * @method public static
     * @param f_event evt
     * @return boolean
     *
    ListenerExample: function(evt) {
    	var value = evt.f_getValue();
    	return true;
    },
    */
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
		if (!selectComp) {
			return;
		}
		selectComp.value = "";
		selectComp._column = undefined;
		selectComp._sort = 0;
		var options = selectComp.options;
		if (options) {
			var j=options.length;
			while (j > 0) {
				options[--j]._column = undefined;
				selectComp.remove(j);
			} 
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
	 * <p>Destruct a  <code>f_columnSortDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {		
		// this._title=undefined; // String
		this._grid=undefined; // f_grid

		if (this._baseMem) {
			this._clean(this._baseMem);
		}

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
			return [];
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
		this._baseMem = baseMem;

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

		// Creation de la ligne de libell� Trier par
		var ligneCorps = docBase.createElement("tr");
		
		var cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne � la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		var grid = this._grid;
		var sortedCols = grid.f_getSortedColumns();
		var sortedColsIndex = 0;
		var cols = grid.f_getColumns();

		var selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		selectComp._sort = 1;

		var selectedCol = undefined;
		if (sortedColsIndex < sortedCols.length) {
			selectedCol = sortedCols[sortedColsIndex++];
		}
				
		// Remplissage
		f_columnSortDialog.AddOption(docBase, selectComp);
		selectComp.selectedIndex = 0;
		selectComp.value = "";
		for (var i = 0; i<cols.length; i++) {
			f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
			if (selectedCol && selectedCol == cols[i]) {
				selectComp._sort = grid.f_getColumnOrderState(selectedCol);
				selectComp._column = selectedCol;
				selectComp.selectedIndex = i+1;
				selectedCol = undefined;
			}
		}
		
		selectComp._base = baseMem;
		selectComp._number = 0;
		baseMem._radios = new Array;
		baseMem._selects = new Array;
		baseMem._selects.push(selectComp);
		selectComp.onchange = f_columnSortDialog._SelectOnChange;
		
		cellCorps.appendChild(selectComp);

		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");
		
		var tableRadio = this._createTableRadio(docBase, baseMem, "sort0", selectComp);

		cellCorps.appendChild(tableRadio);
		ligneCorps.appendChild(cellCorps);
		
		tbodCorps.appendChild(ligneCorps);

		// Creation de la ligne de libelle Puis Trier par (2)
		ligneCorps = docBase.createElement("tr");
		
		cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Puis trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne a la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		selectComp._sort = 1;
		
		if (sortedColsIndex < sortedCols.length) {
			selectedCol = sortedCols[sortedColsIndex++];
		}

		// Remplissage si la precedente a ete preselectionnee
		if (sortedCols.length > 0) {
			selectComp.selectedIndex = 0;
			selectComp.value = "";
			f_columnSortDialog.AddOption(docBase, selectComp);
			for (var i = j = 0; i<cols.length; i++) {
				if (cols[i] != sortedCols[0]) {
					j++;
					f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
					if (selectedCol && selectedCol == cols[i]) {
						selectComp._sort = grid.f_getColumnOrderState(selectedCol);
						selectComp._column = selectedCol;
						selectComp.selectedIndex = j;
						selectedCol = undefined;
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
		
		var tableRadio = this._createTableRadio(docBase, baseMem, "sort1", selectComp);

		cellCorps.appendChild(tableRadio);
		ligneCorps.appendChild(cellCorps);
		
		tbodCorps.appendChild(ligneCorps);

		// Creation de la ligne de libell� Puis Trier par (3)
		ligneCorps = docBase.createElement("tr");
		
		cellCorps = docBase.createElement("td");
		cellCorps.colSpan="2";

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Puis trier par"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Ajout de la ligne � la table
		tbodCorps.appendChild(ligneCorps);

		// ligne 1er combo et radios
		ligneCorps = docBase.createElement("tr");
		cellCorps = docBase.createElement("td");

		selectComp = docBase.createElement("select");
		selectComp.className = cssClassBase+"_select";
		selectComp._sort = 1;
		selectComp.selectedIndex = 0;
		
		if (sortedColsIndex < sortedCols.length) {
			selectedCol = sortedCols[sortedColsIndex++];
		}

		// Remplissage si la precedente est deselectionnee
		if (sortedCols.length > 1) {
			f_columnSortDialog.AddOption(docBase, selectComp);
			for (var i = j = 0; i<cols.length; i++) {
				if (cols[i] != sortedCols[0] && cols[i] != sortedCols[1]) {
					j++;
					f_columnSortDialog.AddOption(docBase, selectComp, cols[i]);
					if (selectedCol && selectedCol == cols[i]) {
						selectComp._sort = grid.f_getColumnOrderState(selectedCol);
						selectComp._column = selectedCol;
						selectComp.selectedIndex = j;
						selectedCol = undefined;
					}
				}
			}
		}
		
		selectComp._base = baseMem;
		selectComp._number = 2;
		baseMem._selects.push(selectComp);
		selectComp.onchange = f_columnSortDialog._SelectOnChange;
		
		cellCorps.appendChild(selectComp);

		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");
		
		var tableRadio = this._createTableRadio(docBase, baseMem, "sort2", selectComp);

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
		button._close = true;
		button._apply = true;
		button.onfocusin=noFocus;
		baseMem._buttons = [ button ];
			
		cellb.appendChild(button);
		actTr.appendChild(cellb);

		// Bouton Appliquer
		cellb = docBase.createElement("td");
//		button = docBase.createElement("input");
		
//		button.type="button";
//		button.className=cssClassBase+"_button";
//		button.value="Appliquer";
//		button._close = false;
//		button._apply = true;
//		button.onclick=f_columnSortDialog._OnClick;
//		button.onClick=null;
//		button._base = baseMem;
//		button.onfocusin=noFocus;
//		baseMem._buttons.push(button);
			
//		cellb.appendChild(button);
		actTr.appendChild(cellb);

		// Bouton Annuler
		cellb = docBase.createElement("td");
		button = docBase.createElement("input");
		
		button.type="button";
		button.className=cssClassBase+"_button";
		button.value="Annuler";
		button._close = true;
		button._apply = false;
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
	 * @param HTMLElement base element that hold the references to other elements
	 * @param string name
	 * @param number sort
	 * @return HTMLElement table with radios
	 */
	_createTableRadio: function(docBase, base, name, selectComp) {

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
			radioComp.name = name;
			radioComp.type = "radio";
			if (selectComp._sort != -1) {
				radioComp.checked = true;
			}
		}
		
		radioComp.id = name+"_asc";
		radioComp.value = f_columnSortDialog.LIB_ASCENDANT;
		var cssClassBase = this.f_getCssClassBase();
		radioComp.className = cssClassBase+"_radio_text";
		base._radios.push(radioComp);
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
			radioComp.name = name;
			radioComp.type = "radio";
			if (selectComp._sort == -1) {
				radioComp.checked = true;
			}
		}
		radioComp.id = name+"_desc";
		radioComp.value = f_columnSortDialog.LIB_DESCENDANT;
		radioComp.className = cssClassBase+"_radio_text";
		base._radios.push(radioComp);
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
		var popup=base._popup;
		var buttons=base._buttons;
		var selects=base._selects;
		var radios=base._radios;
		var close=selectedButton._close;
		var apply=selectedButton._apply;

     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: button close="+close+", apply="+apply);
		
		var colsSorted = new Array;

		if (apply) {
			for (var i=0; i<selects.length; i++) {
				var select = selects[i];
				if (!select._column) {
					break;
				}
				
				colsSorted.push({_col: select._column, _sort: select._sort});
			}
		}
		
		if (close) {
			this._clean(base);
		}

		var iframe = this.f_getIframe();
		if (apply) {
			// Impact the grid
			var grid = this._grid;
			var lon = colsSorted.length;
	     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: sorting "+lon+" cols");
	     	var delayedSort;
			if (lon == 0) {
				delayedSort = function () {
					grid.f_clearSort();
				};
			} else if (lon == 1) {
				delayedSort = function () {
					grid.f_setColumnSort(colsSorted[0]._col, colsSorted[0]._sort >= 0, false);
				};
			} else if (lon == 2) {
				delayedSort = function () {
					grid.f_setColumnSort(colsSorted[0]._col, colsSorted[0]._sort >= 0, false, colsSorted[1]._col, colsSorted[1]._sort >= 0);
				};
			} else if (lon == 3) {
				delayedSort = function () {
					grid.f_setColumnSort(colsSorted[0]._col, colsSorted[0]._sort >= 0, false, colsSorted[1]._col, colsSorted[1]._sort >= 0, colsSorted[2]._col, colsSorted[2]._sort >= 0);
				};
			}
			if (delayedSort) {
		     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: setting timeout on "+window);
				// main window
				window.setTimeout(delayedSort, 10);
			}
		}

		if (close) {
			//delete the iFrame
			this.f_delModIFrame();
		}
	},
	
	/**
	 * @method private
	 * @param HTMLElement base element that hold the references to other elements
	 * @return void
	 */
	_clean: function(base) {
		if (!base) {
			return;
		}
		var buttons = base._buttons;
		var radios = base._radios;
		var selects = base._selects;

		if (buttons) {
			// Buttons cleaning
			for (var i=0; i<buttons.length; i++) {
				var button = buttons[i];
				button._base=undefined;
				button._close=undefined;
				button._apply=undefined;
				button.onclick=null;
				button.onfocusin=null;
				
				f_core.VerifyProperties(button);
			}
		}

		if (radios) {
			// Radios cleaning
			for (var i=0; i<radios.length; i++) {
				var radio = radios[i];
				radio.onclick=null;
				
				f_core.VerifyProperties(radio);
			}
		}	
		
		if (selects) {
			// Selects cleaning
			// & Get the informations !!!
			for (var i=0; i<selects.length; i++) {
				var select = selects[i];
				select._base=undefined;
				select._column=undefined;
				select.onchange=null;
				select.onfocusin=null;
				var options = select.options;
				if (options) {
					for (var j=0; j<options.length; j++) {
						options[j]._column = undefined;
					}
				}
				
				f_core.VerifyProperties(select);
			}
		}

		// Table cleaning
		base._popup=undefined;
		base._buttons=undefined;
		base._radios=undefined;
		base._selects=undefined;
			
		f_core.VerifyProperties(base);
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
