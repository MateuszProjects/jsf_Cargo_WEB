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

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.UserDAO;
import info.entities.Address;
import info.entities.User;
import info.lazydatamodel.LazyDataModelEmployee;

@ManagedBean
@ViewScoped
public class EmployeeBB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String PAGE_ADDRESS = "a_address?faces-redirect=true";
	private final String PAGE_EMPLOYEE = "a_employee?faces-redirect=true";

	// param for looking for
	private Integer idEmployee;

	private Integer idCargo;
	private String name;
	private String surname;
	private String login;
	private String pass;
	private boolean skip;

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@EJB
	UserDAO userDAO;

	User user = new User();

	private LazyDataModelEmployee lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelEmployee();
	}

	public LazyDataModel<User> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idEmployee != null) {
			searchParams.put("idEmployee", idEmployee);
		}

		if (name != null) {
			searchParams.put("name", name);
		}

		if (surname != null) {
			searchParams.put("surname", surname);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setUserDAO(userDAO);
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

	public boolean renderIs(String name) {
		boolean result = false;
		if (name.equals(surname)) {
			result = true;
		}
		return result;
	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null) {
			ctx.addMessage(null, new FacesMessage("Name Wymagane"));
		}

		if (surname == null) {
			ctx.addMessage(null, new FacesMessage("Surname Wymagane"));
		}

		if (login == null) {
			ctx.addMessage(null, new FacesMessage("idAddress Wymagane"));
		}

		if (pass == null) {
			ctx.addMessage(null, new FacesMessage("pass Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {

			user.setName(name);
			user.setSurname(surname);
			user.setLogin(login);
			user.setPass(pass);
			result = true;
		}

		return result;
	}

	public void edit(User userObject) {

		userObject.setName(userObject.getName());
		userObject.setSurname(userObject.getSurname());
		userObject.setPass(userObject.getPass());
		userObject.setLogin(userObject.getLogin());

		try {
			userDAO.merge(userObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Update Success" + userObject.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("User Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu user"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate wrong"));
		}

		try {
			userDAO.createUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String addAddress(User userObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("userObject", userObject);
		return PAGE_ADDRESS;
	}

	public String deleteUser(User userObject) {
		userDAO.remove(userObject);
		return PAGE_EMPLOYEE;
	}

}
