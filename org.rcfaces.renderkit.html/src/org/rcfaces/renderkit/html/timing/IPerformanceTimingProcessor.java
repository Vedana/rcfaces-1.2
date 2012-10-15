/*
 * $Id$
 */
package org.rcfaces.renderkit.html.timing;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPerformanceTimingProcessor {
    void process(FacesContext facesContext, IPerformanceTiming timing);
}
