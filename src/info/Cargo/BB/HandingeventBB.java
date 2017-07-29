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

import info.dao.HandingeventDAO;
import info.entities.Handlingevent;
import info.lazydatamodel.LazyDataModelHandingevent;

@ManagedBean
@ViewScoped
public class HandingeventBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_HANDINGEVENT = "a_handingevent?faces-redirect=true";

	@EJB
	HandingeventDAO handingeventDAO;

	private LazyDataModelHandingevent lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelHandingevent();
	}

	public LazyDataModel<Handlingevent> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setHandingeventDAO(handingeventDAO);
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
		return result;
	
	
	}
	
	public void edit(Handlingevent handlingeventObject){
		
		handlingeventObject.setDecriptonEvent(handlingeventObject.getDecriptonEvent());
		
		try {
			handingeventDAO.merge(handlingeventObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void save() {
		
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public String delete(Handlingevent handlingevent){
		handingeventDAO.remove(handlingevent);
		return PAGE_HANDINGEVENT;
	}
}