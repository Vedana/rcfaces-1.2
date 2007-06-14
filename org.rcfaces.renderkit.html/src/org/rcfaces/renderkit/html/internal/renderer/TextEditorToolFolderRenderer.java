/*
 * $Id$
 */
package org.rcfaces.renderkit.html.internal.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.FacesException;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.ComboComponent;
import org.rcfaces.core.component.TextEditorImageButtonComponent;
import org.rcfaces.core.component.TextEditorToolFolderComponent;
import org.rcfaces.core.component.ToolItemSeparatorComponent;
import org.rcfaces.renderkit.html.internal.IHtmlWriter;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class TextEditorToolFolderRenderer extends ToolFolderRenderer {

    private static final String REVISION = "$Revision$";

    private static final Log LOG = LogFactory
            .getLog(TextEditorToolFolderRenderer.class);

    private static final String DEFAULT_ITEM_TYPES = "undo, redo, separator, font, fontSize, bold, italic, underline, strike, separator, right, center, left, separator, orderedlist, unorderedlist, outdent, indent";

    private static final SelectItem[] FONT_SIZE_SELECT_ITEMS = new SelectItem[] {
            new SelectItem("8", "8"), new SelectItem("9", "9"),
            new SelectItem("10", "10"), new SelectItem("11", "11"),
            new SelectItem("12", "12"), new SelectItem("14", "14"),
            new SelectItem("16", "16"), new SelectItem("18", "18"),
            new SelectItem("20", "20"), new SelectItem("24", "24"),
            new SelectItem("36", "36"), new SelectItem("48", "48") };

    private static final SelectItem[] FONT_SELECT_ITEMS = new SelectItem[] {
            new SelectItem("Arial", "Arial"),
            new SelectItem("Tahoma", "Tahoma") };

    private static final Map TOOL_ITEMS_RENDERER = new HashMap(32);
    static {
        TOOL_ITEMS_RENDERER.put("bold",
                new TextEditorButtonItemRenderer("bold"));
        TOOL_ITEMS_RENDERER.put("italic", new TextEditorButtonItemRenderer(
                "italic"));
        TOOL_ITEMS_RENDERER.put("underline", new TextEditorButtonItemRenderer(
                "underline"));
        TOOL_ITEMS_RENDERER.put("strike", new TextEditorButtonItemRenderer(
                "strike"));

        TOOL_ITEMS_RENDERER.put("left", new TextEditorButtonItemRenderer(
                "left", "alignment"));
        TOOL_ITEMS_RENDERER.put("right", new TextEditorButtonItemRenderer(
                "right", "alignment"));
        TOOL_ITEMS_RENDERER.put("center", new TextEditorButtonItemRenderer(
                "center", "alignment"));

        TOOL_ITEMS_RENDERER.put("separator", new IToolFolderItemRenderer() {
            private static final String REVISION = "$Revision$";

            public void appendChildren(IHtmlWriter htmlWriter, List children) {
                children.add(new ToolItemSeparatorComponent());
            }
        });

        TOOL_ITEMS_RENDERER.put("outdent", new TextEditorButtonItemRenderer(
                "outdent"));
        TOOL_ITEMS_RENDERER.put("indent", new TextEditorButtonItemRenderer(
                "indent"));

        TOOL_ITEMS_RENDERER.put("undo",
                new TextEditorButtonItemRenderer("undo"));
        TOOL_ITEMS_RENDERER.put("redo",
                new TextEditorButtonItemRenderer("redo"));

        TOOL_ITEMS_RENDERER.put("unorderedlist",
                new TextEditorButtonItemRenderer("unorderedlist"));
        TOOL_ITEMS_RENDERER.put("orderedlist",
                new TextEditorButtonItemRenderer("orderedlist"));

        TOOL_ITEMS_RENDERER.put("font", new TextEditorComboItemRenderer("font",
                FONT_SELECT_ITEMS) {

            protected String listItems(IHtmlWriter htmlWriter) {
                TextEditorToolFolderComponent toolFolderComponent = (TextEditorToolFolderComponent) htmlWriter
                        .getComponentRenderContext().getComponent();

                return toolFolderComponent.getFontNames(htmlWriter
                        .getComponentRenderContext().getFacesContext());
            }

        });

        TOOL_ITEMS_RENDERER.put("fontsize", new TextEditorComboItemRenderer(
                "fontSize", FONT_SIZE_SELECT_ITEMS) {

            protected String listItems(IHtmlWriter htmlWriter) {
                TextEditorToolFolderComponent toolFolderComponent = (TextEditorToolFolderComponent) htmlWriter
                        .getComponentRenderContext().getComponent();

                return toolFolderComponent.getFontSizes(htmlWriter
                        .getComponentRenderContext().getFacesContext());
            }
        });
    }

    protected List getChildren(IHtmlWriter htmlWriter) {
        List children = new ArrayList();

        String itemTypes = ((TextEditorToolFolderComponent) htmlWriter
                .getComponentRenderContext().getComponent())
                .getItemTypes(htmlWriter.getComponentRenderContext()
                        .getFacesContext());

        if (itemTypes == null) {
            itemTypes = getDefaultItemTypes(htmlWriter);
        }

        if (itemTypes != null) {
            for (StringTokenizer st = new StringTokenizer(itemTypes, ",; "); st
                    .hasMoreTokens();) {

                String type = st.nextToken().toLowerCase();

                IToolFolderItemRenderer renderer = (IToolFolderItemRenderer) TOOL_ITEMS_RENDERER
                        .get(type);

                if (renderer == null) {
                    throw new FacesException("Unknown text editor item type '"
                            + type + "'");
                }

                renderer.appendChildren(htmlWriter, children);
            }
        }
        children.addAll(super.getChildren(htmlWriter));

        return children;
    }

    protected String getDefaultItemTypes(IHtmlWriter htmlWriter) {
        return DEFAULT_ITEM_TYPES;
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected interface IToolFolderItemRenderer {
        void appendChildren(IHtmlWriter htmlWriter, List children);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static class TextEditorButtonItemRenderer implements
            IToolFolderItemRenderer {

        private static final String REVISION = "$Revision$";

        private final String type;

        private final String groupName;

        public TextEditorButtonItemRenderer(String type) {
            this(type, null);
        }

        public TextEditorButtonItemRenderer(String type, String groupName) {
            this.type = type;
            this.groupName = groupName;
        }

        public void appendChildren(IHtmlWriter htmlWriter, List children) {

            TextEditorImageButtonComponent button = new TextEditorImageButtonComponent(
                    type);

            button.setType(type);

            TextEditorToolFolderComponent toolFolderComponent = (TextEditorToolFolderComponent) htmlWriter
                    .getComponentRenderContext().getComponent();

            String forId = toolFolderComponent.getId();

            String forClientId = htmlWriter.getHtmlComponentRenderContext()
                    .getHtmlRenderContext().computeBrotherComponentClientId(
                            toolFolderComponent, forId);

            button.setFor(forClientId);

            if (groupName != null) {
                button.setGroupName(groupName);
            }

            children.add(button);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    protected static abstract class TextEditorComboItemRenderer implements
            IToolFolderItemRenderer {

        private static final String REVISION = "$Revision$";

        private final String type;

        private final SelectItem selectItems[];

        public TextEditorComboItemRenderer(String type,
                SelectItem selectItems[]) {

            this.type = type;
            this.selectItems = selectItems;
        }

        protected abstract String listItems(IHtmlWriter htmlWriter);

        public void appendChildren(IHtmlWriter htmlWriter, List children) {

            ComboComponent combo = new ComboComponent(type);

            UISelectItems selectItemsComponent = new UISelectItems();

            SelectItem selectItems[] = null;

            String sItems = listItems(htmlWriter);
            if (sItems != null) {
                StringTokenizer st = new StringTokenizer(sItems, ", ");

                selectItems = new SelectItem[st.countTokens()];
                for (int i = 0; st.hasMoreTokens(); i++) {
                    String token = st.nextToken();

                    selectItems[i] = new SelectItem(token, token);
                }
            }

            if (selectItems == null) {
                selectItems = this.selectItems;
            }

            if (selectItems == null || selectItems.length < 1) {
                return;
            }

            selectItemsComponent.setValue(selectItems);

            TextEditorToolFolderComponent toolFolderComponent = (TextEditorToolFolderComponent) htmlWriter
                    .getComponentRenderContext().getComponent();

            String forId = toolFolderComponent.getId();

            String forClientId = htmlWriter.getHtmlComponentRenderContext()
                    .getHtmlRenderContext().computeBrotherComponentClientId(
                            toolFolderComponent, forId);

            // button.setFor(forClientId);

            combo.getChildren().add(selectItemsComponent);

            children.add(combo);
        }
    }
}
