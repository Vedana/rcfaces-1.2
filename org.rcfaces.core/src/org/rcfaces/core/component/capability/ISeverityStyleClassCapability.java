/*
 * $Id$
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISeverityStyleClassCapability extends IStyleClassCapability {
    String getInfoStyleClass();

    void setInfoStyleClass(String infoStyleClass);

    String getWarnStyleClass();

    void setWarnStyleClass(String warnStyleClass);

    String getErrorStyleClass();

    void setErrorStyleClass(String errorStyleClass);

    String getFatalStyleClass();

    void setFatalStyleClass(String fatalStyleClass);

}
