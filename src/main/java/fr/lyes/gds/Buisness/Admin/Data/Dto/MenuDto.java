package fr.lyes.gds.Buisness.Admin.Data.Dto;


import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Shared.ParentDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto extends ParentDto<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Setter
	private String label;

	@Getter
	@Setter
	private String routers;

	@Getter
	@Setter
	private String urls;

	@Getter
	@Setter
	private String icon;

	@Getter
	@Setter
	private String color;

	@Getter
	@Setter
	private boolean parents;

	@Getter
	@Setter
	private List<GroupeDto> menuGroupe = new ArrayList<>();

	@Getter
	@Setter
	private MenuDto menu;
//	
//	@Getter
//	@Setter
//	private List<MenuDto> childs = new ArrayList<>();

	@Override
	public String getLabel() {
		return label;
	}

}
