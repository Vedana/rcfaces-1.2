/*
 * $Id$
 */

/**
 * Class fsvg_cursor
 *
 * @class fsvg_cursor extends Object
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	Show: function(element) {
		fsvg_cursor.Get().f_show(element);
	},
	Hide: function(element) {
		fsvg_cursor.Get().f_hide(element);		
	},
	Get: function() {
		var singleton=fsvg_cursor._Singleton;
		if (singleton) {
			return singleton;
		}
		singleton=new fsvg_cursor(document);
		fsvg_cursor._Singleton=singleton;
		
		return singleton;
	}
};

var __members = {
	fsvg_cursor: function(doc) {		
		this._document=doc;
		
		var elts=[];
		this._elements=elts;
		
		for(var i=0;i<4;i++) {
			elts[i]=f_core.CreateElement(doc.body, "DIV", {
				className: "fsvg_cursor fsvg_cursor_"+i,
				"role": "presentation",
				"aria-hidden": "true"
			});
		}
	},
	f_show: function(element) {
		var box=element.getBoundingClientRect();
		var position=f_core.GetAbsolutePosition(element);
		
		var x1=position.x+"px";
		var y1=position.y+"px";
		var w=Math.floor(box.width);
		var x2=(position.x+w)+"px";
		var h=Math.floor(box.height);
		var y2=(position.y+h)+"px";
		w=w+"px";
		h=h+"px";
		var elts=this._elements;
		
		var s1=elts[0].style;
		var s2=elts[1].style;
		var s3=elts[2].style;
		var s4=elts[3].style;
		
		s1.left=x1;
		s1.width=w;
		s1.top=y1;
		s1.display="block";
		
		s2.left=x2;
		s2.height=h;
		s2.top=y1;
		s2.display="block";
		
		s3.left=x1;
		s3.width=w;
		s3.top=y2;
		s3.display="block";
		
		s4.left=x1;
		s4.height=h;
		s4.top=y1;
		s4.display="block";
	},
	f_hide: function() {		
		var elts=this._elements;
		
		for (var i=0;i<4;i++) {
			elts[i].style.display="none";
		}
	}
}

new f_class("fsvg_cursor", {
	members: __members,
	statics: __statics
});
