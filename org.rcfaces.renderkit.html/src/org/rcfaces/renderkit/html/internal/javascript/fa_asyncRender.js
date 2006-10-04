/*
 * $Id$
 */
 
/**
 * Aspect AsyncRender
 *
 * @aspect hidden fa_asyncRender
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {
	fa_asyncRender: function() {
		var v_interactive=f_core.GetAttribute(this, "v:asyncRender");
		if (!v_interactive) {
			return;
		}

		this._interactive=true;	
	},
	f_finalize: function() {
//		this._interactive=undefined; // boolean
//		this._intLoading=undefined; // boolean
		this._intWaiting=undefined; // f_waiting
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
	 * @param boolean synch Wait preparation if necessery.
	 * @param optional Function parent Function returns parent node.
	 * @return boolean <code>true</code> if component is prepared !
	 */
	f_prepare: function(synch, parent) {
		if (!this._interactive) {
			return true;
		}
		this._interactive=undefined;
		
		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.TEXT_HTML_MIME_TYPE);
		var component=this;
		if (!parent) {
			if (typeof(this.fa_getInteractiveParent)=="function") {
				parent=this.fa_getInteractiveParent();
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
				f_core.Info(fa_asyncRender, "Bad status: "+request.f_getStatus());
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
						f_core.Error(fa_asyncRender, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
				
					var ret=request.f_getResponse();
					//	alert("Ret="+ret);

					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)>=0) {
						eval(ret);
						return;
					}
					
					if (responseContentType.indexOf(f_httpRequest.TEXT_HTML_MIME_TYPE)>=0) {
						if (waiting) {
							waiting.f_hide();
							waiting.f_close();
							waiting=null;
						}
						
						window._classLoader._load(component, component, component.innerHTML+ret);
						return;
					}
					
					f_core.Error(fa_asyncRender, "Unknown content type: "+responseContentType);
					
				} finally {				
					component._intLoading=false;

					if (waiting) {
						waiting.f_hide();
					}
				}
	 		}			
		});

		this._intLoading=true;
		request.f_setRequestHeader("X-Camelia", "asyncRender.request");

		var	param={
			id: this.id
		};
		request.f_doFormRequest(param);
		
		return false;
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getInteractiveParent: f_class.ABSTRACT
	
}

var fa_asyncRender=new f_aspect("fa_asyncRender", null, __prototype);
