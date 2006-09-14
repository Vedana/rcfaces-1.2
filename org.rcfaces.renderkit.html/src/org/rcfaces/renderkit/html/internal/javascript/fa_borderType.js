/*
 * $Id$
 */
 
/**
 * Aspect BorderType
 *
 * @aspect fa_borderType
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __static = {
	
	/** 
	 * @field hidden static final string 
	 */
	NONE_BORDER_TYPE: "none"
}

var __prototype = {

	f_finalize: function() {
		this._borderType=undefined; // string
		this._flatType=undefined; // string
		
		var border=this._border;
		if (border) {
			this._border=undefined;

			border._className=undefined;
			border.f_link=undefined;
			
			this._a_borderFinalizer(border);
		
			f_core.VerifyProperties(border);
		}
	},
	/**
	 * Returns the border type name.
	 *
	 * @method public
	 * @return string
	 */
	f_getBorderType: function() {
		if (this._borderType!==undefined) {
			return this._borderType;
		}
		
		// Appel depuis le constructor de l'objet !

		var borderType=null;
		
		var v_borderType=f_core.GetAttribute(this, "v:borderType");
		if (v_borderType && v_borderType!=fa_borderType.NONE_BORDER_TYPE) {
			var border=f_core.GetFirstElementByTagName(this, "TABLE", false);
			borderType=v_borderType;
			
			if (border) {
	//			f_core.Assert(border, "Can not find border of component '"+this.id+"' (borderType='"+v_borderType+"').");
				this._border=border;
				
				border.f_link=this;
				
				var cl=f_core.GetAttribute(border, "v:className");
				if (cl) {
					border._className=cl;
	
				} else {
					border._className=border.className;
				}
				
				this._flatType=(f_core.GetAttribute(this, "v:flatMode")!=null);
			}
		}
	
		this._borderType=borderType;
		
		return borderType;
	},
	/**
	 *
	 * @method protected
	 * @return HTMLElement
	 */
	f_getBorderComponent: function() {
		if (!this.f_getBorderType()) {
			return;
		}
		
		return this._border;
	},
	/**
	 * @method protected
	 */	
	f_isFlatTypeBorder: function() {
		if (!this.f_getBorderType()) {
			return;
		}
		
		return this._flatType;
	},
	/**
	 * @method protected
	 */
	f_updateLastFlatBorder: function() {
		var lastFlat=window._lastFlatBorder;
		if (lastFlat && f_class.IsObjectInitialized(lastFlat)) {
			if (lastFlat==this) {
				return;
			}
			
			window._lastFlatBorder=undefined;

			//alert("Update last !");
			// Il s'est peut être passé un garbage ou qq chose du genre :
			// Soyons prudents !!!
			if (typeof(lastFlat._updateImage)=="function") {
				lastFlat._updateImage();
			}
		}
		
		if (!this.f_isFlatTypeBorder()) {
			return;
		}
		
		window._lastFlatBorder=this;
	},
	/**
	 * @method protected
	 */
	f_getLastFlatBorder: function() {
		return window._lastFlatBorder;
	},
	/**
	 * @method static hidden
	 */
	Finalizer: function() {
		window._lastFlatBorder=undefined;
	},
	
	/**
	 * @method protected
	 * @param HTMLElement border
	 * @return void
	 */
	_a_borderFinalizer: f_class.ABSTRACT
}

var fa_borderType=new f_aspect("fa_borderType", __static, __prototype);
	
