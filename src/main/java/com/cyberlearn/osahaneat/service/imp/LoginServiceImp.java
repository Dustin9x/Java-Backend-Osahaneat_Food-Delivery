package com.cyberlearn.osahaneat.service.imp;

import com.cyberlearn.osahaneat.dto.UserDTO;
import com.cyberlearn.osahaneat.payload.request.SignUpRequest;

import java.util.List;

public interface LoginServiceImp {

    List<UserDTO> getAllUser();
    Boolean checkLogin(String username, String password);
    Boolean addUser(SignUpRequest signUpRequest);
}
