package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class IntegerConverterHandler extends CameliaConverterHandler {

	public IntegerConverterHandler(TagConfig config) {
		super(config);
	}

	protected String getConverterId() {
		return "org.rcfaces.Integer";
	}

}
