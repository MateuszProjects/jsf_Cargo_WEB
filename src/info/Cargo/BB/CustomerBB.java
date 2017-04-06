package info.Cargo.BB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import info.dao.CustomerDAO;

@ManagedBean
public class CustomerBB implements Serializable{


	private static final long serialVersionUID = 1L;
	
	
	
	@EJB(mappedName="customerDAO")
	CustomerDAO customerDAO;
	

}
