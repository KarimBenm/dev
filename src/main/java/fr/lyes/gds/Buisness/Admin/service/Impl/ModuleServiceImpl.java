package fr.lyes.gds.Buisness.Admin.service.Impl;


import fr.lyes.gds.Buisness.Admin.Dao.ModuleDao;
import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Repository.ModuleRepo;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.ModuleService;
import fr.lyes.gds.Shared.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl extends GenericServiceImpl<ModuleDao, Module,ModuleDto, Long> implements ModuleService {

	private static final long serialVersionUID = 1L;
	@Autowired
	ModuleRepo moduleRepo;
	@Override
	public Module findByLabel(String label) {
		return dao.findByLabel(label);
	}

	@Override
	public Module findByCode(String code) {
		return dao.findByCode(code);
	}

	@Override
	public List<Groupe> findByModule(String code) {
			List<Groupe> groupeList = new ArrayList<>();
		groupeList = dao.findByModule(code);
			return groupeList;
	}



	@Override
	public List<String> findAllCouleur() {
		return dao.findAllCouleur();
	}

	@Override
	public List<Module> findHiberanteModule(String code,String label,String name, int first, int max) {
		List<Module> modList = new ArrayList<>();
        modList = moduleRepo.findLazyModules(code,label,name,first,max);
		return modList;
	}

	@Override
	public Page<Module> findPageLazyModules(String code, String label, String name, int first, int max) {
		Page<Module> modList = moduleRepo.findPageLazyModules(code,label,name,first,max);
		return modList;
	}

	@Override
	public Page<Module> findAndFilterPageLazyModules(String code, String label, String name, int first, int max, String sort,String field) {
		Page<Module> modList = moduleRepo.findAndFilterPageLazyModules(code,label,name,first,max,sort,field);
		return modList;
	}


}
