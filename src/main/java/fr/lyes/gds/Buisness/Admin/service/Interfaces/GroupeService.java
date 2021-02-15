package fr.lyes.gds.Buisness.Admin.service.Interfaces;


import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupeService {

	public Groupe findByLabel(String label);
	public void addMenuToGroupe(Groupe groupe, Menu  menu);
	public Groupe findByCode(String code);
	public List<User> findUsersOfGroupe(String code);
	public Module findModuleByCode(String code);
	public List<Module> findGroupeOfUser (String username);
	public List<Groupe> findDispoGroupe(String username);
	public List<Menu> findMenuExclude(String code);
	public Page<Groupe> findPageLazyGroupes(String code, String label, Boolean active, int first, int max);
	public Page<Groupe> findAndFilterPageLazyGroupes(String code, String label, Boolean active, int first, int max, String sort ,String field);

	List<Groupe> findAll();

	Groupe save(Groupe entity);

	void deleteById(Long id);

	Groupe getOne(Long id);

	Long count();
}
