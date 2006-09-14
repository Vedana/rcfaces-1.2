/*
 * $Id$
 */

/**
 * Classe ProgressIndicator
 *
 * @class public f_progressIndicator extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$) 
 * @version $Revision$ $Date$
 */ 
 
var __static = {
	/**
	 * @field private static final number
	 */
	_UPDATE_DELAY: 200,
	
	/**
	 * @field hidden static final number
	 */
	PROGRESS_MAX: 1000
}

var __prototype = {
	f_progressIndicator: function() {
		this.f_super(arguments);

		var progressBar=this.f_findComponent("progressBar");
		this._progressBar=progressBar;
		
		progressBar.f_ignorePropertyChanges(); // On optimise le temps d'affichage !
		progressBar.f_setValue(-1);
		progressBar.f_setMin(0);
		progressBar.f_setMax(f_progressIndicator.PROGRESS_MAX);
		
		var label=this.f_findComponent("label");
		this._label=label;
		label.f_ignorePropertyChanges();// On optimise le temps d'affichage !
	},
	f_finalize: function() {
		this._progressBar=undefined; // f_progressBar
		this._label=undefined; // f_text
		
		var progressMonitor=this._progressMonitor;
		if (progressMonitor) {
			this._progressMonitor=undefined;
			
			if (progressMonitor.f_cancel) {
				progressMonitor.f_cancel();
			}
		}

		// this._oldTaskName=undefined; // string
		// this._lastUpdate=undefined; // number
	
		// this._nextValue=undefined; // number
		// this._nextTaskName=undefined; // string
		// this._nextSubTaskName=undefined; // string
		
		var timer=this._timer; // TIMER_ID
		if (timer) {
			this._timer=undefined;
			
			window.clearTimeout(timer);
		}
	
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return f_progressMonitor
	 */
	f_createProgressMonitor: function() {		
		var old=this._progressMonitor;
		if (old) {
			this._progressMonitor=undefined;
 			if (!old.f_isCanceled()) {
	 			old.f_cancel();
	 		}
		}
		
		var progressMonitor=f_progressIndicatorMonitor.f_newInstance(this);
		
		this._progressMonitor=progressMonitor;
		return progressMonitor;
	},
	/**
	 * @method public
	 * @return f_progressMonitor
	 */
	f_getCurrentProgressMonitor: function() {
		return this._progressMonitor;
	},
	/**
	 * @method hidden
	 */
	f_setIndeterminate: function(indeterminate) {
		var progressBar=this._progressBar;		
		if (progressBar) {
			progressBar.f_setIndeterminate(indeterminate);
		}
	},
	/**
	 * @method hidden
	 */
	f_getValue: function() {
		var progressBar=this._progressBar;		
		if (!progressBar) {
			return 0;
		}
		
		return progressBar.f_getValue();
	},
	/**
	 * @method hidden
	 */
	f_changeValues: function(value, taskName, subTaskName, important) {
	
		var modified=false;
	
		if (value!==undefined && value!=this._nextValue) {
			this._nextValue=value;
			modified=true;
		}
		
		if (taskName!==undefined && taskName!=this._nextTaskName) {
			this._nextTaskName=taskName;
			modified=true;
		}
		
		if (subTaskName!==undefined && subTaskName!=this._nextSubTaskName) {
			this._nextSubTaskName=subTaskName;
			modified=true;
		}
		
		if (!modified) {
			return;
		}

		if (important) {
			this._updateValues();
			return;
		}

		var time=new Date().getTime();
		
		if (!this._lastUpdate || (time-this._lastUpdate)>f_progressIndicator._UPDATE_DELAY) {
			this._updateValues();
			return;
		}

		var timer=this._timer;
		if (timer) {
			return;
		}
		
		var progressIndicator=this;
		this._timer=window.setTimeout(function() {
			progressIndicator._updateValues();
			
		}, f_progressIndicator._UPDATE_DELAY);
	},
	_updateValues: function() {
		this._lastUpdate=new Date().getTime();
		
		if (this._nextValue===undefined) {
			return;
		}
	
		var progressBar=this._progressBar;		
		if (progressBar) {
			progressBar.f_setValue(this._nextValue);
		}
		
		var label=this._label;
		if (label) {
			var name=this._nextTaskName;
			if (!name) {
				name=" ";
			}
		
			if (name!=this._oldTaskName) {
				this._oldTaskName=name;
				
				label.f_setText(name);
			}
		}
		
		this._nextValue=undefined;
	}
}
 
var f_progressIndicator=new f_class("f_progressIndicator", null, __static, __prototype, f_component);
