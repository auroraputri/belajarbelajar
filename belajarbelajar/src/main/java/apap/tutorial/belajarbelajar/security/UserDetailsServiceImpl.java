package apap.tutorial.belajarbelajar.security;

import apap.tutorial.belajarbelajar.model.UserModel;
import apap.tutorial.belajarbelajar.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDb userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserModel user = userDb.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
//        System.out.println(user.getRole().getRole());
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
