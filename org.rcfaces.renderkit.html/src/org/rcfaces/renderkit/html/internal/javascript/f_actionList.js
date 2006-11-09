/*
 * $Id$
 */

/**
 *
 * @class hidden f_actionList extends Object
 * @author Joel Merlin & Olivier Oeuillot
 * @version $Revision$ $Date$
 */
 
function f_actionList(component,type) {
	f_core.Assert(typeof(type)=="string", "Type of actionList is invalid '"+type+"'.");
	
	this._link = component;
	this._type = type;
}

f_actionList.prototype.f_finalize=function() {
	this._link=undefined;  // object
//	this._type=undefined; // string
	this._actions=undefined;  // function[]
}

/**
 * 
 * @method public
 * @return f_component 
 */
f_actionList.prototype.f_getElement=function() {
	return this._link;
}

/**
 * 
 * @method public
 * @return String 
 */
f_actionList.prototype.f_getType=function() {
	return this._type;
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_addAction=function(action) {
	var as=this._actions
	if (!as) {
		as = new Array;
		this._actions = as;
	}
	
	f_core.PushArguments(as, arguments);
	
	if (f_core.IsDebugEnabled("f_actionList")) {
		for(var i=0;i<as.length;i++) {
			var a=as[i];
			
			f_core.Assert(typeof(a)=="string" || typeof(a)=="function", "Bad action type ! ("+a+").");
		}
	}
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_addActions=function(actions) {	
	this.f_addAction.apply(this, actions);
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_removeAction=function(actions) {
	var as=this._actions
	if (!as) {
		return;
	}
	
	for(var i=0;i<arguments.length;i++) {
		var action=arguments[i];
		
		f_core.Assert(typeof(action)=="string" || typeof(action)=="function", "Bad action type ! ("+action+").");
		
		as.f_removeElement(action);
	}
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_removeActions=function(actions) {
	this.f_removeAction.apply(this, actions);
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_isEmpty=function() {
	return !this._actions;
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_addActionFirst=function(actions) {
	var as=this._actions
	if (!as) {
		as = new Array;
		this._actions = as;
	}
	
	for(var i=0;i<arguments.length;i++) {
		var action=arguments[i];
		
		f_core.Assert(typeof(action)=="string" || typeof(action)=="function", "Bad action type ! ("+action+").");
		as.unshift(action);
	}
}

/**
 * 
 * @method hidden
 */
f_actionList.prototype.f_callActions=function(evt) {
	f_core.Assert(this._link, "No linked component for this actionList !");
	
	var ret = true;
	if (!this._actions) {
		f_core.Debug(f_actionList, "No actions '"+this._type+"' returns true.");
		return ret;
	}

	var link=this._link;
	
	// Remet dans le context de la window de l'objet !
	var actions = this._actions;
	for(var i=0;i<actions.length;i++) {
		var fct = actions[i];
		if (!fct) {
			continue;
		}

		var oldEvent=null;
		try {
			oldEvent=f_event.SetEvent(evt);
			
			if (typeof(fct) == "string") {
				// Le commande est transformée en fonction !
				fct=new Function("event", fct);
				actions[i]=fct;
			}

			ret = fct.call(link, evt);
			
		} catch (ex) {
			f_actionList._ShowEventException(i, this._type, link, evt, fct, ex);
			ret=false;
			
		} finally {
			if (f_event && f_event.SetEvent) {
				f_event.SetEvent(oldEvent);
			}
		}

		if (f_core) {
			var fn=String(fct._kclass?(fct._kclass._name+"."+fct._kname):(fct.name?fct.name:fct));
			if (fn.length>64) {
				var idx=fn.indexOf('\n', 64);
				if (idx>0) {
					fn=fn.substring(0, idx)+"   ...";
				}
			}
			
			f_core.Debug(f_actionList, "callActions("+this._type+":"+link.id+"): "+fn+"\n=> returns "+ret);
		}
		
		if (ret===false) {
			break;
		}
	}
	
	// Attention un submit a pu survenir, et nous sommes plus dans un contexte camelia sain !
	// => Plus d'appels aux méthodes !

	if (evt && evt._eventReturn!==undefined) {
		f_core.Debug(f_actionList, "Call actions: eventReturn is forced to "+evt._eventReturn);
		return evt._eventReturn;
	}

	f_core.Debug(f_actionList, "Call actions returns "+ret);
	return ret;
}

/**
 * @method private static final
 */
f_actionList._ShowEventException=function(index, type, link, evt, fct, ex) {
	var s="*** Action Error: type="+type+" (action #"+index+")\n";

	s+="-- Target Object --------------------------\n";
	
	if (!link) {
		s+="link=NULL\n";
		
	} else {
		if (link.tagName) {
			s+="id="+link.id+" tagName="+link.tagName+" cssClass="+link.className+"\n";
		}
		if (link._kclass) {
			s+="f_class="+link._kclass._name+"\n";
		}

		if (link.toString) {
			s+=link.toString();
			
		} else {
			s+=link;
		}
	}
	s+="\n-- Event Object --------------------------\n";
	
	var cmp=evt.f_getComponent();
	if (cmp) {
		if (cmp==link) {
			s+="evt.component = *** target ***\n";
		} else {
			if (cmp.tagName) {
				s+="evt.component: id="+cmp.id+" tagName="+cmp.tagName+" cssClass="+cmp.className+"\n";
			}
			if (cmp._kclass) {
				s+="evt.component: f_class="+cmp._kclass._name+"\n";
			}
					
			if (cmp.toString) {
				s+="evt.component="+cmp.toString()+"\n";
			} else {
				s+="evt.component="+cmp+"\n";
			}
		}
	}
	if (evt.f_getItem()) {
		s+="evt.component="+evt.f_getItem()+"\n";
	}
	if (evt.f_getValue()) {
		s+="evt.component="+evt.f_getValue()+"\n";
	}
	s+="-- Exception Object -----------------------\n";
	s+=ex;
	
	var code=fct.toString().split('\n');
	if (code.length>15) {
		s+="\n-- Function source - (first 15 lines) ---\n";

	} else {
		s+="\n-- Function source ----------------------\n";
	}		
	
	for(var i=0;i<code.length && i<15;i++) {
		s+=code[i]+"\n";
	}
	
	//alert(s);
	f_core.Error(f_actionList, s, ex);
}

f_actionList.f_getName=function() {
	return "f_actionList";
}
