/*
 * $Id$
 */

/**
 * Aspect Message1
 *
 * @aspect fa_message1 extends fa_messageText
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {
/*
	fa_message1: function() {
	}
	*/
	
	f_finalize: function() {
		// this._fors=undefined; // string[]
		// this._forsTranslated=undefined; // String[]
		
		this._currentMessage=undefined; // f_messageObject
 	},
	f_update: {
		after: function() {
			this.f_performMessageChanges();
		}
	},
	/**
	 * @method public 
	 * @return String Return an identifier (naming separator might not be ':')
	 */
	f_getFor: function() {
		var fors=this.f_getForComponentIds();
		
		return fors[0];
	},
	/**
	 * @method public 
	 * @return String Returns an array of identifiers (naming separator might not be ':')
	 */
	f_getForComponentIds: function() {
		var fors=this._fors;
		if (fors!==undefined) {
			return fors;
		}
		
		var s=f_core.GetAttribute(this, "v:for");
		
		fors=new Array();
		this._fors=fors;
		
		var keys=new Array();
		this._forsTranslated=keys;
		
		if (!s || !s.length) {
			return fors;
		}
		
		var sd=s.split(",");
		for(var i=0;i<sd.length;i++) {
			var f=f_core.Trim(sd[i]);
			if (!f.length) {
				continue;
			}
			fors.push(f);

			var forTranslated=fa_namingContainer.ComputeComponentId(this, f);					

			f_core.Assert(forTranslated, "f_message.f_getForComponents: Component '"+f+"' associated to this message is not defined !");
			keys.push(forTranslated);

			f_core.Debug(fa_message1, "f_getForComponentIds: translate '"+f+"' to '"+forTranslated+"'.");
		}
		
		return fors;
	},
	/**
	 * @method private 
	 * @return String
	 */
	_getForTranslatedComponentIds: function() {
		this.f_getForComponentIds();
		
		return this._forsTranslated;
	},
	/**
	 * @method public 
	 * @return boolean
	 */
	f_isSetFocusIfMessage: function() {
		if (this._setFocusIfMessage===undefined) {
			this._setFocusIfMessage=f_core.GetBooleanAttribute(this, "v:setFocusIfMessage", false);
		}
		
		return this._setFocusIfMessage;
	},
	/**
	 * @method public 
	 * @param boolean setFocusIfMessage
	 * @return void
	 */
	f_setFocusIfMessage: function(setFocusIfMessage) {
		f_core.Assert(typeof(setFocusIfMessage)=="boolean", "Invalid setFocusIfMessage parameter ('"+setFocusIfMessage+"')");

		this._setFocusIfMessage=setFocusIfMessage;
	},
	f_performMessageChanges: function() {

		var keys=this._getForTranslatedComponentIds();
		
		if (!keys.length) {
			keys=[null]; // On prend les globaux
		}

		f_core.Debug(fa_message1, "f_performMessageChanges: Search keys: nb="+keys.length+" keys");

		var msg;
		for(var i=0;i<keys.length;i++) {
			var key=keys[i];

			f_core.Debug(fa_message1, "f_performMessageChanges: key#"+i+"="+key);
			
			var messages;
			if (key===null) {
				messages=f_messageContext.Get(this).f_listMessages(null);
			
				f_core.Debug(fa_message1, "f_performMessageChanges: Get messages for key=null : "+ messages);
				
			} else {
				var component=f_core.GetElementByClientId(key);
				if (!component) {
					f_core.Debug(fa_message1, "f_performMessageChanges: Can not get component associated to key '"+key+"'.");
				
					continue;
				}
			
				messages=f_messageContext.ListMessages(component);

				f_core.Debug(fa_message1, "f_performMessageChanges: Messages associated to component: '"+key+"': "+messages);
			}
			
			for(var j=0;j<messages.length;j++) {
				var m=messages[j];
				
				if (!msg || msg.f_getSeverity()<m.f_getSeverity()) {
					msg=m;
				}
			}
		}
						
		if (msg==this._currentMessage) {
			f_core.Debug(fa_message1, "f_performMessageChanges: no modifications ("+msg+")");
			return;
		}

		f_core.Debug(fa_message1, "f_performMessageChanges: change message: "+msg);
		
		this._currentMessage=msg;
		
		this.fa_updateMessages();
		
		if (msg && this.f_isSetFocusIfMessage()) {
			var forComponent=this.f_getFor();
			
			// Pas de GLOBAL pour le for !
			if (forComponent) {
				var comp=this.f_findComponent(forComponent);
				if (comp) {
					f_core.SetFocus(comp, true); // async SVP !
				}
			}
		}
	}
}

new f_aspect("fa_message1", null, __prototype, fa_messageText);
