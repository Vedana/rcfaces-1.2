package org.rcfaces.core.internal.facelets;

import javax.faces.view.facelets.ConverterConfig;

public class ByteConverterHandler extends CameliaConverterHandler {

	public ByteConverterHandler(ConverterConfig config) {
		super(config);
	}

	@Override
	protected String getConverterId() {
		return "org.rcfaces.Byte";
	}

}
