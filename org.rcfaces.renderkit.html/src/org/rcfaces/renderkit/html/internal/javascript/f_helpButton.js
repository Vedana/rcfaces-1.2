/*
 * $Id$
 *
 * $Log$
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:26  oeuillot
 * Renommage  en rcfaces
 *
 */
 
/** 
 * f_helpButton class
 *
 * @class f_helpButton extends f_imageButton
 * @author Olivier Oeuillot
 * @version $Revision$
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
		f_help._Open(this);
		return false;
	}
}

var f_helpButton=new f_class("f_helpButton", null, __static, __prototype, f_imageButton);
