package fr.lyes.gds.Buisness.Admin.service.Interfaces;

import fr.lyes.gds.Buisness.Admin.Data.Dto.ModuleDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Shared.GenericService;
import org.springframework.data.domain.Page;

import java.util.List;


public interface ModuleService extends GenericService<Module, ModuleDto, Long> {
    public Module findByLabel(String label);
    public Module findByCode(String code);
    public List<Groupe> findByModule(String code);
    public List<String> findAllCouleur();
    public List<Module> findHiberanteModule(String code,String label,String name, int first, int max);
    public Page<Module> findPageLazyModules(String code, String label, String name, int first, int max);
    public Page<Module> findAndFilterPageLazyModules(String code, String label, String name, int first, int max, String sort ,String field);
}
