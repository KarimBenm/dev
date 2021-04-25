package fr.lyes.gds.Presentation.Controllers.Admin;


import fr.lyes.gds.Buisness.Admin.Data.Dto.GroupeDto;
import fr.lyes.gds.Buisness.Admin.Data.Dto.UserDto;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Module;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.GroupeService;
import fr.lyes.gds.Buisness.Admin.service.Interfaces.UserService;
import fr.lyes.gds.Presentation.Utils.ImageModel;
import fr.lyes.gds.Presentation.Utils.RequestConstants;
import fr.lyes.gds.Security.payload.request.CreateRequest;
import fr.lyes.gds.Security.payload.request.SignUpRequest;
import fr.lyes.gds.Security.payload.request.UserRequest;
import fr.lyes.gds.Security.payload.response.MessageResponse;
import fr.lyes.gds.Shared.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin("*")
@RequestMapping(RequestConstants.User_REQUEST_MAPPING_ROOT)
public class UserController implements Serializable {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private MailService notificationService;

    @Bean
    @Qualifier("coder")
    public BCryptPasswordEncoder getNewBCPE() {
        return new BCryptPasswordEncoder();
    }

    private static final long serialVersionUID = 1L;

    @PostMapping(value = RequestConstants.User_Register_REQUEST_MAPPING_ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    User create(@RequestBody User entity) {
        User user = service.findByUsername(entity.getUsername());
        //  System.out.println("fff"+user.getUsername());
        if (user != null) {
            System.out.println("fdgdg");
            throw new ResponseStatusException(HttpStatus.IM_USED, "User already exists");
            // throw new RuntimeException("cet utilisateur existe déja.");
            //service.saveUser(user);
        }
        return null;
    }


    @RequestMapping(value = RequestConstants.User_Find_REQUEST_MAPPING_ROOT, method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    UserDto findByUsername(@PathVariable(value = "username") String username) {
        if (username != null) {
            UserDto postDto = modelMapper.map(service.findByUsername(username), UserDto.class);
            return postDto;
        }
        return null;
    }

    @RequestMapping(value = RequestConstants.User_Profile_REQUEST_MAPPING_ROOT, method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    User findProfileByUsername(@PathVariable(value = "username") String username) {
        if (username != null) {
            return service.findByUsername(username);
        }
        return null;
    }

    @PutMapping(value = RequestConstants.User_Update_REQUEST_MAPPING_ROOT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User update(@RequestBody UserRequest entity) throws IOException {
        if (nullCheck(entity.getUser().getId())) {
            User oldUser = service.findByUsername(entity.getUser().getUsername());
            List<Groupe> oldGroupe = new ArrayList<>();

            oldGroupe.addAll(oldUser.getAppGroupeList());
            User newUser = new User(entity.getUser());
            oldUser.setDateNaissance(newUser.getDateNaissance());
            oldUser.setSexe(newUser.getSexe());
            oldUser.setValid(newUser.getValid());
            oldUser.setAddress(newUser.getAddress());
            oldUser.setEmail(newUser.getEmail());
            oldUser.setFirstName(newUser.getFirstName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setUsername(newUser.getUsername());
            if (entity.getGroupesList() != null) {
                if (!entity.getGroupesList().isEmpty()) {
                    entity.getGroupesList().forEach(x -> {
                        if (!customEquals(oldGroupe , x)) {
                            oldUser.getAppGroupeList().add(groupeService.findByCode(x));
                        }
                    });
                }
            }
            if (oldGroupe != null) {
                if (!oldGroupe.isEmpty()) {
                    oldGroupe.forEach(x -> {
                        if (!entity.getGroupesList().contains(x.getCode())) {
                            oldUser.getAppGroupeList().remove(x);
                        }
                    });
                }
            }
            String pass = oldUser.getPassword();
            oldUser.setPassword(pass);
            return service.save(oldUser);
        }
        return null;

    }

    @RequestMapping(value = RequestConstants.User_Find_UserName_REQUEST_MAPPING_ROOT, method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    boolean findPassByUsername(@PathVariable(value = "userPass") String userPass, @PathVariable(value = "username") String username) {
        if (userPass != null) {
            return getNewBCPE().matches(userPass, service.findByUsername(username).getPassword());
        }
        return false;
    }

    @PutMapping(value = RequestConstants.User_Change_Password_REQUEST_MAPPING_ROOT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User updateAndMore(@RequestBody User entity) {
        if (nullCheck(entity.getId())) {
            return service.saveUser(entity);
        }
        return service.save(entity);
    }

    @GetMapping(value = RequestConstants.User_Get_Users_REQUEST_MAPPING_ROOT)
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping(value = RequestConstants.User_Groupes_REQUEST_MAPPING_ROOT)
    public List<Module> findGroupeOfUseres(@PathVariable(value = "username") String username) {
        return groupeService.findGroupeOfUser(username);
    }

    @GetMapping(value = RequestConstants.Pages_REQUEST_MAPPING_ROOT)
    public Page<UserDto> findPageUser(@RequestParam(name = "username", required = false) String username,
                                      @RequestParam(name = "lastName", required = false) String lastName,
                                      @RequestParam(name = "firstName", required = false) String firstName,
                                      @RequestParam(name = "valid", required = false) Boolean valid,
                                      @RequestParam(name = "email", required = false) String email,
                                      @RequestParam(name = "page") int page,
                                      @RequestParam(name = "size") int size
    ) {
        System.out.println("test");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> mod = service.findPageLazyUsers(username, lastName, firstName, valid, email, page, size);
        List<UserDto> userDtos = new ArrayList<>();
        mod.forEach(x -> {
            UserDto postDto = modelMapper.map(x, UserDto.class);
            userDtos.add(postDto);
        });
        return new PageImpl<UserDto>(userDtos, pageRequest, mod.getTotalElements());
    }

    @GetMapping(value = RequestConstants.Sort_Update_REQUEST_MAPPING_ROOT)
    public Page<UserDto> findAndSortPageUser(@RequestParam(name = "username", required = false) String username,
                                             @RequestParam(name = "lastName", required = false) String lastName,
                                             @RequestParam(name = "firstName", required = false) String firstName,
                                             @RequestParam(name = "valid", required = false) Boolean valid,
                                             @RequestParam(name = "email", required = false) String email,
                                             @RequestParam(name = "page") int page,
                                             @RequestParam(name = "size") int size,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @RequestParam(name = "field", required = false) String field

    ) {
        if (sort == null) {
            sort = "DESC";
        }
        if (field == null) {
            field = "code";
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> mod = service.findAndFilterPageLazyUsers(username, lastName, firstName, valid, email, page, size, sort, field);
        List<UserDto> userDtos = new ArrayList<>();
        mod.forEach(x -> {
            UserDto postDto = modelMapper.map(x, UserDto.class);
            userDtos.add(postDto);
        });
        return new PageImpl<UserDto>(userDtos, pageRequest, mod.getTotalElements());
    }

    @PostMapping(value = "/image/upload/{username}")
    public ResponseEntity<?> createImage(@RequestParam("imageFile") MultipartFile file , @PathVariable("username") String username) throws IOException{
        System.out.println("test");
        User user = this.service.findByUsername(username);
        if(user != null){
            System.out.println("notnull");
            user.setProfilImage(compressBytes(file.getBytes()));
            this.service.save(user);
            return ResponseEntity.ok(user);
        }
        System.out.println("null");
        return ResponseEntity.badRequest()
                .body(new MessageResponse("Error: f is already taken!"));
    }

    @GetMapping(value = "/showImage/{username}")
    public ImageModel getImage(@PathVariable("username") String username) throws IOException{
        ImageModel img = new ImageModel("test",decompressBytes(this.service.findByUsername(username).getProfilImage()));
        return img;
    }
    @PostMapping(value = RequestConstants.User_Create_Users_REQUEST_MAPPING_ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateRequest createRequest) {
        if (service.findByUsername(createRequest.getUsername()) != null) {
            /*return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));*/
            //throw new ResponseStatusException(HttpStatus.IM_USED, "UserName already exists");
            throw new RuntimeException("this userName is taken please provide another one");

        }


        //if (service.find(signUpRequest.getEmail())) {
            /*return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));*/
        //   throw new ResponseStatusException(HttpStatus.IM_USED, "UserEmail already exists");
        //  throw new RuntimeException("this Email is taken please provide another one");
        //  }
        // Create new user's account
        User user = new User(createRequest.getUsername(),
                createRequest.getLastName(), createRequest.getFirstName(), createRequest.getAddress(),
                createRequest.getEmail(), createRequest.getProfilImage()
        );
        List<String> strRoles = createRequest.getRole();
        List<Groupe> stRoles = new ArrayList<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "Admin":
                    Groupe adminRole = groupeService.findByCode("Admin");
                    stRoles.add(adminRole);
                    break;
                case "Facturation":
                    Groupe factRole = groupeService.findByCode("Facturation");
                    stRoles.add(factRole);
                    break;
                case "Stock":
                    Groupe stockRole = groupeService.findByCode("Stock");
                    stRoles.add(stockRole);
                    break;
                case "Produit":
                    Groupe prodRole = groupeService.findByCode("Produit");
                    stRoles.add(prodRole);
                    break;
                case "DashBoard":
                    Groupe dashRole = groupeService.findByCode("DashBoard");
                    stRoles.add(dashRole);
                    break;
            }
        });
        user.setAppGroupeList(stRoles);
        user.setSexe(createRequest.getGender());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime newDate = LocalDateTime.parse(createRequest.getDateNaissance(), formatter);
        user.setDateNaissance(newDate.toLocalDate());
        user.setValid(createRequest.isValid());
        String pass = getNewBCPE().encode(this.generatePassword(user));
        user.setConfirmPassword(this.generatePassword(user));
        user.setPassword(pass);
        service.save(user);

        try {
            notificationService.sendEmailWithAttachment(user, true);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(user);
    }

    protected <T> Boolean nullCheck(T obj) {
        Optional<T> opt = Optional.ofNullable(obj);
        return opt.isPresent();
    }

    public String generatePassword(User user) {
        StringBuilder stringBuilder = new StringBuilder(user.getFirstName());
        stringBuilder.append(".");
        stringBuilder.append(user.getLastName());
        stringBuilder.append("@");
        stringBuilder.append(service.findMaxUser());
        String passGenerated = stringBuilder.toString();
        return passGenerated;
    }
    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
    public boolean customEquals(List<Groupe> lgpe , String gpe) {
        boolean contain = false;
        for(Groupe x : lgpe){
            if(x.getCode()!= null && gpe!= null ){
                if(x.getCode().equals(gpe)){
                    contain = true;
                }
            }
        }
        return contain;
    }
}

