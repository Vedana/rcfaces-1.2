/*
 * $Id$
 */

/**
 * 
 *
 * @class hidden f_timerEffect extends f_effect
 * @author Olivier Oeuillot
 * @version $Revision$
 */
var __static = {
	/**
	 * @method private static
	 */
	_TimeOut: function(effect) {
		var cur=effect._current;
		var next=effect._nextValue;
		var step=effect.f_getStep();
		var component=effect._component;
		
		if (next!=cur) {
			cur+=(next>cur)?step:-step;
		}
		
		if (cur>1) {
			cur=1;
			
		} else if (cur<0) {
			cur=0;
		}
		effect._current=cur;
		
		if (cur==next) {
			window.clearInterval(effect._timerId);

			effect._timerId=undefined;
		}

		effect.f_performTick(component, cur, next);
	}
}

var __prototype = {
	f_finalize: function() {
		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;

			window.clearInterval(timerId);
		}

		this.f_super(arguments);
	},
	f_getStepMs: function() {	
		return 100;
	},
	f_getStep: function() {
		return 0.1;
	},
	f_performTick: function(component, cur, next) {
	},
	f_wakeUpTimer: function() {
		if (this._timerId) {
			return;
		}
		var effect=this;
		
		this._timerId=window.setInterval(function() {
			f_timerEffect._TimeOut.call(f_timerEffect, effect);
			
		}, this.f_getStepMs());
	}
}

var f_timerEffect=new f_class("f_timerEffect", null, __static, __prototype, f_effect);

