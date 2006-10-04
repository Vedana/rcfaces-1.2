/*
 * $Id$
 * 
 * $Log$
 * Revision 1.4  2006/10/04 12:31:43  oeuillot
 * Stabilisation
 *
 * Revision 1.3  2006/09/14 14:34:39  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.2  2006/09/01 15:24:34  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.7  2006/08/28 16:03:55  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.6  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 * Revision 1.5  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.4  2006/02/06 16:47:04  oeuillot
 * Renomme le logger commons.log en LOG
 * Ajout du composant focusManager
 * Renomme vfc-all.xml en repository.xml
 * Ajout de la gestion de __Vversion et __Llocale
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.2  2005/11/08 12:16:28  oeuillot
 * Ajout de  Preferences
 * Stabilisation de imageXXXButton
 * Ajout de la validation cot� client
 * Ajout du hash MD5 pour les servlets
 * Ajout des accelerateurs
 *
 * Revision 1.1  2005/09/16 09:54:41  oeuillot
 * Ajout de fonctionnalit�s AJAX
 * Ajout du JavaScriptRenderContext
 * Renomme les classes JavaScript
 *
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collection;

import javax.faces.application.FacesMessage;

import org.rcfaces.core.internal.renderkit.IScriptRenderContext;
import org.rcfaces.core.internal.renderkit.WriterException;
import org.rcfaces.core.internal.webapp.IRepository;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IJavaScriptRenderContext extends IScriptRenderContext {

    boolean isInitialized();

    void forceJavaScriptStub();

    boolean isJavaScriptStubForced();

    void clearJavaScriptStubForced();

    void computeRequires(IHtmlWriter writer, AbstractJavaScriptRenderer renderer);

    String[] popUnitializedComponentsClientId();

    IRepository.IFile[] popRequiredFiles();

    boolean isRequiresPending();

    IJavaScriptRenderContext pushInteractive();

    String allocateVarName();

    boolean isUnitializedComponentsPending();

    void pushUnitializedComponent(String componentId);

    String allocateFacesMessage(FacesMessage message, boolean mustDeclare[]);

    String allocateString(String text, boolean mustDeclare[]);

    String allocateComponentVarId(String componentId, boolean mustDeclare[]);

    void restoreState(Object state);

    Object saveState();

    void initializeJavaScriptDocument(IJavaScriptWriter writer)
            throws WriterException;

    void appendRequiredClasses(Collection classNames, String className,
            String requiredId);
}
