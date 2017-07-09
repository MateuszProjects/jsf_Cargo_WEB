package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.PaginationInfo;
import info.dao.RoleDAO;
import info.entities.Role;

public class LazyDataModelRole extends LazyDataModel<Role> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	RoleDAO roleDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Role getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Role role) {
		return null;
	}

	@Override
	public List<Role> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Role> list = null;

		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		setRowCount(info.getCount());
		list = roleDAO.getSearchList(searchParams, info);
		return list;
	}

}
