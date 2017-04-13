package info.dao;

import info.entities.Employee;

public interface IEmployeeLoginDAO {
	
	public Employee getEmployee(String login, String pass);

}
