package fr.lyes.gds.Buisness.Produit.Data.Dto;

import fr.lyes.gds.Shared.ParentDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProduitDto extends ParentDto<Long> implements Serializable {
    @Getter
    @Setter
    private String name;
    @Setter
    private String label;

    @Getter
    @Setter
    private Boolean active;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String codeBarre;

    @Getter
    @Setter
    private byte[] image;

    @Getter
    @Setter
    private Long sousFamilleId;

    @Override
    public String getLabel() {
        return label;
    }
}
