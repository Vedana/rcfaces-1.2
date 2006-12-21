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
	 * @field private static final string
	 */
	_BLANK_IMAGE_URL: "/blank.gif",


	f_image: function() {
		this.f_super(arguments);
		
		this._className=this.className;
		
		this._imageURL=(f_core.GetAttribute(this, "v:blank"))?null:this.src;
	},

	f_finalize: function() {
//		this._imageURL=undefined; // string
//		this._loading=undefined; // boolean
		this._waiting=undefined; // f_waiting
//		this._oldWidth=undefined; // string
//		this._className=undefined; // string

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
		f_core.Assert(url===null || typeof(url)=="string", "Invalid imageURL parameter. ("+url+")");

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
		if (!window.f_httpRequest) {
			f_core.Error(f_image, "Class f_httpRequest is not defined !");
			return;
		}

		this.className=this._className+"_loading";
 	
		var params=new Object;
		params.componentId=this.id;
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}
	
		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
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
	 			f_core.Info(f_image, "Bad status: "+request.f_getStatus());
	 			
				if (image.f_processNextCommand()) {
					return;
				}
	 		
				image._loading=false;		
				
				image.className=image._className;
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
					image.className=image._className;

					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_image, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
	
					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
						f_core.Error(f_image, "Unsupported content type: "+responseContentType);
						return;
					}

					var ret=request.f_getResponse();
					try {
						eval(ret);
						
					} catch (x) {
						f_core.Error(f_image, "Can not eval response '"+ret+"'.", x);
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
 
var f_image=new f_class("f_image", null, null, __prototype, f_component, fa_filterProperties, fa_commands);
