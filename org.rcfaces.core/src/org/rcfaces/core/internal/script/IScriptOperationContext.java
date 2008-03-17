/*
 * $Id$
 */
package org.rcfaces.core.internal.script;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IScriptOperationContext {
    String getCharset();

    void setCharset(String charset);

    long getLastModifiedDate();

    void setLastModifiedDate(long lastModifiedDate);

}
