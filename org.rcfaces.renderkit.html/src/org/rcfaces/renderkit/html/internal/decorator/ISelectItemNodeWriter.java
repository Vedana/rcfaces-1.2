/*
 * $Id$
 * 
 */
package org.rcfaces.renderkit.html.internal.decorator;

import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;

import org.rcfaces.core.internal.renderkit.WriterException;


/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface ISelectItemNodeWriter {

    int EVAL_NODE = 0;

    int SKIP_NODE = 1;

    // int SHOW_NODE = 2;

    SelectItemsContext getContext();

    void encodeNodeInit(UIComponent component, SelectItem selectItem);

    int encodeNodeBegin(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException;

    void encodeNodeEnd(UIComponent component, SelectItem selectItem,
            boolean hasChild, boolean isVisible) throws WriterException;
}
