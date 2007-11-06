/*
 * $Id$
 */
 
/**
 * Aspect BorderType
 *
 * @aspect abstract fa_borderType
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __statics = {
	
	/** 
	 * @field private static final String 
	 */
	_BORDER_ID_SUFFIX: "::border",
	
	/** 
	 * @field hidden static final String 
	 */
	NONE_BORDER_TYPE: "none",

	/** 
	 * @field private static fa_borderType
	 */
	_LastFlatBorder: undefined,

	/**
	 * @method hidden static
	 */
	GetCurrentBorder: function() {
		return fa_borderType._LastFlatBorder;
	},

	/**
	 * @method protected static
	 * @return void
	 */
	Finalizer: function() {
		fa_borderType._LastFlatBorder=undefined;
	}
}

var __members = {

	f_finalize: function() {
		// this._borderType=undefined; // string
		// this._flatType=undefined; // string
		
		var border=this._border;
		if (border) {
			this._border=undefined;

			// border._className=undefined; // string
			border.f_link=undefined;
			
			this.fa_borderFinalizer(border);
		
			f_core.VerifyProperties(border);
		}
	},
	/**
	 * Returns the border type name.
	 *
	 * @method public
	 * @return String
	 */
	f_getBorderType: function() {
		if (this._borderType!==undefined) {
			return this._borderType;
		}
		
		// Appel depuis le constructor de l'objet !

		var borderType=null;
		
		var v_borderType=f_core.GetAttribute(this, "v:borderType");
		if (v_borderType && v_borderType!=fa_borderType.NONE_BORDER_TYPE) {
			var border=this.ownerDocument.getElementById(this.id+fa_borderType._BORDER_ID_SUFFIX);
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
				
				this._flatType=f_core.GetBooleanAttribute(this, "v:flatMode", false);
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
			return null;
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
		var lastFlat=fa_borderType._LastFlatBorder;
		if (lastFlat && f_class.IsObjectInitialized(lastFlat)) {
			if (lastFlat==this) {
				return;
			}
			
			fa_borderType._LastFlatBorder=undefined;

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
		
		fa_borderType._LastFlatBorder=this;
	},
	
	/**
	 * @method protected abstract
	 * @param HTMLElement border
	 * @return void
	 */
	fa_borderFinalizer: f_class.ABSTRACT
}

new f_aspect("fa_borderType", {
	statics: __statics,
	members: __members
});
	
