package com.example.demo.Service.Impl;

import com.example.demo.Dto.LoginDTO;
import com.example.demo.Dto.UserDTO;
import com.example.demo.Entity.User;
import com.example.demo.Repo.IUserRepo;
import com.example.demo.Response.LoginResponse;
import com.example.demo.RiotApi.GetPuuidByGameNameTagLine;
import com.example.demo.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserIMPL implements IUserService {

    @Autowired
    private IUserRepo iUserRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String addUser(UserDTO userDTO) {
        GetPuuidByGameNameTagLine getPuuid = new GetPuuidByGameNameTagLine();
        User user = new User(
                userDTO.getUserId(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword()),
                getPuuid.fetchPuuid(userDTO.getSummonerName(),userDTO.getTagline()),
                userDTO.getSummonerName(),
                userDTO.getTagline()

        );
        iUserRepo.save(user);

        return "Saved " + user.getSummonerName();
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user1 = iUserRepo.findByEmail(loginDTO.getEmail());
        if (user1 != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<User> user = iUserRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (user.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {
                return new LoginResponse("password doesn`t match", false);
            }
        }else {
            return new LoginResponse("Email doesn`t exist", false);
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = iUserRepo.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserDTO(user.getEmail(), user.getSummonerName(), user.getTagline());
    }

}
