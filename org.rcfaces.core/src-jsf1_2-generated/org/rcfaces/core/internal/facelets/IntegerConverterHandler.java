package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.jsf.ConverterConfig;

public class IntegerConverterHandler extends CameliaConverterHandler {

    public IntegerConverterHandler(ConverterConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Integer";
    }

}
