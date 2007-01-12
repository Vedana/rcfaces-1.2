/*
 * $Id$
 */
 
/**
 *
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 */
 
var __resources = {
	FIRST: "Premier",
	LAST: "Dernier",
	NEXT: "Suivant",
	PREVIOUS: "Précédant",
	FIRST_TOOLTIP: "Voir la première page",
	NEXT_TOOLTIP: "Voir la page suivante",
	LAST_TOOLTIP: "Voir la dernière page",
	PREVIOUS_TOOLTIP: "Voir la page précédente",
	INDEX_TOOLTIP: "Voir la page {0}",
	UNKNOWN_INDEX_TOOLTIP: "Voir la page suivante",
	
	NO_PAGED_MESSAGE: "'Cette liste n''utilise pas de pages.'",
	ZERO_RESULT_MESSAGE: "'Aucune entrée.'",
	ONE_RESULT_MESSAGE: "'Une entrée.'",
	MESSAGE: "{rowcount}' entrées, affichage de '{first}' à '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'",
	MANY_RESULTS_MESSAGE: "'Nombre d''entrée indéterminé, affichage à partir de '{first}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
}

f_resourceBundle.Define(f_pager, __resources);
