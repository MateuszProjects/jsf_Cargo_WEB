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
import javax.servlet.http.HttpSession;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import info.dao.BillofladingDAO;
import info.dao.CargoDAO;
import info.entities.Billoflading;
import info.entities.Cargo;
import info.lazydatamodel.LazyDataModelBilloflading;

@ManagedBean
@ViewScoped
public class BillofloadingBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_BILL_OF_LOADING = "a_billofloading?faces-redirect=true";
	
	private Billoflading billoflading = new Billoflading();
	private Cargo cargo = null;

	private Integer idBillofLoading;
	private String text;
	private Integer idCargo;
	

	private boolean skip;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	public Integer getIdBillofLoading() {
		return idBillofLoading;
	}

	public void setIdBillofLoading(Integer idBillofLoading) {
		this.idBillofLoading = idBillofLoading;
	}

	@EJB
	BillofladingDAO billofladingDAO;

	@EJB
	CargoDAO cargoDAO;

	private LazyDataModelBilloflading lazyModel;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		cargo = (Cargo) session.getAttribute("cargo");

		if (cargo != null) {
			setIdCargo(cargo.getIdcargo());
			session.removeAttribute("cargo");
		}

		lazyModel = new LazyDataModelBilloflading();
	}

	public LazyDataModel<Billoflading> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idBillofLoading != null) {
			searchParams.put("idBillofLoading", idBillofLoading);
		}
		
		if(text != null){
			searchParams.put("text", text);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setBillofladingDAO(billofladingDAO);
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

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}

	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (idCargo == null) {
			ctx.addMessage(null, new FacesMessage("idCargo Wymagane"));
		}

		if (text == null) {
			ctx.addMessage(null, new FacesMessage("text Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			billoflading.setTekst(text);
			Cargo cargofind = cargoDAO.find(idCargo);
			billoflading.setCargo(cargofind);
			result = true;
		}

		return result;
	}

	public void edit(Billoflading billofladingObject) {

		billofladingObject.setTekst(billofladingObject.getTekst());

		try {
			billofladingDAO.merge(billofladingObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void save() {

		if (billoflading == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu billoflading"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate false"));
		}

		try {
			billofladingDAO.createBilloflading(billoflading);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success"));
	}

	public String deleate(Billoflading billofladingObject) {
		billofladingDAO.remove(billofladingObject);
		return PAGE_BILL_OF_LOADING;

	}
}
