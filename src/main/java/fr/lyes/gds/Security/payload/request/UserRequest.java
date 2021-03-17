package fr.lyes.gds.Security.payload.request;

import fr.lyes.gds.Buisness.Admin.Data.Dto.UserTest;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserRequest {

    @NotBlank
    private UserTest user;

    @NotBlank
    private List<String> groupesList;

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public UserTest getUser() {
        return user;
    }

    public void setUser(UserTest user) {
        this.user = user;
    }

    public List<String> getGroupesList() {
        return groupesList;
    }

    public void setGroupesList(List<String> groupesList) {
        this.groupesList = groupesList;
    }

    public UserRequest(@NotBlank UserTest user, @NotBlank List<String> groupesList) {
        this.user = user;
        this.groupesList = groupesList;
    }

    public UserRequest(@NotBlank UserTest user, @NotBlank List<String> groupesList, MultipartFile file) {
        this.user = user;
        this.groupesList = groupesList;
        this.file = file;
    }

    public UserRequest() {

    }
}
