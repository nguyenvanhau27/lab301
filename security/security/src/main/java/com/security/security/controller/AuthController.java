package com.security.security.controller;

import com.security.security.dto.LoginRequest;
import com.security.security.exception.ChangePasswordRequest;
import com.security.security.model.User;
import com.security.security.service.JwtService;
import com.security.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")  // All routes related to authentication
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    // Render the login page
    @GetMapping("/login")
    public String loginPage(Model model) {
        // We can pass some attributes to the view if needed
        model.addAttribute("loginRequest", new LoginRequest()); // for binding form data
        return "login";  // Returns login.html
    }

    // Handle login form submission
    @PostMapping("/login")
    public String login(@Valid LoginRequest loginRequest, Model model) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            final UserDetails userDetails = (UserDetails) userService.getUserByEmail(loginRequest.getEmail());
            final String token = jwtService.generateToken(userDetails);

            // Store the token or some user details if needed (or redirect after successful login)
            model.addAttribute("message", "Login successful! Token: " + token);
            return "login-success";  // Render login-success.html after successful login

        } catch (Exception e) {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // Return to login page with error message
        }
    }

    // Render the registration page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); // Bind form data
        return "register";  // Returns register.html
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(@Valid User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("message", "User registered successfully!");
            return "register-success";  // Render register-success.html after registration

        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";  // Return to register page with error message
        }
    }

    // Render the change password page
    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest()); // Bind form data
        return "change-password";  // Returns change-password.html
    }

    // Handle password change form submission
    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordRequest changePasswordRequest, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            userService.changePassword(email, changePasswordRequest);

            model.addAttribute("message", "Password changed successfully!");
            return "change-password-success";  // Render change-password-success.html

        } catch (Exception e) {
            model.addAttribute("error", "Failed to change password: " + e.getMessage());
            return "change-password";  // Return to change-password page with error message
        }
    }
}

//    // GET methods for rendering HTML templates
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login"; // Renders login.html from the templates folder
//    }
//
//    @GetMapping("/register")
//    public String registerPage() {
//        return "register"; // Renders register.html
//    }
//
//    @GetMapping("/change-password")
//    public String changePasswordPage() {
//        return "change-password"; // Renders change-password.html
//    }


//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//
//        );
//        final UserDetails userDetails = (UserDetails) userService.getUserByEmail(loginRequest.getEmail());
//        final String token = jwtService.generateToken(userDetails);
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<User> register(@Valid @RequestBody User user) {
//        return ResponseEntity.ok(userService.registerUser(user));
//    }
//
//    @PostMapping("/change-password")
//    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        userService.changePassword(email, changePasswordRequest);
//        return ResponseEntity.ok().body("Password changed");
//    }




