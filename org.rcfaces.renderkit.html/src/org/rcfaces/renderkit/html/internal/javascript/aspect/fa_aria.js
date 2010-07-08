/**
 *	Aspect Aria for accesibilty
 *
 * @aspect public abstract fa_iaria
 * @author jean-baptiste.meslin@vedana.com 
 * @version $Revision$ $Date: 
 */

var __statics = {
	
		/** 
		 * @field private static final String
		 */
		_ARIA_DISABLED:		"aria-disabled",
		
		/** 
		 * @field private static final String
		 */
		_ARIA_SELECTED:		"aria-selected",
		
		/** 
		 * @field private static final String
		 */
		_ARIA_EXPANDED:		"aria-expanded",
		
		/** 
		 * @field private static final String
		 */
		_ARIA_ACTIVEDESCENDANT:		"aria-activedescendant",
		
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean disabled.
		 * @return void.
		 */
		SetElementAriaDisabled: function(element, disabled) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaDisabled: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria._ARIA_DISABLED, disabled);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean expanded.
		 * @return void.
		 */
		SetElementAriaExpanded: function(element, expanded) {
			element.setAttribute(fa_aria._ARIA_EXPANDED, expanded);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Boolean selected.
		 * @return void.
		 */
		SetElementAriaSelected: function(element, selected) {
			element.setAttribute(fa_aria._ARIA_SELECTED, selected);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param String activeId .
		 * @return void.
		 */
		SetElementAriaActiveDescendant: function(element, activeId) {
			element.setAttribute(fa_aria._ARIA_ACTIVEDESCENDANT, activeId);
		}
		
};

var __members = {
	
};

new f_aspect("fa_aria", {
	statics: __statics,
	members: __members
});