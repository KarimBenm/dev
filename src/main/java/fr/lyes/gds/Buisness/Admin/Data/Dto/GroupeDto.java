package fr.lyes.gds.Buisness.Admin.Data.Dto;

import fr.lyes.gds.Shared.ParentDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupeDto extends ParentDto<Long> implements Serializable {
    @Getter
    @Setter
    private String code;
    @Setter
    private String label;

    @Getter
    @Setter
    private Boolean active;
 /*   @Getter
    @Setter
    private List<UserDto> appUsers = new ArrayList<>();*/
    @Getter
    @Setter
    private List<MenuDto> menuGroupe = new ArrayList<>();

    public GroupeDto() {
        super();
    }

    public GroupeDto(@NotNull String code) {
        this.code = code;
    }

    public GroupeDto(@NotNull String code, @NotNull ModuleDto module) {
        this.code = code;
        this.module = module;
    }
    @Getter
    @Setter
    private ModuleDto module;

    @Override
    public String getLabel() {
        return label;
    }
}
