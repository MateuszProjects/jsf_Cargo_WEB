package info.lazydatamodel;

import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import info.dao.LoactionDAO;
import info.dao.PaginationInfo;
import info.entities.Location;


public class LazyDataModelLoaction extends LazyDataModel<Location> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LoactionDAO locationDAO;

	private Map<String, Object> searchParams;

	public LoactionDAO getLocationDAO() {
		return locationDAO;
	}

	public void setLocationDAO(LoactionDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}
	
	@Override
	public Location  getRowData(String rowKey) {
		return null;
	}
	
	@Override
	public Object getRowKey(Location location) {
		return null;
	}
	
	@Override
	public List<Location> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Location> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		list = locationDAO.getSearchList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}
	
	
}
