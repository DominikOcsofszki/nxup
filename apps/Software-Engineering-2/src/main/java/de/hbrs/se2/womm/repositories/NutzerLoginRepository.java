package de.hbrs.se2.womm.repositories;

import de.hbrs.se2.womm.entities.Nutzer;
import de.hbrs.se2.womm.entities.NutzerLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NutzerLoginRepository extends JpaRepository<NutzerLogin,Long> {
    List<NutzerLogin> findByNutzerNameContaining(String name);

    NutzerLogin findNutzerByNutzerName(String name);

    NutzerLogin findNutzerByNutzerPasswort(String passwort);

    Optional<NutzerLogin> findByNutzer_NutzerId(Long id);

    boolean existsNutzerByNutzerName(String name);
}
