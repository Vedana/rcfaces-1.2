package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class FloatConverterHandler extends CameliaConverterHandler {

    public FloatConverterHandler(TagConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Float";
    }

}
