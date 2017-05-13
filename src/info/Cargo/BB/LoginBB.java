package info.Cargo.BB;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import info.dao.AccessDAO;
import info.dao.AddressDAO;


import info.entities.Access;
import info.entities.Customer;
import info.entities.Employee;

@ManagedBean
@SessionScoped
public class LoginBB {

	LoginBB loginBB = null;

	@EJB
	AccessDAO accessDAO;
	

	@EJB
	AddressDAO addressDAO;
	

	private static final String PAGE_MAIN_ADMIN = "admin/admin?faces-redirect=true";
	private static final String PAGE_LOGIN = "/index?faces-redirect=true";
	private static final String PAGE_MAIN_USER = "users/user?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	public String login;
	public String pass;


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

	

	public boolean validateData() {
		boolean result = true;
		FacesContext ctx = FacesContext.getCurrentInstance();

		if (login == null || login.length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "podaj login", "null"));
		}

		if (pass == null || pass.length() == 0) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "podaj password", "null"));
		}

		if (ctx.getMessageList().isEmpty()) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Access access = null;
		Employee employee = null;
		Customer customer = null;

		if (!validateData()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			access = accessDAO.getLogin(login, pass);

			employee = access.getEmployee();
			customer = access.getCustomer();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);
		
		if(employee != null){
		session.setAttribute("employee", employee);
		}
		
		if(customer != null){
		session.setAttribute("customer", customer);
		}

		if (employee != null) {
			return PAGE_MAIN_ADMIN;
		} else if (customer != null) {
			return PAGE_MAIN_USER;
		}

		return PAGE_MAIN_ADMIN;

	}

	public Employee getEmployee() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (Employee) session.getAttribute("employee");
	}

	public Customer getCustomer() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (Customer) session.getAttribute("customer");
	}

	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return PAGE_LOGIN;
	}

}
