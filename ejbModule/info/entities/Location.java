package info.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idlocation;

	private String description;

	//bi-directional many-to-one association to Deliveryspecification
	@ManyToOne
	@JoinColumn(name="iddeliverySpecification")
	private Deliveryspecification deliveryspecification;

	public Location() {
	}

	public int getIdlocation() {
		return this.idlocation;
	}

	public void setIdlocation(int idlocation) {
		this.idlocation = idlocation;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Deliveryspecification getDeliveryspecification() {
		return this.deliveryspecification;
	}

	public void setDeliveryspecification(Deliveryspecification deliveryspecification) {
		this.deliveryspecification = deliveryspecification;
	}

}