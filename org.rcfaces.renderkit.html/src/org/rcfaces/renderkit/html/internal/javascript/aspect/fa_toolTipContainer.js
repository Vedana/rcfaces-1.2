/**
 * Aspect Tooltip Container
 *
 * @aspect public abstract fa_toolTipContainer 
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {
	
	/**
	 * @method hidden
	 * @param HTMLElement element
	 * @return f_toolTip
	 */
	fa_getTooltipForElement: function(element) {
		var parent= element;
		
		for (;parent;parent = parent.parentNode){
			var tooltipClientId = parent._tooltipId;
			if (!tooltipClientId) {
				continue;
			}
			
			var tooltipComponent =  this.f_findComponent(tooltipClientId);
			if (!tooltipComponent){
				
				tooltipComponent = f_core.CreateElement(parent, "div", {
					className: "f_toolTip"			
				});
				
				tooltipComponent.setAttribute("v:class", "f_toolTip");

				tooltipComponent = this.f_getClass().f_getClassLoader().f_init(component, true, true);
			}
			
			tooltipComponent.f_setElementContainer(parent);
			
			return tooltipComponent;
		}
		
		return null;
	}

};

new f_aspect("fa_toolTipContainer", {
	members: __members
});
