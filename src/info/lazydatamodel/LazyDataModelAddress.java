package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.AddressDAO;
import info.dao.PaginationInfo;
import info.entities.Address;

public class LazyDataModelAddress extends LazyDataModel<Address> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AddressDAO addressDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

	@Override
	public Address getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Address address) {
		return null;
	}

	@Override
	public List<Address> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Address> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);

		list = addressDAO.getAddressList(searchParams, info);
		setRowCount(info.getCount());
		
		return list;

	}
}
