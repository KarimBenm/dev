package fr.lyes.gds.Buisness.Produit.Data.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.lyes.gds.Shared.Parents;
import fr.lyes.gds.helpers.DBSchemaConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produit", schema = DBSchemaConstants.PRODUIT_DB_SCHEMA)

public class Produit extends Parents<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "name", unique = true)
	@NotNull
	private String name;

	@Column(name = "label")
	private String label;

	@Column(name = "active")
	private Boolean active;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@Getter
	@Setter
	@Column(name = "description")
	private String description;

	@Getter
	@Setter
	@Column(name = "code_Barre")
	private String codeBarre;


	private SousFamille sousFamille;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sousFamille_id")
	public SousFamille getSousFamille() {
		return sousFamille;
	}

	public void setSousFamille(SousFamille sousFamille) {
		this.sousFamille = sousFamille;
	}
	public Produit() {
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
