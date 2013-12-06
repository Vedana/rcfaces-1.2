/*
 * $Id$
 */

/**
 * Class fsvg_svg.
 * 
 * @class fsvg_svg extends f_box
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field public static final String
	 */
	SVG_XMLNS : "http://www.w3.org/2000/svg",
	
	/**
	 * @field public static final String
	 */
	XLINK_XMLNS: "http://www.w3.org/1999/xlink"
}

var __members = {
	fsvg_svg: function() {
		this.f_super(arguments);
		
	},
	f_finalize: function() {
		this._delayedStyleSheets=undefined;
		this._delayedStyles=undefined;
		
		this.f_super(arguments);
	},
	/**
	 * @method public
	 * @return Document
	 */
	f_getSVGDocument : function() {
		return this.contentDocument;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getRootElement : function(id) {
		return this.f_getSVGDocument().rootElement;
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_getElementById : function(id) {
		return this.f_getSVGDocument().getElementById(id);
	},
	/**
	 * @method public
	 * @return Element
	 */
	$ : function(expr) {
		return this.f_querySelector(expr);
	},
	/**
	 * @method public
	 * @return Element
	 */
	f_querySelector : function(expr) {
		return this.f_getSVGDocument().querySelector(expr);
	},
	/**
	 * @method public
	 * @return Element[]
	 */
	f_querySelectorAll : function(expr) {
		return this.f_getSVGDocument().querySelectorAll(expr);
	},

	/**
	 * @method public
	 * @param String
	 *            url URL of stylesheet
	 * @return void
	 */
	f_appendStyleSheet : function(url) {
		var doc = this.f_getSVGDocument();
		
		if (!doc) {
			var dss=this._delayedStyleSheets;
			
			if (!dss) {
				dss=[];
				this._delayedStyleSheets=dds;
			}
			
			dds.push(url);
			return;
		}
		
		url=f_env.ResolveContentUrl(url, window);

		var pi = doc.createProcessingInstruction('xml-stylesheet', 'href="'
				+ url + '" type="text/css"');

		doc.insertBefore(pi, doc.firstChild);
	},
	/**
	 * @method public
	 * @param String
	 *            styles
	 * @return void
	 */
	f_appendSVGStyle : function(styles) {
		var doc = this.f_getSVGDocument();
		
		if (!doc) {
			var dss=this._delayedStyles;
			
			if (!dss) {
				dss=[];
				this._delayedStyles=dds;
			}
			
			dds.push(styles);
			return;
		}

		var pi = doc.createElementNS(fsvg_svg.SVG_XMLNS, "style");
		pi.setAttributeNS(null, "type", "text/css");

		var pt = doc.createTextNode(styles);
		pi.appendChild(pt);

		doc.rootElement.insertBefore(pi, doc.rootElement.firstChild);
	},
	f_update : function() {

		var svgDocument = this.f_getSVGDocument();
		if (svgDocument) {
			this.f_updateSVG(svgDocument);
			
		} else {
			var self=this;
			
			this.addEventListener("load",function() {
				self.removeEventListener("load", arguments.callee, false);

				var doc = self.f_getSVGDocument();
				
				self.f_updateSVG(doc);
				
			 }, false);
		}

		this.f_super(arguments);
	},
	f_updateSVG : function(svgDocument) {
		var dds=this._delayedStyleSheets;
		if (dds) {
			this._delayedStyleSheets=undefined;

			for(var i=0;i<dds.lenght;i++) {
				this.f_appendStyleSheet(dds[i]);
			}
		}

		var ds=this._delayedStyles;
		if (ds) {
			this._delayedStyles=undefined;

			for(var i=0;i<ds.lenght;i++) {
				this.f_appendSVGStyle(ds[i]);
			}
		}
	}
}

new f_class("fsvg_svg", {
	extend : f_box,
	members : __members,
	statics : __statics
});
