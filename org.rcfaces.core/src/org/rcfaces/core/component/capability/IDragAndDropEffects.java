/*
 * $Id$
 */

package org.rcfaces.core.component.capability;

/**
 * 
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IDragAndDropEffects {

    int UNKNOWN_DND_EFFECT = -1;

    int NONE_DND_EFFECT = 0x00;

    int COPY_DND_EFFECT = 0x01;

    int LINK_DND_EFFECT = 0x02;

    int MOVE_DND_EFFECT = 0x04;

    int DEFAULT_DND_EFFECT = NONE_DND_EFFECT;
}