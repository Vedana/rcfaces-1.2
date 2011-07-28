/**
 * Aspect Criteria Manager
 *
 * @aspect public abstract fa_criteriaManager 
 * @author jbmeslin@vedana.com
 * @version 
 */
 
var __members = {

		/**
		 * @method public
		 * @params Object selectedCriteria
		 * @params Object callback 
		 * @return void
		 */
		fa_evaluateCriteria: f_class.ABSTRACT,
		
		/**
		 * @method protected abstract
		 * @param Array criteriaSelected  Sous la forme  [{ id: "idColonne", values: [ val1, val2 ] }, {...} ] 
		 * @return void
		 */
		fa_setSelectedCriteria: f_class.ABSTRACT,
		
		/**
		 * @method protected abstract
		 * @return Array selectedDatagrid
		 */
		fa_getSelectedCriteria: f_class.ABSTRACT,
		
		/**
		 * @method protected abstract
		 * @param String columnId
		 * @param Array values
		 * @return Object criteriaSelected
		 */
		fa_addSelectedCriteria : f_class.ABSTRACT,
		
		/**
		 * @method public abstract
		 * @return Integer cardinality
		 */
		fa_getColumnCriteriaCardinality: f_class.ABSTRACT

};

new f_aspect("fa_criteriaManager", {
	members: __members
});
