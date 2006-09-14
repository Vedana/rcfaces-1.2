/*
 * $Id$
 */

/**
 * Classe Image.
 *
 * @class f_image extends f_component
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __prototype = {

	/**
	 * Returns the url of the image.
	 * 
	 * @method public
	 * @return string
	 */
	f_getImageURL: function() {
		return this.src;
	},
	/**
	 * Set the url of the image.
	 * 
	 * @method public
	 * @param string url
	 * @return void
	 */
	f_setImageURL: function(url) {
		this.src = url;
		this.f_setProperty(f_prop.IMAGE_URL, url);
	}
}
 
var f_image=new f_class("f_image", null, null, __prototype, f_component);
