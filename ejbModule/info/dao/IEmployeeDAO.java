package info.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import info.entities.Employee;

@Local
public interface IEmployeeDAO {

	public void createEmployee(Employee employee);

	public Employee find(Integer id);

	public void remove(Employee customer);

	public Employee merge(Employee customer);

	public List<Employee> getSearchList(Map<String, Object> searchParams);

}
