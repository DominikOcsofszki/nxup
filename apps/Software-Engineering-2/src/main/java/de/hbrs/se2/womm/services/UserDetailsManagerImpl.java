package de.hbrs.se2.womm.services;

import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.entities.NutzerLogin;
import de.hbrs.se2.womm.repositories.NutzerLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserDetailsManagerImpl implements UserDetailsManager {

    @Autowired
    private NutzerLoginRepository nutzerLoginRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsManagerImpl() {
    }

    @Override
    public void createUser(UserDetails user) {
        nutzerLoginRepository.save((NutzerLogin) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        nutzerLoginRepository.save((NutzerLogin) user);
    }

    @Override
    public void deleteUser(String username) throws UsernameNotFoundException {
        NutzerLogin user = nutzerLoginRepository.findNutzerByNutzerName(username);
        if (user == null) throw new UsernameNotFoundException("No User found for username: " + username);
    }

    /**
     * Changes the password of the user
     *
     * @param oldPassword the old password of the user
     * @param newPassword the new password of the user
     */
    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        long nutzerID = securityService.getLoggedInNutzerID();
        Optional<NutzerLogin> nutzerLogin = nutzerLoginRepository.findByNutzer_NutzerId(nutzerID);

        nutzerLogin.map(
                nutzerLogin1 -> {
                    if (passwordEncoder.matches(oldPassword, nutzerLogin1.getNutzerPasswort())) {
                        String newPasswordEncoded = passwordEncoder.encode(newPassword);
                        nutzerLogin1.setNutzerPasswort(newPasswordEncoded);
                        nutzerLoginRepository.save(nutzerLogin1);
                    }
                    return nutzerLogin1;
                }
        );
    }

    @Override
    public boolean userExists(String username) {
        return nutzerLoginRepository.existsNutzerByNutzerName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NutzerLogin user = nutzerLoginRepository.findNutzerByNutzerName(username);
        if (user == null) throw new UsernameNotFoundException("No User found for username: " + username);
        return user;
    }
}
