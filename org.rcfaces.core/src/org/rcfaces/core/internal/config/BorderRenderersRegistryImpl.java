/*
 * $Id$
 * 
 * $Log$
 * Revision 1.3  2006/10/04 12:31:59  oeuillot
 * Stabilisation
 *
 * Revision 1.2  2006/09/14 14:34:51  oeuillot
 * Version avec ClientBundle et correction de findBugs
 *
 * Revision 1.1  2006/08/29 16:13:13  oeuillot
 * Renommage  en rcfaces
 *
 * Revision 1.2  2005/12/22 11:48:08  oeuillot
 * Ajout de :
 * - JS:  calendar, locale, dataList, log
 * - Evenement User
 * - ClientData  multi-directionnel (+TAG)
 *
 * Revision 1.1  2005/11/17 10:04:55  oeuillot
 * Support des BorderRenderers
 * Gestion de camelia-config
 * Ajout des stubs de Operation
 * Refactoring de ICssWriter
 *
 */
package org.rcfaces.core.internal.config;

import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderer;
import org.rcfaces.core.internal.renderkit.border.IBorderRenderersRegistry;
import org.rcfaces.core.internal.util.ClassLocator;
import org.xml.sax.Attributes;

/**
 * 
 * @author Olivier Oeuillot (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class BorderRenderersRegistryImpl extends AbstractRenderKitRegistryImpl
        implements IBorderRenderersRegistry {

    protected AbstractRenderKitRegistryImpl.RenderKit createRenderKit() {
        return new RenderKit();
    }

    public IBorderRenderer getBorderRenderer(FacesContext facesContext,
            String renderKitId, String family, String componentRenderType,
            String borderType) {

        RenderKit renderKit = (RenderKit) getRenderKit(facesContext,
                renderKitId);
        if (renderKit == null) {
            throw new FacesException("No renderKit '" + renderKitId
                    + "' defined !");

        }

        BorderRendererFacade borderRenderer = renderKit
                .getBorderRendererFacade(family, componentRenderType,
                        borderType);
        if (borderRenderer == null) {
            if (borderType == null) {
                return null;
            }

            throw new FacesException("Border '" + borderType
                    + "' is not defined ! (family=" + family
                    + ", rendererType=" + componentRenderType + ")");
        }

        return borderRenderer.getBorderRenderer(facesContext);
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class RenderKit extends
            AbstractRenderKitRegistryImpl.RenderKit {
        private static final String REVISION = "$Revision$";

        private final Map bordersById = new HashMap();

        private final Map families = new HashMap(4);

        private final Family defaultFamily = new Family();

        public BorderRendererFacade getBorderRendererFacade(String familyId,
                String componentType, String borderId) {

            if (borderId != null) {
                return (BorderRendererFacade) bordersById.get(borderId);
            }

            if (familyId != null) {
                Family family = (Family) families.get(familyId);
                if (family != null) {
                    BorderRendererFacade facade = family
                            .getBorderRendererFacade(this, componentType,
                                    borderId);
                    if (facade != null) {
                        return facade;
                    }
                }
            }

            return defaultFamily.getBorderRendererFacade(this, componentType,
                    borderId);
        }

        public void addBorder(BorderRendererFacade facade) {
            bordersById.put(facade.getId(), facade);
        }

        public BorderRendererFacade getBorderById(String borderId) {
            return (BorderRendererFacade) bordersById.get(borderId);
        }

        public void addDefaultBorder(DefaultBorder defaultBorder) {
            Family f = defaultFamily;

            String fm = defaultBorder.getFamily();
            if (fm != null) {
                f = (Family) families.get(fm);
                if (f == null) {
                    f = new Family();

                    families.put(fm, f);
                }
            }

            f.addDefaultBorder(defaultBorder);
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class Family {

        private static final String REVISION = "$Revision$";

        private final Map componentTypes = new HashMap(32);

        public synchronized BorderRendererFacade getBorderRendererFacade(
                RenderKit renderKit, String componentType, String borderId) {

            Object obj = componentTypes.get(componentType);
            if (obj == null) {
                return null;
            }

            if (obj instanceof BorderRendererFacade) {
                return (BorderRendererFacade) obj;
            }

            BorderRendererFacade brf = renderKit.getBorderById((String) obj);
            if (brf == null) {
                componentTypes.remove(componentType);
                return null;
            }

            componentTypes.put(componentType, brf);
            return brf;
        }

        public void addDefaultBorder(DefaultBorder defaultBorder) {
            String fm = defaultBorder.getRenderType();
            if (fm != null) {
                componentTypes.put(fm, defaultBorder.getBorderId());
            }
        }

    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static class BorderRendererFacade {
        private static final String REVISION = "$Revision$";

        private IBorderRenderer threadSafeObject;

        private boolean threadSafe;

        private String id;

        private String className;

        private Class borderClass;

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }

        public final String getClassName() {
            return className;
        }

        public final void setClassName(String className) {
            this.className = className;
        }

        public final boolean isThreadSafe() {
            return threadSafe;
        }

        public final void setThreadSafe(boolean threadSafe) {
            this.threadSafe = threadSafe;
        }

        public synchronized IBorderRenderer getBorderRenderer(
                FacesContext facesContext) {

            if (threadSafeObject != null) {
                return threadSafeObject;
            }

            if (className != null) {
                String cls = className;
                className = null;

                try {
                    borderClass = ClassLocator.load(cls, this, facesContext);

                } catch (Throwable th) {
                    throw new FacesException("Can not load border class '"
                            + cls + "'.", th);
                }

                if (threadSafe) {
                    threadSafeObject = getBorderRenderer(facesContext);

                    return threadSafeObject;
                }
            }

            IBorderRenderer borderRenderer;
            try {
                borderRenderer = (IBorderRenderer) borderClass.newInstance();

            } catch (FacesException ex) {
                throw ex;

            } catch (Throwable th) {
                throw new FacesException(th);
            }

            return borderRenderer;
        }
    }

    /**
     * 
     * @author Olivier Oeuillot (latest modification by $Author$)
     * @version $Revision$ $Date$
     */
    public static final class DefaultBorder {
        private static final String REVISION = "$Revision$";

        private String borderId;

        private String family;

        private String renderType;

        public final String getBorderId() {
            return borderId;
        }

        public final void setBorderId(String borderId) {
            this.borderId = borderId;
        }

        public final String getFamily() {
            return family;
        }

        public final void setFamily(String family) {
            this.family = family;
        }

        public final String getRenderType() {
            return renderType;
        }

        public final void setRenderType(String renderType) {
            this.renderType = renderType;
        }

    }

    public void configureRules(Digester digester) {

        digester.addRule("rcfaces-config/borders/render-kit", new Rule() {
            private static final String REVISION = "$Revision$";

            public void begin(String namespace, String name,
                    Attributes attributes) throws Exception {

                String renderKitId = attributes.getValue("render-kit-id");

                RenderKit renderKit = (RenderKit) allocate(renderKitId);

                super.digester.push(renderKit);
            }

            public void end(String namespace, String name) throws Exception {
                super.digester.pop();
            }
        });

        digester.addObjectCreate(
                "rcfaces-config/borders/render-kit/border-renderer",
                BorderRendererFacade.class);

        digester.addBeanPropertySetter(
                "rcfaces-config/borders/render-kit/border-renderer/border-id",
                "id");

        digester
                .addSetProperties(
                        "rcfaces-config/borders/render-kit/border-renderer/renderer-class",
                        "threadSafe", "threadSafe");

        digester
                .addBeanPropertySetter(
                        "rcfaces-config/borders/render-kit/border-renderer/renderer-class",
                        "className");

        digester.addSetNext(
                "rcfaces-config/borders/render-kit/border-renderer",
                "addBorder");

        digester.addObjectCreate(
                "rcfaces-config/borders/render-kit/default-border",
                DefaultBorder.class);

        digester.addBeanPropertySetter(
                "rcfaces-config/borders/render-kit/default-border/border-id",
                "borderId");

        digester
                .addBeanPropertySetter(
                        "rcfaces-config/borders/render-kit/default-border/component-family",
                        "family");

        digester
                .addBeanPropertySetter(
                        "rcfaces-config/borders/render-kit/default-border/renderer-type",
                        "renderType");

        digester.addSetNext("rcfaces-config/borders/render-kit/default-border",
                "addDefaultBorder");
    }

}
