package fr.lyes.gds.Buisness.Produit.Data.Dto;


import fr.lyes.gds.Shared.ParentDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FamilleDto extends ParentDto<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Setter
	private String name;

	@Getter
	@Setter
	private String label;

	@Getter
	@Setter
	private boolean active;


	@Getter
	@Setter
	private String description;



	@Getter
	@Setter
	private List<Long> sousFamilleIds;

	@Override
	public String getLabel() {
		return label;
	}

}
