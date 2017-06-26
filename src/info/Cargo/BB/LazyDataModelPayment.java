package info.Cargo.BB;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import info.dao.PaginationInfo;
import info.dao.PaymentDAO;
import info.entities.Cargo;
import info.entities.Payment;

public class LazyDataModelPayment extends LazyDataModel<Payment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PaymentDAO paymentDAO;

	private Map<String, Object> searchParams;

	public Map<String, Object> getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(Map<String, Object> searchParams) {
		this.searchParams = searchParams;
	}

	public void setPaymentDAO(PaymentDAO paymentDAO) {
		this.paymentDAO = paymentDAO;
	}

	@Override
	public Payment getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(Payment payment) {
		return null;
	}

	@Override
	public List<Payment> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Payment> list = null;
		PaginationInfo info = new PaginationInfo();
		info.setLimit(pageSize);
		info.setOffset(first);
		list = paymentDAO.getSearchList(searchParams, info);
		setRowCount(info.getCount());
		return list;

	}

}
