/**
 * Aspect Criteria Manager
 *
 * @aspect public abstract fa_criteriaManager 
 * @author jbmeslin@vedana.com
 * @version 
 */
 
var __members = {

		/**
		 * @method protected abstract
		 * @return void
		 */
		fa_evaluateCriteria: f_class.ABSTRACT,
		/**
		 * @method protected abstract
		 * @return void
		 */
		fa_setSelectedCriteria: f_class.ABSTRACT,
		/**
		 * @method protected abstract
		 * @return void
		 */
		fa_fa_getSelectedCriteria: f_class.ABSTRACT

};

new f_aspect("fa_criteriaManager", {
	members: __members
});
