package com.cyberlearn.osahaneat.service;

import com.cyberlearn.osahaneat.dto.UserDTO;
import com.cyberlearn.osahaneat.entity.Users;
import com.cyberlearn.osahaneat.repository.UserRepository;
import com.cyberlearn.osahaneat.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
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
}
