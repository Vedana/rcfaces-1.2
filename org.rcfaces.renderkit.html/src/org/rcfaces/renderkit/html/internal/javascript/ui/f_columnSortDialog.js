/*
 * $Id$
 */

/**
 * <p><strong>f_columnSortDialog</strong> represents columns Sort popup modal window.
 *
 * @class public final f_columnSortDialog extends f_dialog
 * @author Fred Lefevere-Laoide Lefevere-Laoide (latest modification by $Author$)
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
	 * @field private static final
	 */
	_DEFAULT_FEATURES: {
		width: 250,
		height: 272,
		dialogPriority: 0,
		styleClass: "f_columnSortDialog",
		backgroundMode: f_shell.LIGHT_GREYED_BACKGROUND_MODE
	},
		
	/**
	 * Style constant for application modal behavior
	 * 
	 * @field public static final number
	 */
	APPLY_BUTTON_STYLE: 1<<20,
	
	/**
	 * @field private static final String
	 */
	LIB_ASCENDANT: "ascendant",
	
	/**
	 * @field private static final String
	 */
	LIB_DESCENDANT: "descendant",
	
	/**
	 * @field private static final String
	 */
	_SORT_MANAGER_NAME: "dialog",
	
     /**
     * @method private static
     * @param Event evt the event
     * @return boolean
     * @context object:base
     */
    _OnClick: function(evt) {
    	var button=this;
		var base=button._base;
		
		f_core.Debug(f_columnSortDialog, "_OnClick: entering");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		if (base.f_getEventLocked(evt, true)) {
			f_core.Debug(f_columnSortDialog, "_OnClick : popup.f_getEventLocked(true)");
			return false;
		}
		
		f_core.Debug(f_columnSortDialog, "_OnClick: before popup.f_buttonOnClick(button);");
		base.f_buttonOnClick(button, evt);
		

		return f_core.CancelJsEvent(evt);
    },
    
     /**
     * @method private static
     * @param Event evt the event
     * @return boolean
     * @context object:base
     */
    _SelectOnChange: function(evt) {
    	var selectedSelect=this;
		var base=selectedSelect._base;
		
		var docBase = selectedSelect.ownerDocument;
		var number = selectedSelect._number
		
		f_core.Debug(f_columnSortDialog, "_SelectOnChange: entering");

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		var grid=base._grid;
		var cols = f_columnSortDialog.GetColumns(grid);		
		
		var selects=base._selects;
		var radios=base._radios;
		for(var i=0;i<selects.length;i++) {
			var select=selects[i];
			
			var selection=select.selectedIndex;
			
			if (selection==0) {
				select._sort=undefined;
				// Plus de selection !
				for(i++;i<selects.length;i++) {
					// Deselectionne le reste ...
					selects[i].selectedIndex=0;
					selects[i]._sort=undefined;
				}
				break;
			}

			var colIndex=select.options[selection]._columnIndex;

			// On verifie qu'il n'y a pas de doublons			
			for(var j=0;j<i;j++) {
				var s=selects[j];
				var sc=s.options[s.selectedIndex]._columnIndex;
				
				if (sc!=colIndex) {
					continue;
				}
				
				// Conflit, on reinitialise tout !
								
				for(i=j+1;i<selects.length;i++) {
					selects[i].selectedIndex=0;
					selects[i]._sort=undefined;
				}
				break;
			}
		}
		
		if (selectedSelect.selectedIndex>0 && !selectedSelect._sort) {
			selectedSelect._sort=1;
		}

		f_columnSortDialog._UpdateRadioButtons(selects, 0, radios);

		for(var i=1;i<selects.length;i++) { // On debute a la deuxieme combo !
			// On remplis les selects en essayant de conserver la selection
				
			var select=selects[i];
			
			select.disabled=(selects[i-1].selectedIndex==0);
				
			var colIndex=-1;
			var selection=select.selectedIndex;
			if (selection) {
				colIndex=select.options[selection]._columnIndex;
			}
			
			for(;select.firstChild;) {
				select.removeChild(select.firstChild);
			}
			
			// Remplissage
			f_columnSortDialog._AddOptions(grid, select, cols, selects, radios, colIndex);
		}
		
		return true;
    },
    /**
     * @method private static
     * @return void
     */
	_AddOptions: function(grid, select, cols, selects, radios, colIndex) {
		f_columnSortDialog.AddOption(select); // Ajoute vide
		select.selectedIndex = 0;
		
		var i=0;
		for(;i<selects.length;i++) {
			if (selects[i]==select) {
				break;
			}
		}

		f_columnSortDialog._UpdateRadioButtons(selects, 0, radios);
		
		var cnt=0;
		for (var j = 0; j < cols.length; j++) {				
			var found=false; // On retire les colonnes deja referencees !
			for(var k=0;k<i;k++) {
				var selection=selects[k].selectedIndex;
				if (!selection) {
					break;
				}
				
				var s=selects[k].options[selection]._columnIndex;
				if (s==j) {
					found=true;
					break;
				}
			}
			
			if (found) {
				continue;
			}
			
			f_columnSortDialog.AddOption(select, cols[j], j);
			
			if (colIndex==j) {
				if (!select._sort) {
					select._sort = grid.f_getColumnOrderState(cols[j]);
				}
				select.selectedIndex = cnt+1;
			}

			cnt++;	
		}
		if (!select._sort) {
			//select._sort = 1;
		}
			
		f_columnSortDialog._UpdateRadioButtons(selects, i, radios);			
		
	},
	/**
	 * @method private static
	 * @param Object[] selects
	 * @param number i
	 * @param HTMLInputElement[] radios
	 * @return void
	 */
	_UpdateRadioButtons: function(selects, i, radios) {		
		if (i && selects[i-1].selectedIndex==0) {
			// Le precedant n'est pas trié !
			i*=2;
			radios[i].checked=false;
			radios[i+1].checked=false;
			radios[i].disabled=true;
			radios[i+1].disabled=true;
			return;
		}


		var sort=selects[i]._sort;

		i*=2;
		radios[i].checked=(sort==1);
		radios[i+1].checked=(sort==-1);
		radios[i].disabled=false;
		radios[i+1].disabled=false;
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
     * @method hidden static
     * @param HTMLDocument docBase document
     * @param HTMLElement selectComp Select
     * @param object column
     * @return HTMLOptionElement
     */
    AddOption: function(selectComp, column, columnIndex) {
        var newOpt = selectComp.ownerDocument.createElement("option");
        var text;

        if (column) {
	        text = column.f_getGrid().f_getColumnName(column);
	        if (!text) {
	        	text = column.f_getId();
	        }

	    } else {
	    	text = f_resourceBundle.Get(f_columnSortDialog).f_get("NO_COLUMN");
	    	
	    	newOpt.className="f_columnSortDialog_no_column";
	    }
	    
        newOpt.value = text;
        f_core.AppendChild(newOpt, selectComp.ownerDocument.createTextNode(text));
        newOpt._columnIndex = columnIndex;
        
        return selectComp.appendChild(newOpt);
  	},
	/**
	 *  <p>get the columns.</p>
     * @method public static
     * @param Object grid
     * @return Array array of visible columns
	 */
	GetColumns: function(grid) {
		if (!grid) {
			f_core.Error(f_columnSortDialog, "GetColumns: grid is undefined !");
			return [];
		}
		var allCols = grid.f_getColumns();
		
		var visibleCols = new Array;
		for (var i=0; i<allCols.length; i++) {
			var col = allCols[i];
			f_core.Debug(f_columnSortDialog, "GetColumns: col "+col.f_getId()+" visibility :" +col.f_isVisible());
			f_core.Debug(f_columnSortDialog, "GetColumns: col "+col.f_getId()+" sortability :" +col.f_isSortable());
			if (col.f_isVisible() && col.f_isSortable()) {
				visibleCols.push(col);
			}
		}
		return visibleCols;
	},

	Initializer: function() {
		f_grid.RegisterSortManager(f_columnSortDialog._SORT_MANAGER_NAME, function(event) {
			var grid=event.f_getComponent();
			
			var dialog=f_columnSortDialog.f_newInstance(grid);
			
			dialog.f_open();
		});
	}   
}

var __members = {
	
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
		this.f_super(arguments, 
			f_shell.PRIMARY_MODAL_STYLE | 
			f_shell.TITLE_STYLE | 
			f_shell.CLOSE_STYLE | 
			f_shell.COPY_STYLESHEET);
		
		this.f_setTitle(f_resourceBundle.Get(f_columnSortDialog).f_get("TITLE"));
		this._grid=grid;
	},

	/**
	 * <p>Destruct a  <code>f_columnSortDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {		
		// this._title=undefined; // String
		this._grid=undefined; // f_grid

		this.f_super(arguments);
	},
	/**
	 * @method protected
	 * @return Object
	 */
	f_getDefaultFeatures: function() {
		return f_columnSortDialog._DEFAULT_FEATURES;
	},
	/**
	 *  <p>draw a message box.
	 *  </p>
	 *
	 * @method private 
	 * @param HTMLElement the base html element to construct the dialog
	 * @return void
	 */
	f_fillBody: function(base) {
		this.f_super(arguments, base);
		
     	f_core.Debug(f_columnSortDialog, "f_fillBody: entering ("+base+")");
		
		var cssClassBase = "f_columnSortDialog";

		var docBase = base.ownerDocument;
		var grid = this._grid;
		
		// form to catch the return
		var actForm = docBase.createElement("form");
		actForm.className="f_columnSortDialog_form";
		actForm.style.width=this.f_getWidth()+"px";
		actForm.style.height=this.f_getHeight()+"px";
		
		base.appendChild(actForm);

		actForm.onsubmit=f_columnSortDialog._OnClick;
		//actForm._button=button;
		
		// Creation de la table
		var table = docBase.createElement("ul");
		actForm.appendChild(table);

		table.className = "f_columnSortDialog_dialog";
		
		//set size and pos
		table.style.width=this.f_getWidth()+"px";
		table.style.height=this.f_getHeight()+"px";
		table.width=this.f_getWidth();
		
		// Creation du corps de la popup
		var ligne = f_core.CreateElement(table, "li", {	
			className: cssClassBase+"_corps_tr"
		});
		
		var tableCorps = docBase.createElement("table");
		ligne.appendChild(tableCorps);
		tableCorps.cellPadding=0;
		tableCorps.cellSpacing=0;
		tableCorps.width="100%";
	
		var tbodCorps = docBase.createElement("tbody");
		tableCorps.appendChild(tbodCorps);
		
		// Corps de la popup : 3 (max) combos et des radios

		this._radios = new Array;
		this._selects = new Array;
		this._buttons = new Array;

		var sortedCols = grid.f_getSortedColumns();
		var cols = f_columnSortDialog.GetColumns(grid);
		var nbCols = cols.length;
		if (nbCols > 3) {
			nbCols = 3;
		}

		for(var j=0;j<nbCols;j++) {
			// Creation de la ligne de libell� Trier par

			var ligneCorps = docBase.createElement("tr");
			tbodCorps.appendChild(ligneCorps);

			var cellCorps = docBase.createElement("td");
			ligneCorps.appendChild(cellCorps);
			cellCorps.width=20;
			
			var cellCorps = docBase.createElement("td");
			ligneCorps.appendChild(cellCorps);
			cellCorps.colSpan=2;
	
			var zone = docBase.createElement("label");
			cellCorps.appendChild(zone);
			zone.className = "f_columnSortDialog_text";
			
			var key=(j==0)?"SORT_BY":"NEXT_SORT_BY";			
			var sortBy=f_resourceBundle.Get(f_columnSortDialog).f_get(key);		
			f_core.SetTextNode(zone, sortBy);
	
			// ligne 1er combo et radios
			ligneCorps = docBase.createElement("tr");
			tbodCorps.appendChild(ligneCorps);

			var cellCorps = docBase.createElement("td");
			ligneCorps.appendChild(cellCorps);
			cellCorps.width=20;

			cellCorps = docBase.createElement("td");			
			ligneCorps.appendChild(cellCorps);
			cellCorps.width=120;
	
			var selectComp = docBase.createElement("select");
			cellCorps.appendChild(selectComp);

			selectComp.className = "f_columnSortDialog_select";
	
			var selectedColIndex=-1;
			if (j < sortedCols.length) {
				var selectedCol = sortedCols[j];
				
				for (var i = 0; i < cols.length; i++) {
					if (cols[i]==selectedCol) {
						selectedColIndex=i;
						break;
					}
				}			
			}
				
			if (j>0) {
				selectComp.disabled=(this._selects[j-1].selectedIndex==0);
			}
					
			selectComp._base = this;
			selectComp.onchange = f_columnSortDialog._SelectOnChange;
	
			this._selects.push(selectComp);

			cellCorps = docBase.createElement("td");
			ligneCorps.appendChild(cellCorps);
			
			this._createTableRadio(cellCorps, "sort"+j, selectComp);			

			// Remplissage
			f_columnSortDialog._AddOptions(grid, selectComp, cols, this._selects, this._radios,selectedColIndex);
			
			if (j+1<nbCols) {
				var ligneCorps = docBase.createElement("tr");
				tbodCorps.appendChild(ligneCorps);
				ligneCorps.style.height="20px";
				ligneCorps.className = "f_columnSortDialog_hr";
				
				var cellCorps = docBase.createElement("td");
				ligneCorps.appendChild(cellCorps);
				cellCorps.colSpan=3;
			
				var hr = docBase.createElement("div");
				cellCorps.appendChild(hr);
				
				f_core.SetTextNode(hr, " ");
			}
		}

	//fin de la table de corps

		// Creation de la ligne de boutons
		var ligne = f_core.CreateElement(table, "li", {	
			className: "f_shellDecorator_body_buttons"
		});
				
		// Bouton OK
		var button = f_core.CreateElement(ligne, "input", {
			type: "submit",
			className: "f_columnSortDialog_button",
			value: f_resourceBundle.Get(f_shell).f_get("VALID_BUTTON")
		});
		
		button.onclick=f_columnSortDialog._OnClick;
		button._base = this;
		button._close = true;
		button._apply = true;
		//button.onfocusin=noFocus;
		this._buttons.push(button);

		// Bouton Apply
		if (this._style & f_columnSortDialog.APPLY_BUTTON) {
			
			var button = f_core.CreateElement(ligne, "input", {
				type: "button",
				className: "f_columnSortDialog_button",
				value: f_resourceBundle.Get(f_shell).f_get("APPLY_BUTTON")
			});
			
			button.onclick=f_columnSortDialog._OnClick;
			button._base = this;
			button._apply = true;
			//button.onfocusin=noFocus;
			this._buttons.push(button);
		}

		// Bouton Annuler
		var button = f_core.CreateElement(ligne, "input", {
			type: "button",
			className: "f_columnSortDialog_button",
			value: f_resourceBundle.Get(f_shell).f_get("CANCEL_BUTTON")
		});

		button._close = true;
		button._apply = false;
		button.onclick=f_columnSortDialog._OnClick;
		button._base = this;
		//button.onfocusin=noFocus;
		this._buttons.push(button);
	},
	
	/**
	 * @method private
	 * @param HTMLElement parent
	 * @param String name
	 * @param number sort
	 * @return HTMLElement table with radios
	 */
	_createTableRadio: function(parent, name, selectComp) {

		var resourceBundle = f_resourceBundle.Get(f_columnSortDialog);
 
		var tableRadio =  f_core.CreateElement(parent, "table", {
			cellPadding: 0,
			cellSpacing: 2
		});
		
		var tbodyRadio = f_core.CreateElement(tableRadio, "tbody");

		var rowRadio=f_core.CreateElement(tbodyRadio, "tr");
		rowRadio.verticalAlign="middle";
		rowRadio.style.height="20px";

		var cellRadio = f_core.CreateElement(rowRadio, "td");
  
        var label=f_core.CreateElement(cellRadio, "label", {
        	className: "f_columnSortDialog_radio_text"
        });
 		
		var radioComp=f_core.CreateElement(label, "input", {
			type: "radio",
			name: name,
			id: name+"_asc",
			value: f_columnSortDialog.LIB_ASCENDANT,
			className: "f_columnSortDialog_radio"		
		});
		
		label.appendChild(label.ownerDocument.createTextNode(resourceBundle.f_get("ASCENDANT")));
		
		this._radios.push(radioComp);
        radioComp.onclick = function() {
        	selectComp._sort = 1;
        };
  
		var rowRadio=f_core.CreateElement(tbodyRadio, "tr");
		rowRadio.verticalAlign="middle";
		rowRadio.style.height="20px";
		
		var cellRadio = f_core.CreateElement(rowRadio, "td");
          
       	var label= f_core.CreateElement(cellRadio, "label", {
        	className: "f_columnSortDialog_radio_text"
        });
 		
		var radioComp=f_core.CreateElement(label, "input", {
			type: "radio",
			name: name,
			id: name+"_desc",
			value: f_columnSortDialog.LIB_DESCENDANT,
			className: "f_columnSortDialog_radio"
		});
		
		label.appendChild(label.ownerDocument.createTextNode(resourceBundle.f_get("DESCENDANT")));
		
		this._radios.push(radioComp);
        radioComp.onclick = function() {
        	selectComp._sort = -1;
        };

  		
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
	
		var close=selectedButton._close;
		var apply=selectedButton._apply;

     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: button close="+close+", apply="+apply);
		
		var colsSorted = new Array;

		var grid = this._grid;
		if (apply) {
			var cols = f_columnSortDialog.GetColumns(grid);		

			var selects=this._selects;
			for (var i=0; i<selects.length; i++) {
				var select = selects[i];
				
				if (!select.selectedIndex) {
					break;
				}
				
				var sc=select.options[select.selectedIndex]._columnIndex;
				
				colsSorted.push(cols[sc], select._sort>=0);
				if (i==0) {
					colsSorted.push(false); // Append mode
				}
			}

			// Impact the grid
	     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: sorting "+(colsSorted.length/3)+" cols");
			
     		var delayedSort = function () {
     			if (!colsSorted.length) {
     				grid.f_clearSort();
     				return;
     			}
     			
     			grid.f_setColumnSort.apply(grid, colsSorted);
			};	     		

	     	f_core.Debug(f_columnSortDialog, "f_buttonOnClick: setting timeout on "+window);
			// main window
			window.setTimeout(delayedSort, 10);
		}

		if (close) {
			//delete the iFrame
			this.f_close();
		}
	},
	/**
	 * @method protected
	 */
	f_preDestruction: function() {
		this._cleanInputs();

		this.f_super(arguments);		
	},
	
	/**
	 * @method private
	 * @return void
	 */
	_cleanInputs: function() {
		var buttons = this._buttons;
		if (buttons) {
			this._buttons=undefined;
			
			// Buttons cleaning
			for (var i=0; i<buttons.length; i++) {
				var button = buttons[i];
				button._base=undefined; // f_columnSortDialog
				// button._close=undefined; // boolean
				// button._apply=undefined; // boolean
				button.onclick=null;
				button.onfocusin=null;
				
				f_core.VerifyProperties(button);
			}
		}

		var radios = this._radios;
		if (radios) {
			this._radios=undefined;
			
			// Radios cleaning
			for (var i=0; i<radios.length; i++) {
				var radio = radios[i];
				radio.onclick=null;
				
				f_core.VerifyProperties(radio);
			}
		}	
		
		var selects = this._selects;
		if (selects) {
			this._selects=undefined;
			
			// Selects cleaning
			// & Get the informations !!!
			for (var i=0; i<selects.length; i++) {
				var select = selects[i];
				select._base=undefined; // f_columnSortDialog
				select.onchange=null;
				select.onfocusin=null;
				
				f_core.VerifyProperties(select);
			}
		}
	},
	
	/**
	 * @method public
	 * @return String
	 */
	_toString: function() {
		var ts = this.f_super(arguments);
		ts += "\n[f_columnSortDialog title='"+this._title+"' text='"+this._text+"' defaultValue='"+this._defaultValue+"']";
		return ts;
	}
}

new f_class("f_columnSortDialog", null, __statics, __members, f_dialog);
