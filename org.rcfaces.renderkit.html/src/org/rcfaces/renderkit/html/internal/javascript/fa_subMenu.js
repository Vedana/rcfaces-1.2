/*
 * $Id$
 */

/**
 * Aspect fa_SubMenu
 *
 * @aspect public fa_subMenu
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __prototype = {
	f_finalize: function() {
		this._subMenus=undefined; // Map<String,f_menu>
		// this._subMenuCount=undefined; // number
	},
	f_update: {
		after: function() {
			var subMenus=this._subMenus;
			if (subMenus) {
				for(var name in subMenus) {
					var menu=subMenus[name];
					
					menu.f_update();
				}
			}
		}
	},
	/**
	 * @method hidden
	 */
	f_newSubMenu: function(menuId, id, removeAllWhenShown, itemImageWidth, itemImageHeight) {
		if (!window.f_menu) {
			throw new Error("f_menu class has not beeen loaded !");
		}
		
		if (!id) {
			// La forme de menuId n'est peut-etre pas normalisée !
			
			var cnt=this._subMenuCount;
			if (!cnt) {
				cnt=0;
			}
			cnt++;
			this._subMenuCount=cnt;
			
			id=this.id+"__subMenu"+cnt;
		}

		var componentEventRedirect=this.fa_componentCaptureMenuEvent();
		
		var menu=f_menu.f_newInstance(this, componentEventRedirect, id, menuId, itemImageWidth, itemImageHeight);

		var subMenus=this._subMenus;
		if (!subMenus) {
			subMenus=new Object;
			this._subMenus=subMenus;
		}
		
		subMenus[menuId]=menu;

		f_core.Debug("fa_subMenu", "Define new menuId='"+menuId+"' id='"+id+"' for component '"+this.id+"'.");
		
		return menu;
	},
	/**
	 * List all menus associated to the component.
	 *
	 * @method public
	 * @return f_menu[]
	 */
	f_listSubMenus: function() {
		var l=new Array;

		if (!window.f_menu) {
			return l;
		}
		
		var subMenus=this._subMenus;
		if (!subMenus) {
			return l;
		}

		for(var name in subMenus) {
			var menu=subMenus[name];
			
			l.push(menu);
		}
		
		return l;
	},
	/**
	 * Returns the menu associated with an identifier.
	 * 
	 * @method public
	 * @param string menuId Identifier of menu
	 * @return f_menu The menu or <code>null</code>.
	 */
	f_getSubMenuById: function(menuId) {
		f_core.Assert(typeof(menuId)=="string", "fa_subMenu.f_getSubMenuById: MenuId parameter is not a string !");
		
		var subMenus=this._subMenus;
		if (!subMenus) {
			return null;
		}
		
		var menu=subMenus[menuId];

		return (menu)?menu:null;
	},
	
	/**
	 * @method protected
	 */
	fa_componentCaptureMenuEvent: f_class.ABSTRACT
}


var fa_subMenu=new f_aspect("fa_subMenu", null, __prototype);
