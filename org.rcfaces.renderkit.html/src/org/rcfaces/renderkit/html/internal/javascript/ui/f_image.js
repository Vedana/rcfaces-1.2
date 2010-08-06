/*
 * $Id$
 */

/**
 * Class f_image.
 *
 * @class f_image extends f_filtredComponent
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __members = {

	f_image: function() {
		this.f_super(arguments);
		
		this._imageURL=(f_core.GetAttribute(this, "v:blank"))?null:this.src;
	},

	f_finalize: function() {
//		this._imageURL=undefined; // string

		this.f_super(arguments);
	},

	/**
	 * Returns the url of the image.
	 * 
	 * @method public
	 * @return String
	 */
	f_getImageURL: function() {
		return this._imageURL;
	},
	/**
	 * Set the url of the image.
	 * 
	 * @method public
	 * @param String url
	 * @return void
	 */
	f_setImageURL: function(url) {
		f_core.Assert(url===null || typeof(url)=="string", "f_image.f_setImageURL: Invalid imageURL parameter. ("+url+")");

		this._imageURL = url;
		var u=url;
		if (!u) {
			u=f_env.GetBlankImageURL();
		}
		this.src=u;
		
		this.f_setProperty(f_prop.IMAGE_URL, url);
	},
	/**
	 * @method hidden
	 */
	f_setImageSize: function(width, height) {
		f_core.Assert(typeof(width)=="number", "f_image.f_setImageSize: Invalid width parameter ("+width+")");
		f_core.Assert(typeof(height)=="number", "f_image.f_setImageSize: Invalid height parameter ("+height+")");
		
		this.width=width;
		this.height=height;
		
		this.f_setProperty(f_prop.WIDTH, width);
		this.f_setProperty(f_prop.HEIGHT, height);
	},
	/**
	 * @method protected
	 */
	f_getServiceId: function() {
		return "image.request";
	}
};
 
new f_class("f_image", {
	extend: f_filtredComponent, 
	members: __members
});
