package fr.lyes.gds.Buisness.Admin.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserDao extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

    @Query("select max(user.id) from User user")
    public Long findMaxUser();
}
