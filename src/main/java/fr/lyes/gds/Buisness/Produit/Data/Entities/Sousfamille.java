package fr.lyes.gds.Buisness.Produit.Data.Entities;

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
@Table(name = "sousFamille", schema = DBSchemaConstants.PRODUIT_DB_SCHEMA)

public class SousFamille extends Parents<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Column(name = "name", unique = true)
	@NotNull
	private String name;

	@Getter
	@Setter
	@Column(name = "label")
	private String label;

	@Getter
	@Setter
	@Column(name = "active")
	private Boolean active;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@Getter
	@Setter
	@Column(name = "description")
	private String description;



	private Famille famille;

	public SousFamille() {
		super();
	}



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}



	@ManyToOne
	@JoinColumn(name = "famille_id")
	public Famille getFamille() {
		return famille;
	}
	public void setFamille(Famille famille) {
		this.famille = famille;
	}

	private List<Produit> produitList = new ArrayList<>() ;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "sousFamille", cascade = CascadeType.MERGE, orphanRemoval = true)
	public List<Produit> getProduitList() {
		return produitList;
	}
	public void setProduitList(List<Produit> produitList) {
		this.produitList = produitList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SousFamille other = (SousFamille) obj;

		return true;
	}

	@Override
	public String toString() {
		return "Menu [label=" + label + "]";
	}

}
