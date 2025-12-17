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

    // 아이디 중복 확인
    public boolean checkByUsername(String username) {
        return userRepository.checkByUsername(username);
    }
    // 닉네임 중복 확인 (추가)
    public boolean checkByNickname(String nickname) {
        return userRepository.checkByNickname(nickname);
    }

    // 회원가입
    public void signup(UserDTO userDTO) {
        userRepository.save(userDTO);
    }



    // 로그인
    public UserDTO login(String username, String password) {
        UserDTO user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }
}

