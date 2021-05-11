package fr.lyes.gds;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@Configuration
public class GdsApplication implements CommandLineRunner  {
    /*	@Autowired
	private UserService userService;
    	@Autowired
        private GroupeService groupeService;
    @Autowired
    private ModuleService moduleService;
    */

   // @Bean
   // public ModelMapper modelMapper() {
   //     return new ModelMapper();
   // }

    public static void main(String[] args) {
        SpringApplication.run(GdsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

   /* @Override
    public void run(String... args) throws Exception {
        System.setProperty("webdriver.gecko.driver","C:\\Users\\Karim\\Desktop\\Lyes\\sel\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/angular");
        driver.findElement(By.id("email")).sendKeys("lyes");
        driver.findElement(By.id( "password")).sendKeys("*****");
        WebElement btn = driver.findElement(By.id("login"));
        btn.submit();
*/
        //moduleService.save(new ModuleDto("ADMIN01","ADMIN01","ADMIN01"));
       // moduleService.save(new ModuleDto("ADMIN02","ADMIN02","ADMIN02"));
       // moduleService.save(new ModuleDto("ADMIN03","ADMIN03","ADMIN03"));
       // moduleService.save(new ModuleDto("ADMIN04","ADMIN04","ADMIN04"));
      //  moduleService.save(new ModuleDto("ADMIN05","ADMIN05","ADMIN05"));
       // moduleService.save(new ModuleDto("ADMIN06","ADMIN06","ADMIN06"));
       // moduleService.save(new ModuleDto("ADMIN07","ADMIN07","ADMIN07"));

      //  moduleService.save(new ModuleDto("ADMIN","ADMIN","ADMIN"));
       // groupeService.save(new Groupe("Facturation",moduleService.findByLabel("Facturation")));
       // groupeService.save(new Groupe("Stock",moduleService.findByLabel("Stock")));
      //  groupeService.save(new Groupe("Produit",moduleService.findByLabel("Produit")));
       // groupeService.save(new Groupe("DashBoard",moduleService.findByLabel("DashBoard")));
       // userService.saveUser(new User("admin","admin09"));
        //userService.addGroupeToUser("admin","Facturation");
       // userService.addGroupeToUser("admin","Stock");
       // userService.addGroupeToUser("admin","Produit");
     //   userService.addGroupeToUser("admin","DashBoard");
  //  }
}
