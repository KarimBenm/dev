package fr.lyes.gds.Presentation.Controllers.Admin;


import fr.lyes.gds.Buisness.Admin.Data.Dto.GroupeDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.ModuleService;
import fr.lyes.gds.Buisness.Admin.Data.payload.GroupeRequest;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(RequestConstants.Groupe_REQUEST_MAPPING_ROOT)
public class GroupeController implements Serializable {


    @Autowired
    private GroupeService service;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 1L;


    @GetMapping(value = RequestConstants.Groupe_Find_By_Code_REQUEST_MAPPING_ROOT)
    public Groupe findByGroupeCode(@PathVariable(value = "codeGroupe") String codeGroupe) {
        return service.findByCode(codeGroupe);
    }

    @GetMapping(value = RequestConstants.Groupe_ALL_REQUEST_MAPPING_ROOT)
    public List<Groupe> findAllGroupes() {
        return service.findAll();
    }

    @PutMapping(value = RequestConstants.Groupe_Update_REQUEST_MAPPING_ROOT)
    public @ResponseBody
    Groupe update(@RequestBody Groupe entity) {
        List<Menu> lmenu = new ArrayList<>();
        if (nullCheck(entity.getId())) {
            lmenu.addAll(entity.getMenuGroupe());
            Groupe gpe = service.findByLabel(entity.getLabel());
            lmenu.forEach(x -> {
                if (!gpe.getMenuGroupe().contains(x)) {
                    Menu menu = menuService.findByMenuLabel(x.getLabel());
                    gpe.getMenuGroupe().add(menu);
                    menu.getMenuGroupeList().add(gpe);
                }
            });
            gpe.getMenuGroupe().forEach(y -> {
                if (!lmenu.contains(y)) {
                    Menu menu = menuService.findByMenuLabel(y.getLabel());
                    gpe.getMenuGroupe().remove(menu);
                    menu.getMenuGroupeList().remove(gpe);
                }
            });
            return service.save(gpe);
        }
        return null;
    }

    @GetMapping(value = RequestConstants.Pages_REQUEST_MAPPING_ROOT)
    public Page<Groupe> findPageGroupe(@RequestParam(name = "code", required = false) String code,
                                       @RequestParam(name = "label", required = false) String label,
                                       @RequestParam(name = "active", required = false) Boolean active,
                                       @RequestParam(name = "page") int page,
                                       @RequestParam(name = "size") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Groupe> mod = service.findPageLazyGroupes(code, label, active, page, size);
        return mod;
    }

    @GetMapping(value = RequestConstants.Groupe_Disponible_REQUEST_MAPPING_ROOT)
    public List<Groupe> findDispoGroupes(@PathVariable(value = "userName") String userName) {
        List<GroupeDto> groupeDtos = new ArrayList<>();
        List<Groupe> groupes = new ArrayList<>();
        groupes = service.findDispoGroupe(userName);
        /*if (groupes != null && !groupes.isEmpty())
            groupes.forEach(x -> {
                GroupeDto postDto = modelMapper.map(x, GroupeDto.class);
                groupeDtos.add(postDto);
            });*/
        return groupes;
    }

    @GetMapping(value = RequestConstants.Sort_Update_REQUEST_MAPPING_ROOT)
    public Page<Groupe> findAndSortPageGroupe(@RequestParam(name = "code", required = false) String code,
                                              @RequestParam(name = "label", required = false) String label,
                                              @RequestParam(name = "active", required = false) Boolean active,
                                              @RequestParam(name = "page") int page,
                                              @RequestParam(name = "size") int size,
                                              @RequestParam(name = "sort", required = false) String sort,
                                              @RequestParam(name = "field", required = false) String field

    ) {
        if (sort == null) {
            sort = "DESC";
        }
        if (field == null) {
            field = "code";
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Groupe> mod = service.findAndFilterPageLazyGroupes(code, label, active, page, size, sort, field);
        return mod;
        // return service.findAndFilterPageLazyGroupes(code, label, active, page, size, sort, field);
    }

    @GetMapping(value = RequestConstants.Groupe_Testing_REQUEST_MAPPING_ROOT)
    public List<Menu> findPageTest(@PathVariable(value = "code") String code) {
        return service.findMenuExclude(code);
    }

    @GetMapping(value = RequestConstants.Groupe_Size_REQUEST_MAPPING_ROOT)
    public int findUsersOfGroupe(@PathVariable(value = "code") String code) {
        return service.findUsersOfGroupe(code).size();
    }

    @PostMapping(value = RequestConstants.Groupe_Create_REQUEST_MAPPING_ROOT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Groupe createGroupe(@RequestBody GroupeRequest entity) {
        Groupe newGroupe = new Groupe();
        newGroupe.setActive(entity.isActive());
        newGroupe.setCode(entity.getCode());
        newGroupe.setLabel(entity.getLabel());
        newGroupe.setMenuGroupe(new ArrayList<>());
        newGroupe.setAppUsers(new ArrayList<>());
        if (entity.getMenusList() != null && !entity.getMenusList().isEmpty()) {
            entity.getMenusList().forEach((x -> {
                MenuDto menu = menuService.getOne(x);
                Menu postDto = modelMapper.map(menu, Menu.class);
                postDto.getMenuGroupeList().add(newGroupe);
                newGroupe.getMenuGroupe().add(postDto);
            }));
        }
        if (entity.getModuleId() != null) {
            ModuleDto module = moduleService.getOne(entity.getModuleId());
            Module m = modelMapper.map(module, Module.class);
            newGroupe.setModule(m);
            m.getGroupeList().add(newGroupe);
        }
        return service.save(newGroupe);
    }

    protected <T> Boolean nullCheck(T obj) {
        Optional<T> opt = Optional.ofNullable(obj);
        return opt.isPresent();
    }
}
