package com.edu.ruse.studypal.controllers;

import com.edu.ruse.studypal.dtos.LoginDto;
import com.edu.ruse.studypal.dtos.RegisterDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anniexp
 */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {
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

        @PostMapping("/signin")
        public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        }

        private boolean isRoleValid(String inputRole) {
            if (!inputRole.equalsIgnoreCase("ROLE_ADMIN") &&
                    !inputRole.equalsIgnoreCase("ROLE_STUDENT") &&
                    !inputRole.equalsIgnoreCase("ROLE_TEACHER") &&
                    !inputRole.equalsIgnoreCase("ROLE_COORDINATOR")) {
                throw new NotFoundRoleException();
            }
            return true;
        }

        @PostMapping("/signup")
        public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
            if (userRepository.existsByUsername(registerDto.getUsername())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }
            User user = userMapper.toEntityFromPostDto(registerDto);
            user.setPassword(encoder.encode(registerDto.getPassword()));
            String strRoles = String.valueOf(registerDto.getRole());
            isRoleValid(strRoles);

            User res = userRepository.save(user);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
}
