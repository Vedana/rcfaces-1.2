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
		this.f_setHeight(500);
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
		
		var tableCorps = docBase.createElement("table");
		tableCorps.style.top=0;
		tableCorps.style.left=0;
		tableCorps.cellPadding=0;
		tableCorps.cellSpacing=0;
		var tbodCorps = docBase.createElement("tbody");
		
		// Corps de la popup : 2 liste et une palanquée de boutons

		// Creation de la ligne de libellés
		var ligneCorps = docBase.createElement("tr");
		
		var cellCorps = docBase.createElement("td");

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Cols dispo."));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);

		// Col des boutons entre les listes
		cellCorps = docBase.createElement("td");
		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");

		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		zone.appendChild(docBase.createTextNode("Cols triées"));

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);
		
		// Col des boutons d'organisation des cols triees
		cellCorps = docBase.createElement("td");
		ligneCorps.appendChild(cellCorps);
		
		// Ajout de la ligne à la table
		tbodCorps.appendChild(ligneCorps);
		

		// Creation de la ligne de liste
		ligneCorps = docBase.createElement("tr");
		
		cellCorps = docBase.createElement("td");

		var colsDispos; //liste des colonnes disponibles
		var colsSorted; //liste des colonnes triées

		colsDispos = docBase.createElement("select");
		colsDispos.multiple = true;
		colsDispos.size = 6;
		
		cellCorps.appendChild(colsDispos);
		ligneCorps.appendChild(cellCorps);

		// Col des boutons entre les listes
		cellCorps = docBase.createElement("td");
		
		var tableBut = docBase.createElement("table");
		var tbodBut = docBase.createElement("tbody");

		// ligne vide de 50px
		var ligneBut = docBase.createElement("tr");
		ligneBut.style.height = "50px";
		var cellBut = docBase.createElement("td");
		ligneBut.appendChild(cellBut);
		tbodBut.appendChild(ligneBut);
		
		// premier bouton : selection
		ligneBut = docBase.createElement("tr");
		cellBut = docBase.createElement("td");
		var but = docBase.createElement("input");
		but.type="button";
		but.className=cssClassBase+"_button";
		but.value=" >>> ";
		var selCol=function() {
			var options = colsDispos.options;
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					var column = selOpt._column;
					var opt = docBase.createElement("option");
					opt.text = "+ "+selOpt.text;
					opt.value = selOpt.text;
					opt._column = column;
					opt._sort = 1;
					colsSorted.add(opt);
				}
			}
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					colsDispos.remove(i);
				}
			}
			if (colsDispos.length == 0) {
				but.disabled = true;
			}
			if (colsSorted.length > 0) {
				but.disabled = false;
			}
		};
		but.onclick=selCol;
		but.onClick=null;
		
		cellBut.appendChild(but);
		ligneBut.appendChild(cellBut);
		tbodBut.appendChild(ligneBut);

		// ligne vide de 10 px
		ligneBut = docBase.createElement("tr");
		ligneBut.style.height = "10px";
		cellBut = docBase.createElement("td");
		ligneBut.appendChild(cellBut);
		tbodBut.appendChild(ligneBut);
		
		// second bouton : déselection
		ligneBut = docBase.createElement("tr");
		cellBut = docBase.createElement("td");
		but = docBase.createElement("input");
		but.type="button";
		but.className=cssClassBase+"_button";
		but.value=" >>> ";
		var deselCol=function() {
			var options = colsSorted.options;
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					var column = selOpt._column;
					var opt = docBase.createElement("option");
					opt.text = selOpt.value;
					opt.value = selOpt.value;
					opt._column = column;
					colsDispos.add(opt);
				}
			}
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					colsSorted.remove(i);
				}
			}
			if (colsDispos.length > 0) {
				but.disabled = false;
			}
			if (colsSorted.length == 0) {
				but.disabled = true;
			}
		};
		but.onclick=deselCol;
		but.onClick=null;
		
		cellBut.appendChild(but);
		ligneBut.appendChild(cellBut);
		tbodBut.appendChild(ligneBut);

		tableBut.appendChild(tbodBut);
		cellCorps.appendChild(tableBut);
		
		ligneCorps.appendChild(cellCorps);

		cellCorps = docBase.createElement("td");

		colsSorted = docBase.createElement("select");
		colsSorted.multiple = true;
		colsSorted.size = 6;
		
		cellCorps.appendChild(colsSorted);
		ligneCorps.appendChild(cellCorps);

		cellCorps.appendChild(zone);
		ligneCorps.appendChild(cellCorps);
		
		// Col des boutons d'organisation des cols triees
		cellCorps = docBase.createElement("td");
		
		var tableSort = docBase.createElement("table");
		var tbodSort = docBase.createElement("tbody");

		// ligne vide de 50px
		var ligneSort = docBase.createElement("tr");
		ligneSort.style.height = "50px";
		var cellSort = docBase.createElement("td");
		ligneSort.appendChild(cellSort);
		tbodSort.appendChild(ligneSort);
		
		// premier bouton : ascendant
		ligneSort = docBase.createElement("tr");
		cellSort = docBase.createElement("td");
		but = docBase.createElement("input");
		but.type="button";
		but.className=cssClassBase+"_button";
		but.value=" asc. ";
		var selAsc=function() {
			var options = colsSorted.options;
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					selOpt.text = "+ "+selOpt.value;
					selOpt._sort = 1;
				}
			}
		};
		but.onclick=selAsc;
		but.onClick=null;
		
		cellSort.appendChild(but);
		ligneSort.appendChild(cellSort);
		tbodSort.appendChild(ligneSort);

		// ligne vide de 10 px
		ligneSort = docBase.createElement("tr");
		ligneSort.style.height = "10px";
		cellSort = docBase.createElement("td");
		ligneSort.appendChild(cellSort);
		tbodSort.appendChild(ligneSort);
		
		// second bouton : descendant
		ligneSort = docBase.createElement("tr");
		cellSort = docBase.createElement("td");
		but = docBase.createElement("input");
		but.type="button";
		but.className=cssClassBase+"_button";
		but.value=" desc. ";
		var selDesc=function() {
			var options = colsSorted.options;
			for (var i = 0; i<options.length; i++) {
				var selOpt = options[i];
				if (selOpt.selected) {
					selOpt.text = "- "+selOpt.value;
					selOpt._sort = -1;
				}
			}
		};
		but.onclick=selDesc;
		but.onClick=null;
		
		cellSort.appendChild(but);
		ligneSort.appendChild(cellSort);
		tbodSort.appendChild(ligneSort);

		tableSort.appendChild(tbodSort);
		cellCorps.appendChild(tableSort);
		
		
		ligneCorps.appendChild(cellCorps);
		
		// Ajout de la ligne à la table
		tbodCorps.appendChild(ligneCorps);
		
		tableCorps.appendChild(tbodCorps);
		
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
		
		// Remplir les listes
		
		var columns = grid.getColumns();
		for (var i=0; i<columns.length; i++) {
			var column = columns[i];
			var opt = docBase.createElement("option");
			var label = grid.f_getColumnName(column);
			opt.value = label;
			opt._column = column;
			var sort = grid.f_getColumnSortedState(column);
			if (sort == 0) {
				opt.text = label;
				colsDispos.add(opt);
			} else {
				if (sort > 0) {
					opt._sort = 1;
					opt.text = "+  "+label;
				} else {
					opt._sort = -1;
					opt.text = "-  "+label;
				}
				colsSorted.add(opt);
			}
		}
		
		// Hide the select
		f_shell.HideSelect();
		
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
