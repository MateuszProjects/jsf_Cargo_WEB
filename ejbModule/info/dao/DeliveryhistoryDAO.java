package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Deliveryhistory;

@Stateless
public class DeliveryhistoryDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Deliveryhistory find(Integer id) {
		return em.find(Deliveryhistory.class, id);
	}

	public void createDeliveryhistory(Deliveryhistory deliveryhistory) {
		em.persist(deliveryhistory);
	}

	public void remove(Deliveryhistory deliveryhistory) {
		em.remove(em.merge(deliveryhistory));
	}
}
