package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.jsf.ConverterConfig;

public class ShortConverterHandler extends CameliaConverterHandler {

    public ShortConverterHandler(ConverterConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Short";
    }

}
