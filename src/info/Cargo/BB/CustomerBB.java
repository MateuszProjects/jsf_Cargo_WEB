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
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

import info.dao.CustomerDAO;
import info.dao.UserDAO;
import info.entities.Role;
import info.entities.User;
import info.lazydatamodel.LazyDataModelCustomer;

@ManagedBean
@ViewScoped
public class CustomerBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_CUSTOMER = "a_customer?faces-redirect=true";
	private boolean skip;
	private User user = new User();
	private User check = null;

	private Integer idCustomer;
	private Integer idUser;
	private String name;
	private String surname;
	private String login;
	private String pass;

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
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
	CustomerDAO customerDAO;

	private LazyDataModelCustomer lazyModel;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		check = (User) session.getAttribute("user");
		// check role in system and set variable idUser if role is user
		for(Role r:check.getRoles()){
			if(r.equals("user")){
				setIdUser(check.getIdusers());
			}
		}
		
		lazyModel = new LazyDataModelCustomer();
	}

	public LazyDataModel<User> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idCustomer != null) {
			searchParams.put("idEmployee", idCustomer);
		}

		if (name != null) {
			searchParams.put("name", name);
		}

		if (surname != null) {
			searchParams.put("surname", surname);
		}
		
		if(idUser != null){
			searchParams.put("idUser", idUser);
		}
		
		lazyModel.setSearchParams(searchParams);
		lazyModel.setCustomerDAO(customerDAO);
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

	public void save() {
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu user"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate wrong"));
		}

		try {
			customerDAO.createUserCustomer(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void edit(User userObject) {

		userObject.setName(userObject.getName());
		userObject.setSurname(userObject.getSurname());
		userObject.setPass(userObject.getPass());
		userObject.setLogin(userObject.getLogin());

		try {
			customerDAO.merge(userObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Updata Success" + userObject.getName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String delete(User userObject) {
		customerDAO.remove(userObject);
		return PAGE_CUSTOMER;
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
