/*
 * $Id$
 */
 
/**
 * Aspect AsyncRender
 *
 * @aspect hidden abstract fa_asyncRender
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	fa_asyncRender: function() {
		this._interactive=f_core.GetBooleanAttribute(this, "v:asyncRender");
	},
	f_finalize: function() {
//		this._interactive=undefined; // boolean
//		this._intLoading=undefined; // boolean
		this._intWaiting=undefined; // f_waiting
//		this._asyncDecoded=undefined; // boolean
	},
	f_updateVisibility: {
		after: function(visible) {
			if (visible) {
				this.f_prepare(false);
			}
		}
	},
	/**
	 * @method public
	 * @param Boolean synch Wait preparation if necessary.
	 * @param optional Function parent Function returns parent node.
	 * @return boolean <code>true</code> if component is prepared !
	 */
	f_prepare: function(synch, parent) {
		if (!this._interactive) {
			return true;
		}
		var component = this;
		
		this._interactive=undefined;
		window.setTimeout(function(){
 			if (window._rcfacesExiting) {
 				return false;
 			}

 			var lock = f_event.GetEventLocked(null, false, f_event.SUBMIT_LOCK); 			
 			if (lock) {
 				return;
 			}
 			component._callAsyncRender(parent);
		}, 12);		
		
		return false;
	},
	
	/**
	 * @method private
	 * @param component.
	 * @return void
	 */
	_callAsyncRender: function(parent) {
		if (window._rcfacesExiting) {
			return;
		}
		var component = this;
		var url=f_env.GetViewURI();
		var request=new f_httpRequest(component, url, f_httpRequest.TEXT_HTML_MIME_TYPE);
		
		if (!parent) {
			if (typeof(component.fa_getInteractiveParent)=="function") {
				parent=component.fa_getInteractiveParent();
			}
			
			if (!parent) {
				parent=component;
			}
		}
		
		if (!component.style.height || component.offsetHeight<f_waiting.HEIGHT) {
			component._removeStyleHeight=true;
			component.style.height=f_waiting.HEIGHT+"px";
		}
		
		request.f_setListener({
	 		onInit: function(request) {
	 			var waiting=component._intWaiting;
	 			if (!waiting) {	
	 				waiting=f_waiting.Create(parent);
	 				component._intWaiting=waiting;
	 			}
	 			
	 			waiting.f_setText(f_waiting.GetLoadingMessage());
	 			waiting.f_show();
	 		},
			/* *
			 * @method public
			 */
	 		onError: function(request, status, text) {
				component._intLoading=false;		
				
	 			var waiting=component._intWaiting;
				if (waiting) {
					waiting.f_hide();
				}
				f_core.Info(fa_asyncRender, "f_prepare.onError: Bad status: "+status);
				
				component.f_performAsyncErrorEvent(request, f_error.HTTP_ERROR, text);
	 		},
			/* *
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 			var waiting=component._intWaiting;
				if (waiting) {
					waiting.f_setText(f_waiting.GetReceivingMessage());
				}	 			
	 		},
			/* *
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {			
				if (component._removeStyleHeight) {
					component._removeStyleHeight=null;
					if (component.style.height==f_waiting.HEIGHT+"px") {
						component.style.height="auto";
					}
				}
	

	 			var waiting=component._intWaiting;
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						component.f_performAsyncErrorEvent(request, f_error.INVALID_RESPONSE_ASYNC_RENDER_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}

					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						component.f_performAsyncErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
					
					var ret=request.f_getResponse();
					//	alert("Ret="+ret);

					var responseContentType=request.f_getResponseContentType().toLowerCase();
					if (responseContentType.indexOf(f_error.APPLICATION_ERROR_MIME_TYPE)>=0) {
						var code=f_error.ComputeApplicationErrorCode(request);
				
				 		component.f_performErrorEvent(request, code, content);
						return;
					}
				
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)>=0) {
						try {
							f_core.WindowScopeEval(ret);
							
						} catch (x) {
				 			component.f_performAsyncErrorEvent(x, f_error.RESPONSE_EVALUATION_ASYNC_RENDER_ERROR, "Evaluation exception");
						}
						
						component.fa_contentLoaded(ret, responseContentType, parent);
						return;
					}
					
					if (responseContentType.indexOf(f_httpRequest.TEXT_HTML_MIME_TYPE)>=0) {
						if (waiting) {
							waiting.f_hide();
							waiting.f_close();
							waiting=null;
						}
						
						try {
							component.f_getClass().f_getClassLoader().f_loadContent(component, component, component.innerHTML+ret);
							
							component.fa_contentLoaded(ret, responseContentType, parent);
						} catch (x) {
				 			component.f_performAsyncErrorEvent(x, f_error.RESPONSE_EVALUATION_ASYNC_RENDER_ERROR, "Evaluation exception");
						}
						return;
					}
					
				 	component.f_performAsyncErrorEvent(request, f_error.RESPONSE_TYPE_ASYNC_RENDER_ERROR, "Unsupported content type: "+responseContentType);
					
				} finally {				
					component._intLoading=false;

					if (waiting) {
						waiting.f_hide();
					}
				}
	 		}			
		});

		component._intLoading=true;
		request.f_setRequestHeader("X-Camelia", "asyncRender.request");

		var	param={
			id: component.id
		};
		request.f_doFormRequest(param);
	},

	/**
	 * @method protected
	 */
	f_performAsyncErrorEvent: function(param, messageCode, message) {
		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
		
	/**
	 * @method protected
	 * @param String content
	 * @param String mimeType
	 * @param HTMLElement
	 * @return void
	 */
	fa_contentLoaded: function() {
		if (f_core.GetBooleanAttribute(this, "v:asyncDecode") && !this._asyncDecoded) {
			// On ajoute un tag comme quoi le composant est a décoder !
			
			this._asyncDecoded=true;
			
			var form=f_core.GetParentForm(this);
			
			f_core.CreateElement(form, "INPUT", {
				type: "hidden",
				name: "org.rcfaces.async.partial."+this.id,
				value: "true"
			});
		}
	},
	
	/**
	 * @method protected abstract
	 * @return HTMLElement
	 */
	fa_getInteractiveParent: f_class.ABSTRACT
}

new f_aspect("fa_asyncRender", {
	members: __members
});
