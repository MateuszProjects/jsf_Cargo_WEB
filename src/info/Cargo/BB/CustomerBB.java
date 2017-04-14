package info.Cargo.BB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import info.dao.CustomerDAO;

@ManagedBean
@ViewScoped
public class CustomerBB implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public String nn= "jjjj";
	
	
	public String getNn() {
		return nn;
	}


	public void setNn(String nn) {
		this.nn = nn;
	}


	@EJB(mappedName="customerDAO")
	CustomerDAO customerDAO;
	

}
