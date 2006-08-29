/* 
 * $Id$
 */

/**
 * f_serverAppender
 *
 * @class hidden f_serverAppender extends f_object
 * @author Olivier Oeuillot
 * @version $Revision$
 */

var __static={
	/** 
	 * @field private static final number
	 */
	_TIMER: 500,
	
	Initializer: function() {	
 		// this est la classe !
		this.f_newInstance();
	}
}
var __prototype = {
	f_serverAppender: function() {
		this.f_super(arguments);
		
		this._running=true;
		
		f_log.AddAppenders(this);
	},
	f_finalize: function() {
		this._running=undefined;

		var timerId=this._timerId;
		if (timerId) {
			this._timerId=undefined;
			window.clearTimeout(timerId);
		}
		
		/* Trop tard !
		this._sendEvents();
		*/
		
		this._events=undefined;
	},
	f_doAppend: function(event) {
	
		if (!this._running) {
			return;
		}
		
		var events=this._events;
		if (!events) {
			events=new Array;
			this._events=events;
		}
		
		events.push(event);
		
		this._prepareTimer();
	},
	_sendEvents: function() {
		this._timerId=undefined;
		
		var events=this._events;
		if (!events || events.length<1) {
			return;
		}
		
		this._events=new Array;

		var params=new Object;

		for(var i=0;i<events.length;i++) {
			var event=events[i];
			
			if (event.name) {
				params["name"+i]=event.name;
			}
			if (event.level) {
				params["level"+i]=event.level;
			}
			if (event.message) {
				params["message"+i]=event.message;
			}
			if (event.fileName) {
				params["fileName"+i]=event.fileName;
			}
			if (event.lineNumber) {
				params["lineNumber"+i]=event.lineNumber;
			}
			if (event.date) {
				params["date"+i]=event.date.getTime();
			}
			var ex=event.exception;
			if (ex) {
				if (typeof(ex)!="string") {
					ex=ex.toString();
				}
				
				params["exception"+i]=ex;
			}
		}

		try {
			this._sending=true;
			
			window._ignoreLog=true;
			
			var url=f_env.GetViewURI();
			var request=f_httpRequest.f_newInstance(document, url, null, true);
			
			var self=this;
			
			request.f_setListener({
				/**
				 * @method public
				 */
		 		onError: function() {
					self._sending=undefined;
		 			self._prepareTimer();
		 		},
				/**
				 * @method public
				 */
		 		onLoad: function() {			
					self._sending=undefined;
		 			self._prepareTimer();
		 		}			
			});
			
			request.f_setRequestHeader("X-Camelia", "log.append");
			request.f_doFormRequest(params);
			
		} finally {
			window._ignoreLog=undefined;
		}
	},
	_prepareTimer: function() {
		var events=this._events;
		if (!events || 
				events.length<1 || 
				this._timerId!==undefined || 
				this._sending) {
			return;
		}
		
		var self=this;
		
		this._timerId=window.setTimeout(function() {
			self._sendEvents.call(self);
		}, f_serverAppender._TIMER);
	}
}

var f_serverAppender=new f_class("f_serverAppender", null, __static, __prototype);
