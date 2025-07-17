package com.example.demo.Controller;


import com.example.demo.Config.JwtService;
import com.example.demo.Dto.LoginDTO;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Response.LoginResponse;
import com.example.demo.Service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")




public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService iUserService;

    @PostMapping(path = "/save")
    public String saveUser (@RequestBody UserDTO userDTO)
    {
        String id = iUserService.addUser(userDTO);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = iUserService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/account")
    public ResponseEntity<UserDTO> getAccountInfo(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // remove "Bearer "
        System.out.println("Authorization header: " + authHeader);
        String email = jwtService.extractUsername(token); // will crash if token is malformed

        UserDTO user = iUserService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


}
