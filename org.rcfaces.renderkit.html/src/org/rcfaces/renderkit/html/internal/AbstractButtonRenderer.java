/*
 * $Id$
 * 
 * $Log$
 * Revision 1.2  2006/09/14 14:34:38  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:14:27  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.5  2006/04/27 13:49:48  oeuillot
 * Ajout de ImageSubmitButton
 * Refactoring des composants internes (dans internal.*)
 * Corrections diverses
 *
 * Revision 1.4  2006/03/28 12:22:47  oeuillot
 * Split du IWriter, ISgmlWriter, IHtmlWriter et IComponentWriter
 * Ajout du hideRootNode
 *
 * Revision 1.3  2005/10/05 14:34:19  oeuillot
 * Version avec decode/validation/update des propri�t�s des composants
 *
 * Revision 1.2  2004/11/19 18:01:30  oeuillot
 * Version debut novembre
 *
 * Revision 1.1  2004/08/31 15:30:33  oeuillot
 * *** empty log message ***
 *
 */
package org.rcfaces.renderkit.html.internal;

import javax.faces.component.UIComponent;

import org.rcfaces.core.component.capability.IDisabledCapability;
import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public abstract class AbstractButtonRenderer extends AbstractCssRenderer {

	private static final String REVISION = "$Revision$";

	protected String getButtonType(UIComponent component) {
		return "button";
	}

	protected final IHtmlWriter writeButtonAttributes(IHtmlWriter writer)
			throws WriterException {
		UIComponent component = writer.getComponentRenderContext()
				.getComponent();

		return writeButtonAttributes(writer, component.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.rcfaces.core.internal.renderkit.html.AbstractHtmlRenderer#writeAttributes(org.rcfaces.core.internal.renderkit.IWriter)
	 */
	protected final IHtmlWriter writeButtonAttributes(IHtmlWriter writer, String id)
			throws WriterException {
		UIComponent component = writer.getComponentRenderContext()
				.getComponent();

		if (id != null) {
			writer.writeAttribute("name", id);
		}

		String type = getButtonType(component);
		if (type != null) {
			writer.writeAttribute("type", type);
		}

		if (component instanceof IDisabledCapability) {
			writeEnabled(writer, (IDisabledCapability) component);
		}

		return writer;
	}
}