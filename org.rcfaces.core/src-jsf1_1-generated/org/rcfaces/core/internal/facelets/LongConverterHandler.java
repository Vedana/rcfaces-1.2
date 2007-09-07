package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.TagConfig;

public class LongConverterHandler extends CameliaConverterHandler {

	public LongConverterHandler(TagConfig config) {
		super(config);
	}

	protected String getConverterId() {
		return "org.rcfaces.Long";
	}

}
