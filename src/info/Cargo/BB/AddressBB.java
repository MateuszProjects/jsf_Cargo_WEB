package info.Cargo.BB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import info.dao.AddressDAO;

@ManagedBean
public class AddressBB {

	@EJB
	AddressDAO addressDAO;
}
