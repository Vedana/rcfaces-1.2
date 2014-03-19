/*
 * $Id$
 */
package org.rcfaces.core.internal.content;

import java.util.Map;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IBufferOperation {

    String getName();

    void setName(String name);

    void configure(Map<String, Object> configuration);

}
