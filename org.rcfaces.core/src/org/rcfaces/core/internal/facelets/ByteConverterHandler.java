package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class ByteConverterHandler extends CameliaConverterHandler {

	public ByteConverterHandler(TagConfig config) {
		super(config);
	}

	protected String getConverterId() {
		return "org.rcfaces.Byte";
	}

}
