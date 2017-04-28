package info.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the access database table.
 * 
 */
@Entity
@NamedQuery(name="Access.findAll", query="SELECT a FROM Access a")
public class Access implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idaccess;

	private String login;

	private String pass;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	private Customer customer;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	private Employee employee;

	public Access() {
	}

	public int getIdaccess() {
		return this.idaccess;
	}

	public void setIdaccess(int idaccess) {
		this.idaccess = idaccess;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}