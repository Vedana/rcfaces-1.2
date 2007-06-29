/*
 * $Id$
 */

/**
 * Class MenuBase
 *
 * @class f_menuBase extends f_eventTarget, fa_menuCore
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */

new f_class("f_menuBase", {
	extend: f_eventTarget, 
	aspects: [ fa_menuCore ]
});
