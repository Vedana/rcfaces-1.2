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

    ICssStyleClasses addStyleClass(String... styleClass);

    ICssStyleClasses addSuffix(String... suffixStyleClass);

    ICssStyleClasses addSpecificStyleClass(String... styleClass);

    String constructUserStyleClasses();

    boolean hasStyles();
}
