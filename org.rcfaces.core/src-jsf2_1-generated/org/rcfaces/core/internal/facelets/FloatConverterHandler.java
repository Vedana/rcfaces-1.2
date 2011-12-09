package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class FloatConverterHandler extends CameliaConverterHandler {

	public FloatConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Float";
	}

}
