package de.hbrs.se2.womm.controller;

import de.hbrs.se2.womm.dtos.*;
import de.hbrs.se2.womm.exceptions.AuthenticationException;
import de.hbrs.se2.womm.exceptions.UsernameAlreadyTakenException;
import de.hbrs.se2.womm.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register/student")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentRegistrationRequest request) throws UsernameAlreadyTakenException {
        authenticationService.registerStudent(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register/company")
    public ResponseEntity<Void> registerCompany(@RequestBody CompanyRegistrationRequest request) throws UsernameAlreadyTakenException {
        authenticationService.registerCompany(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> loginUser(@RequestBody LoginRequest request) throws AuthenticationException {
        return new ResponseEntity<>(authenticationService.loginUser(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(@RequestBody LogoutRequest request) {
        // TODO: add logic
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
