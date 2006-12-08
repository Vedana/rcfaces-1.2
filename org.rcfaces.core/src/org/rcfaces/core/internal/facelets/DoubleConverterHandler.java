package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class DoubleConverterHandler extends CameliaConverterHandler {

	public DoubleConverterHandler(TagConfig config) {
		super(config);
	}

	protected String getConverterId() {
		return "org.rcfaces.Double";
	}

}
