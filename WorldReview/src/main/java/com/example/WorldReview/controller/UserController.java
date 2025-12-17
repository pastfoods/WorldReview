package com.example.WorldReview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.WorldReview.dto.UserDTO;
import com.example.WorldReview.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";  
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // 아이디/비밀번호 검사
        UserDTO user = userService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";   
        }

        session.setAttribute("loginUser", user);

        return "redirect:/";
    }
    //회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "signup";  
    }
    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(@RequestParam("username") String username) {

        boolean exists = userService.checkByUsername(username);

        if (exists) {
            return "EXIST";  
        } else {
            return "OK";   
        }
    }
    @GetMapping("/check-nickname")
    @ResponseBody
    public String checkNickname(@RequestParam("nickname") String nickname) {
        boolean exists = userService.checkByNickname(nickname);
        return exists ? "EXIST" : "OK";
    }

//회원가입 저장
    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("nickname") String nickname,
                         Model model) {

        //아이디 중복
        if (userService.checkByUsername(username)) {
            model.addAttribute("usernameError", "이미 존재하는 아이디입니다.");
            model.addAttribute("prevUsername", username);
            model.addAttribute("prevNickname", nickname);
            return "signup";
        }

        //닉네임 중복
        if (userService.checkByNickname(nickname)) {
            model.addAttribute("nicknameError", "이미 존재하는 닉네임입니다.");
            model.addAttribute("prevUsername", username);
            model.addAttribute("prevNickname", nickname);
            return "signup";
        }
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        
        userService.signup(user);
        
        return "redirect:/login";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  
        return "redirect:/";    
    }


}
