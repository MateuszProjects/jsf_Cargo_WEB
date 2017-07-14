package info.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the payment database table.
 * 
 */
@Entity
@NamedQuery(name="Payment.findAll", query="SELECT p FROM Payment p")
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idpayment;

	private double amoutn;

	@Temporal(TemporalType.DATE)
	private Date date;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_idusers")
	private User user;

	public Payment() {
	}

	public int getIdpayment() {
		return this.idpayment;
	}

	public void setIdpayment(int idpayment) {
		this.idpayment = idpayment;
	}

	public double getAmoutn() {
		return this.amoutn;
	}

	public void setAmoutn(double amoutn) {
		this.amoutn = amoutn;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}