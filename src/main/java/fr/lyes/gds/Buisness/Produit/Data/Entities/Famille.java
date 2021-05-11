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

public class Famille extends Parents<Long> implements Serializable {

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





	public Famille() {
		super();
	}



	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}


	private List<SousFamille> sousFamilleList = new ArrayList<>() ;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "famille", cascade = CascadeType.MERGE, orphanRemoval = true)
	public List<SousFamille> getSousFamilleList() {
		return sousFamilleList;
	}
	public void setSousFamilleList(List<SousFamille> sousFamilleList) {
		this.sousFamilleList = sousFamilleList;
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
		Famille other = (Famille) obj;
		return true;
	}

	@Override
	public String toString() {
		return "Menu [label=" + label + "]";
	}

}
