package fr.lyes.gds.Presentation.Controllers.Produit;


import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.ModuleService;
import fr.lyes.gds.Buisness.Produit.Data.Dto.ProduitDto;
import fr.lyes.gds.Buisness.Produit.service.Interfaces.ProduitService;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import fr.lyes.gds.Shared.GenericRestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(RequestConstants.Module_REQUEST_MAPPING_ROOT)
public class ProduitController extends GenericRestController<ProduitService, ProduitDto, Long> {

    @Autowired
    private ProduitService service;
    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 1L;


}
