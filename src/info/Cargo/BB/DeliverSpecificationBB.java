package info.Cargo.BB;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.DeliveryspecificationDAO;
import info.entities.Cargo;
import info.entities.Deliveryspecification;
import info.entities.Location;
import info.lazydatamodel.LazyDataModelDeliveryspecification;

@ManagedBean
@ViewScoped
public class DeliverSpecificationBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_DELIVERYSPECYFICATION = "a_deliveryspecyfication?faces-redirect=true";

	private Deliveryspecification deliveryspcification = new Deliveryspecification();
	private Date date = new Date();
	
	@EJB
	DeliveryspecificationDAO deliveryspecificationDAO;

	private LazyDataModelDeliveryspecification lazyModel;
	private Cargo cargo = null;
	private Location location = null;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		cargo = (Cargo) session.getAttribute("cargo");
		location = (Location) session.getAttribute("location");
		
		if(cargo != null){
			session.removeAttribute("cargo");
		}
		
		if(location != null){
			session.removeAttribute("location");
		}		
		
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
		
		if(ctx.getMessageList().isEmpty()){
			
			// add set for object deliveryspecyfication
			
			result = true;
		}
		
		return result;

	}

	public void edit(Deliveryspecification deliveryspecificationObject) {
	
		deliveryspecificationObject.setArrivaltime(deliveryspecificationObject.getArrivaltime());
		
		try {
			deliveryspecificationDAO.merge(deliveryspecificationObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {
		
		if(deliveryspcification == null){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Brak objektu deliveryspecyfiaction"));
		}
		
		if(!validate()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("validate false"));
		}
		
		try {
			deliveryspecificationDAO.createDeliveryspecyfication(deliveryspcification);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	public String delete(Deliveryspecification deliveryspecification){
		deliveryspecificationDAO.remove(deliveryspecification);
		return PAGE_DELIVERYSPECYFICATION;
	}
}
