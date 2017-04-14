package info.dao;


import javax.ejb.Remote;

import info.entities.Employee;

@Remote
public interface IEmployeeLoginDAO {
	
	public Employee getEmployee(String login, String pass);

}
