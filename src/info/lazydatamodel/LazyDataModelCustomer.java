package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.CustomerDAO;
import info.dao.PaginationInfo;
import info.entities.User;

public class LazyDataModelCustomer  extends LazyDataModel<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerDAO customerDAO;
	
	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}


	
	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Override
	public User getRowData(String rowKey) {
		return null;
	}
	
	@Override
	public Object getRowKey(User user) {
		return null;
	}
	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<User> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		
	    list = customerDAO.getCustomerList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}

}
