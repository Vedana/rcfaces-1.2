/*
 * $Id$
 */
 
/**
 * Aspect Scroll positions. 
 *
 * @aspect public fa_scrollPositions
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __prototype = {

	/**
	 * @field private number
	 */
	_initialHorizontalScrollPosition: undefined,

	/**
	 * @field private number
	 */
	_initialVerticalScrollPosition: undefined,

	fa_scrollPositions: function() {			
		var hsp=f_core.GetAttribute(this, "v:hsp");
		if (hsp) {
			this._initialHorizontalScrollPosition=parseInt(hsp, 10);
		}
			
		var vsp=f_core.GetAttribute(this, "v:vsp");
		if (vsp) {
			this._initialVerticalScrollPosition=parseInt(vsp, 10);
		}
	},
/*
	f_finalize: function() {
//		this._initialHorizontalScrollPosition=undefined; // number
//		this._initialVerticalScrollPosition=undefined; // number
		
	},
	*/
	/**
	 * @method protected
	 */
	fa_initializeScrollBars: function() {
		var body=this.fa_getScrolledComponent();
		if (!body) {
			return;
		}
		var posx=this._initialHorizontalScrollPosition;
		if (posx) {
			this.f_setHorizontalScrollPosition(posx);
		}
		
		var posy=this._initialVerticalScrollPosition;
		if (posy) {
			this.f_setVerticalScrollPosition(posy);
		}
		
		if (f_core.IsDebugEnabled(fa_scrollPositions)) {
			f_core.Debug(fa_scrollPositions, "Set scrollposition x="+posx+" y="+posy+" for component '"+this.id+"' body='"+body.id+"/"+body.tagName+"'.");
		}
	},

	f_serialize: {
		before: function() {
			var body=this.fa_getScrolledComponent();
			if (body) {	
				this.f_setProperty(f_prop.HORZSCROLLPOS, body.scrollLeft);
				this.f_setProperty(f_prop.VERTSCROLLPOS, body.scrollTop);
			}
		}
	},
	/**
	 * Returns an integer value specifying the position of the vertical scroolbar (Browser dependant).
	 * 
	 * @method public 
	 * @return number vertical scroll position
	 */
	f_getVerticalScrollPosition: function() {
		var body=this.fa_getScrolledComponent();
		if (!body) {
			return -1;
		}
		
		return body.scrollTop;
	},
	/**
	 * Sets an integer value specifying the position of the vertical scroolbar (Browser dependant).
	 * 
	 * @method public 
	 * @param number position vertical scroll position
	 * @return void
	 */
	f_setVerticalScrollPosition: function(position) {
		f_core.Assert(typeof(position)=="number" && position>=0, "fa_scrollPositions.f_setVerticalScrollPosition: Invalid position parameter ("+position+")");

		var body=this.fa_getScrolledComponent();
		if (!body) {
			return;
		}
		
		body.scrollTop=position;

		var title=this.fa_getScrolledVerticalTitle();		
		if (title) {
			titlee.scrollTop=position;
		}
	},
	/**
	 * Returns an integer value specifying the position of the horizontal scroolbar (browser dependant).
	 *
	 * @method public 
	 * @return number horizontal scroll position 
	 */
	f_getHorizontalScrollPosition: function() {
		var body=this.fa_getScrolledComponent();
		if (!body) {
			return -1;
		}
		
		return body.scrollLeft;
	},
	/**
	 * Sets an integer value specifying the position of the horizontal scroolbar (browser dependant).
	 *
	 * @method public 
	 * @param number position horizontal scroll position 
	 * @return void
	 */
	f_setHorizontalScrollPosition: function(position) {
		f_core.Assert(typeof(position)=="number" && position>=0, "fa_scrollPositions.f_setHorizontalScrollPosition: Invalid position parameter ("+position+")");

		var body=this.fa_getScrolledComponent();
		if (!body) {
			return;
		}
		
		body.scrollLeft=position;
			
		var title=this.fa_getScrolledHorizontalTitle();		
		if (title) {
			title.scrollLeft=position;
		}
	},
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getScrolledComponent: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getScrolledHorizontalTitle: f_class.ABSTRACT,
	
	/**
	 * @method protected abstract
	 * @return void
	 */
	fa_getScrolledVerticalTitle: f_class.ABSTRACT
	
}

var fa_scrollPositions=new f_aspect("fa_scrollPositions", null, __prototype);
	