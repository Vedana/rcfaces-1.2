/*
 * $Id$
 */

/**
 * @class public f_criteriaList extends f_pager
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */


var __members = {

	f_criteriaList: function() {
		this.f_super(arguments);		
		
	},
	
	/**
	 * @method protected
	 * @return void
	 */
	_readAttributes: function() {
		var message=f_core.GetAttribute(this, "v:criteriaFormat");
		if (!message) {
			var resourceBundle=f_resourceBundle.Get(f_criteriaList);

			message = resourceBundle.f_get("CRITERIA_FORMAT");
		}
		
		this._criteriaMessage=message;

		message=f_core.GetAttribute(this, "v:noCriteriaMessage");
		if (!message) {
			var resourceBundle=f_resourceBundle.Get(f_criteriaList);

			message = resourceBundle.f_get("NO_CRITERIA_MESSAGE");
		}
		
		this._noCriteriaMessage=message;
	},	
	_computeMessage: function(fragment) {
		var dataGrid = this._pagedComponent;

		var selectedCriteria = dataGrid.fa_getSelectedCriteria();
		if (!selectedCriteria || !selectedCriteria.length) {
			var critUL=f_core.CreateElement(fragment, "span", {
				className: "f_criteriaList_noCriteria"			
			});

			this._formatMessage(critUL, this._noCriteriaMessage);
			return;
		}
		
		var critUL=f_core.CreateElement(fragment, "ul", {
			className: "f_criteriaList_list"			
		});
		
		for(var i=0;i<selectedCriteria.length;i++) {
			
			var critDiv=f_core.CreateElement(critUL, "li", {
				className: "f_criteriaList_column f_criteriaList_column_"+i			
			});
			
			var criteria=selectedCriteria[i];
			
			this._formatMessage(critDiv, this._criteriaMessage, {
				index: i+1,
				criteria: criteria
			});			
		}
	},
	_processToken: function(fragment, varName, parameter, target) {		
		switch(varName) {
		case "index":
			f_pager._AddSpan(fragment, "index", String(target.index), "f_criteriaList_item");
			break;
			
		case "label":
			this._addCriteriaLabel(fragment, target.criteria);
			break;
			
		case "firstvalue":
			this._addCriteriaFirstValue(fragment, target.criteria);
			break;
			
		case "values":
			this._addCriteriaValues(fragment, target.criteria);
			break;
		}
	},
	_addCriteriaLabel: function(fragment, criteria) {
		var dataGrid = this._pagedComponent;
		
		var criteriaLabel=dataGrid.fa_getCriteriaLabelByColumn(criteria.id);
		
		f_pager._AddSpan(fragment, "label", criteriaLabel, "f_criteriaList_item");
	},
	_addCriteriaFirstValue: function(fragment, criteria) {
		// var dataGrid = this._pagedComponent;
		
		f_pager._AddSpan(fragment, "first", this._getCriteriaLabel(criteria.values[0]), "f_criteriaList_item");
	},
	_addCriteriaValues: function(fragment, criteria) {
		// var dataGrid = this._pagedComponent;
		
		var valuesSpan=f_core.CreateElement(fragment, "span", {
			className: "f_criteriaList_values"			
		});
		
		for(var i=0;i<criteria.values.length;i++) {
			if (i>0) {
				f_pager._AddText(valuesSpan, ", ");				
			}
			
			var label=this._getCriteriaLabel(criteria.values[i]);
			
			f_pager._AddSpan(fragment, "_"+i, this._getCriteriaLabel(criteria.values[0]), "f_criteriaList_item");
		}
		
		f_pager._AddText(valuesSpan, ".");
	},
	_getCriteriaLabel: function(valueObject) {
		if (valueObject.label) {
			return valueObject.label;
		}
		
		return valueObject.value;
	}
};

new f_class("f_criteriaList", {
	extend: f_pager,
	members: __members
});
