/*
 * $Id$
 *
 * $Log$
 * Revision 1.3  2006/09/20 17:55:24  oeuillot
 * Tri multiple des tables
 * Dialogue modale en JS
 *
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 */

/**
 * f_prop class
 *
 * @class final hidden f_prop
 * @author Joel Merlin
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
var f_prop = {

	/**
	 * @field hidden static final
	 */
	AUTO_COMPLETION:		"autoCompletion",

	/**
	 * @field hidden static final
	 */
	BACKGROUND:				"background",

	/**
	 * @field hidden static final
	 */
	CHECKABLE:				"checkable",

	/**
	 * @field hidden static final
	 */
	CHECKED:				"checked",

	/**
	 * @field hidden static final
	 */
	CHECKED_ITEMS:			"checkedItems",

	/**
	 * @field hidden static final
	 */
	COLLAPSED:				"collapsed",

	/**
	 * @field hidden static final
	 */
	COLLAPSED_ITEMS:		"collapsedItems",

	/**
	 * @field hidden static final
	 */
	COLUMN_WIDTHS:			"columnWidths",
	
	/**
	 * @field hidden static final
	 */
	CURSOR:					"cursor",

	/**
	 * @field hidden static final
	 */
	DATA:					"data",

	/**
	 * @field hidden static final
	 */
	DELAY_MS:				"delayMs",

	/**
	 * @field hidden static final
	 */
	DESELECTED_ITEMS:		"deselectedItems",

	/**
	 * @field hidden static final
	 */
	DISABLED:				"disabled",

	/**
	 * @field hidden static final
	 */
	DISABLED_ITEMS:			"disabledItems",

	/**
	 * @field hidden static final
	 */
	DISABLED_IMAGE_URL:		"disabledImageURL",

	/**
	 * @field hidden static final
	 */
	EDITABLE:				"editable",

	/**
	 * @field hidden static final
	 */
	ENABLED_ITEMS:			"enabledItems",

	/**
	 * @field hidden static final
	 */
	EXPANDED_ITEMS:			"expandedItems",

	/**
	 * @field hidden static final
	 */
	FILTER_EXPRESSION:		"filterExpression",

	/**
	 * @field hidden static final
	 */
	FIRST:					"first",

	/**
	 * @field hidden static final
	 */
	FOCUS_ID:			"focusId",

	/**
	 * @field hidden static final
	 */
	FOREGROUND:				"foreground",

	/**
	 * @field hidden static final
	 */
	GROUPNAME:				"groupName",

	/**
	 * @field hidden static final
	 */
	HEIGHT:					"height",

	/**
	 * @field hidden static final
	 */
	HIDEROOTNODE:			"hideRootNode",

	/**
	 * @field hidden static final
	 */
	HORZSCROLLPOS:			"horizontalScrollPosition",

	/**
	 * @field hidden static final
	 */
	HOVER_IMAGE_URL:		"hoverImageURL",

	/**
	 * @field hidden static final
	 */
	ID:						"id",

	/**
	 * @field hidden static final
	 */
	IMAGE_URL:				"imageURL",

	/**
	 * @field hidden static final
	 */
	IMMEDIATE:				"immediate",

	/**
	 * @field hidden static final
	 */
	INTERNAL:				"internal",

	/**
	 * @field hidden static final
	 */
	LISTITEMSELECTED:		"listItemSelected",

	/**
	 * @field hidden static final
	 */
	MAX:					"max",

	/**
	 * @field hidden static final
	 */
	MIN:					"min",

	/**
	 * @field hidden static final
	 */
	MULTIPLE:				"multiple",

	/**
	 * @field hidden static final
	 */
	READONLY:				"readOnly",

	/**
	 * @field hidden static final
	 */
	ROWS:					"rows",

	/**
	 * @field hidden static final
	 */
	SELECT:					"select",

	/**
	 * @field hidden static final
	 */
	SELECTABLE:				"selectable",

	/**
	 * @field hidden static final
	 */
	SELECTED:				"selected",

	/**
	 * @field hidden static final
	 */
	SELECTED_IMAGE_URL:		"selectedImageURL",

	/**
	 * @field hidden static final
	 */
	SELECTED_ITEMS:			"selectedItems",

	/**
	 * @field hidden static final
	 */
	SORT_INDEX:				"sortIndex",

	/**
	 * @field hidden static final
	 */
	SORT_ORDER:				"sortOrder",

	/**
	 * @field hidden static final
	 */
	STEP:				"step",

	/**
	 * @field hidden static final
	 */
	TABIDSELECTED:			"tabIdSelected",

	/**
	 * @field hidden static final
	 */
	TEXT:					"text",

	/**
	 * @field hidden static final
	 */
	TEXTALIGNMENT:			"textAlignment",

	/**
	 * @field hidden static final
	 */
	TOOLTIP:				"toolTip",

	/**
	 * @field hidden static final
	 */
	TREENODE_DEFEXPANDEDIMAGEURL:"defaultExpandedTreeNodeImageURL",

	/**
	 * @field hidden static final
	 */
	TREENODE_DEFIMAGEURL:	"defaultTreeNodeImageURL",

	/**
	 * @field hidden static final
	 */
	TREENODE_DEFLEAFIMAGEURL:"defaultLeafTreeNodeImageURL",

	/**
	 * @field hidden static final
	 */
	UNCHECKED_ITEMS:		"uncheckedItems",

	/**
	 * @field hidden static final
	 */
	VALUE:					"value",

	/**
	 * @field hidden static final
	 */
	VERTSCROLLPOS:			"verticalScrollPosition",

	/**
	 * @field hidden static final
	 */
	VISIBLE:				"visible",

	/**
	 * @field hidden static final
	 */
	WIDTH:					"width",

	/**
	 * @field hidden static final
	 */
	X:						"x",

	/**
	 * @field hidden static final
	 */
	Y:						"y",
	
	/**
	 * @field hidden static final
	 */
	ALL_VALUE:				"\x07all",
	
	
	/**
	 * @method public static final
	 */
	f_getName: function() {
		return "f_prop";
	}
}
