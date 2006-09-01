/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/01 15:24:29  oeuillot
 * Gestion des ICOs
 *
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/05/11 16:34:19  oeuillot
 * Correction du calendar
 * Ajout de DateChooser
 * Ajout du moteur de filtre d'images
 * Ajout de l'evt   loadListener pour le AsyncManager
 *
 */
package org.rcfaces.core.image;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractImageOperation implements IImageOperation {
    private static final String REVISION = "$Revision$";

    private String name;

    private String forceSuffix;

    private String externalContentType;

    private String internalContentType;

    public final String getExternalContentType() {
        return externalContentType;
    }

    public final void setExternalContentType(String forceContentType) {
        this.externalContentType = forceContentType;
    }

    public final String getInternalContentType() {
        return internalContentType;
    }

    public final void setInternalContentType(String internalContentType) {
        this.internalContentType = internalContentType;
    }

    public final String getForceSuffix() {
        return forceSuffix;
    }

    public final void setForceSuffix(String forceSuffix) {
        this.forceSuffix = forceSuffix;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

}
