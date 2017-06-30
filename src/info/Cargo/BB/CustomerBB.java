package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

	@EJB
	UserDAO userDAO;

	private LazyDataModelCustomer lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelCustomer();
	}
	
	public LazyDataModel<User>  getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		lazyModel.setSearchParams(searchParams);
		lazyModel.setUserDAO(userDAO);
		return lazyModel;
	}
	

}
