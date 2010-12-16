package org.rcfaces.core.internal.converter;

import javax.faces.FacesException;

import org.rcfaces.core.component.capability.IAlignmentCapability;

public class AlignmentNormalizer {

	public static String normalize(String alignment) {
		if (alignment == null) {
			return null;
		}
		alignment = alignment.trim().toLowerCase();
		if (alignment.length() == 0) {
			return null;
		}
		for (int i = 0; i < IAlignmentCapability.VALUES.length; i++) {
			if (IAlignmentCapability.VALUES[i].equals(alignment)) {
				return alignment;
			}
		}
		throw new FacesException("Incorrect alignment value \"" + alignment + "\".");
	}
}
