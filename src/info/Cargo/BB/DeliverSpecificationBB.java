package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
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
	
	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		return result;
	
	
	}
	
	public void edit(Deliveryspecification deliveryspecification){
		
	}
	
	public void save() {
		
	}

}
