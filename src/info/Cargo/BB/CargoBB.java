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
import info.entities.Cargo;
import info.lazydatamodel.LazyDataModelCargo;

@ManagedBean
@ViewScoped
public class CargoBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String BILL_OF_LOADING = "a_billofloading?faces-redirect=true";

	Integer idCargo = null;

	private boolean skip;

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

	private LazyDataModelCargo lazyModel = null;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelCargo();
	}

	public LazyDataModel<Cargo> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
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

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (ctx.getMessageList().isEmpty()) {
			result = true;
		}

		return result;

	}

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

	public void save() {

		if (!validate()) {

		}

		try {

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
