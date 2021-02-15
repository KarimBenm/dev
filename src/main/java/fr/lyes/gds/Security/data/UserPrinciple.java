package fr.lyes.gds.Security.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(UserPrinciple.class);
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilImage;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String username, String password,String email,String firstName, String lastName,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }


    public UserPrinciple(Long id, String username, String firstName, String lastName, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(UserDto user) {
        if(user== null){
            logger.error("user is null");
        }
        if(user.getId()== null){
            logger.error("id is null");
        }
        if(user.getAppGroupeList()== null){
            logger.error("groupes is null");
        }
        if(user.getAppGroupeList().isEmpty()){
            logger.error("User has no groupes ");
        }
        if(user.getUsername()== null){
            logger.error("username is null");
        }
        if(user.getFirstName()== null){
            logger.error("user.getFirstName() is null");
        }
        if(user.getFirstName()== null){
            logger.error("user.getFirstName() is null");
        }
        if(user.getLastName()== null){
            logger.error("user.getLastName() is null");
        }
        if(user.getPassword()== null){
            logger.error("user.getPassword() is null");
        }

        List<GrantedAuthority> authorities = user.getAppGroupeList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
        return new UserPrinciple(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String email) {
        this.email = email;
    }
    public String getEmail() {
        return firstName;
    }

    public String getImageProfile() {
        return profilImage;
    }

    public void setImageProfile(String imageProfile) {
        this.profilImage = imageProfile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserPrinciple other = (UserPrinciple) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
