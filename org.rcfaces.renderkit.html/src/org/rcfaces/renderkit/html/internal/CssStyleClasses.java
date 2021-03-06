/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

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

    private static final Log LOG = LogFactory.getLog(CssStyleClasses.class);

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final String mainStyleClassName;

    private Collection<String> specificStyleClasses;

    private Collection<String> styleClasseNames;

    private Collection<String> suffixes;

    public CssStyleClasses() {
        this.mainStyleClassName = null;
    }

    public CssStyleClasses(String mainStyleClassName) {
        verifyStyleClass(mainStyleClassName);

        this.mainStyleClassName = mainStyleClassName;
    }

    public CssStyleClasses(String mainStyleClassName, String styleClasses[]) {
        this(mainStyleClassName);

        if (styleClasses != null) {
            for (int i = 0; i < styleClasses.length; i++) {
                addStyleClass(styleClasses[i]);
            }
        }
    }

    public CssStyleClasses(String mainStyleClassName,
            String componentStyleClasse) {
        this(mainStyleClassName);

        if (componentStyleClasse != null) {
            addStyleClass(componentStyleClasse);
        }
    }

    private void verifyStyleClass(String styleClass) {
        if (styleClass == null) {
            return;
        }

        if (styleClass.indexOf(' ') < 0) {
            return;
        }

        throw new IllegalArgumentException(
                "StyleClass can not contains spaces ! (" + styleClass + ")");
    }

    public ICssStyleClasses addSpecificStyleClass(String... styleClasses) {
        if (styleClasses == null || styleClasses.length < 1) {
            return this;
        }

        if (specificStyleClasses == null) {
            specificStyleClasses = new OrderedSet<String>(4);
        }

        for (String styleClass : styleClasses) {
            StringTokenizer st = new StringTokenizer(styleClass);
            for (; st.hasMoreTokens();) {
                specificStyleClasses.add(st.nextToken());
            }
        }

        return this;
    }

    public ICssStyleClasses addStyleClass(String... styleClasses) {
        if (styleClasses == null || styleClasses.length < 1) {
            return this;
        }

        if (styleClasseNames == null) {
            styleClasseNames = new OrderedSet<String>(4);

            if (mainStyleClassName != null) {
                styleClasseNames.add(mainStyleClassName);
            }
        }

        for (String styleClass : styleClasses) {
            StringTokenizer st = new StringTokenizer(styleClass);
            for (; st.hasMoreTokens();) {
                styleClasseNames.add(st.nextToken());
            }
        }

        return this;
    }

    public ICssStyleClasses addSuffix(String... suffixStyleClasses) {
        if (suffixStyleClasses == null || suffixStyleClasses.length < 1) {
            return this;
        }

        if (suffixes == null) {
            suffixes = new OrderedSet<String>(2);
        }

        for (String suffixStyleClass : suffixStyleClasses) {
            verifyStyleClass(suffixStyleClass);

            suffixes.add(suffixStyleClass);
        }

        return this;
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
            return styleClasseNames
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
            // Il y a le mainStyleClass dedans ...

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

    public String constructUserStyleClasses() {
        if (styleClasseNames == null || styleClasseNames.isEmpty()) {
            return null;
        }

        Collection os = specificStyleClasses;

        if (mainStyleClassName != null
                && styleClasseNames.contains(mainStyleClassName)) {
            os = new OrderedSet<String>(styleClasseNames);
            os.remove(mainStyleClassName);
        }

        if (os == null || os.isEmpty()) {
            return null;
        }

        if (os.size() == 1) {
            if (os instanceof List) {
                return (String) ((List) os).get(0);
            }

            return (String) os.iterator().next();
        }

        StringAppender sa = new StringAppender(os.size() * 32);

        for (Iterator it = os.iterator(); it.hasNext();) {
            String styleClassName = (String) it.next();

            if (sa.length() > 0) {
                sa.append(' ');
            }
            sa.append(styleClassName);
        }

        return sa.toString();
    }

    public boolean hasStyles() {
        if (mainStyleClassName != null && mainStyleClassName.length() > 0) {
            return true;
        }
        if (styleClasseNames != null && styleClasseNames.size() > 0) {
            return true;
        }
        if (specificStyleClasses != null && specificStyleClasses.size() > 0) {
            return true;
        }

        return false;
    }
}
