/*
 * $Id$
 */
package org.rcfaces.jfreechart.renderer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.faces.FacesException;

import org.jfree.ui.Drawable;
import org.rcfaces.core.image.ImageContentModel;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class ChartContentModel extends ImageContentModel {

    @Override
    protected BufferedImage getBufferedImage() {

        GenerationChartInformation chartInformation = (GenerationChartInformation) generatedInformation;

        BufferedImage bufferedImage = new BufferedImage(chartInformation
                .getImageWidth(), chartInformation.getImageHeight(),
                BufferedImage.TYPE_INT_RGB);

        Drawable drawable = chartInformation.getDrawable();
        if (drawable == null) {
            throw new FacesException("Drawable is NULL !");
        }

        Graphics2D g = bufferedImage.createGraphics();

        try {
            drawable.draw(g, new Rectangle(0, 0, bufferedImage.getWidth(),
                    bufferedImage.getHeight()));

        } finally {
            g.dispose();
        }

        return bufferedImage;
    }
}
