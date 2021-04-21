package fr.lyes.gds.Buisness.Admin.service.Interfaces;


import fr.lyes.gds.Buisness.Admin.Data.Dto.MenuDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.payload.MenuRequest;
import fr.lyes.gds.Buisness.Produit.Data.Dto.FamilleDto;
import fr.lyes.gds.Buisness.Produit.Data.Entities.Famille;
import fr.lyes.gds.Shared.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilleService extends GenericService<Famille,FamilleDto, Long> {

}
