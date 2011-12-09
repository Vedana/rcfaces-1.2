package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class ShortConverterHandler extends CameliaConverterHandler {

	public ShortConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Short";
	}

}
