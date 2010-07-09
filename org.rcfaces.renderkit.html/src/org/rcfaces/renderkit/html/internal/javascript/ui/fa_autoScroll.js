/*
 * $Id$
 */

/**
 * Aspect
 *
 * @aspect public abstract fa_autoScroll
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

var __statics = {
	/**
	 * @field private static final Number
	 */
	_AUTO_SCROLL_SIZE: 20
};

var __members = {

	f_finalize: function() {
		this.fa_uninstallAutoScroll();
	},
	
	/**
	 * @method protected
	 * @return void 
	 */
	fa_installAutoScroll: function() {
		
		var scrollableComponent=this.fa_getScrollableContainer();
		
		if (scrollableComponent.offsetWidth-scrollableComponent.clientWidth<2) { // @TODO Retire les BORDS
			return;
		}
	
		// Scrollable ... il faut surveiller le haut et le bas !
		
		var autoScrollIntervalId=this._autoScrollIntervalId;
		if (autoScrollIntervalId) {
			return;
		}
		
		var self=this;
		
		var treePosition=f_core.GetAbsolutePosition(scrollableComponent);

		this._autoScrollIntervalId=f_core.GetWindow(this).setInterval(function() {
			if (window._rcfacesExiting) {
				return false;
			}
			
			var pos=self.fa_getLastMousePosition();
			if (!pos) {
				return;
			}
			
			var dy=pos.y-treePosition.y;
			var dy2=treePosition.y+scrollableComponent.offsetHeight-pos.y;
			
			if (scrollableComponent.offsetWidth-scrollableComponent.clientWidth>2) {
				// Ajout du scroll horizontal en bas ...
				
				dy2-=(scrollableComponent.offsetHeight-scrollableComponent.clientHeight);
			}
			
			//document.title="dy2="+dy2+"  dd="+(this.offsetHeight-this.clientHeight);
			
			if (dy>=0 && dy<=fa_autoScroll._AUTO_SCROLL_SIZE) {
				var st=scrollableComponent.scrollTop;
				if (st>0) {
					st-=fa_autoScroll._AUTO_SCROLL_SIZE;
					if (st<0) {
						st=0;
					}
					
					scrollableComponent.scrollTop=st;
				}
				
			} else if (dy2>=0 && dy2<=fa_autoScroll._AUTO_SCROLL_SIZE) {
				var st=scrollableComponent.scrollTop;
				st+=fa_autoScroll._AUTO_SCROLL_SIZE;
				if (st<0) {
					st=0;
				}
				
				scrollableComponent.scrollTop=st;
			}
			
		}, 200);	
	},
	
	/**
	 * @method protected
	 * @return void 
	 */
	fa_uninstallAutoScroll: function() {
		
		var scrollIntervalId=this._autoScrollIntervalId;
		if (scrollIntervalId) {
			this._autoScrollIntervalId=undefined;
			
			f_core.GetWindow(this).clearInterval(scrollIntervalId);
		}
	},
	/**
	 * @method protected
	 * @return Object 
	 */
	fa_getLastMousePosition: f_class.ABSTRACT,
	
	/**
	 * @method protected
	 * @return HTMLElement 
	 */
	fa_getScrollableContainer: f_class.ABSTRACT
};

new f_aspect("fa_autoScroll", {
	members: __members,
	statics: __statics
});
