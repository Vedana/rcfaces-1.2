/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ICssStyleClasses {
    String getMainStyleClass();

    String getSuffixedMainStyleClass(String suffix);

    String constructClassName();

    String[] listStyleClasses();

    void addStyleClass(String styleClass);

    void addSuffix(String suffixStyleClass);

    void addSpecificStyleClass(String styleClass);

    String constructUserStyleClasses();
}
