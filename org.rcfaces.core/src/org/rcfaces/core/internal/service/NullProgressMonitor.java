/*
 * $Id$
 * 
 * $Log$
 * Revision 1.1  2006/08/29 16:13:14  oeuillot
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
 */
package org.rcfaces.core.internal.service;

import org.rcfaces.core.progressMonitor.IProgressMonitor;

/**
 * 
 * @author Olivier Oeuillot
 * @version $Revision$
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
