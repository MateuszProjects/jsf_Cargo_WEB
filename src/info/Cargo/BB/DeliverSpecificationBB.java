package info.Cargo.BB;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.primefaces.event.FlowEvent;
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
	private String date = null;

	private Integer idDeliverySpecyfication;
	private Integer idCargo;
	private String arrivaltime;
	
	private boolean skip;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getIdDeliverySpecyfication() {
		return idDeliverySpecyfication;
	}

	public void setIdDeliverySpecyfication(Integer idDeliverySpecyfication) {
		this.idDeliverySpecyfication = idDeliverySpecyfication;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public String getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(String arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

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

		if (cargo != null) {
			session.removeAttribute("cargo");
		}

		if (location != null) {
			session.removeAttribute("location");
		}

		lazyModel = new LazyDataModelDeliveryspecification();
	}

	public LazyDataModel<Deliveryspecification> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idDeliverySpecyfication != null) {
			searchParams.put("idDeliverySpec", idDeliverySpecyfication);
		}

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

		if (arrivaltime == null) {
			ctx.addMessage(null, new FacesMessage("arrivaltime Wymagane"));
		}

		Date arrival_time = null;
		
		try{
			arrival_time = new SimpleDateFormat("dd-MM-yyyy").parse(arrivaltime);
		}catch(ParseException ex){
			ex.printStackTrace();
		}
		
		if (ctx.getMessageList().isEmpty()) {

			deliveryspcification.setArrivaltime(arrival_time);
			deliveryspcification.setCargo(cargo);
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

		if (deliveryspcification == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu deliveryspecyfiaction"));
		}

		if (!validate()) {
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

	public String delete(Deliveryspecification deliveryspecification) {
		deliveryspecificationDAO.remove(deliveryspecification);
		return PAGE_DELIVERYSPECYFICATION;
	}
}
