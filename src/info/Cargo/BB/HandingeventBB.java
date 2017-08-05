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

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.HandingeventDAO;
import info.entities.Cargo;
import info.entities.Handlingevent;
import info.lazydatamodel.LazyDataModelHandingevent;

@ManagedBean
@ViewScoped
public class HandingeventBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_HANDINGEVENT = "a_handingevent?faces-redirect=true";
	private Handlingevent handingevent = new Handlingevent();
	private Cargo cargo = null;

	private Integer idHandlingevent;
	private String description;

	public Integer getIdHandingevent() {
		return idHandlingevent;
	}

	public void setIdHandingevent(Integer idHandingevent) {
		this.idHandlingevent = idHandingevent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@EJB
	HandingeventDAO handlingeventDAO;

	private LazyDataModelHandingevent lazyModel;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		cargo = (Cargo) session.getAttribute("cargo");

		if (cargo != null) {
			session.removeAttribute("cargo");
		}

		if (description != null) {
			session.removeAttribute("description");
		}

		lazyModel = new LazyDataModelHandingevent();
	}

	/**
	 * 
	 * @return
	 */
	public LazyDataModel<Handlingevent> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idHandlingevent != null) {
			searchParams.put("idhandingevent", idHandlingevent);
		}

		if (description != null) {
			searchParams.put("description", description);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setHandingeventDAO(handlingeventDAO);
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

	/**
	 * validate correct inputs data for object handlingevent
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (description == null) {
			ctx.addMessage(null, new FacesMessage("description Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			handingevent.setDecriptonEvent(description);
			handingevent.setCargo(cargo);
			result = true;
		}

		return result;
	}

	/**
	 * Function for edit object handlingevent
	 * 
	 * @param handlingeventObject
	 */
	public void edit(Handlingevent handlingeventObject) {

		handlingeventObject.setDecriptonEvent(handlingeventObject.getDecriptonEvent());

		try {
			handlingeventDAO.merge(handlingeventObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * function for persist new object handlingevent
	 */
	public void save() {

		if (handingevent == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu handlinEvent"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate false"));
		}

		try {
			handlingeventDAO.createHandingevent(handingevent);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * function for remove object handlingevent
	 * 
	 * @param handlingevent
	 * @return
	 */
	public String delete(Handlingevent handlingevent) {
		handlingeventDAO.remove(handlingevent);
		return PAGE_HANDINGEVENT;
	}
}