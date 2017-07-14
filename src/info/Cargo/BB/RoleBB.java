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
	private String name = new String();
	
	private LazyDataModelRole lazyModel;
	
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
		
		if(name == null){
			
		}
		
		if(ctx.getMessageList().isEmpty()){
			
			// add seter for role
			
			result = true;
		}
		
		return result;
	
	
	}
	
	public void edit(Role roleObject){
		
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
		
		if(role == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu role"));
		}
		
		if(!validate()){
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

}
