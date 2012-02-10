/*
 * $Id$
 * 
 */
package org.rcfaces.core.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesListener;

import org.rcfaces.core.internal.service.NullProgressMonitor;
import org.rcfaces.core.progressMonitor.IProgressMonitor;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ServiceEvent extends ActionEvent {

    private static final long serialVersionUID = -5716525897091852723L;

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
