package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.LazyDataModel;

import info.dao.RoleDAO;
import info.entities.Role;
import info.lazydatamodel.LazyDataModelRole;

@ManagedBean
public class RoleBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LazyDataModelRole lazyModel;

	@EJB
	RoleDAO roleDAO;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelRole();
	}

	public LazyDataModel<Role> etLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setRoleDAO(roleDAO);
		return lazyModel;
	}

}
