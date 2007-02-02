/*
 * $Id$
 */
 
/**
 *
 * 
 * @author Nahuel Espíndola (latest modification by $Author$)
 * @version $Revision$ $Date$
 * @charset UTF-8
 */
 
var __resources = {
	FIRST: "Primero",
	LAST: "Último",
	NEXT: "Próximo",
	PREVIOUS: "Previo",
	FIRST_TOOLTIP: "Mostrar la primera página",
	NEXT_TOOLTIP: "Mostrar la página siguiente",
	LAST_TOOLTIP: "Mostrar la ultima página",
	PREVIOUS_TOOLTIP: "Mostrar la página previa",
	INDEX_TOOLTIP: "Mostrar página #{0}",
	UNKNOWN_INDEX_TOOLTIP: "Mostrar la página siguiente",
	
	NO_PAGED_MESSAGE: "No hay páginas para esta tabla.'",
	ZERO_RESULT_MESSAGE: "'No hay datos.'",
	ONE_RESULT_MESSAGE: "'Un registro.'",
	MESSAGE: "{rowcount}' registros encontrados, mostrando del '{first}' al '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'",
	MANY_RESULTS_MESSAGE: "'Cantidad desconocida de registros, mostrando del '{first}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
}

f_resourceBundle.Define(f_pager, __resources);
