/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.1  2006/06/27 09:23:09  oeuillot
 * Mise � jour du calendrier de dateChooser
 *
 */
package org.rcfaces.core.component.capability;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageSizeCapability {
    int getImageWidth();

    void setImageWidth(int width);

    int getImageHeight();

    void setImageHeight(int height);
}
