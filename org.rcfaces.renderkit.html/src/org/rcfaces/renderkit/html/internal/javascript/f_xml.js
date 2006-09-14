/*
 * $Id$
 */

/**
 * XML package
 * 
 * @class f_xml extends f_object
 * @author Joel Merlin
 * @version $Revision$ $Date$
 */
var __static = {

	/**
	 * @method public static
	 * @param Element element XML node.
	 * @param optional string tagName Name of the tag, or null.
	 * @param optional string attrName Name of an attribute or null.
	 * @param optional string attrValue A value for the specified attribute of null.
	 * @return Element[] An array of xml nodes.
	 */
	GetChildElements: function(element, tagName, attrName, attrValue) {
		var list = new Array;
		var acn = element.childNodes;
		if (!acn || acn.length==0) {
			return list;
		}
		for (var i=0; i<acn.length; i++) {
			var cn = acn[i];
			if (cn.nodeType!=1) {
				continue;
			}
			if (tagName && cn.nodeName!=tagName) {
				continue;
			}
			if (attrName) {
				var attr  = cn.getAttributeNode(attrName);
				if (!attr) {
					continue;
				}
				if (attrValue && attr.nodeValue!=attrValue) {
					continue;
				}
			}
			list.push(cn);
		}
		return list;
	},
	/**
	 * @method public static
	 * @param Object element XML node.
	 * @param optional string tagName Name of the tag. (or null)
	 * @return Object XML node or null.
	 */
	GetFirstChildElement: function(element, tagName) {
		var acn = element.childNodes;
		if (!acn || acn.length==0) {
			return null;
		}
		
		for (var i=0; i<acn.length; i++) {
			var cn = acn[i];
			if (cn.nodeType!=1) {
				continue;
			}
			if (tagName && cn.nodeName!=tagName) {
				continue;
			}
			return cn;
		}
		return null;
	},

	/**
	 * @method public static
	 * @param Object element XML node.
	 * @param any value Default value.
	 * @return any Value associated to the XML node.
	 */
	GetValue: function(element, value) {
		var acn = element.childNodes;
		if (!acn || acn.length==0) {
			return value;
		}
		
		var ret = null;
		for (var i=0; i<acn.length; i++) {
			var cn = acn[i];
			if (cn.nodeType==3 || cn.nodeType==4) {
				var v = cn.nodeValue;
				if (!v || v=="") {
					continue;
				}
				if (ret==null) {
					ret = String(v);
				} else {
					ret += v;
				}
				continue;
			}
		}
		return (ret==null)? value:ret; // @TODO .a_trim();
	},

	/**
	 * @method public static
	 * @return Object Empty XML DOM document.
	 */
	Object: function() {
		var dom=null;
		
		if (f_core.IsInternetExplorer()) {
			dom = new ActiveXObject("microsoft.XMLDOM");

		} else if (f_core.IsGecko()) {
			dom = document.implementation.createDocument("","",null);
		}
		
		if (!dom) {
			throw "f_xml: failed to create DOM object";
		}

		return dom;
	},

	/**
	 * @method public static
	 * @param string data XML content.
	 * @return Object XML Document.
	 */
	FromString: function(data) {
		var dom;
		
		if (f_core.IsInternetExplorer()) {
			dom = new ActiveXObject("microsoft.XMLDOM");
			dom.loadXML(data);

		} else if (f_core.IsGecko()) {
			dom = new DOMParser();
			dom = dom.parseFromString(data,"text/xml");
		}
		
		if (!dom) {
			throw "f_xml: failed to create DOM object from string";
		}

		return dom;
	},

	/**
	 * @method public static
	 * @param Object node XML node.
	 * @return string XML output.
	 */
	Serialize: function(node) {
		var ret = "";
		var acn = node.childNodes;
		if (!acn || acn.length==0) {
			return ret;
		}
		
		for (var i=0; i<acn.length; i++) {
			var cn = acn[i];
			var value=cn.nodeValue;
			
			switch(cn.nodeType) {
			case 1: // ELEMENT
				ret += "<"+cn.tagName;
				var attrs = cn.attributes;
				for (var n=0; n<attrs.length; n++) {
					var attr = attrs[n];
					ret += " "+attr.nodeName+"=\"";
					var v=attr.nodeValue;
					if (v) {
						ret+=v;
					}
					ret+="\"";
				}
				
				var content=f_xml.serialize(cn);
				if (content.length<1) {
					ret+="/>";
					break;
				}
				ret += ">"+content+"</"+cn.tagName+">";
				break;
			
			case 3: // Text node
				ret += value; 
				break;
				
			case 4: // CDATA
				ret += "<![CDATA["+value+"]]>";
				break;
			
			case 7: // XML prolog
				ret += "<?xml "+value+"?>"; 
				break;
				
			case 8: // Comment
				ret += "<!-- "+value+" -->"; 
				break;
				
			//case 2: // ATTRIBUTE
			//case 5: // ENTITY REF
			//case 6: // ENTITY
			//case 9: // DOCUMENT
			//case 10: ret += "<!DOCTYPE "+value+">"; break;
			//case 11: // FRAGMENT
			//case 12: // NOTATION
			default: 
				break;
			}
		}
		
		return ret;
	}
}

var f_xml=new f_class("f_xml", null, __static);
