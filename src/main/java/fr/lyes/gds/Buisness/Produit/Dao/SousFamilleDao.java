package fr.lyes.gds.Buisness.Produit.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Produit.Data.Entities.SousFamille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SousFamilleDao extends JpaRepository<SousFamille, Long> {
   @Query("select sousFamille from SousFamille sousFamille" +
           "  where sousFamille.name = :name")
   public SousFamille findByNom(@Param("name")String name);
}
