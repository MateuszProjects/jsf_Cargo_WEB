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

import info.dao.CargoDAO;
import info.dao.CustomerDAO;
import info.dao.TransportDAO;
import info.entities.Cargo;
import info.entities.Transport;
import info.entities.User;
import info.lazydatamodel.LazyDataModelCargo;

@ManagedBean
@ViewScoped
public class CargoBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String BILL_OF_LOADING = "a_billofloading?faces-redirect=true";
	private final String DELIVERY_HISTORY = "a_deliveryhistory?faces-redirect=true";
	private final String HANDING_EVENT = "a_handingevent?faces-redirect=true";
	private final String DELIVERY_SPECYFIACATION = "a_deliveryspecification?faces-redirect=true";

	private Cargo cargo = new Cargo();
	private User user = null;
	private Transport transport = null;
	
	private Integer idCargo;
	private Integer idTransport;
	private Integer idUser;
	private Double weight;
	private String hazMat;
	private String name;

	private boolean skip;

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdTransport() {
		return idTransport;
	}

	public void setIdTransport(Integer idTransport) {
		this.idTransport = idTransport;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getHazMat() {
		return hazMat;
	}

	public void setHazMat(String hazMat) {
		this.hazMat = hazMat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public Integer getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Integer idCargo) {
		this.idCargo = idCargo;
	}

	@EJB
	CargoDAO cargoDAO;

	@EJB
	TransportDAO transportDAO;

	@EJB
	CustomerDAO customerDAO;

	private LazyDataModelCargo lazyModel = null;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		user = (User) session.getAttribute("user");
		
		if(user.getRoles().get(0).equals("user")){
			setIdUser(user.getIdusers());
		}
		
		
		lazyModel = new LazyDataModelCargo();
	}

	/**
	 * 
	 * @return
	 */
	public LazyDataModel<Cargo> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idCargo != null) {
			searchParams.put("idcargo", idCargo);
		}

		if (name != null) {
			searchParams.put("name", name);
		}
		
		if(idUser != null){
			searchParams.put("idUser", idUser);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setCargoDAO(cargoDAO);
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

	/**
	 * 
	 * @return
	 */
	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (name == null) {
			ctx.addMessage(null, new FacesMessage("name Wymagane"));
		}
		
		if(hazMat == null){
			ctx.addMessage(null, new FacesMessage("hazMat Wymagane"));	
		}
		
		if(weight == null){
			ctx.addMessage(null, new FacesMessage("weight Wymagane"));
		}
		
		if(idTransport == null){
			ctx.addMessage(null, new FacesMessage("idTransport Wymagane"));
		}
		
		if(idUser == null){
			ctx.addMessage(null, new FacesMessage("idUser Wymagane"));
		}
		

		if (ctx.getMessageList().isEmpty()) {
			cargo.setHazMat(hazMat);
			cargo.setName(name);
			user = customerDAO.find(idUser);
			cargo.setUser(user);
		    transport =  transportDAO.find(idTransport);
			cargo.setTransport(transport);
			result = true;
		}

		return result;
	}

	/**
	 * 
	 * @param cargoObject
	 */
	public void edit(Cargo cargoObject) {

		cargoObject.setHazMat(cargoObject.getHazMat());
		cargoObject.setName(cargoObject.getName());
		cargoObject.setWeight(cargoObject.getWeight());

		try {
			cargoDAO.merge(cargoObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 */
	public void save() {

		if (cargo == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu cargo"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate false"));
		}

		try {
			cargoDAO.createCargo(cargo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage(" Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String addBillofLoading(Cargo cargoObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("cargo", cargoObject);
		return BILL_OF_LOADING;
	}

	public String addDeliverySpecyfication(Cargo cargoObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("cargo", cargoObject);
		return DELIVERY_SPECYFIACATION;
	}

	public String addHandingEvent(Cargo cargoObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("cargo", cargoObject);
		return HANDING_EVENT;
	}

	public String addDeliveryHistory(Cargo cargoObject) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("cargo", cargoObject);
		return DELIVERY_HISTORY;
	}
	
	public void addUser(User user){
		setIdUser(user.getIdusers());
		FacesMessage msg = new FacesMessage("setUser");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		System.out.println("setUser");
	}
	
	public void addTransport(Transport transport){
		setIdTransport(transport.getIdtransport());
		FacesMessage msg = new FacesMessage("setTransport");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		System.out.println("setTransport");
	}

}
