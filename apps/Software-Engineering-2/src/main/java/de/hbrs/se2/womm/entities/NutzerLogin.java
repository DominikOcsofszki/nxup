package de.hbrs.se2.womm.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nutzer_login")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutzerLogin implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nutzer_login_id")
    private Integer id;

    @Column(name = "nutzer_login_name", nullable = false)
    private String nutzerName;

    @Column(name = "nutzer_login_passwort", nullable = false)
    private String nutzerPasswort;

    @Column(name = "nutzer_login_rolle", nullable = false)
    private String rolle;

    @OneToOne
    @JoinColumn(name = "nutzer_id")
    private Nutzer nutzer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + getRolle()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return nutzerPasswort;
    }

    @Override
    public String getUsername() {
        return nutzerName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
