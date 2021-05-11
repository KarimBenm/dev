package fr.lyes.gds.Buisness.Admin.service.Impl;

import fr.lyes.gds.Buisness.Admin.Dao.MenuDao;
import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Repository.MenuRepo;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import fr.lyes.gds.Shared.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl extends GenericServiceImpl<MenuDao, Menu,MenuDto, Long> implements MenuService {

	private static final long serialVersionUID = 1L;

	@Autowired
	MenuRepo menuRepo;

	@Autowired
	private MenuDao dao;

	@Override
	public List<Menu> findByMenuCode(Long groupeId) {
		return dao.findByMenuCode(groupeId);
	}

	@Override
	public Menu saveMenu(Menu menuRequest) {
		 return dao.save(menuRequest);
	}

	@Override
	public Menu findByMenuLabel(String label) {
		return  dao.findByLabel(label);
	}

	@Override
	public List<Menu> findAllMenu() {
		return  dao.findAll();
	}

	@Override
	public List<Menu> findAllSousMenu(Long menuId) {
		List<Menu> listSousMenu = new ArrayList<>();
		 listSousMenu = dao.findAllSousMenu(menuId);
		return listSousMenu;
	}

	@Override
	public List<Menu> findSousMenuExclude(Long menuId, List<Menu> sousMenuList) {
		List<Menu> listSousMenu = new ArrayList<>();
		listSousMenu = dao.findAllSousMenu(menuId);
		listSousMenu.add(dao.findById(menuId).get());
		List<Menu> listSousMenuDispo = new ArrayList<>();
		listSousMenuDispo = dao.findSousMenuExclude(listSousMenu);
		//listSousMenuDispo.remove(dao.findById(menuId).get());
		return listSousMenuDispo;
	}

	@Override
	public Page<Menu> findPageLazyMenus(String label, String url, String icon, Boolean parents, int first, int max) {
		Page<Menu> menuList = menuRepo.findPageLazyMenus(label, url, icon, parents, first, max);
		return menuList;
	}

	@Override
	public Page<Menu> findAndFilterPageLazyMenus(String label, String url, String icon, Boolean parents, int first, int max, String sort, String field) {
		Page<Menu> menuList = menuRepo.findAndFilterPageLazyMenus(label, url, icon, parents, first, max, sort, field);
		return menuList;
	}
}
