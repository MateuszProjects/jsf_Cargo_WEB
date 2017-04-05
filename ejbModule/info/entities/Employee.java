package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idemployee;

	private String login;

	private String name;

	private String pass;

	private String surname;

	private String telephone;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="employee")
	private List<Address> addresses;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="employees")
	private List<Role> roles;

	//bi-directional many-to-one association to Payment
	@OneToMany(mappedBy="employee")
	private List<Payment> payments;

	//bi-directional many-to-one association to Transport
	@OneToMany(mappedBy="employee")
	private List<Transport> transports;

	public Employee() {
	}

	public int getIdemployee() {
		return this.idemployee;
	}

	public void setIdemployee(int idemployee) {
		this.idemployee = idemployee;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setEmployee(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setEmployee(null);

		return address;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Payment> getPayments() {
		return this.payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public Payment addPayment(Payment payment) {
		getPayments().add(payment);
		payment.setEmployee(this);

		return payment;
	}

	public Payment removePayment(Payment payment) {
		getPayments().remove(payment);
		payment.setEmployee(null);

		return payment;
	}

	public List<Transport> getTransports() {
		return this.transports;
	}

	public void setTransports(List<Transport> transports) {
		this.transports = transports;
	}

	public Transport addTransport(Transport transport) {
		getTransports().add(transport);
		transport.setEmployee(this);

		return transport;
	}

	public Transport removeTransport(Transport transport) {
		getTransports().remove(transport);
		transport.setEmployee(null);

		return transport;
	}

}