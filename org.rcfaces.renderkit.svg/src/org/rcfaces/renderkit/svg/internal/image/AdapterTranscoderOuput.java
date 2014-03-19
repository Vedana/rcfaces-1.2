package org.rcfaces.renderkit.svg.internal.image;

import java.awt.image.BufferedImage;

import org.apache.batik.transcoder.TranscoderOutput;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class AdapterTranscoderOuput extends TranscoderOutput {

    private BufferedImage bufferedImage;

    public AdapterTranscoderOuput() {
    }

    public final BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public final void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

}