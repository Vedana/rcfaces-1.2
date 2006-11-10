/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.capability;

/**
 * Un élement qui implemente l'interface VisibilityCapability peut d'etre
 * visible ou invisible.
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
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
