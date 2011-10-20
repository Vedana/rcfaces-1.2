/**
 * Aspect Tooltip Container
 *
 * @aspect public abstract fa_tooltipContainer 
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {
		
		f_finalize: function() {
			
			
			
		},

		/**
		 * @method public
		 * @return String
		 */
		fa_getTooltipId: f_class.ABSTRACT
};

new f_aspect("fa_tooltipContainer", {
	members: __members
});
