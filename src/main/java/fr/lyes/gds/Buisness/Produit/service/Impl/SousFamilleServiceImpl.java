package fr.lyes.gds.Buisness.Produit.service.Impl;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Produit.service.Interfaces.SousFamilleService;
import fr.lyes.gds.Buisness.Produit.Dao.FamilleDao;
import fr.lyes.gds.Buisness.Admin.Repository.MenuRepo;
import fr.lyes.gds.Buisness.Produit.Dao.SousFamilleDao;
import fr.lyes.gds.Buisness.Produit.Data.Dto.SousFamilleDto;
import fr.lyes.gds.Buisness.Produit.Data.Entities.SousFamille;
import fr.lyes.gds.Shared.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SousFamilleServiceImpl extends GenericServiceImpl<SousFamilleDao, SousFamille,SousFamilleDto, Long> implements SousFamilleService {

	private static final long serialVersionUID = 1L;


	@Autowired
	private SousFamilleDao dao;

	@Override
	public SousFamilleDto findByNom(String name) {
		SousFamilleDto postDto = modelMapper.map(dao.findByNom(name), SousFamilleDto.class);
		return postDto;
	}
}
