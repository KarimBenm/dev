package fr.lyes.gds.Buisness.Produit.service.Interfaces;


import fr.lyes.gds.Buisness.Produit.Data.Dto.SousFamilleDto;
import fr.lyes.gds.Buisness.Produit.Data.Entities.SousFamille;
import fr.lyes.gds.Shared.GenericService;


public interface SousFamilleService extends GenericService<SousFamille, SousFamilleDto, Long> {
    public SousFamilleDto findByNom(String name);

}
