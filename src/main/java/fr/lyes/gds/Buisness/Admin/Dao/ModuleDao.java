package fr.lyes.gds.Buisness.Admin.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ModuleDao extends JpaRepository<Module, Long> {
   @Query("select module.groupeList from Module module" +
           "  where module.code = :code")
   public List<Groupe> findByModule(@Param("code")String code);
   public Module findByLabel(String label);
   public Module findByCode(String code);
   @Query("select color from Module")
   public List<String> findAllCouleur();
  @Query("select module from Module  module  order by module.code ASC "
         )
   public List<Module> findAllModules();
}
