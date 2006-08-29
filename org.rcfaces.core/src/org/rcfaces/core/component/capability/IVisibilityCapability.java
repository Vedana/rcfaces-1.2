/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.6  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 * Revision 1.5  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.4  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.3  2005/10/05 14:34:20  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/09/08 09:26:08  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.core.component.capability;

/**
 * Un élement qui implemente l'interface VisibilityCapability peut d'etre
 * visible ou invisible.
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public interface IVisibilityCapability {

    /**
     * Type d'invisibilité 'SERVER' (c'est a dire que le serveur n'envoie pas le
     * composant, le client ne connait meme pas son existance !).
     */
    int SERVER_HIDDEN_MODE = 1;

    /**
     * Type d'invisibilité 'PHANTOM' (c'est a dire que le client doit afficher
     * une zone vide à la place du composant visible).
     */
    int PHANTOM_HIDDEN_MODE = 2;

    /**
     * Type d'invisibilité 'IGNORE' (c'est a dire que le client n'en tient pas
     * compte pour le Layout de la vue).
     */
    int IGNORE_HIDDEN_MODE = 4;

    /**
     * Valeur par defaut du HiddenMode.
     */
    int DEFAULT_HIDDEN_MODE = IGNORE_HIDDEN_MODE;

    /**
     * Valeur par defaut du HiddenMode.
     */
    int CLIENT_HIDDEN_MODE = IGNORE_HIDDEN_MODE;

    /**
     * Marks the receiver as visible if the argument is
     * <code>{@link Boolean#TRUE TRUE}</code>, and marks it invisible if
     * argument is <code>{@link Boolean#FALSE FALSE}</code>. <br>
     * If one of the receiver's ancestors is not visible or some other condition
     * makes the receiver not visible, marking it visible may not actually cause
     * it to be displayed.
     * 
     * @param visible
     *            the new visibility state.
     */
    void setVisible(Boolean visible);

    /**
     * Returns <code>{@link Boolean#TRUE TRUE}</code> if the receiver is
     * visible, <code>{@link Boolean#FALSE FALSE}</code> if the receiver is
     * specified "not visible", and <code>null</code> otherwise. <br>
     * If one of the receiver's ancestors is not visible or some other condition
     * makes the receiver not visible, this method may still indicate that it is
     * considered visible even though it may not actually be showing.
     * 
     * @return the receiver's visibility state
     * @xxxsee #isVisible()
     */
    Boolean getVisible();

    /*
     * Returns <code>true</code> if the receiver is visible and all ancestors
     * up to and including the receiver's nearest ancestor view are visible.
     * Otherwise, <code>false</code> is returned.
     * 
     * @return the receiver's visibility state
     * 
     * @see #getVisible()
     * 
     * boolean isVisible();
     */

    /**
     * Set the visibility mode in client side.
     */
    void setHiddenMode(int hiddenMode);

    /**
     * Returns the visibility mode in client side.
     */
    int getHiddenMode();
}