package info.Cargo.BB;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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

import info.dao.AddressDAO;
import info.entities.Address;
import info.entities.User;
import info.lazydatamodel.LazyDataModelAddress;

@ManagedBean
@ViewScoped
public class AddressBB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String PAGE_ADDRESS = "a_address?faces-redirect=true";
	
	private Integer idAddress;
	private Integer idUser;
	private String cityCode;
	private String street;
	private String telephone;
	private String email;

	User userObject = null;
	Address address = new Address();
	private boolean skip;

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}

	}

	public Integer getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(Integer idAddress) {
		this.idAddress = idAddress;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@EJB
	AddressDAO addressDAO;

	private LazyDataModelAddress lazyModel = null;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		
		userObject = (User) session.getAttribute("userObject");
		
		if(userObject != null){
			setIdUser(userObject.getIdusers());
			session.removeAttribute("userObject");
		}
		
		lazyModel = new LazyDataModelAddress();
	}

	public LazyDataModel<Address> getLazylist() {
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (idAddress != null) {
			searchParams.put("idAddress", idAddress);
		}
		
		if(cityCode != null){
			searchParams.put("cityCode", cityCode);
		}
		
		if(street != null){
			searchParams.put("street", street);
		}

		lazyModel.setSearchParams(searchParams);
		lazyModel.setAddressDAO(addressDAO);
		return lazyModel;

	}

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (cityCode == null) {
			ctx.addMessage(null, new FacesMessage("cityCode Wymagane"));
		}

		if (street == null) {
			ctx.addMessage(null, new FacesMessage("street Wymagane"));
		}

		if (telephone == null) {
			ctx.addMessage(null, new FacesMessage("telephone Wymagane"));
		}

		if (email == null) {
			ctx.addMessage(null, new FacesMessage("email Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			address.setCityCode(cityCode);
			address.setTelephone(telephone);
			address.setStreet(street);
			address.setEmail(email);
			address.setUser(userObject);
			result = true;
		}
		return result;
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Car Edited");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void edit(Address addressObject) {

		addressObject.setCityCode(addressObject.getCityCode());
		addressObject.setEmail(addressObject.getEmail());
		addressObject.setStreet(addressObject.getStreet());
		addressObject.setTelephone(addressObject.getTelephone());
		
		try {
			addressDAO.merge(addressObject);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Update Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void save() {

		if (address == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Brak objektu address"));
		}

		if (!validate()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Validate wrong"));
		}

		try {
			addressDAO.createAddress(address);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		FacesMessage msg = new FacesMessage("Create Success");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public String delete(Address address){
		addressDAO.remove(address);
		return PAGE_ADDRESS;
	}
}
