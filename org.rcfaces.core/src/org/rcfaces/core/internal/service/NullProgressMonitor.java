/*
 * $Id$
 */
package org.rcfaces.core.internal.service;

import org.rcfaces.core.progressMonitor.IProgressMonitor;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class NullProgressMonitor implements IProgressMonitor {
    private static final String REVISION = "$Revision$";

    private boolean canceled;

    public void beginTask(String name, int totalWork) {
    }

    public void done() {
    }

    public void internalWorked(double work) {
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public void setTaskName(String name) {
    }

    public void subTask(String name) {
    }

    public void worked(int work) {
    }

}
