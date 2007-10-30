/*
 * $Id$
 */
package org.rcfaces.core.internal.util;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public interface IPath {

    IPath makeAbsolute();

    IPath makeRelative();

    boolean isAbsolute();

    String lastSegment();

    IPath append(IPath path);

    IPath removeFirstSegments(int count);

    IPath removeLastSegments(int count);

    String segment(int index);

    int segmentCount();

    String[] segments();

    IPath uptoSegment(int count);
}
