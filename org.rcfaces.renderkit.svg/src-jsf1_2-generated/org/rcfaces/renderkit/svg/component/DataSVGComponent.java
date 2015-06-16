package org.rcfaces.renderkit.svg.component;

import javax.faces.component.NamingContainer;
import org.rcfaces.renderkit.svg.component.Properties;
import org.rcfaces.core.internal.tools.ToolTipTools;
import org.apache.commons.logging.LogFactory;
import org.rcfaces.core.component.iterator.IColumnIterator;
import org.rcfaces.core.internal.capability.IColumnsContainer;
import org.rcfaces.core.internal.tools.GridTools;
import org.rcfaces.core.internal.capability.IToolTipComponent;
import org.rcfaces.core.component.iterator.IToolTipIterator;
import javax.el.ValueExpression;
import org.rcfaces.core.component.capability.IRowToolTipIdCapability;
import java.util.HashSet;
import org.apache.commons.logging.Log;
import java.util.Set;
import java.util.Arrays;
import org.rcfaces.renderkit.svg.component.SVGComponent;

public class DataSVGComponent extends SVGComponent implements 
	IRowToolTipIdCapability,
	IToolTipComponent,
	IColumnsContainer,
	NamingContainer {

	private static final Log LOG = LogFactory.getLog(DataSVGComponent.class);

	public static final String COMPONENT_TYPE="org.rcfaces.svg.dataSVG";

	protected static final Set CAMELIA_ATTRIBUTES=new HashSet(SVGComponent.CAMELIA_ATTRIBUTES);
	static {
		CAMELIA_ATTRIBUTES.addAll(Arrays.asList(new String[] {"dataModel","var","rowToolTipId"}));
	}

	public DataSVGComponent() {
		setRendererType(COMPONENT_TYPE);
	}

	public DataSVGComponent(String componentId) {
		this();
		setId(componentId);
	}

	public IToolTipIterator listToolTips() {


			return ToolTipTools.listToolTips(this);
		
	}

	public IColumnIterator listColumns() {


				return GridTools.listColumns(this,
					org.rcfaces.renderkit.svg.component.SVGDataColumnComponent.class);
			
	}

	public IColumnIterator listOrderedVisibledColumns() {


				return GridTools.listOrderedVisibledColumns(this,
				javax.faces.component.UIColumn.class);
			
	}

	public java.lang.String getRowToolTipId() {
		return getRowToolTipId(null);
	}

	/**
	 * See {@link #getRowToolTipId() getRowToolTipId()} for more details
	 */
	public java.lang.String getRowToolTipId(javax.faces.context.FacesContext facesContext) {
		return engine.getStringProperty(Properties.ROW_TOOL_TIP_ID, facesContext);
	}

	/**
	 * Returns <code>true</code> if the attribute "rowToolTipId" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public final boolean isRowToolTipIdSetted() {
		return engine.isPropertySetted(Properties.ROW_TOOL_TIP_ID);
	}

	public void setRowToolTipId(java.lang.String rowToolTipId) {
		engine.setProperty(Properties.ROW_TOOL_TIP_ID, rowToolTipId);
	}

	public String getVar() {
		return getVar(null);
	}

	public String getVar(javax.faces.context.FacesContext facesContext) {
		String s = engine.getStringProperty(Properties.VAR, facesContext);
		return s;
	}

	public void setVar(String var) {
		engine.setProperty(Properties.VAR, var);
	}

	/**
	 * Returns <code>true</code> if the attribute "var" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isVarSetted() {
		return engine.isPropertySetted(Properties.VAR);
	}

	public Object getDataModel() {
		return getDataModel(null);
	}

	public Object getDataModel(javax.faces.context.FacesContext facesContext) {
		return engine.getValue(Properties.DATA_MODEL, facesContext);
	}

	public void setDataModel(Object dataModel) {
		engine.setValue(Properties.DATA_MODEL, dataModel);
	}

	/**
	 * Returns <code>true</code> if the attribute "dataModel" is set.
	 * @return <code>true</code> if the attribute is set.
	 */
	public boolean isDataModelSetted() {
		return engine.isPropertySetted(Properties.DATA_MODEL);
	}

	protected Set getCameliaFields() {
		return CAMELIA_ATTRIBUTES;
	}
}
