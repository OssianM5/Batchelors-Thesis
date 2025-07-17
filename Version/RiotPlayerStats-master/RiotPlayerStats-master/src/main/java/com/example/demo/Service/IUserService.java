package com.example.demo.Service;

import com.example.demo.Dto.LoginDTO;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Response.LoginResponse;

public interface IUserService {

    String addUser(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    LoginResponse loginUser (LoginDTO loginDTO);
}
