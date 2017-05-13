package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Employee;

@Stateless
public class EmployeeDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	
	public Employee find(Integer id) {
		return em.find(Employee.class, id);
	}

	public void createEmployee(Employee employee) {
		em.persist(employee);
	}

	public void remove(Employee employee) {
		em.remove(em.merge(employee));
	}

	public List<Employee> getSearchList(Map<String, Object> searchParams) {
		List<Employee> list = null;

		String select = "select c ";
		String from = "from Employee c ";
		String where = "";
		String orderby = "";
		String join = "";

		return list;

	}
	
	
	
	

}
