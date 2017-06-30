package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.UserDAO;
import info.entities.User;
import info.lazydatamodel.LazyDataModelEmployee;


@ManagedBean
@ViewScoped
public class EmployeeBB  implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	UserDAO userDAO;
	
	private LazyDataModelEmployee lazyModel;
	
	
	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelEmployee();
	}
	
	public LazyDataModel<User>  getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		lazyModel.setSearchParams(searchParams);
		lazyModel.setUserDAO(userDAO);
		return lazyModel;
	}
	
}
