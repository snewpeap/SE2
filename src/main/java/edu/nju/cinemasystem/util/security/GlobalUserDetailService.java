package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.dataservices.user.UserHasRoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.message.AccountMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlobalUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserHasRoleMapper userHasRoleMapper;
    @Autowired
    private AccountMsg accountMsg;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userMapper.selectByName(name);
        if (user == null){
            throw new UsernameNotFoundException(accountMsg.getAccountNotExist());
        }
        List<SimpleGrantedAuthority> auths = new ArrayList<>(3);
        return null;
    }
}
