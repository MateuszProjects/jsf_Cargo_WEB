package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the deliveryhistory database table.
 * 
 */
@Entity
@NamedQuery(name="Deliveryhistory.findAll", query="SELECT d FROM Deliveryhistory d")
public class Deliveryhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddeliveryHistory;

	@Temporal(TemporalType.DATE)
	private Date arrivayDate;

	@Temporal(TemporalType.DATE)
	private Date leaveDate;

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="idcargo")
	private Cargo cargo;

	public Deliveryhistory() {
	}

	public int getIddeliveryHistory() {
		return this.iddeliveryHistory;
	}

	public void setIddeliveryHistory(int iddeliveryHistory) {
		this.iddeliveryHistory = iddeliveryHistory;
	}

	public Date getArrivayDate() {
		return this.arrivayDate;
	}

	public void setArrivayDate(Date arrivayDate) {
		this.arrivayDate = arrivayDate;
	}

	public Date getLeaveDate() {
		return this.leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}