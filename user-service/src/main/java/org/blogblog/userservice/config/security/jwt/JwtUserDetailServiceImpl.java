package org.blogblog.userservice.config.security.jwt;

import jakarta.transaction.Transactional;
import org.blogblog.userservice.entities.User;
import org.blogblog.userservice.entities.common.ApiException;
import org.blogblog.userservice.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class JwtUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public JwtUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO: we will implement permission system after that
        //        Role role = user.getRole();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> ApiException.create(HttpStatus.FORBIDDEN).withMessage("user not exist, please check with admin"));
    }
}
