/**
 *	Aspect Aria for accesibilty
 *
 * @aspect public abstract fa_iaria
 * @author jean-baptiste.meslin@vedana.com 
 * @version $Revision$ $Date: 
 */

var __statics = {
		
		/** 
		 * @field public static final String
		 */
		ARIA_ACTIVEDESCENDANT:		"aria-activedescendant",
	
		/** 
		 * @field public static final String
		 */
		ARIA_DISABLED:		"aria-disabled",
		
		/** 
		 * @field public static final String
		 */
		ARIA_EXPANDED:		"aria-expanded",
		
		/** 
		 * @field public static final String
		 */
		ARIA_LABELLEDBY:		"aria-labelledby",
		
		/** 
		 * @field public static final String
		 */
		ARIA_SELECTED:		"aria-selected",
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean disabled.
		 * @return void.
		 */
		SetElementAriaDisabled: function(element, disabled) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaDisabled: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_DISABLED, disabled);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean expanded.
		 * @return void.
		 */
		SetElementAriaExpanded: function(element, expanded) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaExpanded: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_EXPANDED, expanded);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean selected.
		 * @return void.
		 */
		SetElementAriaSelected: function(element, selected) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaSelected: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_SELECTED, selected);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param String activeId .
		 * @return void.
		 */
		SetElementAriaActiveDescendant: function(element, activeId) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaActiveDescendant: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_ACTIVEDESCENDANT, activeId);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param String labelId .
		 * @return void.
		 */
		SetElementAriaLabelledBy: function(element, labelId) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaLabelledBy: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_LABELLEDBY, labelId);
		}
		
};

var __members = {
	
};

new f_aspect("fa_aria", {
	statics: __statics,
	members: __members
});