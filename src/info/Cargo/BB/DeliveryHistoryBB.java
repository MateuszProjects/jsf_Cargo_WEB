package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.DeliveryhistoryDAO;
import info.entities.Deliveryhistory;

@ManagedBean
@ViewScoped
public class DeliveryHistoryBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	DeliveryhistoryDAO deliveryhistoryDAO;

	private LazyDataModelDeliveryhistory lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelDeliveryhistory();
	}

	public LazyDataModel<Deliveryhistory> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setDeliveryhistoryDAO(deliveryhistoryDAO);
		return lazyModel;
	}
}
