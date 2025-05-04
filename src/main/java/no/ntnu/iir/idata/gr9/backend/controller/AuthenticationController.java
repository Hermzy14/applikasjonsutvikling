package no.ntnu.iir.idata.gr9.backend.controller;

import no.ntnu.iir.idata.gr9.backend.dto.AuthenticationRequest;
import no.ntnu.iir.idata.gr9.backend.dto.AuthenticationResponse;
import no.ntnu.iir.idata.gr9.backend.service.AccessUserService;
import no.ntnu.iir.idata.gr9.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for authentication endpoints.
 */
@CrossOrigin
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccessUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticate a user and generate JWT token.
     *
     * @param authenticationRequest The authentication request containing username and password
     * @return ResponseEntity with JWT token or error message
     */
    @PostMapping("/api/users/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        ResponseEntity<?> response;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

            UserDetails userDetails = userService.loadUserByUsername(
                    authenticationRequest.getUsername()
            );

            String jwt = jwtUtil.generateToken(userDetails);

            response = new ResponseEntity<>(
                    new AuthenticationResponse(jwt),
                    HttpStatus.OK
            );

        } catch (BadCredentialsException e) {
            response = new ResponseEntity<>(
                    "Invalid username or password",
                    HttpStatus.UNAUTHORIZED
            );
        } catch (UsernameNotFoundException e) {
            response = new ResponseEntity<>(
                    "User not found",
                    HttpStatus.UNAUTHORIZED
            );
        } catch (Exception e) {
            response = new ResponseEntity<>(
                    "Internal server error",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return response;
    }
}