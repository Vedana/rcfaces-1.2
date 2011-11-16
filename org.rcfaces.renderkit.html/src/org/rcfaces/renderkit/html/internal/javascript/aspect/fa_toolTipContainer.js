/**
 * Aspect Tooltip Container
 *
 * @aspect public abstract fa_toolTipContainer 
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {
	
	/**
	 * @field private String
	 */
	_toolTipId: undefined,
		
	/**
	 * @method public
	 * @return String
	 */
	f_getTooltipId: function() {
		if (this._toolTipId!==undefined) {
			return this._toolTipId;
		}

	  	this._toolTipId=f_core.GetAttribute(this, "v:toolTipId", null);

	  	return this._toolTipId;
	},
		
	/**
	 * @method hidden
	 * @param HTMLElement element
	 * @return Object
	 */
	fa_getToolTipForElement: function(element) {
		var parent= element;
		
		for (;parent;parent = parent.parentNode){
			if (parent.nodeType==f_core.TEXT_NODE) {
				continue;
			}

			if (parent.nodeType!=f_core.ELEMENT_NODE) {
				break;
			}
			
			var tooltipClientId;			

			if (parent.f_getTooltipId) {
				tooltipClientId=parent.f_getTooltipId();
			}
			
			if (!tooltipClientId) {			
				tooltipClientId = f_core.GetAttribute(parent, "v:toolTipId");
			}
			
			if (!tooltipClientId) {			
				tooltipClientId = parent._toolTipId;
			}
			
			if (!tooltipClientId) {
				continue;
			}
				
			return {
				toolTipClientId: tooltipClientId,
				item: parent,
				container: this
			};
		}
		
		return null;
	}

};

new f_aspect("fa_toolTipContainer", {
	members: __members
});
