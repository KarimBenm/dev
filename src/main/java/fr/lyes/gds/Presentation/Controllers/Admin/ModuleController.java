package fr.lyes.gds.Presentation.Controllers.Admin;


import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.ModuleService;
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
public class ModuleController  extends GenericRestController<ModuleService, ModuleDto, Long> {

    @Autowired
    private ModuleService service;
    @Autowired
    private ModelMapper modelMapper;

    private static final long serialVersionUID = 1L;

    @GetMapping(value = RequestConstants.Module_Get_Couleurs_REQUEST_MAPPING_ROOT)
    public List<String> findAllCouleur() {
        return service.findAllCouleur();
    }
    @GetMapping(value = "/groupes/{code}")
    public List<Groupe> findGroupeByModule(@PathVariable(value = "code")String code) {
        return service.findByModule(code);
    }
    @GetMapping(value = RequestConstants.Module_REQUEST_MAPPING_ROOT)
    public List<ModuleDto> findAllModule(@RequestParam (name = "code",required = false) String code,
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
    @GetMapping(value = RequestConstants.Pages_REQUEST_MAPPING_ROOT)
    public Page<ModuleDto> findPageModule(@RequestParam (name = "code",required = false) String code,
                                         @RequestParam(name = "label",required = false) String label,
                                         @RequestParam (name = "name", required = false) String name,
                                         @RequestParam (name = "page") int page,
                                         @RequestParam(name = "size") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Module> mod = service.findPageLazyModules(code,label,name,page,size);
        List<ModuleDto> moduleDtos = mod
                .stream()
                .map(ModuleDto->new ModuleDto(ModuleDto))
                .collect(Collectors.toList());

        return new PageImpl<ModuleDto>(moduleDtos, pageRequest, mod.getTotalElements());
    }
    @GetMapping(value = RequestConstants.Sort_Update_REQUEST_MAPPING_ROOT)
    public Page<ModuleDto> findAndSortPageModule(@RequestParam (name = "code",required = false) String code,
                                          @RequestParam(name = "label",required = false) String label,
                                          @RequestParam (name = "name", required = false) String name,
                                          @RequestParam (name = "page") int page,
                                          @RequestParam(name = "size") int size,
                                           @RequestParam (name = "sort", required = false)String sort,
                                          @RequestParam (name = "field", required = false) String field

    ) {
        if(sort == null){
            sort = "DESC";
        }
        if(field == null){
            field = "code";
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Module> mod = service.findAndFilterPageLazyModules(code,label,name,page,size,sort,field);
        List<ModuleDto> moduleDtos = mod
                .stream()
                .map(ModuleDto->new ModuleDto(ModuleDto))
                .collect(Collectors.toList());
        return new PageImpl<ModuleDto>(moduleDtos, pageRequest, mod.getTotalElements());
    }
   // protected ModuleDto convertToDto(Module entity) {
     //   ModuleDto dto = modelMapper.map(entity,
      //          (Class<ModuleDto>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2]);
      //  return dto;
    //}
   @PutMapping(value = RequestConstants.Module_Update_REQUEST_MAPPING_ROOT,consumes = MediaType.APPLICATION_JSON_VALUE)
   public ModuleDto update(@RequestBody Module entity) {
       ModuleDto postDto = modelMapper.map(entity, ModuleDto.class);
       // ModuleDto mdt = new ModuleDto(entity);
           return service.save(postDto);
    }
}
