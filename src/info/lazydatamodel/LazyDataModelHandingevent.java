package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.HandingeventDAO;
import info.dao.PaginationInfo;
import info.entities.Handlingevent;

public class LazyDataModelHandingevent extends LazyDataModel<Handlingevent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HandingeventDAO handingeventDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setHandingeventDAO(HandingeventDAO handingeventDAO) {
		this.handingeventDAO = handingeventDAO;
	}

	@Override
	public Handlingevent getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Handlingevent handlingevent) {
		return null;
	}

	@Override
	public List<Handlingevent> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Handlingevent> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		
	    list = handingeventDAO.getHandingEventList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}

}
