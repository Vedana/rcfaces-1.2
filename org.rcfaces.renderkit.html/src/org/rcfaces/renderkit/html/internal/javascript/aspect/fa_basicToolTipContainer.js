/**
 * Aspect Tooltip Container
 *
 * @aspect public fa_basicToolTipContainer  extends fa_toolTipContainer
 * @author olivier.oeuillot@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {
	
	/**
	 * @method protected
	 * @param f_toolTip tooltip
	 * @param Boolean show
	 * @param Event jsEvent
	 * @return void
	 */
	fa_setToolTipVisible : function(tooltip, show, jsEvent) {
		
		if (show) {
			tooltip.f_show(tooltip.f_getStateId(), jsEvent, f_toolTip.BOTTOM_COMPONENT);

		} else {
			tooltip.f_hide(tooltip.f_getStateId());
		}
	}

};

new f_aspect("fa_basicToolTipContainer", {
	extend: [ fa_toolTipContainer ],
	members: __members
});
