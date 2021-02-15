package fr.lyes.gds.Buisness.Admin.Data.Entities;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import fr.lyes.gds.Buisness.Admin.Data.Dto.UserTest;
import fr.lyes.gds.helpers.DBSchemaConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user", schema = DBSchemaConstants.ADMIN_DB_SCHEMA)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @Column(name = "password")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = Access.WRITE_ONLY)
    private String confirmPassword;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "address")
    private String address;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "email")
    private String email;

    @Column(name = "sexe")
    private String sexe;

    @Lob
    @Column(name = "profil_image")
    private byte[] profilImage;

    private List<Groupe> appGroupeList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
        super();
    }

    public User(@NotNull String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public User(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String password, String lastName, String firstName, String address, String email,byte[] profilImage) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.email = email;
        this.profilImage = profilImage;
    }
    public User(UserTest userTest) {
        this.username = userTest.getUsername();
        this.password = userTest.getPassword();
        this.lastName = userTest.getLastName();
        this.firstName = userTest.getFirstName();
        this.address = userTest.getAddress();
        this.email = userTest.getEmail();
        this.profilImage = userTest.getProfilImage();
        this.dateNaissance = userTest.getDateNaissance();
        this.sexe = userTest.getSexe();
        this.valid = userTest.getValid();
    }
    public User(String username, String lastName, String firstName, String address, String email ,byte[] profilImage) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.email = email;
        this.profilImage = profilImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Transient
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JoinTable(name = "user_groupe", schema = DBSchemaConstants.ADMIN_DB_SCHEMA, joinColumns = {
            @JoinColumn(name = "user_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "groupe_id", nullable = false)})
    public List<Groupe> getAppGroupeList() {
        return appGroupeList;
    }

    public void setAppGroupeList(List<Groupe> appGroupeList) {
        this.appGroupeList = appGroupeList;
    }


    @Override
    public String toString() {
        return "AppUser [username=" + username + ", lastName=" + lastName + ", firstName=" + firstName + "]";
    }

}
