/**
 * Aspect Tooltip Container
 *
 * @aspect public abstract fa_tooltipContainer 
 * @author jbmeslin@vedana.com (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __members = {
		
		
		
		fa_getTooltipForElement: function(element){
			var parent= element;
			while (parent){
				var tooltipId = parent._tooltipId;
				if(tooltipId) {
					var component =  this.f_findComponent(tooltipId);
					if (!component){
						
						component =f_core.CreateElement(parent, "div", {
							className: "f_tooltip"			
						});
						
						component.setAttribute("v:class", "f_tooltip");
						component = f_tooltip.f_getClassLoader().f_init(component, false, false);
					}
					component._elementContainer = parent;
					return component;
				}
				parent = parent.parentNode;
			}
			
			return undefined;
		}

};

new f_aspect("fa_tooltipContainer", {
	members: __members
});
