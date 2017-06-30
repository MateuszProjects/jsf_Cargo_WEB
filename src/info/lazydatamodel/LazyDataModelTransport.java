package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.PaginationInfo;
import info.dao.TransportDAO;
import info.entities.Transport;

public class LazyDataModelTransport extends LazyDataModel<Transport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TransportDAO transportDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setTransportDAO(TransportDAO transportDAO) {
		this.transportDAO = transportDAO;
	}

	@Override
	public Transport getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Transport transport) {
		return null;
	}

	@Override
	public List<Transport> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Transport> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);

		setRowCount(info.getCount());
		list = transportDAO.getSearchList(searchParams, info);
		return list;
	}

}
