package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import info.entities.Employee;

@Stateless
public class EmployeeDAO implements IEmployeeDAO {

	private final static String UNIT_NAME = "jsfcourse-CargoPU";

	public IEmployeeDAO _IEmployeeDAO;

	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	@Override
	public void createEmployee(Employee employee) {
		em.persist(employee);

	}

	@Override
	public Employee find(Integer id) {
		return em.find(Employee.class, id);
	}

	@Override
	public void remove(Employee employee) {
		em.remove(em.merge(employee));

	}

	@Override
	public Employee merge(Employee employee) {
		return em.merge(employee);
	}

	@Override
	public List<Employee> getSearchList(Map<String, Object> searchParams) {
		
		return null;
	}

}
