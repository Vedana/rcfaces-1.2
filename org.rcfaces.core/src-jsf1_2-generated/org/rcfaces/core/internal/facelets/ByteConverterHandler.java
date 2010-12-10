package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.jsf.ConverterConfig;

public class ByteConverterHandler extends CameliaConverterHandler {

    public ByteConverterHandler(ConverterConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Byte";
    }

}
