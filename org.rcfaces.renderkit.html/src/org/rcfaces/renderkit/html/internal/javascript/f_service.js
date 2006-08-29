/*
 * $Id$
 */
 
/**
 * Service class.
 *
 * @class public f_service extends f_object, fa_serializable, fa_eventTarget, fa_filterProperties, fa_commands
 */
var __static= {
	/**
	 * @field private static final String
	 */
	_CAMELIA_CONTENT_TYPE: "X-Camelia-Content-Type",
	
	/**
	 * @field public static final number
	 */
	INIT_STATE: 0,
	
	/**
	 * @field public static final number
	 */
	REQUESTING_STATE: 1,
	
	/**
	 * @field public static final number
	 */
	LOADING_STATE: 10,
	
	/**
	 * @field public static final number
	 */
	LOADED_STATE: 20,
	
	/**
	 * @field public static final number
	 */
	ERRORED_STATE: 21,

	/**
	 * @field private static final number
	 */
	_TOTAL_WORK_PROGRESS_MONITOR: 20,
	
	/**
	 * @field private static final number
	 */
	_INIT_PROGRESS_MONITOR: 1,

	/**
	 * @field private static final number
	 */
	_LOADING_PROGRESS_MONITOR: 1,

	/**
	 * @field private static final number
	 */
	_LOADED_PROGRESS_MONITOR: 1,

	/**
	 * @field private static number
	 */
	_Id: 0
	
}

var __prototype={

	f_service: function() {
		this.f_super(arguments);
		
		this._serviceId=f_core.GetAttribute(this, "v:serviceId");
	},
	f_finalize: function() {
		// this._serviceId=undefined;  // string
		// this._loading=undefined; // boolean
		this._requests=undefined; // object[]
		// this._progressing=false; // boolean
		
		this.f_super(arguments);
	},
	_setRequestState: function(requestId, state) {
		var requests=this._requests;
		if (!requests) {
			requests=new Object;
			this._requests=requests;
		}

		requests[requestId]=state;		
	},
	/**
	 * @method public
	 * @param string requestId Request identifier. (returned by f_asyncCall() )
	 * @return number State of the request, or -1 if the request is unknown !
	 * @see #f_asyncCall
	 */
	f_getRequestState: function(requestId) {
		var requests=this._requests;
		if (!requests) {
			return -1;
		}
		
		var state=requests[requestId];
		if (state===undefined) {
			return -1;
		}
		
		return state;
	},
	/**
	 * @method public
	 * @return string
	 */
	f_getServiceId: function() {
		return this._serviceId;
	},
	/**
	 * @method hidden
	 * @return string
	 */
	_allocateRequestId: function() {
		return (f_service._Id++)+"."+(new Date().getTime())*Math.random();
	},
	/**
	 * @method public
	 * @param function resultCallback Callback which will be called, when the result has been received.
	 * @param any parameter Parameters of the request.
	 * @param f_progressMonitor progressMonitor Progress monitor associated to the call.
	 * @return string Request identifier.
	 */
	f_asyncCall: function(resultCallback, parameter, progressMonitor) {
		var requestId=this._allocateRequestId();
		
		this._setRequestState(requestId, f_service.INIT_STATE);
		
		this.f_appendCommand(function(service) {			
			service._asyncCallServer(requestId, resultCallback, parameter, progressMonitor);
		});
		
		return requestId;
	},
	/**
	 * @method public
	 * @param any parameter Parameters of the request. (Supported types: String, object, and number)
	 * @param f_progressMonitor progressMonitor Progress monitor associated to the call.
	 * @return any Result of request.
	 */
	f_syncCall: function(parameter, progressMonitor) {
		var requestId=this._allocateRequestId();
		
		if (progressMonitor && window.f_subProgressMonitor) {
			progressMonitor=f_subProgressMonitor.f_newInstance(progressMonitor, f_service._TOTAL_WORK_PROGRESS_MONITOR);
		}
		
		this._setRequestState(requestId, f_service.INIT_STATE);

		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url);
		var params=this._prepareRequest(request, requestId, parameter);

		var ret=this._sendRequest(request, params, progressMonitor);
		
		if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
			f_core.Error(f_service, "Bad Status ! ("+request.f_getStatusText()+")");
			this._setRequestState(requestId, f_service.ERRORED_STATE);

			throw new Error("Bad status of http response '"+request.f_getStatus()+"'.");
		}

		if (request.f_isXmlResponse()) {
			this._setRequestState(requestId, f_service.LOADED_STATE);
			return request.f_getXmlResponse();
		}
		
		var content=request.f_getResponse();

		var state;
		var contentType=request.f_getResponseHeader(f_httpRequest.HTTP_CONTENT_TYPE);
		if (contentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)>=0) {
			content=eval(content);
			
			state = f_service.ERRORED_STATE;
			
		} else {					
			content = this.f_decodeResponse(content, contentType, request);

			state = f_service.LOADED_STATE;
		}
		
		this._setRequestState(requestId, state);
		
		return content;
	},
	/**
	 * @method public
	 */
	f_getRequestState: function(requestId) {
	},
	_prepareRequest: function(request, requestId, parameter) {
		var params;
		var type;		
				
		if (parameter==null) {
			type="null";

		} else if (typeof(parameter)=="object") {	
			if (parameter.nodeType==9) {
				// OK !
				type="xml";
				params=parameter;
				
			} else {
				type="object";
				params=new Object;
				params.type=type;
				params.data=f_core.EncodeObject(parameter);
			}
		} else {
			type="string";
			params=new Object;
			params.type=type;
			params.string=String(parameter);
		}
	
		if (!type) {
			var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);

			params.filterExpression=filterExpression;
		}
		
		request.f_setRequestHeader("X-Camelia", "client.newService");
		request.f_setRequestHeader("X-Camelia-Request-Id", requestId);
		request.f_setRequestHeader("X-Camelia-Component-Id", this.id);
		if (type) {
			request.f_setRequestHeader(f_service._CAMELIA_CONTENT_TYPE, type);
		}
		
		return params;
	},
	/**
	 * @method private
	 * @return void
	 */
	_asyncCallServer: function(requestId, resultCallback, parameter, progressMonitor) {
		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url);
		var params=this._prepareRequest(request, requestId, parameter);
		
		var subProgressMonitor;
		if (progressMonitor && window.f_subProgressMonitor) {
			subProgressMonitor=f_subProgressMonitor.f_newInstance(progressMonitor, f_service._TOTAL_WORK_PROGRESS_MONITOR);
		}
		
		this._progressing=false;
		
		var service=this;
		request.f_setListener({
			/**
			 * @method public
			 */
			onInit: function(request) {						
				service._setRequestState(requestId, f_service.REQUESTING_STATE);
				
				if (subProgressMonitor) {
					subProgressMonitor.f_work(f_service._INIT_PROGRESS_MONITOR);
				}
			},
			/**
			 * @method public
			 */
			onProgress: function(request) {			
				if (service._progressing) {
					return;
				}
				service._progressing=true;
							
				service._setRequestState(requestId, f_service.LOADING_STATE);

				if (subProgressMonitor) {
					subProgressMonitor.f_work(f_service._LOADING_PROGRESS_MONITOR);
				}
			},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (subProgressMonitor) {
					subProgressMonitor.f_work(f_service._LOADED_PROGRESS_MONITOR);
				}

	 			var loading=false;
				try {
					var state = f_service.LOADED_STATE;
					
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_service, "Bad Status ! ("+request.f_getStatusText()+")");
						state = f_service.ERRORED_STATE;
						content = undefined;
						
					} else if (contentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)>=0) {
						eval(content);
						
						content=null;
						state = f_service.ERRORED_STATE;
						
					} else {					
						content = service.f_decodeResponse(content, contentType, request);
					}

					service._setRequestState(requestId, state);
				
					try {
						resultCallback.call(service, state, parameter, content);

					} catch (x) {
						f_core.Error(f_service, "Call of callback throws an exception : "+resultCallback+".", x);
					}
					
					if (subProgressMonitor) {
						subProgressMonitor.f_done();
					}			
					
					if (service.f_processNextCommand()) {
						loading=true;
						return;
					}					
				
				} finally {
					service._loading=loading;	
				}
	 		},
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
				var state=f_service.ERRORED_STATE;

				service._setRequestState(requestId, state);
			
				try {
					resultCallback.call(service, state, parameter, undefined);

				} catch (x) {
					f_core.Error(f_service, "Call of callback throws an exception : "+resultCallback+".", x);
				}

	 			f_core.Info(f_service, "Bad status: "+request.f_getStatus());

				if (subProgressMonitor) {
					subProgressMonitor.f_done();
				}			
	 			
				if (service.f_processNextCommand()) {
					return;
				}
	 		
				service._loading=undefined;		
	 		}
		});

		this._loading=true;
		this._sendRequest(request, params, progressMonitor);
	},
	/**
	 * @method private
	 * @param Object request
	 * @param any params
	 * @param f_progressMonitor progressMonitor
	 * @return Object f_doFormRequest call result.
	 */	
	_sendRequest: function(request, params, progressMonitor) {			
		var ctype=f_httpRequest.TEXT_PLAIN_MIME_TYPE;
		if (params && params.nodeType==9) {
			ctype=f_httpRequest.TEXT_XML_MIME_TYPE;
		}
		
		ctype+="; charset=UTF-8";
		
		request.f_setAcceptType(ctype);
		
		return request.f_doFormRequest(params, progressMonitor);
	},
	
	/**
	 * @method protected
	 */
	f_decodeResponse: function(content, contentType, request) {
		if (contentType.indexOf(f_httpRequest.XML_MIME_TYPE)>=0) {
			// Le content doit deja etre un Document !
			return content;
		}
	
		if (contentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)>=0) {
			return content;
		}
		
		var cameliaContentType=request.f_getResponseHeader(f_service._CAMELIA_CONTENT_TYPE);
//		alert("CameliaContent="+cameliaContentType);
		
		switch(cameliaContentType) {
		case "object":
			return f_core.DecodeObject(content, true);

		case "null":
			return null;
		}

		return content;
	},
	/**
	 * @method public
	 * @param string id Identifier of component.
	 * @return HTMLElement
	 */
	f_findComponent: function(id) {
		return fa_namingContainer.FindComponents(this, arguments);
	}
}
 
var f_service = new f_class("f_service", null, __static, __prototype, f_object, fa_serializable, fa_eventTarget, fa_filterProperties, fa_commands);
