package fr.lyes.gds.Buisness.Admin.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupeDao extends JpaRepository<Groupe, Long> {
    public Groupe findByLabel(String label);
    public Groupe findByCode(String code);
    @Query("select groupe.module from Groupe groupe" +
            " join groupe.appUsers app where app.username = :username")
    public List<Module> findGroupeOfUser(@Param("username") String username);

    @Query(value = "select  gpe.groupe_id from admin.user_groupe gpe  where gpe.user_id Not in (select admin.user.id from admin.user  where admin.user.id = :userId)",nativeQuery =  true
          )
    public List<Groupe> findGrouppeOfUser(@Param("userId") Long userId);

    @Query("select groupe.module from Groupe groupe" +
            "  where groupe.code = :code")
    public Module findModuleByCode(@Param("code") String code);

    @Query("select groupe from Groupe groupe where groupe.id Not in (select groupe.id from Groupe groupe join groupe.appUsers app where app.username = :username)")
    public List<Groupe> findDispoGroupe(@Param("username")String username);

    @Query("select menu from Menu menu where menu.id Not in (select menu.id from Menu menu join menu.menuGroupeList app where app.code = :code)")
    public List<Menu> findMenuExclude(@Param("code")String code);

    @Query("select groupe.appUsers from Groupe groupe where groupe.code = :code")
    public List<User> findUsersOfGroupe(@Param("code")String code);


}
