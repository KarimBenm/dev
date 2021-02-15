package fr.lyes.gds.Buisness.Admin.Data.Dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Shared.ParentDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto extends ParentDto<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Setter
	private String label ;

	@Getter
	@Setter
	private String code;

	@Getter
	@Setter
	private String name;
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
	private List<Groupe> groupeList = new ArrayList<>();

	public ModuleDto(String label , String code , String name) {
		this.label = label;
		this.code = code;
		this.name = name;
	}
	public ModuleDto(Module module) {
		this.label =  module.getLabel();
		this.code =  module.getCode();
		this.name =  module.getName();
		this.color = module.getColor();
		this.icon = module.getIcon();
		this.urls = module.getUrls();
		this.groupeList = module.getGroupeList();
		this.id = module.getId();

	}

	@Override

	public String getLabel() {
		return label;
	}

}
