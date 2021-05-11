package fr.lyes.gds.Buisness.Admin.service.Impl;


import fr.lyes.gds.Buisness.Admin.Dao.GroupeDao;
import fr.lyes.gds.Buisness.Admin.Dao.UserDao;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Buisness.Admin.Repository.GroupeRepo;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {

    @Autowired
    private GroupeDao dao;

    @Autowired
    GroupeRepo groupeRepo;

    @Autowired
    private UserDao userdao;

    @Override
    public Groupe findByLabel(String label) {
        return dao.findByLabel(label);
    }

    @Override
    public void addMenuToGroupe(Groupe groupe, Menu menu) {
        //Groupe groupe = groupeService.findByCode(code);
        //	User user = dao.findByUsername(username);
        groupeRepo.editGroupe(groupe,menu);
    }

    @Override
    public Groupe findByCode(String code) {
        return dao.findByCode(code);
    }

    @Override
    public List<User> findUsersOfGroupe(String code) {
        return dao.findUsersOfGroupe(code);
    }

    @Override
    public List<Module> findGroupeOfUser(String username) {
        List<Module> modList = new ArrayList<>();
        modList = dao.findGroupeOfUser(username);
        return modList;
    }

    @Override
    public List<Groupe> findDispoGroupe(String username) {
        List<Groupe> groupeList = new ArrayList<>();
        groupeList = dao.findDispoGroupe(username);
        return groupeList;
    }

    @Override
    public List<Menu> findMenuExclude(String code) {
        List<Menu> menuList = new ArrayList<>();
        menuList = dao.findMenuExclude(code);
        return menuList;
    }

    @Override
    public Page<Groupe> findPageLazyGroupes(String code, String label, Boolean active, int first, int max) {
        Page<Groupe> groupeList = groupeRepo.findPageLazyGroupe(code, label, active, first, max);
        return groupeList;
    }

    @Override
    public Page<Groupe> findAndFilterPageLazyGroupes(String code, String label, Boolean active, int first, int max, String sort, String field) {
        Page<Groupe> modList = groupeRepo.findAndFilterPageLazyGroupes(code, label, active, first, max, sort, field);
        return modList;
    }

    @Override
    public Module findModuleByCode(String code) {
        return dao.findModuleByCode(code);
    }

    @Override
    public List<Groupe> findAll() {
        return dao.findAll();
    }

    @Override
    public Groupe save(Groupe entity) {
        return dao.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public Groupe getOne(Long id) {
        return dao.getOne(id);
    }

    @Override
    public Long count() {
        return dao.count();
    }
}
