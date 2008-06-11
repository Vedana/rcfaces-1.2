/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.lang.OrderedSet;
import org.rcfaces.renderkit.html.internal.renderer.ICssStyleClasses;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssStyleClasses implements ICssStyleClasses {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory.getLog(CssStyleClasses.class);

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final String mainStyleClassName;

    private Collection specificStyleClasses;

    private Collection styleClasseNames;

    private Collection suffixes;

    public CssStyleClasses(String mainStyleClassName, String styleClasses[]) {
        this.mainStyleClassName = mainStyleClassName;

        if (styleClasses != null && styleClasses.length > 0) {
            styleClasseNames = new OrderedSet(Arrays.asList(styleClasses));
        }
    }

    public CssStyleClasses(String mainStyleClassName,
            String componentStyleClasse) {
        this.mainStyleClassName = mainStyleClassName;

        if (componentStyleClasse != null) {
            addStyleClass(componentStyleClasse);
        }
    }

    public void addSpecificStyleClass(String styleClass) {
        if (specificStyleClasses == null) {
            specificStyleClasses = new OrderedSet(4);
        }

        specificStyleClasses.add(styleClass);
    }

    public void addStyleClass(String styleClass) {
        if (styleClasseNames == null) {
            styleClasseNames = new OrderedSet(4);

            if (mainStyleClassName != null) {
                styleClasseNames.add(mainStyleClassName);
            }
        }

        styleClasseNames.add(styleClass);
    }

    public void addSuffix(String suffixStyleClass) {
        if (suffixes == null) {
            suffixes = new OrderedSet(2);
        }

        suffixes.add(suffixStyleClass);
    }

    public String getMainStyleClass() {
        return mainStyleClassName;
    }

    public String getSuffixedMainStyleClass(String suffix) {
        if (mainStyleClassName == null) {
            return null;
        }

        if (suffix == null) {
            return mainStyleClassName;
        }

        return mainStyleClassName + suffix;
    }

    public String[] listStyleClasses() {
        if (styleClasseNames != null) {
            return (String[]) styleClasseNames
                    .toArray(new String[styleClasseNames.size()]);
        }

        if (mainStyleClassName != null) {
            return new String[] { mainStyleClassName };
        }

        return EMPTY_STRING_ARRAY;
    }

    public String constructClassName() {
        StringAppender sa = new StringAppender(128);

        if (styleClasseNames != null) {
            for (Iterator it = styleClasseNames.iterator(); it.hasNext();) {
                String styleClassName = (String) it.next();

                if (sa.length() > 0) {
                    sa.append(' ');
                }
                sa.append(styleClassName);

                if (suffixes != null) {
                    for (Iterator it2 = suffixes.iterator(); it2.hasNext();) {
                        String suffix = (String) it2.next();

                        sa.append(' ');
                        sa.append(styleClassName);
                        sa.append(suffix);
                    }
                }
            }

        } else if (mainStyleClassName != null) {
            if (sa.length() > 0) {
                sa.append(' ');
            }
            sa.append(mainStyleClassName);

            if (suffixes != null) {
                for (Iterator it = suffixes.iterator(); it.hasNext();) {
                    String suffix = (String) it.next();

                    sa.append(' ');
                    sa.append(mainStyleClassName);
                    sa.append(suffix);
                }
            }
        }

        if (specificStyleClasses != null) {
            for (Iterator it = specificStyleClasses.iterator(); it.hasNext();) {
                String styleClassName = (String) it.next();

                if (sa.length() > 0) {
                    sa.append(' ');
                }
                sa.append(styleClassName);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Compute styleClass:  mainStyleClassName='"
                    + mainStyleClassName + "' styleClassNames='"
                    + styleClasseNames + "' suffixes='" + suffixes
                    + "' specific='" + specificStyleClasses + "' => " + sa
                    + "'.");
        }

        return sa.toString();
    }
}
