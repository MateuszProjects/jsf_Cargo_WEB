package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.LazyDataModel;

import info.dao.AddressDAO;
import info.entities.Address;

@ManagedBean
@ViewScoped
public class AddressBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	AddressDAO addressDAO;
	
	private LazyDataModelAddress lazyModel = null;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelAddress();
	}

	public LazyDataModel<Address> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		lazyModel.setSearchParams(searchParams);
		lazyModel.setAddressDAO(addressDAO);
		return lazyModel;

	}
}
