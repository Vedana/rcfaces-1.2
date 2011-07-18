package org.rcfaces.core.internal.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rcfaces.core.component.capability.ICriteriaContainer;
import org.rcfaces.core.component.capability.ICriteriaManagerCapability;
import org.rcfaces.core.internal.capability.IGridComponent;
import org.rcfaces.core.item.CriteriaItem;
import org.rcfaces.core.model.ICriteriaConfig;
import org.rcfaces.core.model.ICriteriaConfigResult;

/**
 * 
 * @author Oeuillot
 * 
 */
public class GridCriteriaConfigResult implements ICriteriaConfigResult {

	private final ICriteriaManagerCapability manager;
	private final ICriteriaConfig[] configs;

	private Map criteriaItemsByContainer;

	private List result;

	public GridCriteriaConfigResult(ICriteriaManagerCapability manager,
			ICriteriaConfig[] configs) {
		this.manager = manager;
		this.configs = configs;
	}

	public ICriteriaManagerCapability getCriteriaManager() {
		return manager;
	}

	public ICriteriaConfig[] getConfig() {
		return configs;
	}

	public CriteriaItem[] getAvailableCriteriaItems(ICriteriaContainer container) {
		computeDatas();

		return (CriteriaItem[]) criteriaItemsByContainer.get(container);
	}

	public int getResultCount() {
		computeDatas();

		return result.size();
	}

	public List getResult() {
		computeDatas();

		return result;
	}

	protected synchronized void computeDatas() {
		if (result != null) {
			return;
		}

		result = new ArrayList();
		criteriaItemsByContainer = new HashMap();

	}

}
