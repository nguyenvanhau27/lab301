package com.security.security.controller;

import com.security.security.model.User;
import com.security.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {
    private final UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        System.out.println("listUsers() called"); // Log để kiểm tra
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "dashboard";
    }
    @GetMapping("/test")
    public String test() {
        System.out.println("PublicController: test() called");
        return "test";
    }
}
