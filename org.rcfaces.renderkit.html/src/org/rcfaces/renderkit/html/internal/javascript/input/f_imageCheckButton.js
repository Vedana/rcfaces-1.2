/*
 * $Id$
 */

/**
 * class f_imageCheckButton
 *
 * @class f_imageCheckButton extends f_imageButton, fa_selected
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	
	/**
	 * @method protected
	 * @param f_event event
	 * @return boolean
	 */
	f_performImageSelection: function(event) {
		this.f_setSelected(!this.f_isSelected());
		return true;
	},
	fa_updateSelected: function() {
		this._updateImage();
	},
	/**
	 * @method protected
	 */
	f_serialize: function() {
		// Dans tous les cas, il faut renvoyer au serveur l'état
		// car il peut utiliser des Beans request !
		this.f_setProperty(f_prop.SELECTED,this.f_isSelected());

		this.f_super(arguments);
	}
}
new f_class("f_imageCheckButton", {
	extend: f_imageButton, 
	aspects: [fa_selected],
	members: __members
});
