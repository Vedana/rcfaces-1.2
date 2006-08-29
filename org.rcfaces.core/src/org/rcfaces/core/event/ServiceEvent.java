/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2006/08/28 16:03:56  oeuillot
 * Version avant migation en org.rcfaces
 *
 * Revision 1.1  2006/03/02 15:31:56  oeuillot
 * Ajout de ExpandBar
 * Ajout des services
 * Ajout de HiddenValue
 * Ajout de SuggestTextEntry
 * Ajout de f_bundle
 * Ajout de f_md5
 * Debut de f_xmlDigester
 *
 * Revision 1.2  2006/01/31 16:04:25  oeuillot
 * Ajout :
 * Decorator pour les listes, tree, menus, ...
 * Ajax (filtres) pour les combo et liste
 * Renomme interactiveRenderer par AsyncRender
 * Ajout du composant Paragraph
 *
 * Revision 1.1  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.service.NullProgressMonitor;
import org.rcfaces.core.progressMonitor.IProgressMonitor;


/**
 * @author Olivier Oeuillot
 * @version $Revision$
 */
public class ServiceEvent extends ActionEvent {
    private static final String REVISION = "$Revision$";

    private final Object data;

    private IProgressMonitor progressMonitor;

    public ServiceEvent(UIComponent component, Object data) {
        super(component);

        this.data = data;
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof IServiceEventListener);
    }

    public void processListener(FacesListener listener) {
        ((IServiceEventListener) listener).processServiceEvent(this);
    }

    public final Object getData() {
        return data;
    }

    public IProgressMonitor getProgressMonitor() {
        if (progressMonitor != null) {
            return progressMonitor;
        }
        progressMonitor = createProgressMonitor();

        return progressMonitor;
    }

    protected void resetProgressMonitor() {
        progressMonitor = null;
    }

    protected IProgressMonitor createProgressMonitor() {
        return new NullProgressMonitor();
    }
}