package fr.lyes.gds.Buisness.Admin.Dao;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuDao extends JpaRepository<Menu, Long> {
    @Query(value = "select m.* from admin.menu m,admin.menu_groupe u where m.id = u.menu_id and u.groupe_id = :groupeId", nativeQuery = true)
    public List<Menu> findByMenuCode(@Param("groupeId") Long groupeId);
    public Menu findByLabel(String label);
    @Query("select menu from Menu menu" +
            " where menu.menu.id = :menuId")
    public List<Menu> findAllSousMenu(@Param("menuId") Long menuId);

    @Query("select menu from Menu menu where menu Not in  :sousMenuList")
    public List<Menu> findSousMenuExclude(@Param("sousMenuList")List<Menu> sousMenuList);
}
