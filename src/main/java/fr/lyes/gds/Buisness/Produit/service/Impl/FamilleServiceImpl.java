package fr.lyes.gds.Buisness.Produit.service.Impl;

import fr.lyes.gds.Buisness.Admin.Repository.MenuRepo;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.FamilleService;
import fr.lyes.gds.Buisness.Produit.Dao.FamilleDao;
import fr.lyes.gds.Buisness.Produit.Data.Dto.FamilleDto;
import fr.lyes.gds.Buisness.Produit.Data.Entities.Famille;
import fr.lyes.gds.Shared.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FamilleServiceImpl extends GenericServiceImpl<FamilleDao, Famille,FamilleDto, Long> implements FamilleService {

	private static final long serialVersionUID = 1L;

	@Autowired
	MenuRepo menuRepo;

	@Autowired
	private FamilleDao dao;


}
