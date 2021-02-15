package fr.lyes.gds.Buisness.Admin.Data.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lyes.gds.Shared.Parents;
import fr.lyes.gds.helpers.DBSchemaConstants;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module", schema = DBSchemaConstants.ADMIN_DB_SCHEMA)
	public class Module extends Parents<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "code", unique = true)
	@NotNull
	private String code;

	@Column(name = "label")
	private String label;

	@Column(name = "icon")
	private String icon;

	@Column(name = "color")
	private String color;

	@Column(name = "urls")
	private String urls;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "col")
	private long col;

	@Column(name = "raw")
	private long raw;

	@JsonIgnoreProperties("module")
	private List<Groupe> groupeList = new ArrayList<>() ;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "module", cascade = CascadeType.MERGE, orphanRemoval = true)

	public List<Groupe> getGroupeList() {
		return groupeList;
	}

	public void setGroupeList(List<Groupe> groupeList) {
		this.groupeList = groupeList;
	}


	public Module() {
		super();
	}

	public Module(String label,String code) {
		super();
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getCol() {
		return col;
	}

	public void setCol(long col) {
		this.col = col;
	}

	public long getRaw() {
		return raw;
	}

	public void setRaw(long raw) {
		this.raw = raw;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
