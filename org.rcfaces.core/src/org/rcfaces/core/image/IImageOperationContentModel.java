/*
 * $Id$
 */
package org.rcfaces.core.image;

import java.util.Map;

import org.rcfaces.core.model.IContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageOperationContentModel extends IContentModel {

    Map<String, Object> getFilterParameters();
}
