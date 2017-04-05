package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the deliveryspecification database table.
 * 
 */
@Entity
@NamedQuery(name="Deliveryspecification.findAll", query="SELECT d FROM Deliveryspecification d")
public class Deliveryspecification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iddeliverySpecification;

	@Temporal(TemporalType.DATE)
	private Date arrivaltime;

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="idcargo")
	private Cargo cargo;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="deliveryspecification")
	private List<Location> locations;

	public Deliveryspecification() {
	}

	public int getIddeliverySpecification() {
		return this.iddeliverySpecification;
	}

	public void setIddeliverySpecification(int iddeliverySpecification) {
		this.iddeliverySpecification = iddeliverySpecification;
	}

	public Date getArrivaltime() {
		return this.arrivaltime;
	}

	public void setArrivaltime(Date arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Location addLocation(Location location) {
		getLocations().add(location);
		location.setDeliveryspecification(this);

		return location;
	}

	public Location removeLocation(Location location) {
		getLocations().remove(location);
		location.setDeliveryspecification(null);

		return location;
	}

}