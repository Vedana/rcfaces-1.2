/*
 * $Id$
 */
 
/**
 *
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 */
 
var __resources = {
	FIRST: "First",
	LAST: "Last",
	NEXT: "Next",
	PREVIOUS: "Prev",
	FIRST_TOOLTIP: "Show first page",
	NEXT_TOOLTIP: "Show next page",
	LAST_TOOLTIP: "Show last page",
	PREVIOUS_TOOLTIP: "Show previous page",
	INDEX_TOOLTIP: "Show page #{0}",
	UNKNOWN_INDEX_TOOLTIP: "Show next page",
	
	NO_PAGED_MESSAGE: "'No page for this table.'",
	ZERO_RESULT_MESSAGE: "'No result.'",
	ONE_RESULT_MESSAGE: "'One result.'",
	MESSAGE: "{rowcount}' results, results from '{first}' to '{last}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'",
	MANY_RESULTS_MESSAGE: "'Unknown result number, results from '{first}'.\n['{bfirst}'/'{bprev}'] '{bpages}' ['{bnext}'/'{blast}']'"
}

f_resourceBundle.Define(f_pager, __resources);
