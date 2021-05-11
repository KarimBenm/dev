package fr.lyes.gds.ControllerTest;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
