package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.LoginDto;
import com.edu.ruse.studypal.dtos.RegisterDto;
import com.edu.ruse.studypal.entities.RoleEnum;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.exceptions.NotFoundRoleException;
import com.edu.ruse.studypal.mappers.UserMapper;
import com.edu.ruse.studypal.repositories.UserRepository;
import com.edu.ruse.studypal.security.jwt.JwtUtils;
import com.edu.ruse.studypal.security.responses.JwtResponse;
import com.edu.ruse.studypal.security.responses.MessageResponse;
import com.edu.ruse.studypal.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RestController
    @RequestMapping("auth")
    public class AuthController {
        public static String jwt = "";

    @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        UserRepository userRepository;

        @Autowired
        PasswordEncoder encoder;

        @Autowired
        UserMapper userMapper;

        @Autowired
        JwtUtils jwtUtils;

       // @PostMapping("/signin")
    @RequestMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.ALL_VALUE)
        public RedirectView authenticateUser(@Valid LoginDto loginRequest, BindingResult result) {

        if (result.hasErrors()) {
            return new RedirectView("login.html");
        }

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = jwtUtils.generateJwtToken(authentication);


            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new RedirectView("/");
        }

        private boolean isRoleValid(String inputRole) {
            for (RoleEnum role : RoleEnum.values()) {
                if (inputRole.equals(role.name())) {
                    return true;
                }
            }
            throw new NotFoundRoleException("Role with name " + inputRole + " was not found");
        }

        @PostMapping("/signup")
        public ModelAndView registerUser(@Valid RegisterDto registerDto) {
//            if (userRepository.existsByUsername(registerDto.getUsername())) {
//                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
//            }

            isRoleValid(registerDto.getRole());
            User user = userMapper.toEntityFromPostDto(registerDto);
            user.setPassword(encoder.encode(registerDto.getPassword()));

            User res = userRepository.save(user);

            return new ModelAndView("/adminPanel");
        //ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
}
