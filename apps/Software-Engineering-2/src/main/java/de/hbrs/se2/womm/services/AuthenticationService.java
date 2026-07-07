package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.dtos.CompanyRegistrationRequest;
import de.hbrs.se2.womm.dtos.LoginRequest;
import de.hbrs.se2.womm.dtos.RegistrationRequest;
import de.hbrs.se2.womm.dtos.StudentRegistrationRequest;
import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.NutzerLogin;
import de.hbrs.se2.womm.entities.Student;
import de.hbrs.se2.womm.entities.Unternehmen;
import de.hbrs.se2.womm.exceptions.AuthenticationException;
import de.hbrs.se2.womm.exceptions.UsernameAlreadyTakenException;
import de.hbrs.se2.womm.model.Roles;
import de.hbrs.se2.womm.repositories.NutzerLoginRepository;
import de.hbrs.se2.womm.repositories.NutzerRepository;
import de.hbrs.se2.womm.repositories.StudentRepository;
import de.hbrs.se2.womm.repositories.UnternehmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserDetailsManagerImpl userDetailsManager;
    @Autowired
    private NutzerLoginRepository nutzerLoginRepository;
    @Autowired
    private NutzerRepository nutzerRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UnternehmenRepository unternehmenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void registerStudent(StudentRegistrationRequest request) throws UsernameAlreadyTakenException {
        String username = request.getUsername();
        String email = request.getEmail();
        createUser(request, username, Roles.STUDENT.name());
        // speichert Studenten mit FK zur Nutzer-Tabelle
        Nutzer user = nutzerRepository.findNutzerByNutzerMail(email);
        studentRepository.save(Student.builder()
                .studentSemester(0)
                .studentSpezialisierung("")
                .studentBio("")
                .studentVorname(request.getFirstname())
                .studentName(request.getLastname())
                .studentGeburtstag(request.getDob())
                .studentBenachrichtigung(false)
                .nutzer(user)
                .build());
    }

    public void registerCompany(CompanyRegistrationRequest request) throws UsernameAlreadyTakenException {
        String username = request.getUsername();
        String email = request.getEmail();
        createUser(request, username, Roles.UNTERNEHMEN.name());
        // speichert Unternehmen mit FK zur Nutzer-Tabelle
        Nutzer user = nutzerRepository.findNutzerByNutzerMail(email);
        unternehmenRepository.save(Unternehmen.builder()
                .beschreibung("")
                .gruendung("")
                .website_url("")
                .nutzer(user)
                .name(request.getName())
                .build());
    }

    public Authentication loginUser(LoginRequest request) throws AuthenticationException {
        NutzerLogin user = nutzerLoginRepository.findNutzerByNutzerName(request.getUsername());
        if (user == null) throw new AuthenticationException("Invalid username");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, request.getPassword(), user.getAuthorities()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e) {
            throw new AuthenticationException("Invalid password");
        }
    }

    /**
     * speichert einen Nutzer für Student- oder Unternehmensanfrage
     */
    private void createUser(RegistrationRequest request, String username, String role) throws UsernameAlreadyTakenException {
        if (nutzerLoginRepository.existsNutzerByNutzerName(username))
            throw new UsernameAlreadyTakenException("Username " + username + " is already taken!");

        Nutzer nutzer = Nutzer.builder()
                .nutzerMail(request.getEmail())
                .nutzerOrt(request.getLocation())
                .nutzerAktiv(true).build();

        Nutzer savedNutzer = nutzerRepository.save(nutzer);

        NutzerLogin nutzerLogin = NutzerLogin.builder()
                .nutzerName(request.getUsername())
                .nutzerPasswort(passwordEncoder.encode(request.getPassword()))
                .rolle(role)
                .nutzer(savedNutzer).build();

        userDetailsManager.createUser(nutzerLogin);
    }
}
