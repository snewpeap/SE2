package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

public class GlobalAuthenticationProvider extends DaoAuthenticationProvider {
    @Qualifier("passwordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMsg accountMsg;

    private UsernamePasswordAuthenticationToken token;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException(accountMsg.getAccountNotExist());
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException(accountMsg.getWrongPassword());
        }
        Collection<? extends GrantedAuthority> auth = userDetails.getAuthorities();
        token = new UsernamePasswordAuthenticationToken(username, authentication.getCredentials(), auth);
        token.setDetails(userDetails);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
