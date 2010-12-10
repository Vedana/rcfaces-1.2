package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.jsf.ConverterConfig;

public class FloatConverterHandler extends CameliaConverterHandler {

    public FloatConverterHandler(ConverterConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Float";
    }

}
