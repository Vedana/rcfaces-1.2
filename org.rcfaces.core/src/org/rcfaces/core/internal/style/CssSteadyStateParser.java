package org.rcfaces.core.internal.style;

import java.io.IOException;
import java.io.StringReader;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.core.internal.util.IPath;
import org.rcfaces.core.internal.util.Path;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSCharsetRule;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

import com.steadystate.css.parser.CSSOMParser;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
class CssSteadyStateParser implements ICssParser {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(CssSteadyStateParser.class);

    public String getParserName() {
        return "Steady State Css parser";
    }

    public String mergesBuffer(IResourceLoaderFactory resourceLoaderFactory,
            String baseURL, String styleSheetContent, String defaultCharSet)
            throws IOException {

        CSSOMParser parser = new CSSOMParser();

        CSSStyleSheet styleSheet = parser.parseStyleSheet(new InputSource(
                new StringReader(styleSheetContent)));

        computeStyleSheet(styleSheet, new Path(baseURL), new Path(""), false,
                new ParserContext(resourceLoaderFactory, defaultCharSet));

        return styleSheet.toString();
    }

    private void computeStyleSheet(CSSStyleSheet styleSheet, IPath base,
            IPath relativePath, boolean removeCharset,
            ParserContext parserContext) {

        CSSRuleList ruleList = styleSheet.getCssRules();
        for (int i = 0; i < ruleList.getLength(); i++) {
            CSSRule rule = ruleList.item(i);

            if (rule instanceof CSSCharsetRule) {
                if (removeCharset) {
                    styleSheet.deleteRule(i--);
                    continue;
                }

                removeCharset = true;
                continue;
            }

            if (rule instanceof CSSStyleRule) {
                CSSStyleDeclaration declaration = ((CSSStyleRule) rule)
                        .getStyle();

                for (int j = 0; j < declaration.getLength(); j++) {
                    String property = declaration.item(j);

                    CSSValue value = declaration.getPropertyCSSValue(property);

                    if (value instanceof CSSPrimitiveValue) {
                        alterCssValue((CSSPrimitiveValue) value, relativePath);
                    }

                    if (value instanceof CSSValueList) {
                        CSSValueList list = (CSSValueList) value;

                        for (int k = 0; k < list.getLength(); k++) {
                            alterCssValue((CSSPrimitiveValue) list.item(k),
                                    relativePath);
                        }
                    }
                }
            }

            if ((rule instanceof CSSImportRule) == false) {
                continue;
            }

            CSSImportRule importRule = (CSSImportRule) rule;

            String href = importRule.getHref();

            if (href == null || href.length() < 1) {
                continue;
            }

            href = href.trim();

            if (href.toLowerCase().startsWith("url(")) {
                int idx2 = href.lastIndexOf(')');
                if (idx2 < 0) {
                    idx2 = href.length();
                }

                href = href.substring(4, idx2).trim();

                if (href.startsWith("\"") && href.endsWith("\"")) {
                    href = href.substring(1, href.length() - 1);

                } else if (href.startsWith("'") && href.endsWith("'")) {
                    href = href.substring(1, href.length() - 1);
                }
            }

            if (href.length() < 1 || href.indexOf(':') >= 0
                    || href.indexOf("//") >= 0 || href.charAt(0) == '/') {
                // On ne traite pas les absolus
                continue;
            }

            if (href.indexOf('#') >= 0 || href.indexOf('?') >= 0) {
                continue;
            }

            // @TODO on doit verifier les medias ...

            IPath newPath = new Path(href);

            newPath = base.removeFirstSegments(1).append(newPath);

            relativePath = relativePath.append(newPath.removeLastSegments(1));

            String importedPath = newPath.toString();

            try {
                String childContent = StyleOperationContentModel.loadContent(
                        parserContext.facesContext,
                        parserContext.resourceLoaderFactory, importedPath,
                        parserContext.defaultCharset, null);

                InputSource inputSource = new InputSource(new StringReader(
                        childContent));

                CSSOMParser parser = new CSSOMParser();

                CSSStyleSheet importedStyleSheet = parser
                        .parseStyleSheet(inputSource);

                computeStyleSheet(importedStyleSheet, newPath, relativePath,
                        true, parserContext);

                styleSheet.deleteRule(i);

                CSSRuleList importedRuleList = importedStyleSheet.getCssRules();

                for (int j = 0; j < importedRuleList.getLength(); j++) {
                    styleSheet.insertRule(importedRuleList.item(j).toString(),
                            i++);
                }

                i--;

            } catch (IOException ex) {
                LOG.error("Can not inline css '" + importedPath + "'", ex);
            }
        }
    }

    private void alterCssValue(CSSPrimitiveValue value, IPath relativePath) {
        if (value.getPrimitiveType() != CSSPrimitiveValue.CSS_URI) {
            return;
        }

        String s = value.getStringValue();

        System.out.println("S=" + s);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ParserContext {
        FacesContext facesContext;

        IResourceLoaderFactory resourceLoaderFactory;

        String defaultCharset;

        public ParserContext(IResourceLoaderFactory resourceLoaderFactory,
                String defaultCharSet) {
            this.facesContext = FacesContext.getCurrentInstance();
            this.resourceLoaderFactory = resourceLoaderFactory;
            this.defaultCharset = defaultCharSet;
        }

    }
}