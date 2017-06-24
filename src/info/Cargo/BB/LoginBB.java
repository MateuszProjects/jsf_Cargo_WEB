package info.Cargo.BB;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import info.dao.AccessDAO;

import info.entities.User;

@ManagedBean
@SessionScoped
public class LoginBB {

	LoginBB loginBB = null;

	@EJB
	AccessDAO accessDAO;

	/*
	 * @EJB AddressDAO addressDAO;
	 */
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
		User user = null;

		if (!validateData()) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			user = accessDAO.getLogin(login, pass);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		HttpSession session = (HttpSession) ctx.getExternalContext().getSession(true);

		if (user != null) {
			session.setAttribute("user", user);
		}

		if (user != null) {
			
			if(user.getRoles().get(0).getNameRole().equals("admin")){
			return PAGE_MAIN_ADMIN;
			}
			
			if(user.getRoles().get(0).getNameRole().equals("user"))
			{
				return PAGE_MAIN_USER;
			}
			
		
		}


		 return null;

	}

	public User getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return (User) session.getAttribute("user");
	}

	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.invalidate();
		return PAGE_LOGIN;
	}

}
