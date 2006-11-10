/*
 * $Id$
 * 
 */
package org.rcfaces.core.component.iterator;

import org.rcfaces.core.component.ImageRadioButtonComponent;

/**
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IImageRadioButtonIterator extends IComponentIterator {

	ImageRadioButtonComponent next();
    
    ImageRadioButtonComponent [] toArray();
}
