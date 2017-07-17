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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import info.dao.BillofladingDAO;
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

	private Billoflading billoflading = new Billoflading();
	private Cargo cargo = null;

	private String text = new String();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@EJB
	BillofladingDAO billofladingDAO;

	private LazyDataModelBilloflading lazyModel;

	@PostConstruct
	public void init() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

		cargo = (Cargo) session.getAttribute("cargo");

		if (cargo != null) {
			session.removeAttribute("cargo");
		}

		lazyModel = new LazyDataModelBilloflading();
	}

	public LazyDataModel<Billoflading> getLazyList() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
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

	private boolean validate() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		boolean result = false;

		if (text == null) {
			ctx.addMessage(null, new FacesMessage("text Wymagane"));
		}

		if (ctx.getMessageList().isEmpty()) {
			billoflading.setTekst(text);
			billoflading.setCargo(cargo);
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
	}
}
