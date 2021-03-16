package fr.lyes.gds.Presentation.Controllers.Admin;


import fr.lyes.gds.Buisness.Admin.Data.Dto.GroupeDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Buisness.Admin.Data.payload.MenuRequest;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.UserService;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import fr.lyes.gds.Shared.GenericRestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(RequestConstants.Menu_REQUEST_MAPPING_ROOT)
public class MenuController implements Serializable {

    @Autowired
    private MenuService service;
    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private GroupeService groupeService;
    private static final long serialVersionUID = 1L;


    @GetMapping(value = RequestConstants.Menu_Groupes_REQUEST_MAPPING_ROOT)
    public List<Menu> findByMenuCode(@PathVariable(value = "groupeId") Long groupeId) {
        List<MenuDto> menuDtos = new ArrayList<>();
        return service.findByMenuCode(groupeId);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Menu> list() {
        return service.findAllMenu();
    }

    @GetMapping(value = RequestConstants.Menu_SousMenu_REQUEST_MAPPING_ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Menu> listSousMenu(@PathVariable(value = "menuId") Long menuId) {
        return service.findAllSousMenu(menuId);
    }

    @GetMapping(value = RequestConstants.Menu_SousMenu_Disponible_REQUEST_MAPPING_ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Menu> listSousMenuDispo(@PathVariable(value = "menuId") Long menuId) {
        List<Menu> listMenuDispo = new ArrayList<>();
        listMenuDispo = service.findAllSousMenu(menuId);
        System.out.println("dispo"+listMenuDispo.size());
        return service.findSousMenuExclude(menuId, listMenuDispo);
    }

    @GetMapping(value = RequestConstants.Menu_Search_REQUEST_MAPPING_ROOT)
    public Menu findByMenuLabel(@PathVariable(value = "label") String label) {
        return service.findByMenuLabel(label);
    }

    protected <T> Boolean nullCheck(T obj) {
        Optional<T> opt = Optional.ofNullable(obj);
        return opt.isPresent();
    }

    @GetMapping(value = RequestConstants.Pages_REQUEST_MAPPING_ROOT)
    public Page<Menu> findPageMenus(@RequestParam(name = "label", required = false) String label,
                                    @RequestParam(name = "url", required = false) String url,
                                    @RequestParam(name = "icon", required = false) String icon,
                                    @RequestParam(name = "parents", required = false) Boolean parents,
                                    @RequestParam(name = "page") int page,
                                    @RequestParam(name = "size") int size
    ) {
        return service.findPageLazyMenus(label, url, icon, parents, page, size);
    }

    @GetMapping(value = RequestConstants.Sort_Update_REQUEST_MAPPING_ROOT)
    public Page<Menu> findAndSortPageUser(@RequestParam(name = "label", required = false) String label,
                                          @RequestParam(name = "url", required = false) String url,
                                          @RequestParam(name = "icon", required = false) String icon,
                                          @RequestParam(name = "parents", required = false) Boolean parents,
                                          @RequestParam(name = "page") int page,
                                          @RequestParam(name = "size") int size,
                                          @RequestParam(name = "sort", required = false) String sort,
                                          @RequestParam(name = "field", required = false) String field

    ) {
        if (sort == null) {
            sort = "DESC";
        }
        if (field == null) {
            field = "label";
        }
        return service.findAndFilterPageLazyMenus(label, url, icon, parents, page, size, sort, field);
    }

    @PutMapping(value = RequestConstants.Menu_Update_REQUEST_MAPPING_ROOT)
    public @ResponseBody
    Menu update(@RequestBody MenuRequest entity) {
        List<Menu> lmenu = new ArrayList<>();
        MenuDto postDtoo = service.getOne(entity.getId());
        Menu oldMenu = modelMapper.map(postDtoo, Menu.class);
        if (nullCheck(entity.getId())) {
            final List<Menu> lmenuOld = service.findAllSousMenu(entity.getId());
            if (entity.getSousMenuList() != null && !entity.getSousMenuList().isEmpty()) {
                entity.getSousMenuList().forEach(x -> {
                    /*MenuDto postDto = service.getOne(x);
                    Menu menu = modelMapper.map(postDto, Menu.class);*/
                    lmenu.add(x);
                });
            }
            if (!lmenu.isEmpty()) {
                lmenu.forEach(x -> {
                    if (!lmenuOld.contains(x)) {
                        x.setMenu(oldMenu);
                        MenuDto menuDto = modelMapper.map(x, MenuDto.class);
                        service.save(menuDto);
                    }
                });
                oldMenu.setParents(true);
                if (!lmenuOld.isEmpty()) {
                    lmenuOld.forEach(x -> {
                        if (!lmenu.contains(x)) {
                            x.setMenu(null);
                            MenuDto menuDto = modelMapper.map(x, MenuDto.class);
                            service.save(menuDto);
                        }
                    });
                }
            } else {
                if (!lmenuOld.isEmpty()) {
                    lmenuOld.forEach(x -> {
                        x.setMenu(null);
                        MenuDto menuDto = modelMapper.map(x, MenuDto.class);
                        service.save(menuDto);
                    });
                }
            }
            oldMenu.setUrls(entity.getUrls());
            oldMenu.setLabel(entity.getLabel());
            oldMenu.setParents(entity.isParents());
            if (entity.getMenu() != null) {
                Menu menuDto = modelMapper.map(entity.getMenu(), Menu.class);
                oldMenu.setMenu(menuDto);
            }
            return service.saveMenu(oldMenu);
        }
        return null;
    }

}
