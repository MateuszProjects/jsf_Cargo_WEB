package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import info.dao.BillofladingDAO;
import info.entities.Billoflading;
import info.lazydatamodel.LazyDataModelBilloflading;

@ManagedBean
@ViewScoped
public class BillofloadingBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	BillofladingDAO billofladingDAO;

	private LazyDataModelBilloflading lazyModel;

	@PostConstruct
	public void init() {
		lazyModel = new LazyDataModelBilloflading();
	}
	
	public LazyDataModel<Billoflading> getLazyList(){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		lazyModel.setSearchParams(searchParams);
		lazyModel.setBillofladingDAO(billofladingDAO);
		return lazyModel;
	}

}
