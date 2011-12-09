package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class DoubleConverterHandler extends CameliaConverterHandler {

	public DoubleConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Double";
	}

}
