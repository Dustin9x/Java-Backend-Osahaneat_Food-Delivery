package com.cyberlearn.osahaneat.service;

import com.cyberlearn.osahaneat.dto.UserDTO;
import com.cyberlearn.osahaneat.entity.Roles;
import com.cyberlearn.osahaneat.entity.Users;
import com.cyberlearn.osahaneat.payload.request.SignUpRequest;
import com.cyberlearn.osahaneat.repository.UserRepository;
import com.cyberlearn.osahaneat.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
//    @Qualifier("tenBean")
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUser(){
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> listUserDTO = new ArrayList<>();

        for (Users users:listUser){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUsername(users.getUsername());
            userDTO.setFullname(users.getFullname());
            userDTO.setPassword(users.getPassword());
            userDTO.setCreatedDate(users.getCreatedDate());

            listUserDTO.add(userDTO);
        }

        return listUserDTO;
    }

    @Override
    public Boolean checkLogin(String username, String password) {
        Users users = userRepository.findByUsername(username);
        return passwordEncoder.matches(password,users.getPassword());
    }

    @Override
    public Boolean addUser(SignUpRequest signUpRequest) {

        Roles roles = new Roles();
        roles.setId(signUpRequest.getRoleId());

        Users users = new Users();
        users.setFullname(signUpRequest.getFullname());
        users.setUsername(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setRoles(roles);
        try{
            userRepository.save(users);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
