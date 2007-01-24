/*
 * $Id$
 */
 
/**
 * Aspect Commands
 *
 * @aspect hidden fa_commands
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
	f_finalize: function() {
		this._nextCommand=undefined; // function
	},
	/**
	 * @method protected
	 * @param function callback
	 * @return void
	 */
	f_appendCommand: function(callBack) {
		if (!this._loading) {
			f_core.Info(fa_commands, "Call immediatly the callback !");
			callBack.call(this, this);
			return;
		}
		
		if (this._nextCommand) {
			f_core.Info(fa_commands, "Replace an other callback !");

		} else  {
			f_core.Info(fa_commands, "Set the next callback !");
		}

		this._nextCommand=callBack;
	},
	/**
	 * @method protected
	 * @return boolean
	 */
	f_processNextCommand: function() {
		var nextCommand=this._nextCommand;
		if (!nextCommand) {
			return false;
		}
	
		f_core.Info(fa_commands, "Process callback !");
		
		this._nextCommand=undefined;
		
		try {
			nextCommand.call(this, this);
			
		} catch (ex) {
			f_core.error(fa_commands, "Call of callback: "+nextCommand+" throws exception.", ex);
			return false;
		}
		
		return true;
	},

	
	_loading: f_class.ABSTRACT
}

var fa_commands=new f_aspect("fa_commands", null, __prototype);
