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

import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.PaymentDAO;
import info.entities.Payment;
import info.lazydatamodel.LazyDataModelPayment;

@ManagedBean
@ViewScoped
public class PaymentBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idPayment;
	private Double amoutn;
	
	
	
	public Integer getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(Integer idPayment) {
		this.idPayment = idPayment;
	}

	public Double getAmoutn() {
		return amoutn;
	}

	public void setAmoutn(Double amoutn) {
		this.amoutn = amoutn;
	}

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
	
	public void edit(Payment payment){
		
	}
	
	public void save() {
		
	}
	
	

}
