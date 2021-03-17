package fr.lyes.gds.Buisness.Admin.Data.Dto;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Shared.ParentDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserTest extends ParentDto<Long> implements Serializable {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String lastName;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String ipAddress;
    @Getter
    @Setter
    private LocalDate dateNaissance;
    @Getter
    @Setter
    private Boolean admin;
    @Getter
    @Setter
    private Boolean valid;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String sexe;

    public UserTest() {
        super();
    }

    public UserTest(@NotNull String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public UserTest(String username, String email, String password) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserTest(String username, String password, String lastName, String firstName, String address, String email) {
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.email = email;
    }
    @Override
    public String toString() {
        return "AppUser [username=" + username + ", lastName=" + lastName + ", firstName=" + firstName + "]";
    }

    @Override
    public String getLabel() {
        return null;
    }
}
