package com.example.WorldReview.service;

import org.springframework.stereotype.Service;
import com.example.WorldReview.dto.UserDTO;
import com.example.WorldReview.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //회원가입시 중복 확인
    public boolean signup(UserDTO userDTO) {

        if (userRepository.checkByUsername(userDTO.getUsername())) {
            return false;  // 중복 아이디
        }
        userRepository.save(userDTO);
        return true;
    }
    //로그인
    public UserDTO login(String username, String password) {

        UserDTO user = userRepository.findByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
