package info.Cargo.BB;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import info.dao.EmployeeDAO;

@ManagedBean
public class EmployeeBB  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName="employeeDAO")
	EmployeeDAO employeeDAO;
	
	
}
