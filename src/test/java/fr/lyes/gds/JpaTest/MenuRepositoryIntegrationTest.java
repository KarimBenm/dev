package fr.lyes.gds.JpaTest;
/*
import fr.lyes.gds.Buisness.Admin.Dao.MenuDao;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class MenuRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuDao employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Menu menu = new Menu();
        menu.setLabel("test05");
        menu.setIcon("test05");
        menu.setUrls("test05");
        menu.setColor("test05");
        entityManager.persist(menu);
        entityManager.flush();

        // when
        Menu found = employeeRepository.findByLabel(menu.getLabel());

        // then
        assertThat(found.getLabel())
                .isEqualTo(menu.getLabel());
    }
}
*/