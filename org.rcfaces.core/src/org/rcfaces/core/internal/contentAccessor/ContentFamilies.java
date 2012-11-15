/*
 * $Id$
 */
package org.rcfaces.core.internal.contentAccessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.lang.IContentFamily;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ContentFamilies {
    private static final Log LOG = LogFactory.getLog(ContentFamilies.class);

    private static int ORDINAL;

    private static final List<IContentFamily> contentFamilies = new ArrayList<IContentFamily>(
            8);

    public static IContentFamily getContentFamillyByOrdinal(int ordinal) {

        for (Iterator<IContentFamily> it = contentFamilies.iterator(); it
                .hasNext();) {
            IContentFamily contentFamily = it.next();

            if (contentFamily.getOrdinal() == ordinal) {
                return contentFamily;
            }
        }

        return null;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class ContentTypeImpl implements IContentFamily {

        private final String id;

        private final int ordinal;

        public ContentTypeImpl(String id) {
            this.id = id;
            this.ordinal = ORDINAL++;

            contentFamilies.add(this);
        }

        @Override
        public String toString() {
            return "[Content type: id='" + id + "' ordinal=" + ordinal + "]";
        }

        public final int getOrdinal() {
            return ordinal;
        }

        @Override
        public int hashCode() {
            return ordinal;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            final ContentTypeImpl other = (ContentTypeImpl) obj;

            return this.ordinal == other.ordinal;
        }
    }
}
