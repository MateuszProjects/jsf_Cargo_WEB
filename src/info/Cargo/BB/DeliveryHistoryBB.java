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

import info.dao.DeliveryhistoryDAO;
import info.entities.Deliveryhistory;
import info.lazydatamodel.LazyDataModelDeliveryhistory;

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
	
	public void edit(Deliveryhistory deliveryhistory){
		
	}
	
	public void save() {
		
	}
}
