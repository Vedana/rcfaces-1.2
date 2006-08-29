/*
 * $Id$
 *
 * $Log$
 * Revision 1.1  2006/08/29 16:14:26  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 */

/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
 
var __resources = {
	LOADING_MESSAGE: "Chargement ...",
	RECEIVING_MESSAGE: "Réception ..."	
}
f_resourceBundle.Define(f_waiting, __resources);
