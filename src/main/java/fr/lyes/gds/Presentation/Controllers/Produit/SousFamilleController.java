package fr.lyes.gds.Presentation.Controllers.Produit;


import fr.lyes.gds.Buisness.Produit.Data.Dto.SousFamilleDto;
import fr.lyes.gds.Buisness.Produit.service.Interfaces.SousFamilleService;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import fr.lyes.gds.Shared.GenericRestController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping(RequestConstants.Sous_Famille_REQUEST_MAPPING_ROOT)
public class SousFamilleController extends GenericRestController<SousFamilleService, SousFamilleDto, Long> {

    @Autowired
    private SousFamilleService service;

    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 1L;

    @GetMapping(value = RequestConstants.Sort_Update_REQUEST_MAPPING_ROOT)
    public List<SousFamilleDto> findAllModule(@RequestParam (name = "code",required = false) String code,
                                      @RequestParam(name = "label",required = false) String label,
                                      @RequestParam (name = "name", required = false) String name,
                                      @RequestParam (name = "page") String page,
                                      @RequestParam(name = "size") String size
                                     ) {
         int f = Integer.parseInt(page);
         int m = Integer.parseInt(size);
        PageRequest pageRequest = PageRequest.of(f, m);
      return service.findAll(pageRequest);
    }

    @GetMapping(value = RequestConstants.Sous_Famille_find_ONE_REQUEST_MAPPING_ROOT)
    public SousFamilleDto findOneSousFamille(@PathVariable(value = "name") String name) {
        return service.findByNom(name);
    }

}
