package fr.lyes.gds.Buisness.Produit.service.Impl;



import fr.lyes.gds.Buisness.Produit.Dao.ProduitDao;
import fr.lyes.gds.Buisness.Produit.Data.Dto.ProduitDto;
import fr.lyes.gds.Buisness.Produit.Data.Entities.Produit;
import fr.lyes.gds.Buisness.Produit.service.Interfaces.ProduitService;
import fr.lyes.gds.Shared.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProduitServiceImpl  extends GenericServiceImpl<ProduitDao, Produit,ProduitDto, Long> implements ProduitService {
    private static final long serialVersionUID = 1L;

}
