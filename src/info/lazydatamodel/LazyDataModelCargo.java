package info.lazydatamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.CargoDAO;
import info.dao.PaginationInfo;
import info.entities.Cargo;

public class LazyDataModelCargo extends LazyDataModel<Cargo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	CargoDAO cargoDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public CargoDAO getCargoDAO() {
		return cargoDAO;
	}

	public void setCargoDAO(CargoDAO cargoDAO) {
		this.cargoDAO = cargoDAO;
	}

	@Override
	public Cargo getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Cargo cargo) {
		return null;
	}

	@Override
	public List<Cargo> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Cargo> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);

		list = cargoDAO.getCargoList(searchParams, info);
		setRowCount(info.getCount());
		return list;
	}

}
