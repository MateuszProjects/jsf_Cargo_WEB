package info.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Deliveryspecification;

@Stateless
public class DeliveryspecificationDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public Deliveryspecification find(Integer id) {
		return em.find(Deliveryspecification.class, id);
	}

	public void createDeliveryspecyfication(Deliveryspecification deliveryspecification) {
		em.persist(deliveryspecification);
	}

	public void remove(Deliveryspecification deliveryspecification) {
		em.remove(em.merge(deliveryspecification));
	}
}
