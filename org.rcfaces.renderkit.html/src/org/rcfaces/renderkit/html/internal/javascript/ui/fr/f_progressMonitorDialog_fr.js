/*
 * $Id$
 */

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
 
var __resources = {
	TIME_MINUTES: "' '{minutes}' minutes'",
	TIME_MINUTE: " 1 minute",
	TIME_SECONDS: "' '{seconds}' secondes'",
	TIME_SECOND: " 1 seconde",
	TIME_MANY_LEFT: " restantes",
	TIME_ONE_LEFT: " restante",
	
	TIME_COMPUTING: "Calcul du temps restant...",
	TIME_REMAINING: "'Environ'{minutes}{seconds}{left}...",
	TIME_ZERO: "La tâche se termine..."
	
}
f_resourceBundle.Define(f_progressMonitorDialog, __resources);
