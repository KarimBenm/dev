package fr.lyes.gds.Buisness.Admin.service.Interfaces;


import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.payload.MenuRequest;
import fr.lyes.gds.Shared.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuService extends GenericService<Menu, MenuDto, Long> {
    public List<Menu> findByMenuCode(Long groupeId);
    public Menu findByMenuLabel(String label);
    public List<Menu> findAllMenu();
    public Menu saveMenu(Menu menuRequest);
    public List<Menu> findAllSousMenu(Long menuId);
    public List<Menu> findSousMenuExclude(Long menuId ,List<Menu> sousMenuList);
    public Page<Menu> findPageLazyMenus(String label, String url, String icon, Boolean parents, int first, int max);
    public Page<Menu> findAndFilterPageLazyMenus(String label, String url, String icon, Boolean parents,int first, int max, String sort ,String field);

}
