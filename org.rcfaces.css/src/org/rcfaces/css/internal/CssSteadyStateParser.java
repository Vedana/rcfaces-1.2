package org.rcfaces.css.internal;

import java.io.IOException;
import java.io.StringReader;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.style.StyleOperationContentModel;
import org.rcfaces.core.internal.style.CssParserFactory.ICssParser;
import org.rcfaces.core.internal.style.StyleOperationContentModel.ContentInformation;
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
public class CssSteadyStateParser implements ICssParser {
    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(CssSteadyStateParser.class);

    static {
        LOG.info("Enable 'CssSteadyState' css parser.");
    }

    public String getParserName() {
        return "Steady State Css parser";
    }

    public String mergesBuffer(IResourceLoaderFactory resourceLoaderFactory,
            String baseURL, String styleSheetContent,
            IParserContext parserContext) throws IOException {

        CSSOMParser parser = new CSSOMParser();

        CSSStyleSheet styleSheet = parser.parseStyleSheet(new InputSource(
                new StringReader(styleSheetContent)));

        IPath base = new Path(baseURL).makeRelative();
        IPath relative = new Path("..").append(base.removeLastSegments(1));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Merge buffer of '" + base + "' relative='" + relative
                    + "'");
        }

        computeStyleSheet(styleSheet, base, relative, new ParserContext(
                resourceLoaderFactory, parserContext));

        return styleSheet.toString();
    }

    private void computeStyleSheet(CSSStyleSheet styleSheet, IPath base,
            IPath relativePath, ParserContext parserContext) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Refactor stylesheet: '" + base + "'.");
        }

        CSSRuleList ruleList = styleSheet.getCssRules();
        for (int i = 0; i < ruleList.getLength(); i++) {
            CSSRule rule = ruleList.item(i);

            if (rule instanceof CSSCharsetRule) {
                // On enleve le Charset systematiquement !
                styleSheet.deleteRule(i--);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Remove charset rule");
                }
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

                continue;
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

            if (isValidURL(href) == false) {
                if (LOG.isDebugEnabled()) {
                    LOG
                            .debug("Ignore import rule: absolute or protocol, # or ? detected. ("
                                    + href + ")");
                }
                continue;
            }

            // @TODO on doit verifier les medias ...

            IPath newPath = new Path(href);

            IPath newRelativePath = relativePath.append(newPath
                    .removeLastSegments(1));

            newPath = base.removeLastSegments(1).append(newPath);

            String importedPath = newPath.toString();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Import rule detected: '" + href + "' realPath='"
                        + importedPath + "' relativePath='" + relativePath
                        + "'.");
            }

            try {
                ContentInformation contentInformationRef[] = new ContentInformation[1];

                String childContent = StyleOperationContentModel.loadContent(
                        parserContext.facesContext,
                        parserContext.resourceLoaderFactory, importedPath,
                        parserContext.parserContext.getCharset(),
                        contentInformationRef);

                if (childContent == null) {
                    LOG.error("Can not load css resource '" + importedPath
                            + "'.");
                    continue;
                }

                if (contentInformationRef[0] != null) {
                    ContentInformation contentInformation = contentInformationRef[0];

                    IParserContext pc = parserContext.parserContext;

                    long lastModified = contentInformation.getLastModified();
                    if (pc.getLastModifiedDate() < 0
                            || lastModified > pc.getLastModifiedDate()) {
                        pc.setLastModifiedDate(lastModified);
                    }
                }

                InputSource inputSource = new InputSource(new StringReader(
                        childContent));

                CSSOMParser parser = new CSSOMParser();

                CSSStyleSheet importedStyleSheet = parser
                        .parseStyleSheet(inputSource);

                computeStyleSheet(importedStyleSheet, newPath, newRelativePath,
                        parserContext);

                styleSheet.deleteRule(i);

                CSSRuleList importedRuleList = importedStyleSheet.getCssRules();

                for (int j = 0; j < importedRuleList.getLength(); j++) {
                    CSSRule cssRule = importedRuleList.item(j);

                    if (cssRule instanceof CSSStyleRule) {
                        CSSStyleRule cssStyleRule = (CSSStyleRule) cssRule;

                        if (cssStyleRule.getStyle().getLength() == 0) {

                            if (LOG.isTraceEnabled()) {
                                LOG.trace("Remove rule '" + cssRule + "'");
                            }

                            continue;
                        }
                    }

                    styleSheet.insertRule(cssRule.toString(), i++);
                }

                i--;

            } catch (IOException ex) {
                LOG.error("Can not inline css '" + importedPath + "'", ex);
            }
        }

        if (LOG.isTraceEnabled()) {
            LOG.debug("Css content of '" + base + "': " + styleSheet);
        }
    }

    private void alterCssValue(CSSPrimitiveValue value, IPath relativePath) {
        if (value.getPrimitiveType() != CSSPrimitiveValue.CSS_URI) {
            return;
        }

        String href = value.getStringValue().trim();
        if (href.length() < 1) {
            return;
        }

        if (isValidURL(href) == false) {
            if (LOG.isDebugEnabled()) {
                LOG
                        .debug("Ignore import rule: absolute or protocol, # or ? detected. ("
                                + href + ")");
            }
            return;
        }

        IPath path = new Path(href);
        if (path.isAbsolute()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Path relocation: Ignore absolute path '" + path
                        + "'.");
            }
            return;
        }

        IPath newPath = relativePath.append(path);

        value.setStringValue(CSSPrimitiveValue.CSS_URI, newPath.toString());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Path relocation: change path '" + href + "' to '"
                    + newPath + "'.");
        }
    }

    private boolean isValidURL(String href) {
        if (href.length() < 1 || href.indexOf(':') >= 0
                || href.indexOf("//") >= 0 || href.charAt(0) == '/'
                || href.indexOf('#') >= 0 || href.indexOf('?') >= 0) {
            // On ne traite pas les absolus
            return false;
        }

        return true;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    private static class ParserContext {
        private static final String REVISION = "$Revision$";

        FacesContext facesContext;

        IResourceLoaderFactory resourceLoaderFactory;

        IParserContext parserContext;

        public ParserContext(IResourceLoaderFactory resourceLoaderFactory,
                IParserContext parserContext) {
            this.facesContext = FacesContext.getCurrentInstance();
            this.resourceLoaderFactory = resourceLoaderFactory;
            this.parserContext = parserContext;
        }

    }
}