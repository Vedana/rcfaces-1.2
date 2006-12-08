/*
 * $Id$
 */
 
/** 
 * f_helpButton class
 *
 * @class f_helpButton extends f_imageButton
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __static = {
	/**
	 * @field private static final string
	 */
	_IMAGE: "/helpButton/helpButton.gif",

	/**
	 * @field private static final string
	 */
	_HOVER: "/helpButton/helpButtonHover.gif"
}

var __prototype = {
	f_helpButton: function() {
		this.f_super(arguments);

		if (!this.src) {
			var i = f_env.Get("HELPBUTTON_IMAGE_URL");
			if (!i) {
				i=f_env.GetStyleSheetBase()+ f_helpButton._IMAGE;
			}
			f_imageRepository.PrepareImage(i);
			this.f_setImageURL(i);
			
			var h = f_env.Get("HELPBUTTON_HOVER_URL");
			if (!h) {
				h=f_env.GetStyleSheetBase()+ f_helpButton._HOVER;
			}
			f_imageRepository.PrepareImage(h);
			this.f_setHoverImageURL(h);
		}
	},
	f_onSelect: function() {
		var component=this;
		
		var forId=f_core.GetAttribute(this, "v:for");
		if (forId) {
			
		}	
	
		f_help._Open(this);
		return false;
	}
}

var f_helpButton=new f_class("f_helpButton", null, __static, __prototype, f_imageButton);
