package fr.lyes.gds.Buisness.Admin.Data.Entities;

import com.fasterxml.jackson.annotation.*;
import fr.lyes.gds.Shared.Parents;
import fr.lyes.gds.helpers.DBSchemaConstants;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groupe", schema = DBSchemaConstants.ADMIN_DB_SCHEMA)

public class Groupe extends Parents<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "code", unique = true)
	@NotNull
	private String code;

	@Column(name = "label")
	private String label;

	@Column(name = "active")
	private Boolean active;

	private List<User> appUsers = new ArrayList<>();

	@JsonIgnoreProperties("menuGroupeList")
	private List<Menu> menuGroupe = new ArrayList<>();



	public Groupe() {
		super();
	}

	public Groupe(@NotNull String code) {
		this.code = code;
	}

	public Groupe(@NotNull String code, @NotNull Module module) {
		this.code = code;
		this.module = module;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "appGroupeList", cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonIgnore
	public List<User> getAppUsers() {
		return appUsers;
	}
	public void setAppUsers(List<User> appUsers) {
		this.appUsers = appUsers;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "menuGroupeList",cascade = { CascadeType.MERGE })
	@JsonIgnoreProperties("menuGroupeList")
	public List<Menu> getMenuGroupe() {
		return menuGroupe;
	}

	public void setMenuGroupe(List<Menu> menuGroupe) {
		this.menuGroupe = menuGroupe;
	}
	@JsonIgnoreProperties("groupeList")
	private Module module;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

}
