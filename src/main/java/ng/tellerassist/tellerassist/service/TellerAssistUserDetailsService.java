package ng.tellerassist.tellerassist.service;

import ng.tellerassist.tellerassist.auth.dao.TellerAssistAuthDAO;
import ng.tellerassist.tellerassist.entity.TellerAssistRole;
import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;
import ng.tellerassist.tellerassist.enums.UserRoleEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TellerAssistUserDetailsService implements UserDetailsService {

    private TellerAssistAuthDAO userDAO;

    public TellerAssistAuthDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(TellerAssistAuthDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Retrieves a user record containing the user's credentials and access.
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        TellerAssistUserDetail user = this.userDAO.findUser(username);
        List<GrantedAuthority> authorities = this.buildUserAuthority(user.getRole());

        return this.buildUserForAuthentication(user, authorities);
    }

//    public Collection<GrantedAuthority> getAuthorities(Integer access) {
//       
//        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
//
//   
//        //logger.debug("Grant ROLE_USER to this user");
//        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        if (access.compareTo(UserRoleEnum.ADMINISTRATOR.getValue()) == 0) {
//            // User has admin access
//            logger.debug("Grant ROLE_ADMIN to this user");
//            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//
//        // Return list of granted authorities
//        return authList;
//    }
    private User buildUserForAuthentication(TellerAssistUserDetail user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(TellerAssistRole userRole) {

        Set<GrantedAuthority> setAuths = new HashSet();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(UserRoleEnum.USER.getDescription()));

        if (userRole.getRoleKey() == UserRoleEnum.ADMINISTRATOR.getValue()) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRoleKey().toString()));
        }

        List<GrantedAuthority> Result = new ArrayList(setAuths);

        return Result;
    }

}
