package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.PaginationInfo;
import info.dao.UserDAO;
import info.entities.User;

public class LazyDataModelCustomer  extends LazyDataModel<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserDAO userDAO;
	
	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
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
		
		// list = userDAO.getUserCustomerList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}

}
