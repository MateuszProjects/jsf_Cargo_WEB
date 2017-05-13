package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Transport;

@Stateless
public class TransportDAO  {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	
	public Transport find(Integer id) {
		return em.find(Transport.class, id);
	}

	public void createTransport(Transport transport) {
		em.persist(transport);
	}

	public void remove(Transport transport) {
		em.remove(em.merge(transport));
	}

	public List<Transport> getSearchList(Map<String, Object> searchParams) {
		List<Transport> list = null;

		String select = "select c ";
		String from = "from Customer c ";
		String where = "";
		String orderby = "";
		String join = "";

		return list;

	}
	
}
