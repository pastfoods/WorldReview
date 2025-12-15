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

    // UserService 를 스프링이 주입해줌
    private final UserService userService;

    // 로그인 화면
    @GetMapping("/login")
    public String loginForm() {
        return "login";   // templates/login.html
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
            return "login";   // 다시 로그인 화면
        }

        // 로그인 성공 → 세션에 저장
        session.setAttribute("loginUser", user);

        // 로그인 후 이동할 페이지 (메인/home 등으로 수정 가능)
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
            return "EXIST";  // 이미 존재
        } else {
            return "OK";     // 사용 가능
        }
    }
//회원가입 저장
    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("nickname") String nickname,
                         Model model) {

        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);

        boolean ok = userService.signup(user);

        if (!ok) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            return "signup";
        }

        return "redirect:/login";
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // 세션 전체 삭제
        return "redirect:/";    // 메인 페이지로 이동
    }


}
