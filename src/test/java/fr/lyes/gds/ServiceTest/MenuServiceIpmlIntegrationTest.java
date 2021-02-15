package fr.lyes.gds.ServiceTest;

import fr.lyes.gds.Buisness.Admin.Dao.MenuDao;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.service.Impl.MenuServiceImpl;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
/*
@RunWith(SpringRunner.class)
public class MenuServiceIpmlIntegrationTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public MenuService menuService() {
            return new MenuServiceImpl();
        }

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @Autowired
    private MenuService menuService;

    @MockBean
    private MenuDao menuRepository;

    @Before
    public void setUp() {
        Menu menu = new Menu("test06", "test06", "test06", "test06");
        Mockito.when(menuRepository.findByLabel(menu.getLabel()))
                .thenReturn(menu);

    }

    // write test cases here
    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {

    }

    @Test
    public void trueAssumption() {
        String name = "test06";
        String name1 = "test05";
        Menu found = menuService.findByMenuLabel("test08");
        if (found == null) {
            System.out.println("0000");
            Throwable exception = assertThrows(NullPointerException.class, () -> {
                throw new NullPointerException("Null");
             });
            System.out.println("1111");
            assertThat(exception.getMessage()).isEqualTo("Null");
            System.out.println("2222");
        } else {
            System.out.println("33333");
            assertThat(found.getLabel())
                    .isEqualTo(name1);
        }
    }

    Result result = JUnitCore.runClasses(EmployeeServiceImplTestContextConfiguration.class);
        /*if(found == null){
            Throwable exception = assertThrows(NullPointerException.class, () -> {
                throw new NullPointerException("Not Found");
            });
            assertThat(found.getLabel())
                .isEqualTo(name);
          assertThatNullPointerException();
        }
       /* assumeTrue(found != null);
        assertThat(found.getLabel())
                .isEqualTo(name);*/
//}



