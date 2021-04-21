package fr.lyes.gds.Presentation.Controllers.Produit;


import fr.lyes.gds.Buisness.Admin.Data.Dto.GroupeDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.payload.GroupeRequest;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.FamilleService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.ModuleService;
import fr.lyes.gds.Buisness.Produit.Data.Dto.FamilleDto;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import fr.lyes.gds.Shared.GenericRestController;
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
public class FamilleController extends GenericRestController<FamilleService, FamilleDto, Long> {


    @Autowired
    private FamilleService service;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 1L;




    protected <T> Boolean nullCheck(T obj) {
        Optional<T> opt = Optional.ofNullable(obj);
        return opt.isPresent();
    }
}
