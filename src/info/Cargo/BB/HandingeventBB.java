package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
}