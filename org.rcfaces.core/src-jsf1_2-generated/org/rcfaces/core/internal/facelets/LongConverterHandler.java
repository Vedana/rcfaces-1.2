package org.rcfaces.core.internal.facelets;

import com.sun.facelets.tag.jsf.ConverterConfig;

public class LongConverterHandler extends CameliaConverterHandler {

	public LongConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Long";
	}

}
