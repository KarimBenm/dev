package fr.lyes.gds.Buisness.Produit.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Buisness.Produit.Data.Entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProduitDao extends JpaRepository<Produit, Long> {
	public User findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

    @Query("select max(user.id) from User user")
    public Long findMaxUser();
}
