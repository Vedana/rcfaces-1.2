/*
 * $Id$
 */

/**
 * Aspect Message1
 *
 * @aspect fa_message1 extends fa_message
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
			var messageContext=f_messageContext.Get(this);
	
			var messages=messageContext.f_listMessages(this.f_getFor());
			if (messages && messages.length>0 && messages[0]!=this._currentMessage) {
				this._currentMessage=messages[0];
	
				this.fa_updateMessages();
			}
		}
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getFor: function() {
		var fors=this.f_getForComponentIds();
		
		return fors[0];
	},
	/**
	 * @method public 
	 * @return String
	 */
	f_getForComponentIds: function() {
		var fors=this._fors;
		if (fors===undefined) {
			var s=f_core.GetAttribute(this, "v:for");
			
			fors=new Array();
			this._fors=fors;
			
			var keys=new Array();
			this._forsTranslated=keys;
			
			if (s) {
				var sd=s.split(",");
				for(var i=0;i<sd.length;i++) {
					var f=f_core.Trim(sd[i]);
					fors.push(f);

					var forTranslated=fa_namingContainer.ComputeComponentId(this, f);					
		
					f_core.Assert(forTranslated, "f_message.f_getForComponents: Component '"+f+"' associated to this message is not defined !");
					keys.push(forTranslated);
				}
			}
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
			var s=f_core.GetAttribute(this, "v:setFocusIfMessage");		
			this._setFocusIfMessage=(s)?true:false;
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
	f_performMessageChanges: function(messageContext) {

		var keys=this._getForTranslatedComponentIds();
		
		if (keys.length==0) {
			keys=[null]; // On prend les globaux
		}

		var msg;
		for(var i=0;i<keys.length;i++) {
			var key=keys[i];
			var messages=messageContext.f_listMessages(key);
			
			for(var j=0;j<messages.length;j++) {
				var m=messages[j];
				
				if (!msg || msg.f_getSeverity()<m.f_getSeverity()) {
					msg=m;
				}
			}
		}
						
		if (msg==this._currentMessage) {
			f_core.Debug(fa_message1, "Message changes notification: no modifications !");
			return;
		}

		f_core.Debug(fa_message1, "Message changes notification: change message !");
		
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

var fa_message1=new f_aspect("fa_message1", null, __prototype, fa_message);
