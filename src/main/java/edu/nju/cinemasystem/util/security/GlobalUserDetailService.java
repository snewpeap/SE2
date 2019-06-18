package edu.nju.cinemasystem.util.security;

import edu.nju.cinemasystem.data.po.User;
import edu.nju.cinemasystem.data.po.UserHasRoleKey;
import edu.nju.cinemasystem.dataservices.user.RoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserHasRoleMapper;
import edu.nju.cinemasystem.dataservices.user.UserMapper;
import edu.nju.cinemasystem.util.properties.RoleProperty;
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
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserHasRoleMapper userHasRoleMapper;
    @Autowired
    private AccountMsg accountMsg;
    @Autowired
    private RoleProperty roleProperty;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        final String prefix = "ROLE_";
        User user = userMapper.selectByName(name);
        if (user == null) {
            throw new UsernameNotFoundException(accountMsg.getAccountNotExist());
        }
        List<SimpleGrantedAuthority> auth = new ArrayList<>(3);
        UserHasRoleKey uhr = userHasRoleMapper.selectByUserID(user.getId());
        String role = uhr == null ? roleProperty.getAudience() : roleMapper.selectByPrimaryKey(uhr.getRoleId()).getRoleName();
        SimpleGrantedAuthority staffAuth = new SimpleGrantedAuthority(prefix + roleProperty.getStaff());
        SimpleGrantedAuthority managerAuth = new SimpleGrantedAuthority(prefix + roleProperty.getManager());
        if (roleProperty.getRoot().equals(role)) {
            auth.add(new SimpleGrantedAuthority(prefix + roleProperty.getRoot()));
            auth.add(managerAuth);
            auth.add(staffAuth);
        } else if (roleProperty.getManager().equals(role)) {
            auth.add(managerAuth);
            auth.add(staffAuth);
        } else if (roleProperty.getStaff().equals(role)) {
            auth.add(staffAuth);
        } else {
            auth.add(new SimpleGrantedAuthority(prefix + roleProperty.getAudience()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                auth
        );
    }
}
