package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.CargoDAO;
import info.entities.Cargo;

@ManagedBean
@ViewScoped
public class CargoBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	CargoDAO cargoDAO;

	private LazyDataModelCargo lazyModel = null;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelCargo();
	}

	public LazyDataModel<Cargo> getLazyList(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setCargoDAO(cargoDAO);
		return lazyModel;
	}
}
