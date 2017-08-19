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
	private Transport transport = new Transport();

	private final String PAGE_TRANSPORT = "a_transport?faces-redirect=true";

	private Integer idTransport;
	private String name;
	private Double payKm;

	private boolean skip;

	public Integer getIdTransport() {
		return idTransport;
	}

	public void setIdTransport(Integer idTransport) {
		this.idTransport = idTransport;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPayKm() {
		return payKm;
	}

	public void setPayKm(Double payKm) {
		this.payKm = payKm;
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

		if (idTransport != null) {
			searchParams.put("idTransport", idTransport);
		}
		
		if(name != null){
			searchParams.put("name", name);
		}
		
		if(payKm != null){
			searchParams.put("payKM", payKm);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setTransportDAO(transportDAO);
		return lazyModel;

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

		if (ctx.getMessageList().isEmpty()) {
			transport.setName(name);
			transport.setPaykm(payKm);
			result = true;
		}

		return result;

	}

	public void edit(Transport transportObject) {

		transportObject.setName(transportObject.getName());
		transportObject.setPaykm(transportObject.getPaykm());

		try {
			transportDAO.merge(transportObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {

		if (transport == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu transport"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("validate false"));
		}

		try {
			transportDAO.createTransport(transport);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String delete(Transport transportObject) {
		transportDAO.remove(transportObject);
		return PAGE_TRANSPORT;
	}

}
