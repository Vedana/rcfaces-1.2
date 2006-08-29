/*
 * $Id$
 */

/**
 * 
 * @class public f_dataList extends f_component, fa_pagedComponent
 * @author Olivier Oeuillot
 * @version $Revision$
 */

var __static = {
	/**
	 * @method private static
	 */
	_InitializeScrollbars: function(dataList) {
		if (!dataList._scrollBody) {
			return;
		}
		
		var pos=dataList._initialHorizontalScrollPosition;
		if (pos) {
			dataList._scrollBody.scrollLeft=pos;
			if (dataList._scrollTitle) {
				dataList._scrollTitle.scrollLeft=pos;
			}
		}
		
		pos=dataList._initialVerticalScrollPosition;
		if (pos) {
			dataList._scrollBody.scrollTop=pos;
			if (dataList._scrollTitle) {
				dataList._scrollTitle.scrollTop=pos;
			}
		}
	}
}
 
var __prototype = {
	
	f_dataList: function() {
		this.f_super(arguments);

		this._className=f_core.GetAttribute(this, "v:className");
		if (!this._className) {
			this._className=this.className;
		}
	
		this._scrollBody=f_core.GetFirstElementByTagName(this, "TABLE");
		
		this._tbody=f_core.GetFirstElementByTagName(this._scrollBody, "TBODY");
	},
	f_finalize: function() {
		this._scrollBody=undefined;
		this._tbody=undefined;
		this._className=undefined;  // string

		this._nextCommand=undefined; // function

		this._loading=undefined;
		this._waiting=undefined;

		this._oldHeight=undefined;
		this._oldHeightStyle=undefined;
		
		this.f_super(arguments);
	},
	f_update: function() {
		this.f_super(arguments);
		
		this.f_performPagedComponentInitialized();
		
		if (!this.f_isVisible()) {
			this.f_getClass().f_getClassLoader().addVisibleComponentListener(this);			
		}
	},
	f_documentComplete: function() {
		this.f_super(arguments);

	//	this._documentComplete=true;

		if (!this.f_isVisible()) {
			return;
		}
		
		this.f_performComponentVisible();
	},
	/**
	 * @method hidden
	 */
	f_performComponentVisible: function() {
		
		f_dataList._InitializeScrollbars(this);		

		if (this._interactiveShow) {
			this.f_setFirst(this._first, this._currentCursor);			
		}
	},
	/**
	 * Specify the index of the first row which starts the grid.
	 *
	 * @method public
	 * @param number index
	 * @param number cursorIndex The cursor index. (can be undefined)
	 * @return boolean Returns <code>false</code>.
	 */
	f_setFirst: function(index, cursorIndex, jsEvent) {
		var oldFirst=this._first;
		
		this.f_setProperty(f_prop.FIRST, index);
	
		if (this._interactive) {
			this._appendCommand(function(dataGrid) {
				dataGrid._callServer(index, cursorIndex);
			});
			
			return false;
		}

		f_core._Submit(null, this, f_event.CHANGE);
			
		return false;
	},
	_appendCommand: function(callBack) {
		if (!this._loading) {
			callBack.call(this, this);
			return;
		}
		
		this._nextCommand=callBack;
	},
	_processNextCommand: function() {
		var nextCommand=this._nextCommand;
		if (!nextCommand) {
			return;
		}
		
		this._nextCommand=undefined;
		
		nextCommand.call(this, this);
	},
	_callServer: function(firstIndex, cursorIndex) {
//		f_core.Assert(!this._loading, "Already loading ....");
		
		var params=new Object;
		params.dataListId=this.id;
		params.index=firstIndex;
		
		var filterExpression=this.f_getProperty(f_prop.FILTER_EXPRESSION);
		if (filterExpression) {
			params.filterExpression=filterExpression;
		}

		if (true) {
			var tbody=this._tbody;

			var scrollBody=this._scrollBody;
			if (!this._oldHeight) {
				this._oldHeight=true;
				this._oldHeightStyle=this.style.height;
				this.style.height=this.offsetHeight+"px";
			}
			
			if (tbody) {	
				while (tbody.hasChildNodes()) {
					tbody.removeChild(tbody.lastChild);
				}
				
				this.f_getClass().f_getClassLoader().garbageObjects();
			}
		}		

		var url=f_env.GetViewURI();
		var request=f_httpRequest.f_newInstance(this, url, f_httpRequest.JAVASCRIPT_MIME_TYPE);
		var dataList=this;
		request.f_setListener({
			/**
			 * @method public
			 */
	 		onInit: function(request) {
	 			var waiting=dataList._waiting;
	 			if (!waiting) {	
	 				waiting=f_waiting.Create(dataList);
	 				dataList._waiting=waiting;
	 			}
	 			
	 			waiting.f_setText(f_waiting.GetLoadingMessage());
	 			waiting.f_show();
	 		},
			/**
			 * @method public
			 */
	 		onError: function(request, status, text) {
	 			f_core.Info(f_dataList, "Bad status: "+request.f_getStatus());
	 			
				if (dataList._nextCommand) {
					dataList._processNextCommand();
					return;
				}

				dataList._loading=false;		
				
				var waiting=dataList._waiting;
				if (waiting) {
					waiting.f_hide();
				}
	 		},
			/**
			 * @method public
			 */
	 		onProgress: function(request, content, length, contentType) {
	 			var waiting=dataList._waiting;
				if (waiting) {
					waiting.f_setText(f_waiting.GetReceivingMessage());
				}	 			
	 		},
			/**
			 * @method public
			 */
	 		onLoad: function(request, content, contentType) {			
				if (dataList._nextCommand) {
					dataList._processNextCommand();
					return;
				}

	 			var waiting=dataList._waiting;
				try {
					if (request.f_getStatus()!=f_httpRequest.OK_STATUS) {
						f_core.Error(f_dataList, "Bad Status ! ("+request.f_getStatusText()+")");
						return;
					}
	
					var responseContentType=request.f_getResponseContentType();
					if (responseContentType.indexOf(f_httpRequest.JAVASCRIPT_MIME_TYPE)<0) {
						f_core.Error(f_dataList, "Unsupported content type: "+responseContentType);
						return;
					}
				
					var ret=request.f_getResponse();
					
					//alert("ret="+ret);
					eval(ret);
					
				} finally {				
					dataList._loading=false;

					if (waiting) {
						waiting.f_hide();
					}
				}
	
				var event=new f_event(dataList, f_event.CHANGE);
				try {
					dataList.f_fireEvent(event);
					
				} finally {
					f_classLoader.Destroy(event);
				}
	 		}			
		});

		this._loading=true;
		request.f_setRequestHeader("X-Camelia", "dataList.update");
		request.f_doFormRequest(params);
	},
	_startNewPage: function(rowIndex) {
		// Appeler par la génération du serveur !

		var scrollBody=this._scrollBody;
		if (this._oldHeight) {
			this.style.height=this._oldHeightStyle;
			this._oldHeight=undefined;
			this._oldHeightStyle=undefined;
		}

		var tbody=this._tbody;
		if (tbody) {
			while (tbody.hasChildNodes()) {
				tbody.removeChild(tbody.lastChild);
			}	
		}
		
		this._first=rowIndex;
		
		this._componentUpdated=false;
	},
	_updateNewPage: function(rowCount, buffer) {
		// Appeler par la génération du serveur !

		var component=this._tbody;

		if (f_core.IsInternetExplorer()) {
			var b=this._scrollBody.outerHTML;
			
			var pos=b.lastIndexOf("</TBODY>");
			
			buffer=b.substring(0, pos)+buffer+b.substring(pos);
			
			this.removeChild(this._scrollBody);
			
			component=this;
		}
		
		this.f_getClass().f_getClassLoader()._load(this, component, buffer);

		if (component==this) {
			this._scrollBody=f_core.GetFirstElementByTagName(this, "TABLE");
			
			this._tbody=f_core.GetFirstElementByTagName(this._scrollBody, "TBODY");
		}

		if (this._rowCount<0) {
			if (this._maxRows<this._first+this._rows) {
				this._maxRows=this._first+this._rows;
			}
		}

		this._componentUpdated=true;

		if (this._interactiveShow) {
			this._interactiveShow=undefined;
		}

		this.f_performPagedComponentInitialized();
	},
	_cancelServerRequest: function() {
		// Appeler par la génération du serveur !
	},
	_a_updateFilterProperties: function(filterProperties) {
		if (!this._interactive) {
			return false;
		}
		
		this._appendCommand(function(dataGrid) {
			if (dataGrid._rows>0) {
				// Page par page !
				// On ne sait plus le nombre de lignes ...
				dataGrid._rowCount=-1;
				dataGrid._maxRows=dataGrid._rows;
			}
			
			dataGrid._callServer(0);
		});
		
		return false;
	}
}
 
var f_dataList=new f_class("f_dataList", null, __static, __prototype, f_component, fa_pagedComponent);
