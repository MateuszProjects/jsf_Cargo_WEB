package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.LoactionDAO;
import info.entities.Location;
import info.lazydatamodel.LazyDataModelLoaction;

@ManagedBean
@ViewScoped
public class LocationBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	LoactionDAO locationDAO;

	private LazyDataModelLoaction lazyModel;


	public void setLazyModel(LazyDataModelLoaction lazyModel) {
		this.lazyModel = lazyModel;
	}

	public void setLocationDAO(LoactionDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelLoaction();
	}

	public LazyDataModel<Location> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setLocationDAO(locationDAO);
		return lazyModel;
	}

}
