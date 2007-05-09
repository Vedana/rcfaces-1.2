/*
 * $Id$
 */

/**
 * Class f_image.
 *
 * @class f_image extends f_component, fa_filterProperties, fa_commands,
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {

	/**
	 * @field private static final String
	 */
	_BLANK_IMAGE_URL: "/blank.gif",


	f_image: function() {
		this.f_super(arguments);
		
		this._imageURL=(f_core.GetAttribute(this, "v:blank"))?null:this.src;
	},

	f_finalize: function() {
//		this._imageURL=undefined; // string
//		this._loading=undefined; // boolean
		this._waiting=undefined; // f_waiting
//		this._oldWidth=undefined; // string

		this.f_super(arguments);
	},

	/**
	 * Returns the url of the image.
	 * 
	 * @method public
	 * @return String
	 */
	f_getImageURL: function() {
		return this._imageURL;
	},
	/**
	 * Set the url of the image.
	 * 
	 * @method public
	 * @param String url
	 * @return void
	 */
	f_setImageURL: function(url) {
		f_core.Assert(url===null || typeof(url)=="string", "f_image.f_setImageURL: Invalid imageURL parameter. ("+url+")");

		this._imageURL = url;
		var u=url;
		if (!u) {
			u=f_env.GetStyleSheetBase()+f_image._BLANK_IMAGE_URL;
		}
		this.src=u;
		
		this.f_setProperty(f_prop.IMAGE_URL, url);
	},
	fa_updateFilterProperties: function() {
		this.f_appendCommand(function(image) {			
			image._callServer();
		});
	},
	/**
	 * @method private
	 */
	_callServer: function() {
		f_class.IsClassDefined("f_httpRequest", true);

		this.className=this.f_computeStyleClass("_loading");
 	
		var params=new Object;
		params.componentId=this.id;
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}
	
		var url=f_env.GetViewURI();
		var request=new f_httpRequest(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var image=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 		},	 		
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_image, "_callServer.onError: Bad status: "+request.f_getStatus());
	 			
				if (image.f_processNextCommand()) {
					return;
				}
	 		
				image._loading=false;		
				
				image.className=this.f_computeStyleClass();
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {
				if (image.f_processNextCommand()) {
					return;
				}
				
				try {
					image.className=this.f_computeStyleClass();

					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						image.f_performErrorEvent(request, f_error.INVALID_RESPONSE_SERVICE_ERROR, "Bad http response status ! ("+request.f_getStatusText()+")");
						return;
					}

					var cameliaServiceVersion=request.f_getResponseHeader(f_httpRequest.CAMELIA_RESPONSE_HEADER);
					if (!cameliaServiceVersion) {
						image.f_performErrorEvent(request, f_error.INVALID_SERVICE_RESPONSE_ERROR, "Not a service response !");
						return;					
					}
	
					var responseContentType=request.f_getResponseContentType();
					
					if (responseContentType.indexOf(f_error.ERROR_MIME_TYPE)>=0) {
				 		image.f_performErrorEvent(request, f_error.APPLICATION_ERROR, content);
						return;
					}

					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
				 		image.f_performErrorEvent(request, f_error.RESPONSE_TYPE_SERVICE_ERROR, "Unsupported content type: "+responseContentType);
						return;
					}

					var ret=request.f_getResponse();
					try {
						eval(ret);
						
					} catch (x) {
						f_core.Error(f_image, "_callServer.onLoad: Can not eval response '"+ret+"'.", x);
					}

				} finally {
					image._loading=undefined;	
				}
				
				/* A voir ! @TODO
				var event=new f_event(combo, f_event.CHANGE);
				try {
					image.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
				*/
	 		}
		});

		image._loading=true;
		request.f_setRequestHeader("X-Camelia", "image.request");
		request.f_doFormRequest(params);
	},
	/**
	 * @method protected
	 */
	f_performErrorEvent: function(param, messageCode, message) {

		return f_error.PerformErrorEvent(this, messageCode, message, param);
	},
	/**
	 * @method hidden
	 */
	fa_cancelFilterRequest: function() {
	},
	/**
	 * @method hidden
	 */
	f_setImageSize: function(width, height) {
		f_core.Assert(typeof(width)=="number", "f_image.f_setImageSize: Invalid width parameter ("+width+")");
		f_core.Assert(typeof(height)=="number", "f_image.f_setImageSize: Invalid height parameter ("+height+")");
		
		this.width=width;
		this.height=height;
		
		this.f_setProperty(f_prop.WIDTH, width);
		this.f_setProperty(f_prop.HEIGHT, height);
	}
}
 
new f_class("f_image", null, null, __prototype, f_component, fa_filterProperties, fa_commands);
