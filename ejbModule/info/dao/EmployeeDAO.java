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



}
