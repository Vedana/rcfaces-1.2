/*
 * $Id$
 */
package org.rcfaces.jfreechart.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class Constants {
	private static final Log LOG = LogFactory.getLog(Constants.class);

	private static final String RCFACES_JFREECHART_VERSION_SYSTEM_PARAMETER = "rcfaces.jfreechart.version";

	private static final String BUILDER_VERSION = null;

	private static final String version;

	private static final String CONSTANT_PREFIX;
	static {
		String name = Constants.class.getPackage().getName();
		if (name.endsWith(".internal")) {
			name = name.substring(0, name.lastIndexOf('.'));
		}

		CONSTANT_PREFIX = name;
	}

	static {
		if (BUILDER_VERSION != null) {
			version = BUILDER_VERSION;

		} else {
			version = org.rcfaces.core.internal.Constants.searchVersion(
					Constants.class,
					RCFACES_JFREECHART_VERSION_SYSTEM_PARAMETER,
					"RCFaces ImageIO");
		}

		LOG.info("RCFaces jfreeChart version='" + version + "'");
	}

	public static final String getPackagePrefix() {
		return CONSTANT_PREFIX;
	}

}
