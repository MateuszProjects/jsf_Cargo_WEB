package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.DeliveryspecificationDAO;
import info.entities.Deliveryspecification;
import info.lazydatamodel.LazyDataModelDeliveryspecification;

@ManagedBean
@ViewScoped
public class DeliverSpecificationBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	DeliveryspecificationDAO deliveryspecificationDAO;

	private LazyDataModelDeliveryspecification lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelDeliveryspecification();
	}

	public LazyDataModel<Deliveryspecification> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setDeliveryspecificationDAO(deliveryspecificationDAO);
		return lazyModel;
	}

}
