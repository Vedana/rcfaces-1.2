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

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.StateHolder;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

import org.rcfaces.core.internal.Constants;
import org.rcfaces.core.internal.capability.IComponentEngine;
import org.rcfaces.core.internal.capability.IRCFacesComponent;

/**
 * <p>
 * <strong>UIData</strong> is a {@link UIComponent} that supports data binding
 * to a collection of data objects represented by a {@link DataModel} instance,
 * which is the current value of this component itself (typically established
 * via a {@link ValueBinding}). During iterative processing over the rows of
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

public class UIData2 extends UIComponentBase implements NamingContainer {

    // ------------------------------------------------------ Manifest Constants

    /**
     * <p>
     * The standard component type for this component.
     * </p>
     */
    public static final String COMPONENT_TYPE = "javax.faces.Data";

    /**
     * <p>
     * The standard component family for this component.
     * </p>
     */
    public static final String COMPONENT_FAMILY = "javax.faces.Data";

    // ------------------------------------------------------------ Constructors

    /**
     * <p>
     * Create a new {@link UIData} instance with default property values.
     * </p>
     */
    public UIData2() {

        super();
        // setRendererType("javax.faces.Table");

    }

    // ------------------------------------------------------ Instance Variables

    /**
     * <p>
     * The first row number (zero-relative) to be displayed.
     * </p>
     */
    private int first = 0;

    private boolean firstSet = false;

    /**
     * <p>
     * The {@link DataModel} associated with this component, lazily instantiated
     * if requested. This object is not part of the saved and restored state of
     * the component.
     * </p>
     */
    private transient DataModel model = null;

    /**
     * <p>
     * The zero-relative index of the current row number, or -1 for no current
     * row association.
     * </p>
     */
    private int rowIndex = -1;

    /**
     * <p>
     * The number of rows to display, or zero for all remaining rows in the
     * table.
     * </p>
     */
    private int rows = 0;

    private boolean rowsSet = false;

    /**
     * <p>
     * This map contains <code>SavedState</code> instances for each descendant
     * component, keyed by the client identifier of the descendant. Because
     * descendant client identifiers will contain the <code>rowIndex</code>
     * value of the parent, per-row state information is actually preserved.
     * </p>
     */
    private Map saved = new HashMap();

    /**
     * <p>
     * The local value of this {@link UIComponent}.
     * </p>
     */
    private Object value = null;

    /**
     * <p>
     * The request scope attribute under which the data object for the current
     * row will be exposed when iterating.
     * </p>
     */
    private String var = null;

    private transient List decodedIndexes;

    // Par défaut à TRUE
    private boolean saveCompleteState = true;

    // -------------------------------------------------------------- Properties

    public String getFamily() {

        return (COMPONENT_FAMILY);

    }

    /**
     * <p>
     * Return the zero-relative row number of the first row to be displayed.
     * </p>
     */
    public int getFirst() {

        if (this.firstSet) {
            return (this.first);
        }
        ValueBinding vb = getValueBinding("first");
        if (vb != null) {
            Integer value = (Integer) vb.getValue(getFacesContext());
            if (null == value) {
                return first;
            }
            return (value.intValue());
        } else {
            return (this.first);
        }

    }

    /**
     * <p>
     * Set the zero-relative row number of the first row to be displayed.
     * </p>
     * 
     * @param first
     *            New first row number
     * 
     * @exception IllegalArgumentException
     *                if <code>first</code> is negative
     */
    public void setFirst(int first) {

        if (first < 0) {
            throw new IllegalArgumentException("" + first);
        }
        this.first = first;
        this.firstSet = true;

    }

    /**
     * <p>
     * Return the footer facet of this component (if any). A convenience method
     * for <code>getFacet("footer")</code>.
     * </p>
     */
    public UIComponent getFooter() {

        return getFacet("footer");

    }

    /**
     * <p>
     * Set the footer facet of this component. A convenience method for
     * <code>getFacets().put("footer", footer)</code>.
     * </p>
     * 
     * @param footer
     *            the new footer facet
     * 
     * @exception NullPointerException
     *                if <code>footer</code> is <code>null</code>
     */
    public void setFooter(UIComponent footer) {

        getFacets().put("footer", footer);

    }

    /**
     * <p>
     * Return the header facet of this component (if any). A convenience method
     * for <code>getFacet("header")</code>.
     * </p>
     */
    public UIComponent getHeader() {

        return getFacet("header");

    }

    /**
     * <p>
     * Set the header facet of this component. A convenience method for
     * <code>getFacets().put("header", header)</code>.
     * </p>
     * 
     * @param header
     *            the new header facet
     * 
     * @exception NullPointerException
     *                if <code>header</code> is <code>null</code>
     */
    public void setHeader(UIComponent header) {

        getFacets().put("header", header);

    }

    /**
     * <p>
     * Return a flag indicating whether there is <code>rowData</code>
     * available at the current <code>rowIndex</code>. If no
     * <code>wrappedData</code> is available, return <code>false</code>.
     * </p>
     * 
     * @exception FacesException
     *                if an error occurs getting the row availability
     */
    public boolean isRowAvailable() {

        return (getDataModel().isRowAvailable());

    }

    /**
     * <p>
     * Return the number of rows in the underlying data model. If the number of
     * available rows is unknown, return -1.
     * </p>
     * 
     * @exception FacesException
     *                if an error occurs getting the row count
     */
    public int getRowCount() {

        return (getDataModel().getRowCount());

    }

    /**
     * <p>
     * Return the data object representing the data for the currently selected
     * row index, if any.
     * </p>
     * 
     * @exception FacesException
     *                if an error occurs getting the row data
     * @exception IllegalArgumentException
     *                if now row data is available at the currently specified
     *                row index
     */
    public Object getRowData() {

        return (getDataModel().getRowData());

    }

    /**
     * <p>
     * Return the zero-relative index of the currently selected row. If we are
     * not currently positioned on a row, return -1. This property is
     * <strong>not</strong> enabled for value binding expressions.
     * </p>
     * 
     * @exception FacesException
     *                if an error occurs getting the row index
     */
    public int getRowIndex() {

        return (this.rowIndex);

    }

    /**
     * <p>
     * Set the zero relative index of the current row, or -1 to indicate that no
     * row is currently selected, by implementing the following algorithm. It is
     * possible to set the row index at a value for which the underlying data
     * collection does not contain any row data. Therefore, callers may use the
     * <code>isRowAvailable()</code> method to detect whether row data will be
     * available for use by the <code>getRowData()</code> method.
     * </p>
     * </p>
     * <ul>
     * <li>Save current state information for all descendant components (as
     * described below).
     * <li>Store the new row index, and pass it on to the {@link DataModel}
     * associated with this {@link UIData} instance.</li>
     * <li>If the new <code>rowIndex</code> value is -1:
     * <ul>
     * <li>If the <code>var</code> property is not null, remove the
     * corresponding request scope attribute (if any).</li>
     * <li>Reset the state information for all descendant components (as
     * described below).</li>
     * </ul>
     * </li>
     * <li>If the new <code>rowIndex</code> value is not -1:
     * <ul>
     * <li>If the <code>var</code> property is not null, call
     * <code>getRowData()</code> and expose the resulting data object as a
     * request scope attribute whose key is the <code>var</code> property
     * value.</li>
     * <li>Reset the state information for all descendant components (as
     * described below).
     * </ul>
     * </li>
     * </ul>
     * 
     * <p>
     * To save current state information for all descendant components,
     * {@link UIData} must maintain per-row information for each descendant as
     * follows:
     * <p>
     * <ul>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * save the state of its <code>localValue</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * save the state of the <code>localValueSet</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * save the state of the <code>valid</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * save the state of the <code>submittedValue</code> property.</li>
     * </ul>
     * 
     * <p>
     * To restore current state information for all descendant components,
     * {@link UIData} must reference its previously stored information for the
     * current <code>rowIndex</code> and call setters for each descendant as
     * follows:
     * </p>
     * <ul>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * restore the <code>value</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * restore the state of the <code>localValueSet</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * restore the state of the <code>valid</code> property.</li>
     * <li>If the descendant is an instance of <code>EditableValueHolder</code>,
     * restore the state of the <code>submittedValue</code> property.</li>
     * </ul>
     * 
     * @param rowIndex
     *            The new row index value, or -1 for no associated row
     * 
     * @exception FacesException
     *                if an error occurs setting the row index
     * @exception IllegalArgumentException
     *                if <code>rowIndex</code> is less than -1
     */
    public void setRowIndex(int rowIndex) {

        // Save current state for the previous row index
        saveDescendantState();

        // Update to the new row index
        // int previous = this.rowIndex;
        this.rowIndex = rowIndex;
        DataModel model = getDataModel();
        model.setRowIndex(rowIndex);

        // Clear or expose the current row data as a request scope attribute
        if (var != null) {
            Map requestMap = getFacesContext().getExternalContext()
                    .getRequestMap();
            if (rowIndex == -1) {
                requestMap.remove(var);
            } else if (isRowAvailable()) {
                requestMap.put(var, getRowData());
            } else {
                requestMap.remove(var);
            }
        }

        // Reset current state information for the new row index
        restoreDescendantState();

    }

    /**
     * <p>
     * Return the number of rows to be displayed, or zero for all remaining rows
     * in the table. The default value of this property is zero.
     * </p>
     */
    public int getRows() {

        if (this.rowsSet) {
            return (this.rows);
        }
        ValueBinding vb = getValueBinding("rows");
        if (vb != null) {
            Integer value = (Integer) vb.getValue(getFacesContext());
            if (null == value) {
                return rows;
            }
            return (value.intValue());
        } else {
            return (this.rows);
        }

    }

    /**
     * <p>
     * Set the number of rows to be displayed, or zero for all remaining rows in
     * the table.
     * </p>
     * 
     * @param rows
     *            New number of rows
     * 
     * @exception IllegalArgumentException
     *                if <code>rows</code> is negative
     */
    public void setRows(int rows) {

        if (rows < 0) {
            throw new IllegalArgumentException("" + rows);
        }
        this.rows = rows;
        this.rowsSet = true;

    }

    /**
     * <p>
     * Return the request-scope attribute under which the data object for the
     * current row will be exposed when iterating. This property is <strong>not</strong>
     * enabled for value binding expressions.
     * </p>
     */
    public String getVar() {

        return (this.var);

    }

    /**
     * <p>
     * Set the request-scope attribute under which the data object for the
     * current row wil be exposed when iterating.
     * </p>
     * 
     * @param var
     *            The new request-scope attribute name
     */
    public void setVar(String var) {

        this.var = var;

    }

    // ----------------------------------------------------- StateHolder Methods

    public Object saveState(FacesContext context) {

        Object values[] = new Object[10];
        values[0] = super.saveState(context);
        values[1] = new Integer(first);
        values[2] = firstSet ? Boolean.TRUE : Boolean.FALSE;
        values[3] = new Integer(rowIndex);
        values[4] = new Integer(rows);
        values[5] = rowsSet ? Boolean.TRUE : Boolean.FALSE;
        values[6] = value;
        values[7] = var;

        values[8] = saveCompleteState ? Boolean.TRUE : Boolean.FALSE;

        if (saved != null && saved.isEmpty() == false) {
            Object ser[] = new Object[saved.size() * 2];
            values[9] = ser;

            int i = 0;
            for (Iterator it = saved.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();

                SavedState savedState = (SavedState) entry.getValue();

                ser[i++] = entry.getKey();
                ser[i++] = savedState.saveState(context);
            }
        }
        return (values);

    }

    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        first = ((Integer) values[1]).intValue();
        firstSet = ((Boolean) values[2]).booleanValue();
        rowIndex = ((Integer) values[3]).intValue();
        rows = ((Integer) values[4]).intValue();
        rowsSet = ((Boolean) values[5]).booleanValue();

        value = values[6];
        var = (String) values[7];

        saveCompleteState = ((Boolean) values[8]).booleanValue();

        Object v = values[9];
        if (v != null) {
            Object ss[] = (Object[]) v;

            saved = new HashMap(ss.length);

            for (int i = 0; i < ss.length;) {
                String sKey = (String) ss[i++];

                SavedState sState = new SavedState();
                sState.restoreState(context, ss[i++]);

                saved.put(sKey, sState);
            }

        } else {
            saved = new HashMap();
        }
    }

    /**
     * <p>
     * Return the value of the UIData. This value must either be be of type
     * {@link DataModel}, or a type that can be adapted into a
     * {@link DataModel}. <code>UIData</code> will automatically adapt the
     * following types:
     * </p>
     * <ul>
     * <li>Arrays</li>
     * <li><code>java.util.List</code></li>
     * <li><code>java.sql.ResultSet</code></li>
     * <li><code>javax.servlet.jsp.jstl.sql.Result</code></li>
     * </ul>
     * <p>
     * All other types will be adapted using the {@link ScalarDataModel} class,
     * which will treat the object as a single row of data.
     * </p>
     */
    public Object getValue() {

        if (this.value != null) {
            return (this.value);
        }
        ValueBinding vb = getValueBinding("value");
        if (vb != null) {
            return (vb.getValue(getFacesContext()));
        } else {
            return (null);
        }

    }

    /**
     * <p>
     * Set the value of the <code>UIData</code>. This value must either be be
     * of type {@link DataModel}, or a type that can be adapted into a
     * {@link DataModel}.
     * </p>
     * 
     * @param value
     *            the new value
     */
    public void setValue(Object value) {
        this.model = null;
        this.value = value;

    }

    // ----------------------------------------------------- UIComponent Methods

    /**
     * <p>
     * Set the {@link ValueBinding} used to calculate the value for the
     * specified attribute or property name, if any. In addition, if a
     * {@link ValueBinding} is set for the <code>value</code> property, remove
     * any synthesized {@link DataModel} for the data previously bound to this
     * component.
     * </p>
     * 
     * @param name
     *            Name of the attribute or property for which to set a
     *            {@link ValueBinding}
     * @param binding
     *            The {@link ValueBinding} to set, or <code>null</code> to
     *            remove any currently set {@link ValueBinding}
     * 
     * @exception IllegalArgumentException
     *                if <code>name</code> is one of <code>id</code>,
     *                <code>parent</code>, <code>var</code>, or
     *                <code>rowIndex</code>
     * @exception NullPointerException
     *                if <code>name</code> is <code>null</code>
     */
    public void setValueBinding(String name, ValueBinding binding) {

        if ("value".equals(name)) {
            this.model = null;
        } else if ("var".equals(name) || "rowIndex".equals(name)) {
            throw new IllegalArgumentException();
        }
        super.setValueBinding(name, binding);

    }

    /**
     * <p>
     * Return a client identifier for this component that includes the current
     * value of the <code>rowIndex</code> property, if it is not set to -1.
     * This implies that multiple calls to <code>getClientId()</code> may
     * return different results, but ensures that child components can
     * themselves generate row-specific client identifiers (since {@link UIData}
     * is a {@link NamingContainer}).
     * </p>
     * 
     * @exception NullPointerException
     *                if <code>context</code> is <code>null</code>
     */
    public String getClientId(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
        String baseClientId = super.getClientId(context);
        if (rowIndex >= 0) {
            return (baseClientId + NamingContainer.SEPARATOR_CHAR + rowIndex);
        } else {
            return (baseClientId);
        }

    }

    /**
     * <p>
     * Override the default {@link UIComponentBase#queueEvent} processing to
     * wrap any queued events in a wrapper so that we can reset the current row
     * index in <code>broadcast()</code>.
     * </p>
     * 
     * @param event
     *            {@link FacesEvent} to be queued
     * 
     * @exception IllegalStateException
     *                if this component is not a descendant of a
     *                {@link UIViewRoot}
     * @exception NullPointerException
     *                if <code>event</code> is <code>null</code>
     */
    public void queueEvent(FacesEvent event) {

        super.queueEvent(new WrapperEvent(this, event, getRowIndex()));

    }

    /**
     * <p>
     * Override the default {@link UIComponentBase#broadcast} processing to
     * unwrap any wrapped {@link FacesEvent} and reset the current row index,
     * before the event is actually broadcast. For events that we did not wrap
     * (in <code>queueEvent()</code>), default processing will occur.
     * </p>
     * 
     * @param event
     *            The {@link FacesEvent} to be broadcast
     * 
     * @exception AbortProcessingException
     *                Signal the JavaServer Faces implementation that no further
     *                processing on the current event should be performed
     * @exception IllegalArgumentException
     *                if the implementation class of this {@link FacesEvent} is
     *                not supported by this component
     * @exception NullPointerException
     *                if <code>event</code> is <code>null</code>
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException {

        if (!(event instanceof WrapperEvent)) {
            super.broadcast(event);
            return;
        }

        // Set up the correct context and fire our wrapped event
        WrapperEvent revent = (WrapperEvent) event;
        int oldRowIndex = getRowIndex();
        setRowIndex(revent.getRowIndex());
        FacesEvent rowEvent = revent.getFacesEvent();
        rowEvent.getComponent().broadcast(rowEvent);
        setRowIndex(oldRowIndex);
        return;

    }

    /**
     * <p>
     * In addition to the default behavior, ensure that any saved per-row state
     * for our child input components is discarded unless it is needed to
     * rerender the current page with errors.
     * 
     * @param context
     *            FacesContext for the current request
     * 
     * @exception IOException
     *                if an input/output error occurs while rendering
     * @exception NullPointerException
     *                if <code>context</code> is <code>null</code>
     */
    public void encodeBegin(FacesContext context) throws IOException {

        model = null; // Rre-evaluate even with server-side state saving
        if (!keepSaved(context)) {
            saved = new HashMap();
        }

        decodedIndexes = null;

        super.encodeBegin(context);

    }

    /**
     * <p>
     * Override the default {@link UIComponentBase#processDecodes} processing to
     * perform the following steps.
     * </p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * <li>Call the <code>processDecodes()</code> method of all facets of
     * this {@link UIData}, in the order determined by a call to
     * <code>getFacets().keySet().iterator()</code>.</li>
     * <li>Call the <code>processDecodes()</code> method of all facets of the
     * {@link UIColumn} children of this {@link UIData}.</li>
     * <li>Iterate over the set of rows that were included when this component
     * was rendered (i.e. those defined by the <code>first</code> and
     * <code>rows</code> properties), performing the following processing for
     * each row:
     * <ul>
     * <li>Set the current <code>rowIndex</code> to the appropriate value for
     * this row.</li>
     * <li>If <code>isRowAvailable()</code> returns <code>true</code>,
     * iterate over the children components of each {@link UIColumn} child of
     * this {@link UIData} component, calling the <code>processDecodes()</code>
     * method for each such child.</li>
     * </ul>
     * </li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * <li>Call the <code>decode()</code> method of this component.</li>
     * <li>If a <code>RuntimeException</code> is thrown during decode
     * processing, call {@link FacesContext#renderResponse} and re-throw the
     * exception.</li>
     * </ul>
     * 
     * @param context
     *            {@link FacesContext} for the current request
     * 
     * @exception NullPointerException
     *                if <code>context</code> is <code>null</code>
     */
    public void processDecodes(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }

        model = null; // Re-evaluate even with server-side state saving
        if (null == saved) { // !keepSaved(context)
            saved = new HashMap(); // We don't need saved state here
        }

        decode(context);
        // On itére apres, car le décodage peut ajouter des decodedIndexes
        iterate(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    /**
     * <p>
     * Override the default {@link UIComponentBase#processValidators} processing
     * to perform the following steps.
     * </p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * <li>Call the <code>processValidators()</code> method of all facets of
     * this {@link UIData}, in the order determined by a call to
     * <code>getFacets().keySet().iterator()</code>.</li>
     * <li>Call the <code>processValidators()</code> method of all facets of
     * the {@link UIColumn} children of this {@link UIData}.</li>
     * <li>Iterate over the set of rows that were included when this component
     * was rendered (i.e. those defined by the <code>first</code> and
     * <code>rows</code> properties), performing the following processing for
     * each row:
     * <ul>
     * <li>Set the current <code>rowIndex</code> to the appropriate value for
     * this row.</li>
     * <li>If <code>isRowAvailable()</code> returns <code>true</code>,
     * iterate over the children components of each {@link UIColumn} child of
     * this {@link UIData} component, calling the
     * <code>processValidators()</code> method for each such child.</li>
     * </ul>
     * </li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * </ul>
     * 
     * @param context
     *            {@link FacesContext} for the current request
     * 
     * @exception NullPointerException
     *                if <code>context</code> is <code>null</code>
     */
    public void processValidators(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }
        if (isNestedWithinUIData()) {
            model = null;
        }
        iterate(context, PhaseId.PROCESS_VALIDATIONS);
        // This is not a EditableValueHolder, so no further processing is
        // required

    }

    /**
     * <p>
     * Override the default {@link UIComponentBase#processUpdates} processing to
     * perform the following steps.
     * </p>
     * <ul>
     * <li>If the <code>rendered</code> property of this {@link UIComponent}
     * is <code>false</code>, skip further processing.</li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * <li>Call the <code>processUpdates()</code> method of all facets of
     * this {@link UIData}, in the order determined by a call to
     * <code>getFacets().keySet().iterator()</code>.</li>
     * <li>Call the <code>processUpdates()</code> method of all facets of the
     * {@link UIColumn} children of this {@link UIData}.</li>
     * <li>Iterate over the set of rows that were included when this component
     * was rendered (i.e. those defined by the <code>first</code> and
     * <code>rows</code> properties), performing the following processing for
     * each row:
     * <ul>
     * <li>Set the current <code>rowIndex</code> to the appropriate value for
     * this row.</li>
     * <li>If <code>isRowAvailable()</code> returns <code>true</code>,
     * iterate over the children components of each {@link UIColumn} child of
     * this {@link UIData} component, calling the <code>processUpdates()</code>
     * method for each such child.</li>
     * </ul>
     * </li>
     * <li>Set the current <code>rowIndex</code> to -1.</li>
     * </ul>
     * 
     * @param context
     *            {@link FacesContext} for the current request
     * 
     * @exception NullPointerException
     *                if <code>context</code> is <code>null</code>
     */
    public void processUpdates(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
        if (!isRendered()) {
            return;
        }
        if (isNestedWithinUIData()) {
            model = null;
        }
        iterate(context, PhaseId.UPDATE_MODEL_VALUES);
        // This is not a EditableValueHolder, so no further processing is
        // required

    }

    // --------------------------------------------------------- Private Methods

    /**
     * <p>
     * Return the {@link DataModel} object representing the data objects that we
     * will iterate over in this component's rendering.
     * </p>
     */
    protected DataModel getDataModel() {

        // Return any previously cached DataModel instance
        if (this.model != null) {
            return (model);
        }

        // Synthesize a DataModel around our current value if possible
        Object current = getValue();
        if (current == null) {
            this.model = new ListDataModel(Collections.EMPTY_LIST);
        } else if (current instanceof DataModel) {
            this.model = (DataModel) current;
        } else if (current instanceof List) {
            this.model = new ListDataModel((List) current);
        } else if (Object[].class.isAssignableFrom(current.getClass())) {
            this.model = new ArrayDataModel((Object[]) current);
        } else if (current instanceof ResultSet) {
            this.model = new ResultSetDataModel((ResultSet) current);
        } else if (current instanceof Result) {
            this.model = new ResultDataModel((Result) current);
        } else {
            this.model = new ScalarDataModel(current);
        }
        return (model);

    }

    /**
     * <p>
     * Perform the appropriate phase-specific processing and per-row iteration
     * for the specified phase, as follows:
     * <ul>
     * <li>Set the <code>rowIndex</code> property to -1, and process the
     * facets of this {@link UIData} component exactly once.</li>
     * <li>Set the <code>rowIndex</code> property to -1, and process the
     * facets of the {@link UIColumn} children of this {@link UIData} component
     * exactly once.</li>
     * <li>Iterate over the relevant rows, based on the <code>first</code>
     * and <code>row</code> properties, and process the children of the
     * {@link UIColumn} children of this {@link UIData} component once per row.</li>
     * </ul>
     * 
     * @param context
     *            {@link FacesContext} for the current request
     * @param phaseId
     *            {@link PhaseId} of the phase we are currently running
     */
    private void iterate(FacesContext context, PhaseId phaseId) {

        // Process each facet of this component exactly once
        setRowIndex(-1);
        Iterator facets = getFacets().keySet().iterator();
        while (facets.hasNext()) {
            UIComponent facet = (UIComponent) getFacets().get(facets.next());
            if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
                facet.processDecodes(context);
            } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
                facet.processValidators(context);
            } else if (phaseId == PhaseId.UPDATE_MODEL_VALUES) {
                facet.processUpdates(context);
            } else {
                throw new IllegalArgumentException();
            }
        }

        // Process each facet of our child UIColumn components exactly once
        setRowIndex(-1);
        Iterator columns = getChildren().iterator();
        while (columns.hasNext()) {
            UIComponent column = (UIComponent) columns.next();
            if (!(column instanceof UIColumn)) {
                continue;
            }
            if (!column.isRendered()) {
                continue;
            }
            Iterator columnFacets = column.getFacets().keySet().iterator();
            while (columnFacets.hasNext()) {
                UIComponent columnFacet = (UIComponent) column.getFacets().get(
                        columnFacets.next());
                if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
                    columnFacet.processDecodes(context);
                } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
                    columnFacet.processValidators(context);
                } else if (phaseId == PhaseId.UPDATE_MODEL_VALUES) {
                    columnFacet.processUpdates(context);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }

        if (decodedIndexes != null && decodedIndexes.size() > 0) {

            decodedIndexes.add(new int[] { getFirst(), getRows() });

            Collections.sort(decodedIndexes, new Comparator() {

                public int compare(Object arg0, Object arg1) {
                    int i0[] = (int[]) arg0;
                    int i1[] = (int[]) arg1;

                    return i0[0] - i1[0];
                }

            });

            int last[] = null;
            for (Iterator it = decodedIndexes.iterator(); it.hasNext();) {
                int is[] = (int[]) it.next();

                if (last == null) {
                    last = is;
                    continue;
                }

                if (is[0] > last[0] + last[1]) {
                    // Segment séparé !
                    iterateRange(context, phaseId, last[0], last[1]);
                    last = is;
                    continue;
                }

                it.remove();
                // Meme position ou inférieur !
                last[1] = Math.max(last[1], is[0] + is[1] - last[0]);
            }

            // decodedIndexes = null; // Il faut le garder pour les autres
            // phases !
            iterateRange(context, phaseId, last[0], last[1]);

        } else {
            iterateRange(context, phaseId, getFirst(), getRows());
        }

    }

    protected void iterateRange(FacesContext context, PhaseId phaseId,
            int rowIndex, int rows) {
        // Iterate over our UIColumn children, once per row
        int processed = 0;
        rowIndex--;

        while (true) {

            // Have we processed the requested number of rows?
            if ((rows > 0) && (++processed > rows)) {
                break;
            }

            // Expose the current row in the specified request attribute
            setRowIndex(++rowIndex);
            if (!isRowAvailable()) {
                break; // Scrolled past the last row
            }

            // Perform phase-specific processing as required
            // on the *children* of the UIColumn (facets have
            // been done a single time with rowIndex=-1 already)
            Iterator kids = getChildren().iterator();
            while (kids.hasNext()) {
                UIComponent kid = (UIComponent) kids.next();
                if (!(kid instanceof UIColumn)) {
                    continue;
                }
                Iterator grandkids = kid.getChildren().iterator();
                while (grandkids.hasNext()) {
                    UIComponent grandkid = (UIComponent) grandkids.next();
                    if (!grandkid.isRendered()) {
                        continue;
                    }
                    if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
                        grandkid.processDecodes(context);
                    } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
                        grandkid.processValidators(context);
                    } else if (phaseId == PhaseId.UPDATE_MODEL_VALUES) {
                        grandkid.processUpdates(context);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }

        }

        // Clean up after ourselves
        setRowIndex(-1);

    }

    public void addDecodedIndexes(int first, int rows) {
        if (this.decodedIndexes == null) {
            this.decodedIndexes = new ArrayList();
        }

        this.decodedIndexes.add(new int[] { first, rows });
    }

    /**
     * <p>
     * Return <code>true</code> if we need to keep the saved per-child state
     * information. This will be the case if any of the following are true:
     * </p>
     * 
     * <ul>
     * 
     * <li>any of the saved state corresponds to components that have messages
     * that must be displayed</li>
     * 
     * <li>this <code>UIData</code> instance is nested inside of another
     * <code>UIData</code> instance</li>
     * 
     * </ul>
     * 
     * @param context
     *            {@link FacesContext} for the current request
     */
    private boolean keepSaved(FacesContext context) {

        Iterator clientIds = saved.keySet().iterator();
        while (clientIds.hasNext()) {
            String clientId = (String) clientIds.next();
            Iterator messages = context.getMessages(clientId);
            while (messages.hasNext()) {
                FacesMessage message = (FacesMessage) messages.next();
                if (message.getSeverity()
                        .compareTo(FacesMessage.SEVERITY_ERROR) >= 0) {
                    return (true);
                }
            }
        }
        return (isNestedWithinUIData());

    }

    private boolean isNestedWithinUIData() {
        UIComponent parent = this;
        while (null != (parent = parent.getParent())) {
            if (parent instanceof UIData) {
                return true;
            }
            if (parent instanceof UIData2) {
                return true;
            }
        }
        return (false);
    }

    /**
     * <p>
     * Restore state information for all descendant components, as described for
     * <code>setRowIndex()</code>.
     * </p>
     */
    private void restoreDescendantState() {

        FacesContext context = getFacesContext();
        Iterator kids = getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if (kid instanceof UIColumn) {
                restoreDescendantState(kid, context);
            }
        }

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
    private void restoreDescendantState(UIComponent component,
            FacesContext context) {

        // Reset the client identifier for this component
        String id = component.getId();
        component.setId(id); // Forces client id to be reset

        SavedState state = null;

        // Restore state for this component (if it is a EditableValueHolder)
        if (component instanceof EditableValueHolder) {
            EditableValueHolder input = (EditableValueHolder) component;

            if (state == null) {
                String clientId = component.getClientId(context);
                state = (SavedState) saved.get(clientId);
                if (state == null) {
                    state = new SavedState();
                }
            }
            input.setValue(state.getValue());
            input.setValid(state.isValid());
            input.setSubmittedValue(state.getSubmittedValue());
            // This *must* be set after the call to setValue(), since
            // calling setValue() always resets "localValueSet" to true.
            input.setLocalValueSet(state.isLocalValueSet());
        }

        if (component instanceof IRCFacesComponent) {
            if (state == null) {
                String clientId = component.getClientId(context);
                state = (SavedState) saved.get(clientId);
                if (state == null) {
                    state = new SavedState();
                }
            }

            IComponentEngine componentEngine = state.getComponentEngine(
                    context, component);
            if (componentEngine != null) {
                ComponentEngineManager.setComponentEngine(
                        (IRCFacesComponent) component, componentEngine);
            }
        }

        // Restore state for children of this component
        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            restoreDescendantState((UIComponent) kids.next(), context);
        }

    }

    /**
     * <p>
     * Save state information for all descendant components, as described for
     * <code>setRowIndex()</code>.
     * </p>
     */
    private void saveDescendantState() {

        FacesContext context = getFacesContext();
        Iterator kids = getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            if (kid instanceof UIColumn) {
                saveDescendantState(kid, context);
            }
        }

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
    private void saveDescendantState(UIComponent component, FacesContext context) {

        SavedState state = null;

        // Save state for this component (if it is a EditableValueHolder)
        if (component instanceof EditableValueHolder) {
            EditableValueHolder input = (EditableValueHolder) component;
            if (state == null) {
                String clientId = component.getClientId(context);

                state = (SavedState) saved.get(clientId);
                if (state == null) {
                    state = new SavedState();
                    saved.put(clientId, state);
                }
            }
            state.setValue(input.getLocalValue());
            state.setValid(input.isValid());
            state.setSubmittedValue(input.getSubmittedValue());
            state.setLocalValueSet(input.isLocalValueSet());
        }

        if (isSaveCompleteState()) {
            if (component instanceof IRCFacesComponent) {
                IComponentEngine componentEngine = ComponentEngineManager
                        .getComponentEngine((IRCFacesComponent) component);

                if (state == null) {
                    String clientId = component.getClientId(context);

                    state = (SavedState) saved.get(clientId);
                    if (state == null) {
                        state = new SavedState();
                        saved.put(clientId, state);
                    }
                }

                state.setComponentEngine(componentEngine);
            }
        }

        // Save state for children of this component
        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            saveDescendantState((UIComponent) kids.next(), context);
        }

    }

    public final boolean isSaveCompleteState() {
        return saveCompleteState;
    }

    public final void setSaveCompleteState(boolean saveCompleteState) {
        this.saveCompleteState = saveCompleteState;
    }

}

// ------------------------------------------------------------- Private Classes

// Private class to represent saved state information
class SavedState implements StateHolder {

    private Object submittedValue;

    private boolean localValueSet;

    private Object value;

    private boolean valid = true;

    private IComponentEngine componentEngine;

    private Object serializedComponentEngine;

    Object getSubmittedValue() {
        return (this.submittedValue);
    }

    void setSubmittedValue(Object submittedValue) {
        this.submittedValue = submittedValue;
    }

    boolean isValid() {
        return (this.valid);
    }

    void setValid(boolean valid) {
        this.valid = valid;
    }

    Object getValue() {
        return (this.value);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    boolean isLocalValueSet() {
        return (this.localValueSet);
    }

    public void setLocalValueSet(boolean localValueSet) {
        this.localValueSet = localValueSet;
    }

    public final IComponentEngine getComponentEngine(FacesContext facesContext,
            UIComponent component) {
        if (serializedComponentEngine == null) {
            return componentEngine;
        }

        IFactory factory = Constants.getCameliaFactory();

        IComponentEngine componentEngine = factory.createComponentEngine();

        componentEngine.restoreState(facesContext, serializedComponentEngine);

        serializedComponentEngine = null;

        return componentEngine;
    }

    public final void setComponentEngine(IComponentEngine componentEngine) {
        this.componentEngine = componentEngine;
        this.serializedComponentEngine = null;
    }

    public String toString() {
        return ("submittedValue: " + submittedValue + " value: " + value
                + " localValueSet: " + localValueSet + " componentEngine=" + componentEngine);
    }

    public boolean isTransient() {
        return false;
    }

    public void setTransient(boolean newTransientValue) {
    }

    public void restoreState(FacesContext context, Object state) {
        Object params[] = (Object[]) state;

        submittedValue = params[0];

        localValueSet = (params[1] != null);

        value = params[2];

        valid = (params[3] != null);

        serializedComponentEngine = params[4];

    }

    public Object saveState(FacesContext context) {
        Object params[] = new Object[5];

        params[0] = submittedValue;
        params[1] = localValueSet ? Boolean.TRUE : null;
        params[2] = value;
        params[3] = valid ? Boolean.TRUE : null;

        if (componentEngine != null) {
            params[4] = componentEngine.saveState(context);
        }

        return params;
    }
}

// Private class to wrap an event with a row index
class WrapperEvent extends FacesEvent {

    public WrapperEvent(UIComponent component, FacesEvent event, int rowIndex) {
        super(component);
        this.event = event;
        this.rowIndex = rowIndex;
    }

    private FacesEvent event = null;

    private int rowIndex = -1;

    public FacesEvent getFacesEvent() {
        return (this.event);
    }

    public int getRowIndex() {
        return (this.rowIndex);
    }

    public PhaseId getPhaseId() {
        return (this.event.getPhaseId());
    }

    public void setPhaseId(PhaseId phaseId) {
        this.event.setPhaseId(phaseId);
    }

    public boolean isAppropriateListener(FacesListener listener) {
        return (false);
    }

    public void processListener(FacesListener listener) {
        throw new IllegalStateException();
    }

}
