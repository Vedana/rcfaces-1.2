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
var __members = {

	/**
	 * @field protected Boolean
	 */
	_loading: undefined,

	/**
	 * @field private Function
	 */
	_nextCommand: undefined,

	f_finalize: function() {
//		this._loading=undefined; // Boolean
		this._nextCommand=undefined; // function
	},
	/**
	 * @method protected
	 * @param Function callback
	 * @return void
	 */
	f_appendCommand: function(callBack) {
		f_core.Assert(typeof(callBack)=="function", "fa_commands.f_appendCommand: Invalid callback parameter ("+callBack+")");
		
		var nextCommand = this._nextCommand;
		
		if (!nextCommand) {
			f_core.Info(fa_commands, "f_appendCommand: Call immediatly the callback !");
			callBack.call(this, this);
			return;
		}
		
		if (f_core.IsInfoEnabled(fa_commands)) {
			if (nextCommand) {
				f_core.Info(fa_commands, "f_appendCommand: Replace an other callback !");
	
			} else  {
				f_core.Info(fa_commands, "f_appendCommand: Set the next callback !");
			}
		}
		
		this._nextCommand=callBack;
	},
	/**
	 * @method protected
	 * @return Boolean
	 */
	f_processNextCommand: function() {
		var nextCommand=this._nextCommand;
		if (!nextCommand) {
			f_core.Debug(fa_commands, "f_processNextCommand: no more commands");
			return false;
		}
	
		f_core.Info(fa_commands, "f_processNextCommand: Process callback !");
		
		this._nextCommand=undefined;
		
		try {
			nextCommand.call(this, this);
			
		} catch (ex) {
			f_core.Error(fa_commands, "f_processNextCommand: Call of callback: "+nextCommand+" throws exception.", ex);
			return false;
		}
		
		return true;
	},
	/**
	 * @method protected
	 * @return void
	 */
	f_clearCommands: function() {
		f_core.Debug(fa_commands, "f_clearCommands: clear commands");
		this._nextCommand=undefined;
	}
};

new f_aspect("fa_commands", {
	members: __members
});
