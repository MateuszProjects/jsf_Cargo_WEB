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
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.CargoDAO;
import info.dao.CustomerDAO;
import info.dao.TransportDAO;
import info.entities.Cargo;
import info.entities.User;
import info.lazydatamodel.LazyDataModelCargo;

@ManagedBean
@ViewScoped
public class CargoBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String BILL_OF_LOADING = "a_billofloading?faces-redirect=true";
	private Cargo cargo = new Cargo();
	private User user = null;

	private Integer idCargo;
	private String name;

	private boolean skip;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	@EJB
	CargoDAO cargoDAO;

	@EJB
	TransportDAO transportDAO;

	@EJB
	CustomerDAO customerDAO;

	private LazyDataModelCargo lazyModel = null;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		user = (User) session.getAttribute("user");
		lazyModel = new LazyDataModelCargo();
	}

	/**
	 * 
	 * @return
	 */
	public LazyDataModel<Cargo> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idCargo != null) {
			searchParams.put("idcargo", idCargo);
		}
		
		if(name != null){
			searchParams.put("name", name);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setCargoDAO(cargoDAO);
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

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}

	}

	/**
	 * 
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null) {
			ctx.addMessage(null, new FacesMessage("name Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			cargo.setName(name);
			cargo.setUser(user);

			result = true;
		}

		return result;
	}

	/**
	 * 
	 * @param cargoObject
	 */
	public void edit(Cargo cargoObject) {

		cargoObject.setHazMat(cargoObject.getHazMat());
		cargoObject.setName(cargoObject.getName());
		cargoObject.setWeight(cargoObject.getWeight());

		try {
			cargoDAO.merge(cargoObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 */
	public void save() {

		if (cargo == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu cargo"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate false"));
		}

		try {
			cargoDAO.createCargo(cargo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String addBillofLoading(Cargo cargoObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("cargo", cargoObject);
		return BILL_OF_LOADING;
	}
}
