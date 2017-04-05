package info.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idaddress;

	private String cityCode;

	private String streat;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="idcustomer")
	private Customer customer;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="idemployee")
	private Employee employee;

	public Address() {
	}

	public int getIdaddress() {
		return this.idaddress;
	}

	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getStreat() {
		return this.streat;
	}

	public void setStreat(String streat) {
		this.streat = streat;
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