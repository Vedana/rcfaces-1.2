/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.21  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.20  2006/06/19 17:22:18  oeuillot
 * JS: Refonte de fa_selectionManager et fa_checkManager
 * Ajout de l'accelerator Key
 * v:accelerator prend un keyBinding desormais.
 * Ajout de  clientSelectionFullState et clientCheckFullState
 * Ajout de la progression pour les suggestions
 * Fusions des servlets de ressources Javascript/css
 *
 * Revision 1.19  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.18  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.17  2006/01/03 15:21:38  oeuillot
 * Refonte du systeme de menuPopup !
 *
 * Revision 1.16  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.15  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.14  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.13  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.12  2005/09/16 09:54:42  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 * Revision 1.11  2005/03/18 14:42:50  oeuillot
 * Support de la table des symbols pour le javascript compress�
 * Menu du style XP et pas Office !
 *
 * Revision 1.10  2005/02/18 14:46:06  oeuillot
 * Corrections importantes pour stabilisation
 * R�ecriture du noyau JAVASCRIPT pour ameliorer performances.
 * Ajout de IValueLockedCapability
 *
 * Revision 1.9  2004/12/30 17:24:20  oeuillot
 * Gestion des validateurs
 * Debuggage des composants
 *
 * Revision 1.8  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.context.FacesContext;

import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */

public interface IJavaScriptWriter {

    IJavaScriptRenderContext getJavaScriptRenderContext();

    String getComponentVarName();

    void setComponentVarName(String varName);

    /*
     * Peut ne pas être implementée !
     */
    IHtmlComponentRenderContext getComponentRenderContext();

    /*
     * Peut ne pas �tre implement�e !
     */
    IHtmlWriter getWriter();

    FacesContext getFacesContext();

    IJavaScriptWriter ensureInitialization() throws WriterException;

    IJavaScriptWriter write(String string) throws WriterException;

    IJavaScriptWriter writeln(String string) throws WriterException;

    IJavaScriptWriter writeln() throws WriterException;

    IJavaScriptWriter write(char c) throws WriterException;

    IJavaScriptWriter writeInt(int value) throws WriterException;

    IJavaScriptWriter writeRaw(char[] dst, int pos, int length)
            throws WriterException;

    IJavaScriptWriter writeSymbol(String symbol) throws WriterException;

    IJavaScriptWriter writeCall(String object, String symbol)
            throws WriterException;

    IJavaScriptWriter writeString(String s) throws WriterException;

    void addRequestedModule(String moduleName);

    String allocateString(String text) throws WriterException;

    IJavaScriptWriter writeBoolean(boolean value) throws WriterException;

    IJavaScriptWriter writeNull() throws WriterException;

    void end() throws WriterException;

    boolean isOpened();
}