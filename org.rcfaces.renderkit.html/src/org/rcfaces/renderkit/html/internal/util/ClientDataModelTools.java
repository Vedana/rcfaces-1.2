/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.util;

import org.rcfaces.core.model.IClientDataModel;
import org.rcfaces.core.model.IClientModel.IContentIndex;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ClientDataModelTools {

    public static String format(IClientDataModel clientDataModel) {
        IContentIndex[] indexes = clientDataModel.listContentIndexes();

        if (indexes == null || indexes.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder(256);

        for (IContentIndex contentIndex : indexes) {
            if (sb.length() > 0) {
                sb.append(',');
            }

            String fieldName = contentIndex.getFieldName();
            sb.append(fieldName);

            switch (contentIndex.getStrategy()) {
            case Equals:
                break;
            case StartsWith:
                sb.append(":sw");
                break;
            case FullText:
                sb.append(":ft");
                break;
            }

            if (contentIndex.isIgnoreCase()) {
                sb.append(":ic");
            }
            if (contentIndex.isIgnoreAccent()) {
                sb.append(":ia");
            }
            if (contentIndex.isEachWord()) {
                sb.append(":ew");
            }
        }

        return sb.toString();
    }

}
