/*
 * $Id$
 */

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __resources = {
	TIME_MINUTES: "??? {minutes}' minutes '",
	TIME_MINUTE: "??? 1 minute ",
	TIME_SECONDS: "??? {seconds}' seconds '",
	TIME_SECOND: "??? 1 second ",
	TIME_MANY_LEFT: "",
	TIME_ONE_LEFT: "",
	
	TIME_COMPUTING: "??? Computing remaining time...",
	TIME_REMAINING: "??? {minutes}{seconds}'remaining...'",
	TIME_ZERO: "??? Task is finishing..."
}
f_resourceBundle.Define(f_progressMonitorDialog, __resources);
