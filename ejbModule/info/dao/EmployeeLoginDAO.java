package info.dao;


import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



import info.entities.Employee;

@Stateless
public class EmployeeLoginDAO /*implements IEmployeeLoginDAO*/ {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;
	
	
	public Employee getEmployee(String login, String pass) {
		Employee employee = null;

		Query query = em.createQuery("SELECT e FROM Employee e  where e.login=:login and e.pass=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);

		try {
			employee = (Employee) query.getSingleResult();
			
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return employee;
	}
}
