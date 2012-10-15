/*
 * $Id$
 */
package org.rcfaces.core.model;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IClientModel {
    /**
     * 
     * @return Returns the name of the database
     */
    String getContentName();

    /**
     * 
     * @return Returns 128 bytes length content Key
     */
    String getContentKey();

    /**
     * 
     * @return Return the row count of the database
     */
    int getContentRowCount();

    /**
     * 
     * @return How to compute primary key
     */
    String getContentPrimaryKey();

    IContentIndex[] listContentIndexes();

    public interface IContentIndex {
        public enum IndexStrategy {
            Equals, StartsWith, FullText
        }

        String getFieldName();

        IndexStrategy getStrategy();

        boolean isIgnoreCase();

        boolean isIgnoreAccent();

        boolean isEachWord();
    }

}
