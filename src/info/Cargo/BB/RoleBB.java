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

import info.dao.RoleDAO;
import info.entities.Role;
import info.lazydatamodel.LazyDataModelRole;

@ManagedBean
@ViewScoped
public class RoleBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Role role = new Role();
	private String nameRole = new String();
	private final String PAGE_ROLE = "a_role?faces-redirect=true";
	private boolean skip;
	private Integer idRole;
	private String name;
	private LazyDataModelRole lazyModel;

	public Integer getIdRole() {
		return idRole;
	}

	public void setIdRole(Integer idRole) {
		this.idRole = idRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@EJB
	RoleDAO roleDAO;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelRole();
	}

	public LazyDataModel<Role> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setRoleDAO(roleDAO);
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

		if (name == null) {
			ctx.addMessage(null, new FacesMessage("name Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			role.setNameRole(name);
			result = true;
		}

		return result;
	}

	public void edit(Role roleObject) {

		roleObject.setNameRole(roleObject.getNameRole());
		roleObject.setUsers(roleObject.getUsers());

		try {
			roleDAO.merge(roleObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {

		if (role == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu role"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("validate false"));
		}

		try {
			roleDAO.createRole(role);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public String delete(Role roleObject) {
		roleDAO.remove(roleObject);
		return PAGE_ROLE;
	}

}
