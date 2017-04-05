package info.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the billoflading database table.
 * 
 */
@Entity
@NamedQuery(name="Billoflading.findAll", query="SELECT b FROM Billoflading b")
public class Billoflading implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idbillofLading;

	private String tekst;

	//bi-directional many-to-one association to Cargo
	@ManyToOne
	@JoinColumn(name="idcargo")
	private Cargo cargo;

	public Billoflading() {
	}

	public int getIdbillofLading() {
		return this.idbillofLading;
	}

	public void setIdbillofLading(int idbillofLading) {
		this.idbillofLading = idbillofLading;
	}

	public String getTekst() {
		return this.tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Cargo getCargo() {
		return this.cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}