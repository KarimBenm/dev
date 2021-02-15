package fr.lyes.gds.ControllerTest;

import fr.lyes.gds.Buisness.Admin.Data.Entities.Menu;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.MenuService;
import fr.lyes.gds.Presentation.Controllers.Admin.MenuController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@RunWith(SpringRunner.class)
@WebMvcTest(MenuController.class)
public class MenuControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MenuService service;

    @Test
    public void givenMenu_whenGetMenu_thenReturnJsonArray()
            throws Exception {

        Menu menu = new Menu("test06", "test06", "test06", "test06");

        List<Menu> allMenus = service.findAllMenu();



        /*mvc.perform(get("/menus")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(menu.getLabel())));*/
   // }
//}
