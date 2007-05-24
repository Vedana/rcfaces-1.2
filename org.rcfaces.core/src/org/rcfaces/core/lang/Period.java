/*
 * $Id$
 */
package org.rcfaces.core.lang;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Period extends DefaultAdaptable implements Serializable,
        Comparable {
    private static final String REVISION = "$Revision$";

    private static final long serialVersionUID = -3717158186943282329L;

    private final Date start;

    private final Date end;

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public final Date getEnd() {
        return end;
    }

    public final Date getStart() {
        return start;
    }

    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((end == null) ? 0 : end.hashCode());
        result = PRIME * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Period other = (Period) obj;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        return true;
    }

    public int compareTo(Object o) {
        return start.compareTo(((Period) o).getStart());
    }

}
