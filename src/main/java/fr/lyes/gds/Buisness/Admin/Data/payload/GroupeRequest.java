package fr.lyes.gds.Buisness.Admin.Data.payload;

import fr.lyes.gds.Buisness.Admin.Data.Dto.UserTest;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class GroupeRequest {

    @NotBlank
    private String code;
    @NotBlank
    private String label;

    private boolean active;

    @NotBlank
    private List<Long> menusList;

    @NotBlank
    private Long moduleId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Long> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Long> menusList) {
        this.menusList = menusList;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public GroupeRequest() {

    }
}
