package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.PaymentDAO;
import info.entities.Payment;

@ManagedBean
@ViewScoped
public class PaymentBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	PaymentDAO paymentDAO;

	private LazyDataModelPayment lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelPayment();
	}

	public LazyDataModel<Payment> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setPaymentDAO(paymentDAO);
		return lazyModel;
	}

}
