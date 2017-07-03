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
import org.primefaces.model.LazyDataModel;

import info.dao.UserDAO;
import info.entities.User;
import info.lazydatamodel.LazyDataModelEmployee;

@ManagedBean
@ViewScoped
public class EmployeeBB implements Serializable {

	private static final long serialVersionUID = 1L;

	// private Integer Iduser;
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
	UserDAO userDAO;

	User user = new User();

	private LazyDataModelEmployee lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelEmployee();
	}

	public LazyDataModel<User> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		lazyModel.setSearchParams(searchParams);
		lazyModel.setUserDAO(userDAO);
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

	public boolean renderIs(String name){
		boolean result = false;
		if(name.equals(Surname)){
			result = true;
		}
		return result;
	}
	
	
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		

		
		if (Name == null) {
			ctx.addMessage(null, new FacesMessage("Name Wymagane"));
		}
		
		if (Surname == null) {
			ctx.addMessage(null, new FacesMessage("Surname Wymagane"));
		}
		
		if (Login == null) {
			ctx.addMessage(null, new FacesMessage("idAddress Wymagane"));
		}
		
		if (pass == null) {
			ctx.addMessage(null, new FacesMessage("pass Wymagane"));
		}
		
		if (ctx.getMessageList().isEmpty()) {
			
	
			user.setName(Name);
			user.setSurname(Surname);
			user.setLogin(Login);
			user.setPass(pass);
			result = true;
		}
		
		return result;
	}
	
   
 
	public void reset(FlowEvent event){
		this.Name = null;
		this.Surname = null;
		this.Login = null;
		this.pass = null;
		skip = true;
	}

	public void save() {
		if (user == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu user"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate wrong"));
		}
		
		try{
			userDAO.createUser(user);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		RequestContext.getCurrentInstance().reset("panelAdd:");

	}

}
