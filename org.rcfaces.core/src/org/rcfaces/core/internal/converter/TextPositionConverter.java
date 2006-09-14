/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:52  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/07/18 17:06:30  oeuillot
 * Ajout du frameSetConsole
 * Amelioration de l'ImageButton avec du support d'un mode SPAN s'il n'y a pas de texte.
 * Corrections de bugs JS d�tect�s par l'analyseur JS
 * Ajout des items clientDatas pour les dates et items de combo/list
 * Ajout du styleClass pour les items des dates
 *
 * Revision 1.1  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.3  2006/01/31 16:04:24  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.2  2005/11/17 10:04:56  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 * Revision 1.1  2005/02/21 17:33:06  oeuillot
 * Reorganisation du JAVASCRIPT
 * Reorganisation des ImageXxxxButton
 * Reorganise le ComponentTools => Converters
 *
 */
package org.rcfaces.core.internal.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.Converter;

import org.rcfaces.core.component.capability.IHorizontalTextPositionCapability;
import org.rcfaces.core.component.capability.ITextPositionCapability;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextPositionConverter extends HorizontalTextPositionConverter {
    private static final String REVISION = "$Revision$";

    public static final Converter SINGLETON = new TextPositionConverter();

    private static final String TOP_POSITION_NAME = "top";

    private static final String BOTTOM_POSITION_NAME = "bottom";

    private static final Integer DEFAULT_POSITION = new Integer(
            IHorizontalTextPositionCapability.DEFAULT_POSITION);

    private static Map TEXT_POSITIONS = new HashMap(5);
    static {
        Integer i = new Integer(ITextPositionCapability.BOTTOM_POSITION);
        TEXT_POSITIONS.put(BOTTOM_POSITION_NAME, i);

        i = new Integer(IHorizontalTextPositionCapability.LEFT_POSITION);
        TEXT_POSITIONS.put(LEFT_POSITION_NAME, i);

        i = new Integer(ITextPositionCapability.TOP_POSITION);
        TEXT_POSITIONS.put(TOP_POSITION_NAME, i);

        i = new Integer(IHorizontalTextPositionCapability.RIGHT_POSITION);
        TEXT_POSITIONS.put(RIGHT_POSITION_NAME, i);
    }

    protected Map getTextPositions() {
        return TEXT_POSITIONS;
    }

    protected Integer getDefaultPosition() {
        return DEFAULT_POSITION;
    }
}
