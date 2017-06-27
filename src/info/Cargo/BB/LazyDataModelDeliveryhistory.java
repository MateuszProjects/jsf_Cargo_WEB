package info.Cargo.BB;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.DeliveryhistoryDAO;
import info.dao.PaginationInfo;
import info.entities.Deliveryhistory;

public class LazyDataModelDeliveryhistory extends LazyDataModel<Deliveryhistory> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DeliveryhistoryDAO deliveryhistoryDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setDeliveryhistoryDAO(DeliveryhistoryDAO deliveryhistoryDAO) {
		this.deliveryhistoryDAO = deliveryhistoryDAO;
	}

	@Override
	public Deliveryhistory getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Deliveryhistory deliveryhistory) {
		return null;
	}

	@Override
	public List<Deliveryhistory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Deliveryhistory> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);

		list = deliveryhistoryDAO.getSearchList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}

}
