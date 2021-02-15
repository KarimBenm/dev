package fr.lyes.gds.Buisness.Admin.service.Impl;


import fr.lyes.gds.Buisness.Admin.Dao.UserDao;
import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Buisness.Admin.repo.UserRepo;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private GroupeService groupeService;

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        String hashPwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashPwd);
        return dao.save(user);
    }

    @Override
    @Transactional
    public UserDto saveDto(UserDto dto) {
        User entity = convertToEntity(dto);
        User newEntity = dao.saveAndFlush(entity);
        return convertToDto(newEntity);
    }

    @Override
    public Long findMaxUser() {
        return dao.findMaxUser() + 1;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return dao.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return dao.existsByEmail(email);
    }

    @Override
    public Page<User> findPageLazyUsers(String username, String lastName, String firstName, Boolean valid, String email, int first, int max) {
        Page<User> modList = userRepo.findPageLazyUsers(username, lastName, firstName, valid, email, first, max);
        return modList;
    }

    @Override
    public Page<User> findAndFilterPageLazyUsers(String username, String lastName, String firstName, Boolean valid, String email, int first, int max, String sort, String field) {
        Page<User> modList = userRepo.findAndFilterPageLazyUsers(username, lastName, firstName, valid, email, first, max, sort, field);
        return modList;
    }

    @Override
    public Groupe saveGroupe(Groupe role) {
        return null;
    }

    @Override
    public void addGroupeToUser(String username, String code) {
        Groupe groupe = groupeService.findByCode(code);
        User user = dao.findByUsername(username);
        user.getAppGroupeList().add(groupe);
    }


    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User save(User entity) {
        return dao.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public User getOne(Long id) {
        return dao.getOne(id);
    }

    @Override
    public Long count() {
        return dao.count();
    }

    public UserDto convertToDto(User entity) {
        UserDto dto = modelMapper.map(entity,
                (Class<UserDto>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2]);
        return dto;
    }

    @SuppressWarnings({"unchecked"})
    public User convertToEntity(UserDto dto) throws ParseException {
        User entity = modelMapper.map(dto,
                (Class<User>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        return entity;
    }
}
