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
	_tooltipId: undefined,
		
	/**
	 * @method public
	 * @return String
	 */
	f_getTooltipId: function() {
		if (this._tooltipId!==undefined) {
			return this._tooltipId;
		}

	  	this._tooltipId=f_core.GetAttribute(this, "v:tooltipId", null);

	  	return this._tooltipId;
	},
		
	/**
	 * @method hidden
	 * @param HTMLElement element
	 * @return f_toolTip
	 */
	fa_getToolTipForElement: function(element) {
		var parent= element;
		
		for (;parent;parent = parent.parentNode){
			var tooltipClientId;
			
			if (parent.f_getTooltipId) {
				tooltipClientId=parent.f_getTooltipId();
			}
			
			if (!tooltipClientId) {			
				tooltipClientId = f_core.GetAttribute(parent, "v:tooltipId");
			}
			
			if (!tooltipClientId) {			
				tooltipClientId = parent._tooltipId;
			}
			
			if (!tooltipClientId) {
				continue;
			}
			
			var tooltipComponent = f_core.GetElementByClientId(tooltipClientId);
				
			if (!tooltipComponent){
				tooltipComponent = f_core.CreateElement(this.ownerDocument.body, "div", {
					className: "f_toolTip",			
					id: tooltipClientId
				});
				
				tooltipComponent.setAttribute("v:class", "f_toolTip");

				tooltipComponent = this.f_getClass().f_getClassLoader().f_init(tooltipComponent, true, true);
			}
			
			tooltipComponent.f_setElementContainer(this, parent);
			
			return tooltipComponent;
		}
		
		return null;
	}

};

new f_aspect("fa_toolTipContainer", {
	members: __members
});
