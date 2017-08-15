package info.Cargo.BB;

import java.io.Serializable;
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
import java.text.ParseException;
import info.dao.DeliveryhistoryDAO;
import info.entities.Cargo;
import info.entities.Deliveryhistory;
import info.lazydatamodel.LazyDataModelDeliveryhistory;

@ManagedBean
@ViewScoped
public class DeliveryHistoryBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_DELIVERY_HISTORY = "a_deliveryhistory?faces-redirect=true";
	private Deliveryhistory deliveryHistory = new Deliveryhistory();
	private Cargo cargo = null;
	private LazyDataModelDeliveryhistory lazyModel;

	private boolean skip;

	private Integer idDeliveryHistory;
	private Integer idCargo;
	private String arriveDate;
	private String leaveDate;
	private String comment;

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public Integer getIdDeliveryHistory() {
		return idDeliveryHistory;
	}

	public void setIdDeliveryHistory(Integer idDeliveryHistory) {
		this.idDeliveryHistory = idDeliveryHistory;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Deliveryhistory getDeliveryHistory() {
		return deliveryHistory;
	}

	public void setDeliveryHistory(Deliveryhistory deliveryHistory) {
		this.deliveryHistory = deliveryHistory;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "personal";
		} else {
			return event.getNewStep();
		}

	}

	@EJB
	DeliveryhistoryDAO deliveryhistoryDAO;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		cargo = (Cargo) session.getAttribute("cargo");

		if (cargo != null) {
			session.removeAttribute("cargo");
		}
		lazyModel = new LazyDataModelDeliveryhistory();
	}

	/**
	 * 
	 * @return
	 */
	public LazyDataModel<Deliveryhistory> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idDeliveryHistory != null) {
			searchParams.put("idDeliveryHistory", idDeliveryHistory);
		}

		if (comment != null) {
			searchParams.put("comment", comment);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setDeliveryhistoryDAO(deliveryhistoryDAO);
		return lazyModel;
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Delivery History Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (arriveDate == null) {
			ctx.addMessage(null, new FacesMessage("arriveDate Wymagane"));
		}

		if (leaveDate == null) {
			ctx.addMessage(null, new FacesMessage("leaveDate Wymagane"));
		}

		if (comment == null) {
			ctx.addMessage(null, new FacesMessage("comment Wymagane"));
		}

		Date arrive_Date = null;
		Date leave_Date = null;

		try {
			arrive_Date = new SimpleDateFormat("dd-MM-yyyy").parse(arriveDate);
			leave_Date = new SimpleDateFormat("dd-MM-yyyy").parse(leaveDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		if (ctx.getMessageList().isEmpty()) {

			deliveryHistory.setCargo(cargo);
			deliveryHistory.setArrivayDate(arrive_Date);
			deliveryHistory.setLeaveDate(leave_Date);
			deliveryHistory.setComent(comment);

			result = true;
		}
		return result;

	}

	/**
	 * 
	 * @param deliveryhistoryObject
	 */
	public void edit(Deliveryhistory deliveryhistoryObject) {

		deliveryhistoryObject.setComent(deliveryhistoryObject.getComent());
		deliveryhistoryObject.setLeaveDate(deliveryhistoryObject.getLeaveDate());
		deliveryhistoryObject.setArrivayDate(deliveryhistoryObject.getArrivayDate());

		try {
			deliveryhistoryDAO.merge(deliveryhistoryObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 */
	public void save() {

		if (deliveryHistory == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu delivery history"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate false"));
		}

		try {
			deliveryhistoryDAO.createDeliveryhistory(deliveryHistory);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String delete(Deliveryhistory deliveryhistory) {
		deliveryhistoryDAO.remove(deliveryhistory);
		return PAGE_DELIVERY_HISTORY;
	}
}
