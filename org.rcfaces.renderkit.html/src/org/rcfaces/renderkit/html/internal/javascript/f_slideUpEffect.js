/*
 * $Id$
 */

/**
 * 
 *
 * @class hidden f_slideUpEffect extends f_timerEffect
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/** 
	 * @field private static final number
	 */
	_STEP_MS: 50,

	/** 
	 * @field private static final number
	 */
	_STEP: 0.3,

	Initializer: function() {
		f_effect.Declare("slideUp", this);
	}
}

var __prototype = {
	f_slideUpEffect: function(component, callback) {
		this.f_super(arguments, component, callback);
		
		if (component.style.display=="none") {
			this._current=0;
			
		} else {
			this._current=1;
		}
	},
	f_performEffect: function(set) {
		this._nextValue=(set)?0:1;
		
		var component=this._component;
		if (this._current!=this._nextValue && this._current==0) {
			component.style.display="block";
			component.style.height="1";
		}

		if (this._callback) {
			this._callback(this._current);
		}
		
		this.f_wakeUpTimer();
	},
	f_getStepMs: function() {
		return f_slideUpEffect._STEP_MS;
	},
	f_getStep: function() {
		return f_slideUpEffect._STEP;
	},
	f_performTick: function(component, cur, next) {
		
		if (this._callback) {
			this._callback(cur);
		}
		
		if (cur==0) {
			component.style.display="none";
			return;
		}

		if (component.style.opacity!==undefined) {
			// CSS 3  on peut toujours réver !
			component.style.opacity = cur;

		} else if (f_core.IsInternetExplorer()) {
			if (cur==1) {
				component.style.filter = "";
				
			} else {
				component.style.filter = "alpha(opacity="+Math.floor(cur*100)+")";
			}

		} else if (f_core.IsGecko()) {
			component.style.MozOpacity = cur;
		}
		
		var h=component.scrollHeight;
		var ch=Math.floor(h*cur);
		
		component.style.height=ch;
		component.scrollTop=h-ch;
	}
}

var f_slideUpEffect=new f_class("f_slideUpEffect", null, __static, __prototype, f_timerEffect);

