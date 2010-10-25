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
		ARIA_POSEINSET:		"aria-posinset",
		
		/** 
		 * @field public static final String
		 */
		ARIA_LEVEL:		"aria-level",
		
		/** 
		 * @field public static final String
		 */
		ARIA_SELECTED:		"aria-selected",
		
		/** 
		 * @field public static final String
		 */
		ARIA_SETSIZE:		"aria-setsize",
		
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
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Number level .
		 * @return void.
		 */
		SetElementAriaLevel: function(element, level) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaLevel: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_LEVEL, level);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Number position .
		 * @return void.
		 */
		SetElementAriaPosinset: function(element, posinset) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaPosinset: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_POSEINSET, posinset);
		},
		
		/**
		 * @method public static
		 * @param HTMLElement element HTML.
		 * @param Number size.
		 * @return void.
		 */
		SetElementAriaSetsize: function(element, size) {
			f_core.Assert(element && element.nodeType == f_core.ELEMENT_NODE, "fa_aria.SetElementAriaSetsize: invalid element parameter ("+element+")." );
			element.setAttribute(fa_aria.ARIA_SETSIZE, size);
		}
};

var __members = {
	
};

new f_aspect("fa_aria", {
	statics: __statics,
	members: __members
});