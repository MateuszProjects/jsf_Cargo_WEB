package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
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
	private Location location = new Location();
	
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

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;
		
		if(ctx.getMessageList().isEmpty()){
			
			// add set for location
			
			result = true;
		}
		
		return result;

	}

	public void edit(Location locationObject) {
		
		locationObject.setDescription(locationObject.getDescription());
		
		
		try {
			locationDAO.merge(locationObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {
		
		if(location == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu location"));
		}
		
		if(!validate()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("validate false"));
		}
		
		try {
			locationDAO.createLocation(location);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
