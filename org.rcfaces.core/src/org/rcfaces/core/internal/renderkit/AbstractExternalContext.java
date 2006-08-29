/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/28 17:48:28  oeuillot
 * Ajout de dateEntry
 * Ajout D'une constante g�n�rale de sp�cification de l'attributesLocale
 * Ajout d'un attribut <v:init attributesLocale='' />
 *
 */
package org.rcfaces.core.internal.renderkit;

import java.util.Locale;

import javax.faces.context.ExternalContext;

import org.rcfaces.core.internal.tools.ContextTools;


/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public abstract class AbstractExternalContext implements IExternalContext {

    private static final String REVISION = "$Revision$";

    protected final ExternalContext externalContext;

    private Locale userLocale;

    protected AbstractExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public final ExternalContext getExternalContext() {
        return externalContext;
    }

    public boolean getDebugMode() {
        return false;
    }

    public boolean getProfilerMode() {
        return false;
    }

    public final Locale getUserLocale() {
        if (userLocale != null) {
            return userLocale;
        }

        userLocale = ContextTools.getUserLocale(null);

        return userLocale;
    }

}
