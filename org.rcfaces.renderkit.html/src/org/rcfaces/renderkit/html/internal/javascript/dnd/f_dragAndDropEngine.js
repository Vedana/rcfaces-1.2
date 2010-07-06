/*
 * $Id$
 */

/**
 * f_dragAndDropEngine class
 *
 * @class public f_dragAndDropEngine extends f_object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
		
	/**
	 * @field private static final f_dragAndDropEngine
	 */	
	_Current: undefined,
	
	
	/**
	 * @field private static final String[]
	 */	
	_DefaultDragAndDropInfos: ["popup" ],
	
	/**
	 * 
	 * @method hidden static
	 * @param f_component component 
	 * @return f_dragAndDropEngine
	 */
	 Create: function(component) {
	
		var engine = f_dragAndDropEngine.f_newInstance(component);
	
		return engine;
	 },
	 /**
	  * @method private static
	  * @param Event evt
	  * @return Boolean
	  * @context event:evt
	  */
	 _DragMove: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}
		 
		try {
			var current=f_dragAndDropEngine._Current;
	
			f_core.Debug(f_dragAndDropEngine, "_DragMove: drag move ! current="+current);
	
			if (!current) {
				return;
			}
			
			return current._dragMove(evt);
			
		} catch (x) {
			f_core.Error(f_dragAndDropEngine, "_DragMove: exception", x);
		}
	 },
	 /**
	  * @method private static
	  * @param Event evt
	  * @return Boolean
	  * @context event:evt
	  */
	 _DragStop: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		try {
			var current=f_dragAndDropEngine._Current;
	
			f_core.Debug(f_dragAndDropEngine, "_DragStop: stop drag ! current="+current);
	
			if (!current) {
				return;
			}	
			
			current._dragStop(evt);
			
			return f_dragAndDropEngine._Exit();
			
		} catch (x) {
			f_core.Error(f_dragAndDropEngine, "_DragStop: exception", x);
		}
	 },
	 /**
	  * @method private static
	  * @param Event evt
	  * @return Boolean
	  * @context event:evt
	  */
	 _KeyDownUp: function(evt) {
		if (!evt) {
			evt = f_core.GetJsEvent(this);
		}

		try {
			var current=f_dragAndDropEngine._Current;
	
			f_core.Debug(f_dragAndDropEngine, "_KeyDown/_KeyUp: stop drag ! current="+current);
	
			if (!current) {
				return;
			}	
			
			if (evt.keyCode==f_key.VK_ESCAPE) {
				return f_dragAndDropEngine._DragStop();				
			}
			
			switch(evt.keyCode) {
			case f_key.VK_SHIFT:
			case f_key.VK_CONTROL:
			case f_key.VK_ALT:
				return current._modifyEffect(evt);
			}
			
		} catch (x) {
			f_core.Error(f_dragAndDropEngine, "_KeyDown/_KeyUp: exception", x);
		}
	 },
	 /**
	  * @method private static
	  * @return Boolean
	  */
	 _Exit: function() {
		var current=f_dragAndDropEngine._Current;
		
		if (!current) {

			f_core.Debug(f_dragAndDropEngine, "_Exit: nothing to clear");
			return;
		}	
		
		f_core.Debug(f_dragAndDropEngine, "_Exit: exit dnd engine ! current="+current);
		
		f_dragAndDropEngine._Current=undefined;

		return current._exit();
	 },
	/**
	 * @method private static
	 * @param String
	 * @return String[]
	 */
	_SplitTypes: function(type) {
		
		var idx=type.indexOf(';');
		if (idx>=0) {
			type=type.substring(0, idx);
		}

		var cache=f_dragAndDropEngine._Cache;
		if (!cache) {
			cache=new Object();
			f_dragAndDropEngine._Cache=cache;
		}
		
		var types=cache[type];
		if (types!==undefined) {
			return types;
		}
		
		types=type.split("/");
		if (types.length!=2) {
			types=null;
		} else {		
			types = [ f_core.Trim(types[0]), f_core.Trim(types[1]) ];
		}
		
		cache[type]=types;
		
		return types;		
	},
	
	/**
	 * @method public static
	 * @param String name
	 * @param Function constructor
	 * @return void
	 */
	RegisterDragAndDropPopup: function(name, constructor) {
		var infos=f_dragAndDropEngine._DragAndDropInfos;
		if (!infos) {
			infos=new Object;
			f_dragAndDropEngine._DragAndDropInfos = infos;
		}
		infos[name]=constructor;
	},
	/**
	 * @method public static
	 * @param String... name
	 * @return void
	 */
	SetDefaultDragAndDropPopup: function(name) {
		
		f_dragAndDropEngine._DefaultDragAndDropInfos=f_core.PushArguments(null, name);
	},
	/**
	 * @method public static
	 */
	Finalizer: function() {
		 f_dragAndDropEngine._Current = undefined;
		 f_dragAndDropEngine._DragAndDropInfos=undefined;
		 f_dragAndDropEngine._DefaultDragAndDropInfos = undefined; // String[]
	}
}

var __members = {
		
	/**
	 * @field private f_component
	 */
	_sourceComponent: undefined,
	
	/**
	 * @field private f_component
	 */
	_targetComponent: undefined,
	
	/**
	 * @field private Object
	 */
	_targetItem: undefined,
	
	/**
	 * @field private any
	 */
	_targetItemValue: undefined,
	
	/**
	 * @field private any
	 */
	_targetItemComponent: undefined,

	/**
	 * @field private Boolean
	 */
	_initialized: undefined,
		
	/**
	 * @constructor 
	 * @param f_component sourceComponent
	 * @param String[] dragAndDropPopupNames
	 */
	f_dragAndDropEngine: function(sourceComponent, names) {
		this.f_super(arguments);

		this._sourceComponent=sourceComponent;
		this._dragAndDropInfoNames=names;
	},
	/**
	 * @method public
	 * @param String... name
	 * @return f_dragAndDropInfo Las drag and drop info popup instance.
	 */
	f_addDragAndDropInfoByName: function(name) {
		var infos=f_dragAndDropEngine._DragAndDropInfos;
		if (!infos) {
			return null;
		}
		
		for(var i=0;i<arguments.length;i++) {
			var name=arguments[i];
			
			var cb=infos[name];
			if (!cb) {
				continue;
			}
			
			var dndInfo=cb(this);
			if (!dndInfo) {
				continue;
			}
			
			var old=this._dragAndDropInfo;
			if (old) {
				dndInfo.f_setParent(old);
			}
			
			this._dragAndDropInfo=dndInfo;
		}
		
		return this._dragAndDropInfo;
	},
	/**
	 * @method public
	 * @param Event jsEvent
	 * @param Object item
	 * @param any itemValue
	 * @param HTMLElement itemComponent
	 * @param int dragEffects
	 * @param Array dragTypes
	 * @return Boolean
	 */
	f_start: function(jsEvent, sourceItem, sourceItemValue, sourceItemElement, sourceDragEffects, sourceDragTypes) {	
		f_core.Debug(f_dragAndDropEngine, "f_dragAndDropEngine: sourceComponent='"+this._sourceComponent+"' sourceItem='"+sourceItem+"' sourceItemValue='"+sourceItemValue+"' sourceItemElement='"+sourceItemElement+"' sourceDragEffects='"+sourceDragEffects+"' sourceDragTypes='"+sourceDragTypes+"'");

		this._sourceItem=sourceItem;
		this._sourceItemValue=sourceItemValue;
		this._sourceItemElement = sourceItemElement;
		this._sourceDragEffects=sourceDragEffects;
		this._sourceDragTypes=sourceDragTypes;			
		
		if (this._install(jsEvent)===false) {

			f_core.Debug(f_dragAndDropEngine, "f_start: install returns FALSE");

			return false;
		}	
		
		f_dragAndDropEngine._Current = this;		

		f_core.Debug(f_dragAndDropEngine, "f_start: installed returns TRUE");
		
		return true;
	},
	
	/** 
	 * @method public
	 */
	f_finalize: function() {
		this._sourceComponent=undefined;
		this._clearFields();
		// this._lastEffectsInfo=undefined; // Number
		// this._lastTypesInfo=undefined; // String[]
		// this._dragAndDropInfoNames=undefined; // String[]
		
		this.f_super(arguments);
	},

	/** 
	 * @method protected
	 * @return void
	 */
	_clearFields: function() {
		this._sourceItem=undefined;
		this._sourceItemValue=undefined;
		this._sourceItemElement = undefined;
		this._sourceDragEffects=undefined;
		this._sourceDragTypes=undefined;			

		this._targetComponent=undefined;
		this._targetItem=undefined;
		this._targetItemValue=undefined;
		this._targetItemElement = undefined;
		this._targetDragEffects=undefined;
		this._targetDragTypes=undefined;			
		
		this._additionalTargetInformations=undefined;
		this._additionalSourceInformations=undefined;
		
		this._lastEffectInfo=undefined;
		this._lastTypesInfo=undefined;
		
		this._currentDropEffect=undefined;
		this._currentDropTypes=undefined;
		
		this._lastMousePosition=undefined;
		
		var queryDropInfosCall=this._queryDropInfosCall;
		if (queryDropInfosCall) {
			this._queryDropInfosCall=undefined;
			
			try {
				queryDropInfosCall.f_releaseDropInfos(this);
						
			} catch (x) {
				f_core.Error(f_dragAndDropEngine, "f_clearFields: call f_releaseDropInfos throws exception", x); 
			}
		}
	},
	
	/**
	 * @method private
	 * @return Boolean
	 */
	_install: function(jsEvent) {	
		f_dragAndDropEngine._Exit();
				
		var req = {
			_sourceItem: this._sourceItem,
			_sourceItemValue: this._sourceItemValue,
			_sourceComponent: this._sourceComponent
		};

		var ret=this.f_fireEventToSource(f_dndEvent.DRAG_INIT_STAGE, jsEvent, req);
		
		f_core.Debug(f_dragAndDropEngine, "_install: DRAG_INIT_STAGE event returns '"+ret+"'.");
		
		if (ret===false) {
			return false;
		}
		
		this._initialized = true;
		this._started = false;

		var doc=this._sourceComponent.ownerDocument;
		
		var eventPos = f_core.GetJsEventPosition(jsEvent, doc);
		this._initialMousePosition=eventPos;

		f_core.Debug(f_dragAndDropEngine, "_install: Initial mouse position "+eventPos.x+"/"+eventPos.y);

		f_core.AddEventListener(doc, "mousemove", f_dragAndDropEngine._DragMove, doc);
		f_core.AddEventListener(doc, "mouseup", f_dragAndDropEngine._DragStop, doc);
		f_core.AddEventListener(doc, "keydown", f_dragAndDropEngine._KeyDownUp, doc);
		f_core.AddEventListener(doc, "keyup", f_dragAndDropEngine._KeyDownUp, doc);
		
		return true;
	},
	/**
	 * @method private
	 * @return Boolean
	 */
	_exit: function(js) {
		f_core.Debug(f_dragAndDropEngine, "_exit: Exit drag/drop engine.");

		var doc=this._sourceComponent.ownerDocument;
		
		f_core.RemoveEventListener(doc, "mousemove", f_dragAndDropEngine._DragMove, doc);
		f_core.RemoveEventListener(doc, "mouseup", f_dragAndDropEngine._DragStop, doc);
		f_core.RemoveEventListener(doc, "keydown", f_dragAndDropEngine._KeyDownUp, doc);
		f_core.RemoveEventListener(doc, "keyup", f_dragAndDropEngine._KeyDownUp, doc);
	
		if (this._dragLock) {
			this._dragLock=false;
			
			f_event.ExitEventLock(f_event.DND_LOCK);
		}

		var dndInfo=this._dragAndDropInfo;
		if (dndInfo) {
			try {
				dndInfo.f_end();
				
			} catch (x) {
				f_core.Error(f_dragAndDropEngine, "_exit: call f_end of dndInfo throws exception", x); 
			}
		}

		this._clearFields();
	},	
	
	/**
	 * @method hidden
	 * @param String stage
	 * @param Event jsEvent
	 * @param any itemValue
	 */
	f_fireEventToSource: function(stage, jsEvent, detail) {
		f_core.Debug(f_dragAndDropEngine, "f_fireEventToSource: prepare event source ("+this._sourceComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' returns "+ret);
		
		var ret;
		try {
			ret=this._sourceComponent.f_fireEvent(f_event.DRAG, jsEvent, this, detail, null, stage);

			
		} catch (x) {
			f_core.Error(f_dragAndDropEngine, "f_fireEventToSource: fire to source ("+this._sourceComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' throws exception", x);
			
			throw x;
		}

		
		f_core.Debug(f_dragAndDropEngine, "f_fireEventToSource: fire to source ("+this._sourceComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' returns '"+ret+"'");
		
		return ret;
	},
	
	
	/**
	 * @method hidden
	 * @param String stage
	 * @param Event jsEvent
	 * @param any itemValue
	 */
	f_fireEventToTarget: function(stage, jsEvent, detail) {
		f_core.Debug(f_dragAndDropEngine, "f_fireEventToTarget: prepare event source ("+this._targetComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' returns "+ret);
		
		var ret;
		try {
			ret=this._targetComponent.f_fireEvent(f_event.DROP, jsEvent, this, detail, null, stage);

			
		} catch (x) {
			f_core.Error(f_dragAndDropEngine, "f_fireEventToTarget: fire to target ("+this._targetComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' throws exception", x);
			
			throw x;
		}

		
		f_core.Debug(f_dragAndDropEngine, "f_fireEventToTarget: fire to target ("+this._targetComponent+") '"+stage+"' jsEvent='"+jsEvent+"' detail='"+detail+"' returns '"+ret+"'");
		
		return ret;
	},
	
	/**
	 * @method public
	 * @param Event jsEvent
	 * @return Boolean
	 */
	_dragStart: function(jsEvent) {
		f_core.Debug(f_dragAndDropEngine, "_startDrag: started="+this._started);

		if (this._started) {
			return false;
		}
				
		var req = {
			_sourceItem: this._sourceItem,
			_sourceItemValue: this._sourceItemValue,
			_sourceComponent: this._sourceComponent			
		};

		var ret=this.f_fireEventToSource(f_dndEvent.DRAG_START_STAGE, jsEvent, req);
		
		f_core.Debug(f_dragAndDropEngine, "_startDrag: DRAG_START_STAGE event returns '"+ret+"'.");
		
		if (ret===false) {
			return false;
		}
		
		this._started=true;
		
		if (!this._dragLock) {
			this._dragLock=true;
			
			f_event.EnterEventLock(f_event.DND_LOCK);
		}
		
		var dndInfo=this._dragAndDropInfo;
		if (!dndInfo) {
			var names=this._dragAndDropInfoNames;
			if (names===undefined) {
				names=f_dragAndDropEngine._DefaultDragAndDropInfos;
			}
			if (names) {
				this.f_addDragAndDropInfoByName.apply(this, names);
			}
		}
		
		var dndInfo=this._dragAndDropInfo;
		if (dndInfo) {
			try {
				dndInfo.f_start();
				
			} catch (x) {
				f_core.Error(f_dragAndDropEngine, "_dragStart: call f_start of dndInfo throws exception", x); 
			}
		}
	
		// Affichage du phantom !		
		
		return true;
	},
	/**
	 * @method private
	 * @return Boolean
	 */
	_dragStop: function(jsEvent) {
		f_core.Debug(f_dragAndDropEngine, "_dragStop: Drag stop started='"+this._started+"'.")
		
		if (!this._started) {
			return;
		}
		
		this._started=undefined;
		
		var targetComponent=this._targetComponent;
		if (!targetComponent || !this._currentDropEffect) {
			
			var req = {
				_sourceItem: this._sourceItem,
				_sourceItemValue: this._sourceItemValue,
				_sourceComponent: this._sourceComponent
			};

			this.f_fireEventToSource(f_dndEvent.DRAG_ABORTED_STAGE, jsEvent, req);
			
			return false;
		}
		
		var targetItemValue=this._targetItemValue;
		
		var canceled=false;
				
		var req = {
			_targetItem: this._targetItem,
			_targetItemValue: targetItemValue,
			_targetComponent: targetComponent,

			_sourceItem: this._sourceItem,
			_sourceItemValue: this._sourceItemValue,
			_sourceComponent: this._sourceComponent,

			_effect: this._currentDropEffect,
			_types: this._currentDropTypes
		};
		
		var ret=this.f_fireEventToSource(f_dndEvent.DRAG_REQUEST_STAGE, jsEvent, req);
		if (ret===false) {
			canceled=true;
			
		} else {
			var ret=this.f_fireEventToTarget(f_dndEvent.DROP_REQUEST_STAGE, jsEvent, req);
			if (ret===false) {
				canceled=true;
			}
		}
				
		if (canceled) {		
			this.f_fireEventToSource(f_dndEvent.DRAG_CANCELED_STAGE, jsEvent, req);
			this.f_fireEventToTarget(f_dndEvent.DROP_CANCELED_STAGE, jsEvent, req);
			return false;
		}
				
		this.f_fireEventToSource(f_dndEvent.DRAG_COMPLETE_STAGE, jsEvent, req);
		
		this.f_fireEventToTarget(f_dndEvent.DROP_COMPLETE_STAGE, jsEvent, req);
		
		return true;
	},
	
	_dragMove: function(jsEvent) {

		var eventPos = f_core.GetJsEventPosition(jsEvent);
		this._lastMousePosition=eventPos;

		if (!this._started) {

			f_core.Debug(f_dragAndDropEngine, "_dragMove: Test start drag");
			
			var ipos=this._initialMousePosition;
			
			var l=Math.sqrt(Math.pow(ipos.x-eventPos.x, 2)+Math.pow(ipos.y-eventPos.y, 2));

			f_core.Debug(f_dragAndDropEngine, "_dragMove: move '"+l+"' pixels.");

			if (l<5) {
				return false;
			}
	
			f_core.Debug(f_dragAndDropEngine, "_dragMove: Start drag");

			if (this._dragStart()===false) {
				return false;
			}
		}

		var dndInfo=this._dragAndDropInfo;
		if (dndInfo) {
			try {
				dndInfo.f_move(eventPos.x, eventPos.y);
				
			} catch (x) {
				f_core.Error(f_dragAndDropEngine, "_dragMove: call f_move of dndInfo throws exception", x); 
			}
		}
		
		var dropElement=jsEvent.srcElement;
		if (!dropElement) {
			dropElement=jsEvent.target;
		}
	
		//f_core.Debug(f_dragAndDropEngine, "_dragMove: DropElement="+dropElement);
		
		if (!dropElement || dropElement.nodeType!=f_core.ELEMENT_NODE) {

			f_core.Debug(f_dragAndDropEngine, "_dragMove: invalid dropElement '"+dropElement+"'");
			return this._cancelTarget(jsEvent);
		}
		
		var dropComponent=f_core.GetParentByClass(dropElement);
		//f_core.Debug(f_dragAndDropEngine, "_dragMove: DropComponent="+dropComponent);
		
		if (!dropComponent || !dropComponent.f_isDroppable) {
			// Target definie ... on abandonne target

			f_core.Debug(f_dragAndDropEngine, "_dragMove: invalid dropComponent '"+dropComponent+"' (dropElement='"+dropElement+"')");
			return this._cancelTarget(jsEvent);
		}
		
		if (!dropComponent.f_isDroppable()) {
			// Target definie ... on abandonne target

			f_core.Debug(f_dragAndDropEngine, "_dragMove: component is not droppable '"+dropComponent+"' (dropElement='"+dropElement+"')");
			return this._cancelTarget(jsEvent);
		}
		
		this._queryDropInfosCall=dropComponent;
		
		var dropInfos=dropComponent.f_queryDropInfos(this, jsEvent, dropElement);
		if (!dropInfos) {
			// La target ne trouve rien ...

			f_core.Debug(f_dragAndDropEngine, "_dragMove: no dropInfos for component '"+dropComponent+"', dropElement='"+dropElement+"'");
			return this._cancelTarget(jsEvent, false);
		}
		
		if (this._sourceComponent==dropComponent && this._sourceItemValue==dropInfos.itemValue) {
			// Pas de changement !
			f_core.Debug(f_dragAndDropEngine, "_dragMove: same item as source !");
			return;
		}
		
		if (this._targetComponent==dropComponent && this._targetItemValue==dropInfos.itemValue) {
			// Pas de changement !
			f_core.Debug(f_dragAndDropEngine, "_dragMove: same item as target !");
			return;
		}
		
		this._cancelTarget(jsEvent);
		
		this._targetComponent=dropComponent;
		this._targetItem=dropInfos.item;
		this._targetItemValue=dropInfos.itemValue;
		this._targetItemElement = dropInfos.itemElement;
		this._targetDropTypes = dropInfos.dropTypes;
		this._targetDropEffects = dropInfos.dropEffects;		
		this._additionalTargetInformations=undefined;
	
		f_core.Debug(f_dragAndDropEngine, "_dragMove: new target targetComponent='"+this._targetComponent+"' targetItem='"+this._targetItem+"' targetItemValue='"+this._targetItemValue+"' targetItemElement='"+this._targetItemElement+"' targetDropEffects='"+this._targetDropEffects+"' this._targetDropTypes='"+this._targetDropTypes+"'");

		return this._computeDragAndDrop(jsEvent);
	},
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return Boolean
	 */
	_cancelTarget: function(jsEvent, releaseDrop) {
		var target=this._targetComponent;
		f_core.Debug(f_dragAndDropEngine, "_cancelTarget: target='"+target+"'.");

		if (!target) {
			this._computeDragAndDrop(jsEvent);
			return false;
		}
			
		var req = {
			_targetItem: this._targetItem,
			_targetItemValue: this._targetItemValue,
			_targetComponent: this._targetComponent,

			_sourceItem: this._sourceItem,
			_sourceItemValue: this._sourceItemValue,
			_sourceComponent: this._sourceComponent
		};
		
		this.f_fireEventToTarget(f_dndEvent.DRAG_OVER_CANCELED_STAGE, jsEvent, req);		
			
		this.f_fireEventToTarget(f_dndEvent.DROP_OVER_CANCELED_STAGE, jsEvent, req);

		this._additionalTargetInformations=undefined;
		this._targetComponent=undefined;
		this._targetItem=undefined;
		this._targetItemValue=undefined;
		this._targetItemElement = undefined;
		this._targetDropTypes = undefined;
		this._targetDropEffects = undefined;		
			
		if (releaseDrop!==false) {
			var queryDropInfosCall=this._queryDropInfosCall;
			if (queryDropInfosCall) {
				this._queryDropInfosCall=undefined;
				
				try {
					queryDropInfosCall.f_releaseDropInfos(this);
							
				} catch (x) {
					f_core.Error(f_dragAndDropEngine, "f_clearFields: call f_releaseDropInfos throws exception", x); 
				}
			}
		}
		
		this._computeDragAndDrop(jsEvent);
		return false;
	},
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return Boolean
	 */
	_modifyEffect: function(jsEvent) {
		if (!this._targetDropEffects) {
			return true;
		}
		
		var newEffect=this._computeEffect(jsEvent);
		if (newEffect==this._currentDropEffect) {
			return true;
		}
		
		this._computeDragAndDrop(jsEvent);
		return true;
	},
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return Number Effect
	 */
	_computeEffect: function(jsEvent) {
		var targetEffects=this._targetDropEffects;
		var sourceEffects=this._sourceDragEffects;

		var effects=targetEffects & sourceEffects;
	
		var effect=f_dndEvent.NONE_DND_EFFECT;
		if (effects) {
			if (jsEvent) {
				if ((jsEvent.shiftKey || jsEvent.ctrlKey) && jsEvent.altKey && (effects & f_dndEvent.MOVE_DND_EFFECT)) {
					// ALT + (SHIFT || CTRL) => MOVE
					effect=f_dndEvent.MOVE_DND_EFFECT;
	
				} else if (jsEvent.shiftKey && jsEvent.ctrlKey && (effects & f_dndEvent.LINK_DND_EFFECT)) {
					// CTRL + SHIFT => LINK
					effect=f_dndEvent.LINK_DND_EFFECT;
	
				} else if (jsEvent.altKey && (effects & f_dndEvent.LINK_DND_EFFECT)) {
					effect=f_dndEvent.LINK_DND_EFFECT;
					
				} else if (jsEvent.ctrlKey && (effects & f_dndEvent.COPY_DND_EFFECT)) {
					effect=f_dndEvent.COPY_DND_EFFECT;
					
				} else if (jsEvent.shiftKey && (effects & f_dndEvent.MOVE_DND_EFFECT)) {
					effect=f_dndEvent.MOVE_DND_EFFECT;
				}
			}
			
			if (!effect) {
				if (effects & f_dndEvent.DEFAULT_DND_EFFECT) {
					effect=f_dndEvent.DEFAULT_DND_EFFECT;
				
				} else if (effects & f_dndEvent.LINK_DND_EFFECT) {
					effect=f_dndEvent.LINK_DND_EFFECT;
				
				} else if (effects & f_dndEvent.MOVE_DND_EFFECT) {
					effect=f_dndEvent.MOVE_DND_EFFECT;
					
				} else if (effects & f_dndEvent.COPY_DND_EFFECT) {
					effect=f_dndEvent.COPY_DND_EFFECT;
				}				
			}
		}
		
		f_core.Debug(f_dragAndDropEngine, "_computeEffect: targetEffects=0x"+targetEffects.toString(16)+" sourceEffects=0x"+sourceEffects.toString(16)+" effects=0x"+effects.toString(16)+" computedEffect=0x"+effect.toString(16)+" ");
		
		return effect;
	},
	/**
	 * @method private
	 * @param Event jsEvent
	 * @return Boolean
	 */
	_computeDragAndDrop: function(jsEvent) {
		this._currentDropEffect=0;			
		this._currentDropTypes=null;			

		if (!this._targetComponent) {
			f_core.Debug(f_dragAndDropEngine, "_computeDragAndDrop: target='"+this._targetComponent+"'.");

			this._updateDnDMask();			
			return;
		}
		
		var targetTypes=this._targetDropTypes;
		var sourceTypes=this._sourceDragTypes;

		f_core.Debug(f_dragAndDropEngine, "_computeDragAndDrop: match types target='"+targetTypes+"', source='"+sourceTypes+"'.");
		
		var selectedTypes=new Array();		

		if (targetTypes && targetTypes.length && sourceTypes && sourceTypes.length) {
			var ts=new Array();
			for (var j=0;j<targetTypes.length;j++) {
				var tt=targetTypes[j];
				var rt=f_dragAndDropEngine._SplitTypes(tt);
				
				ts.push(rt);
			}
						
			for(var i=0;i<sourceTypes.length;i++) {
				var st=sourceTypes[i];
				var st2=f_dragAndDropEngine._SplitTypes(st);
				if (!st2) {
					continue;
				}
				
				for (var j=0;j<targetTypes.length;j++) {
					var tt2=ts[j];
					
					if ((st2[0]=="*" || tt2[0]=="*" || st2[0]==tt2[0]) && (st2[1]=="*" || tt2[1]=="*" || st2[1]==tt2[1])) {
						selectedTypes.f_addElement(targetTypes[j]); // Il faut conserver le parametre éventuel !
					}
				}
			}
		}
			
		f_core.Debug(f_dragAndDropEngine, "_computeDragAndDrop: match result = "+selectedTypes);
	
		if (!selectedTypes.length) {
			this._updateDnDMask();			
			return;
		}
	
		var effect=this._computeEffect(jsEvent);

		if (!effect) {
			this._updateDnDMask();	
			
			return;
		}
		
		this._additionalTargetInformations=null;
		
		var req = {
			_targetItem: this._targetItem,
			_targetItemValue: this._targetItemValue,
			_targetComponent: this._targetComponent,

			_sourceItem: this._sourceItem,
			_sourceItemValue: this._sourceItemValue,
			_sourceComponent: this._sourceComponent,

			_effect: effect,
			_types: selectedTypes
		};
		
		var ret=this.f_fireEventToSource(f_dndEvent.DRAG_OVER_STAGE, jsEvent, req);
		if (ret===false) {
			this._updateDnDMask();			
			return;
		}
		
		ret=this.f_fireEventToTarget(f_dndEvent.DROP_OVER_STAGE, jsEvent, req);
		if (ret===false) {			
			this.f_fireEventToSource(f_dndEvent.DRAG_OVER_CANCELED_STAGE, jsEvent, req);

			this._updateDnDMask();
			return;
		}
		
		this._currentDropEffect=effect;
		this._currentDropTypes=selectedTypes;
		this._lastEffectInfo=undefined;
		this._lastTypesInfo=undefined;
    
		this._updateDnDMask();			
		
		return true;
	},
	/**
	 * @method private
	 * @return void
	 */
	_updateDnDMask: function() {
		var effect=this._currentDropEffect;
		var types=this._currentDropTypes;

		var dndInfo=this._dragAndDropInfo;
		if (dndInfo) {
			this._lastEffectInfo=effect;
			this._lastTypesInfo=types;
			
			try {
				dndInfo.f_updateTarget(types, effect, this._targetComponent, this._targetItem, this._targetItemValue, this._additionalTargetInformations);
				
			} catch (x) {
				f_core.Error(f_dragAndDropEngine, "_updateDnDMask: call f_updateTarget of dndInfo throws exception", x); 
			}
		}
		
	},
	/**
	 * @method public
	 * @param Map<String, Object> infos
	 * @return void
	 */
	f_setTargetAdditionnalInformations: function(infos) {
		this._additionalTargetInformations=infos;
	},
	/**
	 * @method public
	 * @param Map<String, Object> infos
	 * @return void
	 */
	f_setSourceAdditionnalInformations: function(infos) {
		this._additionalSourceInformations=infos;
	},
	/**
	 * @method public
	 * @return Map<String, Object> infos
	 */
	f_getSourceAdditionnalInformations: function() {
		return this._additionalSourceInformations;
	},
	f_getSourceItem: function() {
		return this._sourceItem;
	},
	f_getSourceItemValue: function() {
		return this._sourceItemValue;
	},
	f_getSourceComponent: function() {
		return this._sourceComponent;
	},
	f_getLastMousePosition: function() {
		return this._lastMousePosition;
	}
}

new f_class("f_dragAndDropEngine", {
	extend: f_object,
	statics: __statics,
	members: __members
});