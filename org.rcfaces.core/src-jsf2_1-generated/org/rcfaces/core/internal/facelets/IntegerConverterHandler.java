package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class IntegerConverterHandler extends CameliaConverterHandler {

	public IntegerConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Integer";
	}

}
