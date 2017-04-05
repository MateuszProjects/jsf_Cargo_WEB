package info.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the handlingevent database table.
 * 
 */
@Entity
@NamedQuery(name="Handlingevent.findAll", query="SELECT h FROM Handlingevent h")
public class Handlingevent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idhandlingEvent;

	private String decriptonEvent;

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="idcargo")
	private Cargo cargo;

	public Handlingevent() {
	}

	public int getIdhandlingEvent() {
		return this.idhandlingEvent;
	}

	public void setIdhandlingEvent(int idhandlingEvent) {
		this.idhandlingEvent = idhandlingEvent;
	}

	public String getDecriptonEvent() {
		return this.decriptonEvent;
	}

	public void setDecriptonEvent(String decriptonEvent) {
		this.decriptonEvent = decriptonEvent;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}