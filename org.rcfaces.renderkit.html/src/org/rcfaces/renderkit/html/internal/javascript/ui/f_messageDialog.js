/*
 * $Id$
 */

/**
 * <p><strong>f_messageDialog</strong> represents popup modal window.
 *
 * @class public final f_messageDialog extends f_dialog
 * @author Fred Lefevere-Laoide (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field private static final
	 */
	_EVENTS: {
			selection: f_event.SELECTION
	},
	
    /**
     * @field private static Array
     */
	_Messages: undefined,
    /**
     * @field private static boolean
     */
	_DocComplete: undefined,
     /**
     * Class Destructor (called in the head ...
     * @method public static
     */
    Finalizer: function() {
    	f_messageDialog._Messages = undefined; // List<Object>
    	// f_messageDialog._DocComplete = undefined; // boolean
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
		
		f_core.Debug(f_messageDialog, "_OnClick: entering");
		
		if (messageBox.f_getEventLocked(true)) {
			f_core.Debug(f_messageDialog, "_OnClick : messageBox.f_getEventLocked(true)");
			return false;
		}

		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		
		f_core.Debug(f_messageDialog, "_OnClick: before messageBox.f_buttonOnClick(button);");
		messageBox.f_buttonOnClick(button, evt);
		
		return f_core.CancelJsEvent(evt);
    },

     /**
     * @method private static
     * @param f_messageDialog msgBox
     * @param function functionOpen
     * @param number priority
     * @return void
     */
    _AddMessage: function(msgBox, functionOpen, priority) {
		if (!f_messageDialog._Messages) {
			f_messageDialog._Messages = new Array();
		}
		f_messageDialog._Messages.push({
			_msgBox: msgBox,
			_function: functionOpen,
			_priority: priority
		});
    },

    /**
     * <p>Call the next stored messageBox</p>
     *
     * @method private static
     * @return void
     */
    _ShowNextMsgStored: function() {
	    f_core.Debug(f_messageDialog, "_ShowNextMsgStored: entering");
    	var msgBoxes = f_messageDialog._Messages;

    	if (msgBoxes && msgBoxes.length) {
		    f_core.Debug(f_messageDialog, "_ShowNextMsgStored: "+msgBoxes.length+" Items");

			var maxPriority = 0;
			var maxIndex = 0;
			
			// Search for the highest priority
			for (var i=0; i<msgBoxes.length; i++) {
		     	var msg = msgBoxes[i];
				if (msg._priority > maxPriority) {
					maxPriority = msg._priority;
					maxIndex = i;
				}
			}
			
			var msg = msgBoxes[maxIndex];
			msgBoxes[maxIndex] = msgBoxes[0];
	     	msgBoxes.shift();
    		var msgBox = msg._msgBox;
			var functionToCall = msg._function;
			var iframe = msgBox.f_getIframe();

		    f_core.Debug(f_messageDialog, "_ShowNextMsgStored: before calling ");
		    
			functionToCall.call(msgBox, iframe.contentWindow.document.body);
			
		    f_core.Debug(f_messageDialog, "_ShowNextMsgStored: after calling ");

			iframe._modalShell.f_setFocus(true);

			return;
		}
		
		f_shell.DelModIFrame();
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
     *
     * @method public static
     */
     DocumentComplete: function() {
     	f_core.Debug(f_messageDialog, "DocumentComplete: entering");
     	
     	f_messageDialog._DocComplete=true;
     	f_messageDialog._ShowNextMsgStored();
     }
}

var __prototype = {

	/**
	 * @field private String
	 */
	_title: undefined,
	/**
	 * @field private String
	 */
	_text: undefined,
	/**
	 * @field private String
	 */
	_defaultValue: undefined,
	/**
	 * @field private String
	 */
	_styleClass: undefined,
	/**
	 * @field private String
	 */
	_priority: undefined,
	/**
	 * @field private Array
	 */
	_actions: undefined,
	/**
	 * <p>Construct a new <code>f_messageDialog</code> with the specified
     * initial values.</p>
	 *
	 * @method public
	 * @param optional String title the title or HTMLElement the suporting v:init tag
	 * @param optional String text the text to display
	 * @param optional String defaultValue the value returned if the popup is closed without selecting a button
	 */
	f_messageDialog: function(title, text, defaultValue) {
		this.f_super(arguments, f_shell.PRIMARY_MODAL_STYLE);
		
		if (this.nodeType==f_core.ELEMENT_NODE) {
			this._title = f_core.GetAttribute(this, "v:title");
			this._text=f_core.GetAttribute(this, "v:text");
			this._defaultValue=f_core.GetAttribute(this, "v:defaultValue");
			
			this.f_setHeight(f_core.GetNumberAttribute(this, "v:height", 300));
			this.f_setWidth(f_core.GetNumberAttribute(this, "v:width", 500));
			
			this._priority=f_core.GetNumberAttribute(this, "v:dialogPriority", 0);
			this._styleClass=f_core.GetAttribute(this, "v:styleClass");
			//v:lookId
			
			var imageURL=f_core.GetAttribute(this, "v:imageURL");
			if (imageURL) {
				this.f_setImageURL(imageURL);
			}
			this.f_setCssClassBase(f_core.GetAttribute(this, "v:cssClassBase", "f_messageDialog"));
			this.f_setBackgroundMode("greyed");

			this.f_initEventAtts(f_messageDialog._EVENTS);

		} else {
			this._title=title;
			this._text=text;
			this._defaultValue=defaultValue;

			this._priority=0;
			this.f_setCssClassBase("f_messageDialog");
			this.f_setBackgroundMode("greyed");
		}

   		this._actions = new Array();
	},

	/**
	 * <p>Destruct a new <code>f_messageDialog</code>.</p>
	 *
	 * @method public
	 */
	f_finalize: function() {		
		// this._title=undefined; // string
		// this._text=undefined; // string
		// this._defaultValue=undefined; // string
		this._actions=undefined; // List<Object>
		//this._styleClass=undefined; // string
		//this._priority=undefined; // int

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
    	f_core.Assert((typeof(title)=="string"), "f_messageDialog.f_setTitle: Invalid parameter '"+title+"'.");
		this._title = title;
	},
	
	/**
	 *  <p>Return the text.</p>
	 *
	 * @method public 
	 * @return String The text
	 */
	f_getText: function() {
		return this._text;
	},
	
	/**
	 *  <p>Sets the text.</p>
	 *
	 * @method public 
	 * @param String text the text
	 */
	f_setText: function(text) {
    	f_core.Assert((typeof(text)=="string"), "f_messageDialog.f_setText: Invalid parameter '"+text+"'.");
		this._text = text;
	},
	
	/**
	 *  <p>Return the defaultValue.</p>
	 *
	 * @method public 
	 * @return String The defaultValue
	 */
	f_getDefaultValue: function() {
		return this._defaultValue;
	},
	
	/**
	 *  <p>Sets the defaultValue.</p>
	 *
	 * @method public 
	 * @param String defaultValue the default Value
	 */
	f_setDefaultValue: function(defaultValue) {
    	f_core.Assert(typeof(defaultValue)=="string", "f_messageDialog.f_setDefaultValue: Invalid parameter '"+defaultValue+"'.");

		this._defaultValue = defaultValue;
	},
	
	/**
	 *  <p>Return the style class.</p>
	 *
	 * @method public 
	 * @return String an additional style Class
	 */
	f_getStyleClass: function() {
		return this._styleClass;
	},
	/**
	 *  <p>Sets an additional Style Class.</p>
	 *
	 * @method public 
	 * @param String styleClass an additional style Class
	 */
	f_setStyleClass: function(styleClass) {
    	f_core.Assert(typeof(styleClass)=="string", "f_messageDialog.f_setStyleClass: Invalid parameter '"+styleClass+"'.");

		this._styleClass = styleClass;
	},
	
	/**
	 *  <p>Return the priority.</p>
	 *
	 * @method public 
	 * @return int priority
	 */
	f_getPriority: function() {
		return this._priority;
	},
	/**
	 *  <p>Sets the priority.</p>
	 *
	 * @method public 
	 * @param int priority
	 * @return void
	 */
	f_setPriority: function(priority) {
    	f_core.Assert(typeof(priority)=="number", "f_messageDialog.f_setPriority: Invalid priority parameter '"+priority+"'."+typeof(priority));

		this._priority = priority;
	},
	
	/**
	 *  <p>Adds an action to the popup.</p>
	 *
	 * @method public 
	 * @param String value the value of the action
	 * @param String text the text displayed for the action
	 * @param optionnal boolean disabled true if the button must be disabled
	 * @param optionnal boolean submitButton true if the button is the default value for the messageDialog
	 * @return void
	 */
	f_addAction: function(value, text, disabled, submitButton) {
    	f_core.Assert((typeof(value)=="string"), "f_messageDialog.f_addAction: Invalid parameter value '"+value+"'.");
    	f_core.Assert((typeof(text)=="string"), "f_messageDialog.f_addAction: Invalid parameter text '"+text+"'.");
    	f_core.Assert(arguments.length < 3 || (typeof(disabled)=="boolean"), "f_messageDialog.f_addAction: Invalid parameter disabled '"+disabled+"'.");
    	f_core.Assert(arguments.length < 4 || (typeof(submitButton)=="boolean"), "f_messageBox.f_addAction: Invalid parameter submitButton '"+submitButton+"'.");
		this._actions.push({ 
			_text: text,
			_value: value,
			_disabled: disabled, 
			_submitButton: submitButton
		});
	},
	
	/**
	 *  <p>Return the array of actions.</p>
	 *
	 * @method public 
	 * @return Array The actions
	 */
	f_getActions: function() {
		return this._actions;
	},
	
	/**
	 *  <p>draw a message box. 
	 *  The first parameter is a callback that must take a String as a first parameter and a f_messageDialog as second parameter.
	 *  the callback can be null;
	 *  </p>
	 *
	 * @method public 
	 * @param Function callback The callback function to be called when the messageBox is closed
	 * @return void
	 */
	f_open: function(callback) {
		f_core.Assert(!arguments.length || typeof(callback) == "function", "f_messageDialog.f_open: Invalid Callback parameter ("+callback+")");
		
		// If a callback is passed : clean the selection listeners and add this callback to the listeners
		if (callback) {
			var actionList=this.f_getActionList(f_event.SELECTION);
			actionList.f_clearActions();
			
			this.f_addEventListener(f_event.SELECTION, callback);
		}

     	f_core.Debug(f_messageDialog, "f_open: entering ("+callback+")");
		
		// Create a blocking Div
		this.f_drawModIFrame();

		f_messageDialog._AddMessage(this, this._open, this.f_getPriority());

		if (f_messageDialog._DocComplete) {
			f_messageDialog._ShowNextMsgStored();
		}

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
		f_core.Assert(base != undefined, "f_messageDialog._open: Invalid base parameter ("+base+")");

     	f_core.Debug(f_messageDialog, "_open: entering ("+base+")");
		
		//Hide Selects
		f_shell.HideSelect();
		
		var cssClassBase = this.f_getCssClassBase();
		if (!cssClassBase) {
			cssClassBase = "f_messageDialog";
		}

		var docBase = base.ownerDocument;
		
		// form to catch the return
		var actForm = docBase.createElement("form");
		
		// Creation de la table
		var table = docBase.createElement("table");
		var baseMem = table;

		var styleClass = this._styleClass;
		if (styleClass) {
			baseMem.className += " "+styleClass;
		}

		baseMem._buttons=new Array();
		
		// Memorisation de la call-back et de l'instance de f_messageDialog
		baseMem._messageBox=this;

		table.className = cssClassBase+"_dialog";
		
		//set size and pos
		table.style.top=0;
		table.style.left=0;
		table.style.width=this.f_getWidth()+"px";
		table.style.height=this.f_getHeight()+"px";
		table.sellPadding=0;
		table.cellSpacing=0;
		table.width=this.f_getWidth()+"px";

		var tbod = docBase.createElement("tbody");
		
		// Creation de la ligne de titre
		var ligne = docBase.createElement("tr");
		ligne.className = cssClassBase+"_title_tr";
		ligne.style.height = "30px";
		
		var cell = docBase.createElement("td");
		cell.colSpan = 2;
		
		// HandleToMove : to recognize a movable item
		cell.className = cssClassBase+"_title_td handleToMove";
		//Handle for Mouse Moves
		cell.onmousedown = f_dialog._OnMouseDown;
		cell.onmouseup = f_dialog._OnMouseUp;
		cell.onmousemove = f_dialog._OnMouseMove;
		
		var zone = docBase.createElement("span");
		zone.className = cssClassBase+"_title_text";
		
		var text=this._title;
		if (text) {
			zone.appendChild(docBase.createTextNode(text));
		}

		cell.appendChild(zone);
		ligne.appendChild(cell);
		tbod.appendChild(ligne);
		
		// Creation de la ligne de texte
		ligne = docBase.createElement("tr");
		ligne.className = cssClassBase+"_text_tr";
		ligne.style.height = (this.f_getHeight() - 60 -30)+"px";
		
		// cell for image
		cell = docBase.createElement("td");
		cell.className = cssClassBase+"_image_td";
		
		text=this.f_getImageResolvedURL();
		if (text) {
			zone = docBase.createElement("img");
			zone.className=cssClassBase+"_image_image"
			zone.src=text;
			cell.appendChild(zone);
		}

		ligne.appendChild(cell);

		// cell for text
		cell = docBase.createElement("td");
		cell.className = cssClassBase+"_text_td";
		
		zone = docBase.createElement("span");
		zone.className = cssClassBase+"_text_text";
		
		text=this._text;
		if (text) {
			zone.appendChild(docBase.createTextNode(text));
		}

		cell.appendChild(zone);
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
		
		var actTable = docBase.createElement("table");
		var actTbod = docBase.createElement("tbody");
		var actTr = docBase.createElement("tr");
		
		var actions=this._actions;
		if (!actions.length) {
			var def = this._defaultValue;
			if (!def) {
				def = "OK";
			}
			this.f_addAction(def, def, false, true);
		}
		
		for (var i=0; i<actions.length; i++) {
			var action=actions[i];
			var cellb = docBase.createElement("td");
			var button = docBase.createElement("input");
			
			if (action._submitButton) {
				button.type="submit";

				actForm.onsubmit=f_messageDialog._OnClick;
				actForm._button=button;
			} else {
				button.type="button";
			}
			button.className=cssClassBase+"_button";
			button.disabled=action._disabled;
			button._value=action._value;
			button.value=action._text;
			button.onclick=f_messageDialog._OnClick;
			button.onClick=null;
			button._base = baseMem;
			baseMem._buttons.push(button);
			
			cellb.appendChild(button);
			actTr.appendChild(cellb);
			
		}

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
	 *  <p>callBack that will call the user provided callBack</p>
	 *
	 * @method protected 
	 * @param HTMLInputElement selectedButton The button that was pushed
	 * @param Event jsEvent
	 * @return void
	 */
	f_buttonOnClick: function(selectedButton, jsEvent) {
     	f_core.Debug(f_messageDialog, "f_buttonOnClick: entering ("+selectedButton+")");
	
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
			
			f_core.VerifyProperties(button);
		}
		
		// Table cleaning
		base._messageBox=undefined;
		base._buttons=undefined;
			
		f_core.VerifyProperties(base);
		
		// Deletion of the base HTMLElement
		var parent = base.parentNode;
		parent.removeChild(base);

		var ret = this.f_fireEvent(f_event.SELECTION, jsEvent, null, value);
		if (ret) {
			f_messageDialog._ShowNextMsgStored();
			return;
		}

		//delete the iFrame
		f_shell.DelModIFrame();
	},
	
	
	/**
	 * @method public
	 * @return String
	 */
	toString: function() {
		return "[f_messageDialog title='"+this._title+"' text='"+this._text+"' defaultValue='"+this._defaultValue+"']";
	}
}

new f_class("f_messageDialog", null, __static, __prototype, f_dialog);
