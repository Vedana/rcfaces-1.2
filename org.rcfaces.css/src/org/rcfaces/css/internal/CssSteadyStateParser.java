package org.rcfaces.css.internal;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.internal.content.AbstractBufferOperationContentModel.ContentInformation;
import org.rcfaces.core.internal.content.IOperationContentLoader;
import org.rcfaces.core.internal.lang.StringAppender;
import org.rcfaces.core.internal.resource.IResourceLoaderFactory;
import org.rcfaces.core.internal.util.IPath;
import org.rcfaces.core.internal.util.Path;
import org.rcfaces.core.lang.IContentFamily;
import org.rcfaces.renderkit.html.internal.style.CssParserFactory.ICssParser;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.DescendantSelector;
import org.w3c.css.sac.ElementSelector;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;
import org.w3c.css.sac.SiblingSelector;
import org.w3c.css.sac.SimpleSelector;
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
import org.w3c.dom.stylesheets.MediaList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.selectors.SelectorsRule;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class CssSteadyStateParser implements ICssParser {
    private static final String REVISION = "$Revision$";

    public static final Log PARSING_LOG = LogFactory
            .getLog("org.rcfaces.css.Parsing");

    private static final Log LOG = LogFactory
            .getLog(CssSteadyStateParser.class);

    static {
        LOG.info("Enable 'CssSteadyState' css parser.");
    }

    private static final Set VALID_ELEMENTS = new HashSet(
            Arrays.asList(new String[] { "A", "ABBR", "ACRONYM", "ADDRESS",
                    "APPLET", "AREA", "B", "BASE", "BASEFONT", "BDO", "BIG",
                    "BLOCKQUOTE", "BODY", "BR", "BUTTON", "CAPTION", "CENTER",
                    "CITE", "CODE", "COL", "COLGROUP", "DD", "DEL", "DFN",
                    "DIR", "DIV", "DL", "DT", "EM", "FIELDSET", "FONT", "FORM",
                    "FRAME", "FRAMESET", "H1", "H2", "H3", "H4", "H5", "H6",
                    "HEAD", "HR", "HTML", "I", "IFRAME", "IMG", "INPUT", "INS",
                    "ISINDEX", "KBD", "LABEL", "LEGEND", "LI", "LINK", "MAP",
                    "MENU", "META", "NOFRAMES", "NOSCRIPT", "OBJECT", "OL",
                    "OPTGROUP", "OPTION", "P", "PARAM", "PRE", "Q", "S",
                    "SAMP", "SCRIPT", "SELECT", "SMALL", "SPAN", "STRIKE",
                    "STRONG", "STYLE", "SUB", "SUP", "TABLE", "TBODY", "TD",
                    "TEXTAREA", "TFOOT", "TH", "THEAD", "TITLE", "TR", "TT",
                    "U", "UL", "VAR" }));

    @Override
    public String getParserName() {
        return "Steady State Css parser";
    }

    @Override
    public String normalizeBuffer(Map applicationParameters,
            IResourceLoaderFactory resourceLoaderFactory, String baseURL,
            String styleSheetContent, IParserContext parserContext,
            IOperationContentLoader operationContentLoader, boolean mergeLinks)
            throws IOException {

        CSSOMParser parser = new CSSOMParser();

        InputSource inputSource = new InputSource(new StringReader(
                styleSheetContent));
        inputSource.setTitle(baseURL);
        CSSStyleSheet styleSheet = parser.parseStyleSheet(inputSource);

        IPath base = new Path(baseURL).makeRelative();
        IPath relative = new Path("..").append(base.removeLastSegments(1));

        if (LOG.isDebugEnabled()) {
            LOG.debug("Merge buffer of '" + base + "' relative='" + relative
                    + "'");
        }

        computeStyleSheet(styleSheet, base, relative, new CSSParserContext(
                resourceLoaderFactory, parserContext, mergeLinks),
                operationContentLoader);

        return styleSheet.toString();
    }

    private void computeStyleSheet(CSSStyleSheet styleSheet, IPath basePath,
            IPath relativePath, CSSParserContext cssParserContext,
            IOperationContentLoader operationContentLoader) {

        if (LOG.isDebugEnabled()) {
            LOG.debug("Refactor stylesheet: '" + basePath + "'.");
        }

        IParserContext parserContext = cssParserContext.parserContext;

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
                CSSStyleRule styleRule = (CSSStyleRule) rule;
                CSSStyleDeclaration declaration = styleRule.getStyle();

                if (styleRule instanceof SelectorsRule) {
                    verifySelectors(
                            ((SelectorsRule) styleRule).getSelectorList(),
                            basePath.toString());
                }

                for (int j = 0; j < declaration.getLength(); j++) {
                    String property = declaration.item(j);

                    CSSValue value = declaration.getPropertyCSSValue(property);

                    alterCssValue(cssParserContext, basePath, value,
                            relativePath);
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
                    LOG.debug("Ignore import rule: absolute or protocol, # or ? detected. ("
                            + href + ")");
                }
                continue;
            }

            // @TODO on doit verifier les medias ...

            IPath contextRelativePath = new Path(href);

            IPath newRelativePath = relativePath.append(contextRelativePath
                    .removeLastSegments(1));

            contextRelativePath = basePath.removeLastSegments(1).append(
                    contextRelativePath);

            String importedPath = contextRelativePath.toString();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Import rule detected: '" + href + "' realPath='"
                        + importedPath + "' relativePath='" + relativePath
                        + "'.");
            }

            if (cssParserContext.mergeLinks == false) {
                // Il faut obtenir une URL versionnée ! (avec le contenu
                // versionné !)

                if (parserContext.isVersioningEnabled()) { // Forcement
                    IPath newVersionedPath = parserContext
                            .processVersioning(basePath, contextRelativePath,
                                    IContentFamily.STYLE);

                    if (newVersionedPath.equals(contextRelativePath) == false) {
                        StringAppender sa = new StringAppender("@import url(",
                                256).append(newVersionedPath.toString())
                                .append(')');

                        MediaList mediaList = importRule.getMedia();
                        if (mediaList != null) {
                            sa.append(' ').append(mediaList.toString());
                        }
                        sa.append(';');

                        importRule.setCssText(sa.toString());
                    }
                }

                continue;
            }

            try {
                ContentInformation contentInformationRef[] = new ContentInformation[1];

                String childContent = operationContentLoader.loadContent(
                        parserContext.getFacesContext(),
                        cssParserContext.resourceLoaderFactory, importedPath,
                        parserContext.getCharset(), contentInformationRef);

                if (childContent == null) {
                    LOG.error("Can not load css resource '" + importedPath
                            + "'.");
                    continue;
                }

                if (contentInformationRef[0] != null) {
                    ContentInformation contentInformation = contentInformationRef[0];

                    long lastModified = contentInformation.getLastModified();
                    if (parserContext.getLastModifiedDate() < 0
                            || lastModified > parserContext
                                    .getLastModifiedDate()) {
                        parserContext.setLastModifiedDate(lastModified);
                    }
                }

                InputSource inputSource = new InputSource(new StringReader(
                        childContent));
                inputSource.setTitle(importedPath);

                CSSOMParser parser = new CSSOMParser();

                CSSStyleSheet importedStyleSheet = parser
                        .parseStyleSheet(inputSource);

                computeStyleSheet(importedStyleSheet, contextRelativePath,
                        newRelativePath, cssParserContext,
                        operationContentLoader);

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
            LOG.debug("Css content of '" + basePath + "': " + styleSheet);
        }
    }

    private void alterCssValue(CSSParserContext cssParserContext,
            IPath basePath, CSSValue value, IPath relativePath) {

        if (value instanceof CSSPrimitiveValue) {
            alterCssPrimitiveValue(cssParserContext, basePath,
                    (CSSPrimitiveValue) value, relativePath);
        }

        if (value instanceof CSSValueList) {
            CSSValueList list = (CSSValueList) value;

            for (int k = 0; k < list.getLength(); k++) {
                alterCssPrimitiveValue(cssParserContext, basePath,
                        (CSSPrimitiveValue) list.item(k), relativePath);
            }
        }
    }

    private void verifySelectors(SelectorList selectors, String source) {
        for (int i = 0; i < selectors.getLength(); i++) {
            Selector selector = selectors.item(i);

            verifySelector(selector, source);
        }
    }

    private void verifySelector(Selector selector, String source) {
        if (selector instanceof ConditionalSelector) {
            ConditionalSelector conditionalSelector = (ConditionalSelector) selector;

            SimpleSelector simpleSelector = conditionalSelector
                    .getSimpleSelector();
            if (simpleSelector instanceof ElementSelector) {
                if ("*".equals(((ElementSelector) simpleSelector)
                        .getLocalName())) {
                    PARSING_LOG.error("* simple selector is not necessery '"
                            + selector + "'. [source:" + source + "]");
                }
            }
        }

        if (selector instanceof DescendantSelector) {
            DescendantSelector descendantSelector = (DescendantSelector) selector;

            verifySelector(descendantSelector.getAncestorSelector(), source);
            verifySelector(descendantSelector.getSimpleSelector(), source);
            return;
        }

        if (selector instanceof SiblingSelector) {
            SiblingSelector descendantSelector = (SiblingSelector) selector;

            verifySelector(descendantSelector.getSiblingSelector(), source);
            verifySelector(descendantSelector.getSelector(), source);
            return;
        }

        if (selector instanceof ElementSelector) {
            ElementSelector elementSelector = (ElementSelector) selector;

            String name = elementSelector.getLocalName().toUpperCase();

            if (VALID_ELEMENTS.contains(name) == false) {
                PARSING_LOG.error("Invalid element name '" + name + "': rule="
                        + selector + " [source:" + source + "]");
            }
        }
    }

    private void alterCssPrimitiveValue(CSSParserContext cssParserContext,
            IPath basePath, CSSPrimitiveValue value, IPath relativePath) {
        if (value.getPrimitiveType() != CSSPrimitiveValue.CSS_URI) {
            return;
        }

        String href = value.getStringValue().trim();
        if (href.length() < 1) {
            return;
        }

        if (isValidURL(href) == false) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Ignore import rule: absolute or protocol, # or ? detected. ("
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

        IPath contextRelativePath = basePath.removeLastSegments(1).append(path);

        if (cssParserContext.parserContext.isVersioningEnabled()) {
            contextRelativePath = cssParserContext.parserContext
                    .processVersioning(basePath, contextRelativePath,
                            IContentFamily.IMAGE);
        } else if (cssParserContext.mergeLinks) {
            // La servlet de merge
            contextRelativePath = new Path("..").append(contextRelativePath);
        }

        value.setStringValue(CSSPrimitiveValue.CSS_URI,
                contextRelativePath.toString());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Path relocation: change path '" + href + "' to '"
                    + contextRelativePath + "'.");
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
    private static class CSSParserContext {
        private static final String REVISION = "$Revision$";

        IResourceLoaderFactory resourceLoaderFactory;

        IParserContext parserContext;

        boolean mergeLinks;

        public CSSParserContext(IResourceLoaderFactory resourceLoaderFactory,
                IParserContext parserContext, boolean mergeLinks) {
            this.resourceLoaderFactory = resourceLoaderFactory;
            this.parserContext = parserContext;
            this.mergeLinks = mergeLinks;
        }
    }
}