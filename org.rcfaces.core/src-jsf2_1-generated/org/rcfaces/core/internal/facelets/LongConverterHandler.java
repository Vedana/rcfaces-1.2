package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class LongConverterHandler extends CameliaConverterHandler {

	public LongConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Long";
	}

}
