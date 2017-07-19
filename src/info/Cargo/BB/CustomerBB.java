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

import info.dao.CustomerDAO;
import info.dao.UserDAO;
import info.entities.User;
import info.lazydatamodel.LazyDataModelCustomer;

@ManagedBean
@ViewScoped
public class CustomerBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String Name;
	private String Surname;
	private String Login;
	private String pass;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@EJB
	CustomerDAO customerDAO;

	private LazyDataModelCustomer lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelCustomer();
	}

	public LazyDataModel<User> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		lazyModel.setSearchParams(searchParams);
		lazyModel.setCustomerDAO(customerDAO);
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
	
	public void edit(User u) {

		u.setName(u.getName());
		u.setSurname(u.getSurname());
		u.setPass(u.getPass());
		u.setLogin(u.getLogin());

		try {
			customerDAO.merge(u);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Updata Success" + u.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Customer Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Customer Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	

}
