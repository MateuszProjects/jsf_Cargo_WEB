package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.DeliveryspecificationDAO;
import info.dao.PaginationInfo;
import info.entities.Deliveryspecification;

public class LazyDataModelDeliveryspecification extends LazyDataModel<Deliveryspecification> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DeliveryspecificationDAO deliveryspecificationDAO;
	
	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}


	public void setDeliveryspecificationDAO(DeliveryspecificationDAO deliveryspecificationDAO) {
		this.deliveryspecificationDAO = deliveryspecificationDAO;
	}

	@Override
	public Deliveryspecification getRowData(String rowKey) {
		return null;
	}
	
	@Override
	public Object getRowKey(Deliveryspecification deliveryspecification){
		return null;
	}
	
	@Override
	public List<Deliveryspecification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Deliveryspecification> list =  null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		
	    list = deliveryspecificationDAO.getDeliberySpecyficationList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}


}
