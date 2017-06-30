package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.BillofladingDAO;
import info.dao.PaginationInfo;
import info.entities.Billoflading;


public class LazyDataModelBilloflading extends LazyDataModel<Billoflading> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BillofladingDAO billofladingDAO;
	
	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setBillofladingDAO(BillofladingDAO billofladingDAO) {
		this.billofladingDAO = billofladingDAO;
	}
	
	@Override
	public Billoflading getRowData(String rowKey) {
		return null;
	}
	
	@Override
	public Object getRowKey(Billoflading billoflading) {
		return null;
	}
	
	@Override
	public List<Billoflading> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Billoflading> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		
		 list = billofladingDAO.getBillofladingList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}


}
