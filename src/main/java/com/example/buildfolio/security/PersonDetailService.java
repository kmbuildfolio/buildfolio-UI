package com.example.buildfolio.security;

import com.example.buildfolio.model.AuthCredential;
import com.example.buildfolio.repository.PersonRepo;
import com.example.buildfolio.security.interf.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

@Service
public class PersonDetailService implements UserDetailsService {

    @Autowired
    private PersonRepo personRepository;

    @Override
    public MyUserDetails loadUserByUsername(String userNameOrEmail) {

        Supplier<AuthCredential> authCredentialSupplier = () -> {
            AuthCredential authCredential;
            try {
                authCredential = this.personRepository.getCredentialByUserName(userNameOrEmail);
                if (authCredential == null)
                    authCredential = this.personRepository.getCredentialByEmail(userNameOrEmail);

                if (authCredential == null) {
                    throw new UsernameNotFoundException("Username Not Found");
                }
                return authCredential;
            } catch (Exception e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        };

        return new MyUserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                AuthCredential authCredential = authCredentialSupplier.get();
                return Arrays.asList(new SimpleGrantedAuthority(authCredential.getRole()));
            }

            @Override
            public String getPassword() {
                AuthCredential authCredential = authCredentialSupplier.get();
                return authCredential.getPass();
            }

            @Override
            public String getUsername() {
                AuthCredential authCredential = authCredentialSupplier.get();
                return authCredential.getUserName();
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

            @Override
            public String getEmail() {
                AuthCredential authCredential = authCredentialSupplier.get();
                return authCredential.getEmail();
            }

            @Override
            public String getRole() {
                AuthCredential authCredential = authCredentialSupplier.get();
                return authCredential.getRole();
            }
        };
    }
}


