/*
 * $Id$
 */

/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or legal/CDDLv1.0.txt. See the
 * License for the specific language governing permission and limitations under
 * the License.
 * 
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt. If applicable, add the
 * following below the CDDL Header, with the fields enclosed by brackets []
 * replaced by your own identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package org.rcfaces.core.internal.component;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.component.StateHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.DataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.capability.IAdditionalInformationContainer;
import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IRCFacesComponent;

/**
 * <p>
 * <strong>UIData</strong> is a {@link UIComponent} that supports data binding
 * to a collection of data objects represented by a {@link DataModel} instance,
 * which is the current value of this component itself (typically established
 * via a {@link ValueExpression}). During iterative processing over the rows of
 * data in the data model, the object for the current row is exposed as a
 * request attribute under the key specified by the <code>var</code> property.
 * </p>
 * 
 * <p>
 * Only children of type {@link UIColumn} should be processed by renderers
 * associated with this component.
 * </p>
 * 
 * <p>
 * By default, the <code>rendererType</code> property is set to
 * <code>javax.faces.Table</code>. This value can be changed by calling the
 * <code>setRendererType()</code> method.
 * </p>
 */

public class UIData2 extends UIData0 {

    private static final Log LOG = LogFactory.getLog(UIData2.class);

    private static boolean DEBUG_ENABLED = LOG.isDebugEnabled();

    private transient List<int[]> decodedIndexes;

    private Map saved = new HashMap();

    private boolean saveCompleteState = true;

    private boolean iterateMode;

    private int iterateModeFirst;

    private int iterateModeIndex;

    private int iterateModeRows;

    public UIData2() {
        // Certains logs sont positionnés APRES !
        DEBUG_ENABLED = LOG.isDebugEnabled();
    }

    public void encodeBegin(FacesContext context) throws IOException {

        decodedIndexes = null;

        super.encodeBegin(context);
    }

    protected void iterate(FacesContext context, PhaseId phaseId) {
        if (decodedIndexes == null || decodedIndexes.isEmpty()) {
            super.iterate(context, phaseId);
            return;
        }

        if (true) {
            super.iterate(context, phaseId);
            return;
        }

        iterateModeFirst = 0;
        iterateModeRows = 0;
        iterateModeIndex = 0;

        for (int[] is : decodedIndexes) {
            iterateModeRows += is[1];
        }

        iterateMode = true;
        try {

            super.iterate(context, phaseId);

        } finally {
            iterateMode = false;
        }
    }

    protected boolean renderColumn(UIColumn column, PhaseId phaseId) {
        if (column instanceof IAdditionalInformationContainer) {
            return decodeAdditionalInformation((IAdditionalInformationContainer) column);
        }

        return true;
    }

    public int getFirst() {
        if (iterateMode == false) {
            int first = super.getFirst();

            if (DEBUG_ENABLED) {
                LOG.debug("getFirst returns " + first);
            }

            return first;
        }

        if (DEBUG_ENABLED) {
            LOG.debug("Iterate translate mode: return first="
                    + iterateModeFirst);
        }

        return iterateModeFirst;
    }

    public int getRowCount() {
        int rowCount = super.getRowCount();

        if (DEBUG_ENABLED) {
            LOG.debug("getRowCount() returns " + rowCount);
        }

        return rowCount;
    }

    public int getRowIndex() {
        int rowIndex = super.getRowIndex();

        if (DEBUG_ENABLED) {
            LOG.debug("getRowIndex() returns " + rowIndex);
        }

        return rowIndex;
    }

    public boolean isRowAvailable() {
        boolean rowAvailable = super.isRowAvailable();

        if (DEBUG_ENABLED) {
            LOG.debug("isRowAvailable() returns " + rowAvailable);
        }

        return rowAvailable;
    }

    public void setFirst(int first) {

        if (DEBUG_ENABLED) {
            LOG.debug("setFirst(" + first + ")");
        }

        super.setFirst(first);
    }

    public void setRows(int rows) {

        if (DEBUG_ENABLED) {
            LOG.debug("setRows(" + rows + ")");
        }

        super.setRows(rows);
    }

    public int getRows() {
        if (iterateMode == false) {
            int rows = super.getRows();

            if (DEBUG_ENABLED) {
                LOG.debug("getRows() returns " + rows);
            }

            return rows;
        }

        if (DEBUG_ENABLED) {
            LOG.debug("getRows(): Iterate translate mode returns rows="
                    + iterateModeRows);
        }

        return iterateModeRows;
    }

    public void setRowIndex(int rowIndex) {
        if (iterateMode == false || rowIndex < 0) {

            if (DEBUG_ENABLED) {
                LOG.debug("setRowIndex(" + rowIndex + ")");
            }

            super.setRowIndex(rowIndex);
            return;
        }

        int translatedRowIndex = 0;
        for (int[] is : decodedIndexes) {
            if (rowIndex >= is[1]) {
                rowIndex -= is[1];
                continue;
            }

            translatedRowIndex = is[0] + rowIndex;
            break;
        }

        if (DEBUG_ENABLED) {
            LOG.debug("setRowIndex(" + rowIndex
                    + ") Iterate translate mode => rowIndex="
                    + translatedRowIndex);
        }

        super.setRowIndex(translatedRowIndex);
    }

    public void addDecodedIndexes(int first, int rows) {

        if (DEBUG_ENABLED) {
            LOG.debug("Add decoded indexes first=" + first + " rows=" + rows);
        }

        if (decodedIndexes == null) {
            decodedIndexes = new ArrayList<int[]>();
        }

        if (rows > 0) {
            decodedIndexes.add(new int[] { first, rows });
        }
    }

    public void restoreState(FacesContext context, Object state) {
        Object states[] = (Object[]) state;

        super.restoreState(context, states[0]);

        Object[] ss = (Object[]) states[1];

        saved = new HashMap(ss.length / 2);
        if (ss.length > 0) {
            for (int i = 0; i < ss.length;) {
                Object key = ss[i++];

                SavedState2 ss2 = new SavedState2();
                ss2.restoreState(context, ss[i++]);

                saved.put(key, ss2);
            }
        }

    }

    public Object saveState(FacesContext context) {
        Object ret[] = new Object[2];

        ret[0] = super.saveState(context);

        Object ss[] = new Object[saved.size() * 2];
        ret[1] = ss;

        if (ss.length > 0) {
            int index = 0;
            for (Iterator it = saved.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                ss[index++] = entry.getKey();
                ss[index++] = ((SavedState2) entry.getValue())
                        .saveState(context);
            }
        }

        return ret;
    }

    /**
     * <p>
     * Restore state information for the specified component and its
     * descendants.
     * </p>
     * 
     * @param component
     *            Component for which to restore state information
     * @param context
     *            {@link FacesContext} for the current request
     */

    protected void restoreDescendantState(UIComponent component,
            FacesContext context) {

        // Reset the client identifier for this component
        String id = component.getId();
        component.setId(id); // Forces client id to be reset

        if (component instanceof IRCFacesComponent) {
            String clientId = component.getClientId(context);
            SavedState2 state = (SavedState2) saved.get(clientId);

            if (state != null) {
                IComponentEngine componentEngine = state.getComponentEngine(
                        context, component);
                if (componentEngine != null) {
                    ComponentEngineManager.setComponentEngine(
                            (IRCFacesComponent) component, componentEngine);
                }
            } else {
                ComponentEngineManager
                        .cloneComponentEngine((IRCFacesComponent) component);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Restore state of '" + clientId + "' => " + state);
            }
        }

        super.restoreDescendantState(component, context);
    }

    /**
     * <p>
     * Save state information for the specified component and its descendants.
     * </p>
     * 
     * @param component
     *            Component for which to save state information
     * @param context
     *            {@link FacesContext} for the current request
     */

    protected void saveDescendantState(UIComponent component,
            FacesContext context) {

        super.saveDescendantState(component, context);

        if (isSaveCompleteState()) {
            if (component instanceof IRCFacesComponent) {
                IComponentEngine componentEngine = ComponentEngineManager
                        .getComponentEngine((IRCFacesComponent) component);

                String clientId = component.getClientId(context);

                SavedState2 state = (SavedState2) saved.get(clientId);
                if (state == null) {
                    state = new SavedState2();
                    saved.put(clientId, state);
                }

                state.setComponentEngine(componentEngine);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Save state of '" + clientId + "' => " + state);
                }
            }
        }
    }

    public final boolean isSaveCompleteState() {
        return saveCompleteState;
    }

    public final void setSaveCompleteState(boolean saveCompleteState) {
        this.saveCompleteState = saveCompleteState;
    }

    // Private class to represent saved state information
    public static class SavedState2 implements Externalizable, StateHolder {

        private IComponentEngine componentEngine;

        private Object serializedComponentEngine;

        public SavedState2() {

        }

        public final IComponentEngine getComponentEngine(
                FacesContext facesContext, UIComponent component) {
            if (serializedComponentEngine == null) {
                return componentEngine;
            }

            IFactory factory = Constants.getCameliaFactory();

            IComponentEngine componentEngine = factory.createComponentEngine();

            componentEngine.restoreState(facesContext,
                    serializedComponentEngine);

            serializedComponentEngine = null;

            return componentEngine;
        }

        public final void setComponentEngine(IComponentEngine componentEngine) {
            this.componentEngine = componentEngine;
            this.serializedComponentEngine = null;
        }

        public String toString() {
            return "componentEngine=" + componentEngine;
        }

        public void readExternal(ObjectInput in) throws IOException,
                ClassNotFoundException {

            serializedComponentEngine = in.readObject();
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            if (componentEngine != null) {
                serializedComponentEngine = componentEngine
                        .saveState(FacesContext.getCurrentInstance());
            }

            out.writeObject(serializedComponentEngine);
        }

        public Object saveState(FacesContext context) {
            if (componentEngine != null) {
                serializedComponentEngine = componentEngine
                        .saveState(FacesContext.getCurrentInstance());
            }

            return serializedComponentEngine;
        }

        public void restoreState(FacesContext context, Object state) {
            serializedComponentEngine = state;
        }

        public boolean isTransient() {
            return false;
        }

        public void setTransient(boolean newTransientValue) {
        }

    }

    public boolean decodeAdditionalInformation(
            IAdditionalInformationContainer additionalInformationComponent) {
        if (decodedIndexes == null) {
            if (DEBUG_ENABLED) {
                int rowIndex = getRowIndex();

                LOG.debug("Decode additional #" + rowIndex + " ("
                        + decodedIndexesToString() + ") => FALSE");
            }
            return false;
        }

        int rowIndex = getRowIndex();

        for (int[] is : decodedIndexes) {
            if (rowIndex >= is[0] && rowIndex < is[0] + is[1]) {
                if (DEBUG_ENABLED) {
                    LOG.debug("Decode additional #" + rowIndex + " ("
                            + decodedIndexesToString() + ") => TRUE");
                }
                return true;
            }
        }

        if (DEBUG_ENABLED) {
            LOG.debug("Decode additional #" + rowIndex + " ("
                    + decodedIndexesToString() + ") => FALSE");
        }

        return false;
    }

    private String decodedIndexesToString() {
        if (decodedIndexes == null) {
            return "null";
        }

        if (decodedIndexes.isEmpty()) {
            return "[]";
        }

        StringBuffer sb = new StringBuffer();

        for (int is[] : decodedIndexes) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append("[" + is[0] + "->" + (is[0] + is[1] - 1) + "]");
        }

        return sb.toString();
    }
}
