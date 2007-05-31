package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class ShortConverterHandler extends CameliaConverterHandler {

    public ShortConverterHandler(TagConfig config) {
        super(config);
    }

    protected String getConverterId() {
        return "org.rcfaces.Short";
    }

}
