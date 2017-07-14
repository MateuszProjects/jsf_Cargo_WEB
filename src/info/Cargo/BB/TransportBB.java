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

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.TransportDAO;
import info.entities.Transport;
import info.lazydatamodel.LazyDataModelTransport;

@ManagedBean
@ViewScoped
public class TransportBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void showMessage() {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity."));
	}

	@EJB
	TransportDAO transportDAO;

	private LazyDataModelTransport lazyModel;

	public LazyDataModelTransport getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModelTransport lazyModel) {
		this.lazyModel = lazyModel;
	}

	public void setTransportDAO(TransportDAO transportDAO) {
		this.transportDAO = transportDAO;
	}

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelTransport();
	}

	public LazyDataModel<Transport> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setTransportDAO(transportDAO);
		return lazyModel;

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
			return "personal";
		} else {
			return event.getNewStep();
		}

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

	public void edit(Transport transport) {

	}

	public void save() {

	}

}
