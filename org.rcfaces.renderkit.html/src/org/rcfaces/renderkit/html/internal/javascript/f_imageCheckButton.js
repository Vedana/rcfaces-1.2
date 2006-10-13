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
var __prototype = {

	_onSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}
		
		this.f_setSelected(!this.f_isSelected());
		
		return false;
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

		return this.f_super(arguments);
	}
}

var f_imageCheckButton=new f_class("f_imageCheckButton", null, null, __prototype, f_imageButton, fa_selected);
