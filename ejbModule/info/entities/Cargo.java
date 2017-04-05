package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cargo database table.
 * 
 */
@Entity
@NamedQuery(name="Cargo.findAll", query="SELECT c FROM Cargo c")
public class Cargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idcargo;

	private String hazMat;

	private String role;

	private int weight;

	//bi-directional many-to-one association to Billoflading
	@OneToMany(mappedBy="cargo")
	private List<Billoflading> billofladings;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="idcustomer")
	private Customer customer;

	//bi-directional many-to-one association to Transport
	@ManyToOne
	@JoinColumn(name="idtransport")
	private Transport transport;

	//bi-directional many-to-one association to Deliveryhistory
	@OneToMany(mappedBy="cargo")
	private List<Deliveryhistory> deliveryhistories;

	//bi-directional many-to-one association to Deliveryspecification
	@OneToMany(mappedBy="cargo")
	private List<Deliveryspecification> deliveryspecifications;

	//bi-directional many-to-one association to Handlingevent
	@OneToMany(mappedBy="cargo")
	private List<Handlingevent> handlingevents;

	public Cargo() {
	}

	public int getIdcargo() {
		return this.idcargo;
	}

	public void setIdcargo(int idcargo) {
		this.idcargo = idcargo;
	}

	public String getHazMat() {
		return this.hazMat;
	}

	public void setHazMat(String hazMat) {
		this.hazMat = hazMat;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<Billoflading> getBillofladings() {
		return this.billofladings;
	}

	public void setBillofladings(List<Billoflading> billofladings) {
		this.billofladings = billofladings;
	}

	public Billoflading addBilloflading(Billoflading billoflading) {
		getBillofladings().add(billoflading);
		billoflading.setCargo(this);

		return billoflading;
	}

	public Billoflading removeBilloflading(Billoflading billoflading) {
		getBillofladings().remove(billoflading);
		billoflading.setCargo(null);

		return billoflading;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Transport getTransport() {
		return this.transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public List<Deliveryhistory> getDeliveryhistories() {
		return this.deliveryhistories;
	}

	public void setDeliveryhistories(List<Deliveryhistory> deliveryhistories) {
		this.deliveryhistories = deliveryhistories;
	}

	public Deliveryhistory addDeliveryhistory(Deliveryhistory deliveryhistory) {
		getDeliveryhistories().add(deliveryhistory);
		deliveryhistory.setCargo(this);

		return deliveryhistory;
	}

	public Deliveryhistory removeDeliveryhistory(Deliveryhistory deliveryhistory) {
		getDeliveryhistories().remove(deliveryhistory);
		deliveryhistory.setCargo(null);

		return deliveryhistory;
	}

	public List<Deliveryspecification> getDeliveryspecifications() {
		return this.deliveryspecifications;
	}

	public void setDeliveryspecifications(List<Deliveryspecification> deliveryspecifications) {
		this.deliveryspecifications = deliveryspecifications;
	}

	public Deliveryspecification addDeliveryspecification(Deliveryspecification deliveryspecification) {
		getDeliveryspecifications().add(deliveryspecification);
		deliveryspecification.setCargo(this);

		return deliveryspecification;
	}

	public Deliveryspecification removeDeliveryspecification(Deliveryspecification deliveryspecification) {
		getDeliveryspecifications().remove(deliveryspecification);
		deliveryspecification.setCargo(null);

		return deliveryspecification;
	}

	public List<Handlingevent> getHandlingevents() {
		return this.handlingevents;
	}

	public void setHandlingevents(List<Handlingevent> handlingevents) {
		this.handlingevents = handlingevents;
	}

	public Handlingevent addHandlingevent(Handlingevent handlingevent) {
		getHandlingevents().add(handlingevent);
		handlingevent.setCargo(this);

		return handlingevent;
	}

	public Handlingevent removeHandlingevent(Handlingevent handlingevent) {
		getHandlingevents().remove(handlingevent);
		handlingevent.setCargo(null);

		return handlingevent;
	}

}