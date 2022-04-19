package com.census.support.acl.auth;


import com.census.support.acl.role.Role;
import com.census.support.acl.role.RoleRepository;
import com.census.support.acl.security.jwt.config.JwtUtils;
import com.census.support.acl.security.jwt.payload.request.LoginRequest;
import com.census.support.acl.security.jwt.payload.request.SignupRequest;
import com.census.support.acl.security.jwt.payload.response.JwtResponse;
import com.census.support.acl.security.service.UserDetailsImpl;
import com.census.support.acl.user.User;
import com.census.support.acl.user.UserRepository;
import com.census.support.acl.user.UserService;
import com.census.support.helper.exception.ResourceNotFoundException;
import com.census.support.helper.response.BaseResponse;
import com.census.support.system.constants.SystemRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @PostMapping("/acl/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.getByUsername(loginRequest.getUsername());

            String token = jwtUtils.generateJwtToken(authentication,user);
            long timeout = jwtUtils.getJwtExpirationMs();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> optionalUser = userRepository.findByEmail(userDetails.getEmail());


            return ResponseEntity.ok(new JwtResponse(true, optionalUser.orElse(null), token, "Bearer", timeout));

        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new BaseResponse(false, "Username or password is incorrect",401));
        }
    }

    @PostMapping("/acl/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest) {

        // create new user's account
        User user = new User(
                signupRequest.getPhone(),
                signupRequest.getUsername(),
                this.bCryptPasswordEncoder.encode(signupRequest.getPassword())
                );

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.getRoleByAuthority(("ROLE_USER"));
            if (userRole==null){
                throw new ResourceNotFoundException("Role not found");
            }
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                if (String.valueOf(SystemRole.ROLE_SUPER_ADMIN).equals(role)) {
                    Role superAdminRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_SUPER_ADMIN));
                    if (superAdminRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        } catch (ResourceNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(superAdminRole);
                } else if (String.valueOf(SystemRole.ROLE_ADMIN).equals(role)) {
                    Role adminRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_ADMIN));
                    if (adminRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        } catch (ResourceNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(adminRole);
                }else if(String.valueOf(SystemRole.ROLE_BBS_USER).equals(role)){
                    Role bbsUserRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_BBS_USER));
                    if (bbsUserRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        }catch (ResourceNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                    roles.add(bbsUserRole);

                }else if(String.valueOf(SystemRole.ROLE_TIRE1_USER).equals(role)){
                    Role tire1UserRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_TIRE1_USER));
                    if (tire1UserRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        }catch (ResourceNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                    roles.add(tire1UserRole);

                }else if(String.valueOf(SystemRole.ROLE_TIRE2_USER).equals(role)){
                    Role tire2UserRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_TIRE2_USER));
                    if (tire2UserRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        }catch (ResourceNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    Role userRole = roleRepository.getRoleByAuthority(String.valueOf(SystemRole.ROLE_USER));
                    if (userRole == null) {
                        try {
                            throw new ResourceNotFoundException("Role not found");
                        } catch (ResourceNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        return this.userService.createUser(user);
    }


//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
//        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
//        return ResponseEntity.ok(new BaseResponse(true,"Logout successfully!",200));
//    }

}
