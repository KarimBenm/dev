package fr.lyes.gds.Presentation.Controllers.Security;


import fr.lyes.gds.Buisness.Admin.Dao.GroupeDao;
import fr.lyes.gds.Buisness.Admin.Dao.UserDao;
import fr.lyes.gds.Buisness.Admin.Data.Entities.Groupe;
import fr.lyes.gds.Buisness.Admin.Data.Entities.User;
import fr.lyes.gds.Security.data.UserPrinciple;
import fr.lyes.gds.Security.jwt.JwtUtils;
import fr.lyes.gds.Security.payload.request.LoginRequest;
import fr.lyes.gds.Security.payload.request.SignUpRequest;
import fr.lyes.gds.Security.payload.response.JwtResponse;
import fr.lyes.gds.Security.payload.response.MessageResponse;
import fr.lyes.gds.Shared.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userRepository;


    @Autowired
    GroupeDao groupeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private MailService notificationService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                roles));
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            /*return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));*/
            //throw new ResponseStatusException(HttpStatus.IM_USED, "UserName already exists");
            throw new RuntimeException("this userName is taken please provide another one");

        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            /*return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));*/
            //   throw new ResponseStatusException(HttpStatus.IM_USED, "UserEmail already exists");
            throw new RuntimeException("this Email is taken please provide another one");
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getLastName(), signUpRequest.getFirstName(), signUpRequest.getAddress(),
                signUpRequest.getEmail(),signUpRequest.getProfilImage()
        );
        user.setAppGroupeList(new ArrayList<>());
        user.setValid(false);
        user.setSexe(signUpRequest.getGender());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        LocalDateTime newDate = LocalDateTime.parse(signUpRequest.getDateNaissance(), formatter);
        user.setDateNaissance(newDate.toLocalDate());
        userRepository.save(user);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        try {
            notificationService.sendEmailWithAttachment(user, false);
        } catch (MailException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(user);
    }

}
