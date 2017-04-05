package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the transport database table.
 * 
 */
@Entity
@NamedQuery(name="Transport.findAll", query="SELECT t FROM Transport t")
public class Transport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idtransport;

	private String name;

	private double pay;

	//bi-directional many-to-one association to Cargo
	@OneToMany(mappedBy="transport")
	private List<Cargo> cargos;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="idemployee")
	private Employee employee;

	public Transport() {
	}

	public int getIdtransport() {
		return this.idtransport;
	}

	public void setIdtransport(int idtransport) {
		this.idtransport = idtransport;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPay() {
		return this.pay;
	}

	public void setPay(double pay) {
		this.pay = pay;
	}

	public List<Cargo> getCargos() {
		return this.cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo addCargo(Cargo cargo) {
		getCargos().add(cargo);
		cargo.setTransport(this);

		return cargo;
	}

	public Cargo removeCargo(Cargo cargo) {
		getCargos().remove(cargo);
		cargo.setTransport(null);

		return cargo;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}