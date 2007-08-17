/*
 * $Id$
 */

/**
 * class f_imagePagerButton
 *
 * @class f_imagePagerButton extends f_imageButton, fa_pager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var __members = {
	f_imagePagerButton: function() {
		this.f_super(arguments);
		
		this._type=f_core.GetAttribute(this, "v:type");
		
		var forComponent=f_core.GetAttribute(this, "v:for");
		this._hideIfDisabled=f_core.GetBooleanAttribute(this, "v:hideIfDisabled", false);
		
		// this.f_setDisabled(true); // D'office !  on attend la synchro !
		// C'est fait sur le serveur !

		if (forComponent) {
			this._for=forComponent;
			fa_pagedComponent.RegisterPager(forComponent, this);
			
		} else  {
			f_core.Error(f_imagePagerButton, "f_imagePagerButton: 'for' attribute is not defined !");
		}
	},
	f_finalize: function() {
		this._pagedComponent=undefined;
		
		// this._for=undefined; // string
		// this._type=undefined; // string
		// this._hideIfDisabled=undefined; // boolean
		
		this.f_super(arguments);
	},
	fa_pagedComponentInitialized: function(pagedComponent) {
		this._pagedComponent=pagedComponent;
		
		var disabled=true;
		var type=this._type;
		
		var rows=pagedComponent.f_getRows();
		// rows = nombre de ligne affichée
		
		var rowCount=pagedComponent.f_getRowCount(); 
		// rowCount peut etre negatif, si on ne connait pas le nombre
		
		if (pagedComponent && type && rowCount && rows) {
			type=type.toLowerCase();
			
			var first=pagedComponent.f_getFirst();
			//var maxRows=pagedComponent.f_getMaxRows();
			// Nombre de ligne 

			switch(type) {
			case "first":
			case "prev":
				disabled=(first<1);
				break;
				
			case "next":
			case "last":
				disabled=(rowCount>0 && first+rows>=rowCount);
				break;			

			default:
				var pageN=parseInt(type, 10);
				if (!isNaN(pageN) && (first/rows)!=pageN) {
					disabled=(rowCount>=0 && pageN*rows>rowCount);
				}
			}
			
		}
		
		f_core.Debug(f_imagePagerButton, "fa_pagedComponentInitialized: Update image: id="+this.id+" type="+type+" disabled="+disabled+" first="+first+" rows="+rows+" rowCount="+rowCount);
		
		this.f_setDisabled(disabled);	
	},
	
	fa_updateDisabled: function() {
		this.f_super(arguments);
		
		if (this._hideIfDisabled) {
			this.f_setVisible(this.f_isDisabled());
		}
	},

	f_imageButtonSelect: function() {
		if (!this._focus)  {
			this.f_setFocus();
		}

		if (this.f_isReadOnly() || this.f_isDisabled()) {
			return false;
		}

		var type=this._type;
		if (!type) {
			return false;
		}

		var pagedComponent=this._pagedComponent;
		if (!pagedComponent) {
			return false;
		}

		var first=pagedComponent.f_getFirst();
		var rows=pagedComponent.f_getRows();

		var newFirst=-1;
				
		switch(type.toLowerCase()) {
		case "first":
			newFirst=0;
			break;
		
		case "prev":
			newFirst=first-rows;
			if (newFirst<0) {
				newFirst=0;
			}
			break;
		
		case "next":
			newFirst=first+rows;
			break;
		
		case "last":
			var maxRows=pagedComponent.f_getMaxRows();
			var rowCount=pagedComponent.f_getRowCount();

			if (rowCount>0) {
				newFirst=rowCount - ((rowCount+rows-1) % rows)-1;
	
			} else if (first+rows==maxRows) {
				newFirst=maxRows;
			
			} else {
				newFirst=maxRows - ((maxRows+rows-1) % rows)-1;
			}
			break;
		}

		if (newFirst>=0) {
			pagedComponent.f_setFirst(newFirst);
		}
		
		return false;
	}
}

new f_class("f_imagePagerButton", {
	extend: f_imageButton,
	aspects: [ fa_pager ],
	members: __members
});
