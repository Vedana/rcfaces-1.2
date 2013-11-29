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
			f_core.Error(fsvg_svg, "f_appendStyleSheet: SVG document is null for stylesheet '"+url+"'");
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
			f_core.Error(fsvg_svg, "f_appendStyleSheet: SVG document is null for stylesheet '"+url+"'");
			return;
		}

		var pi = doc.createElementNS(fsvg_svg.SVG_XMLNS, "style");
		pi.setAttributeNS(null, "type", "text/css");

		var pt = doc.createTextNode(styles);
		pi.appendChild(pt);

		doc.rootElement.insertBefore(pi, doc.rootElement.firstChild);
	}
}

new f_class("fsvg_svg", {
	extend : f_box,
	members : __members,
	statics : __statics
});
