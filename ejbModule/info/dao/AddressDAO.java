package info.dao;

import javax.ejb.EJB;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Address;

@Stateless

public class AddressDAO  {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	public Address find(Integer id){
		return em.find(Address.class, id);
	}
}
