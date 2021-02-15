package fr.lyes.gds.Buisness.Admin.service.Interfaces;

import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

	public User findByUsername(String username);

	public User saveUser(User user);

	public UserDto saveDto(UserDto userDto);

	public Long findMaxUser();

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

	public Page<User> findPageLazyUsers(String username, String lastName, String firstName,Boolean valid,String email, int first, int max);
	public Page<User> findAndFilterPageLazyUsers(String username, String lastName, String firstName,Boolean valid,String email, int first, int max, String sort ,String field);

	public Groupe saveGroupe(Groupe role);

	public void addGroupeToUser(String username, String code);

	List<User> findAll();

	User save(User entity);

	void deleteById(Long id);

	User getOne(Long id);

	Long count();
}
