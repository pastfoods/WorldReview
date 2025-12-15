package com.example.WorldReview.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.WorldReview.dto.MemoDTO;
import com.example.WorldReview.dto.UserDTO;
import com.example.WorldReview.service.MemoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    // 메모 목록 (로그인 필요)
    @GetMapping("/list")
    public String list(HttpSession session, Model model) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<MemoDTO> memos = memoService.getUserMemos(loginUser.getId());
        model.addAttribute("memos", memos);

        return "memo-list";   // templates/memo-list.html
    }

    // 메모 작성 폼
    @GetMapping("/new")
    public String newForm(HttpSession session, Model model) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("memo", new MemoDTO());
        return "memo-form";   // templates/memo-form.html
    }

    // 메모 저장
    @PostMapping("/new")
    public String create(@ModelAttribute MemoDTO memo,
                         HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        memo.setUser_id(loginUser.getId());
        memoService.createMemo(memo);

        return "redirect:/memo/list";
    }

    // 메모 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") int id,
                         HttpSession session,
                         Model model) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        MemoDTO memo = memoService.getMemo(id);
        if (memo == null || memo.getUser_id() != loginUser.getId()) {
            // 다른 사람 메모 접근 또는 없음 → 목록으로
            return "redirect:/memo/list";
        }

        model.addAttribute("memo", memo);
        return "memo-detail";   // templates/memo-detail.html
    }

    // 메모 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id,
                         HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        MemoDTO memo = memoService.getMemo(id);
        if (memo != null && memo.getUser_id() == loginUser.getId()) {
            memoService.deleteMemo(id);
        }

        return "redirect:/memo/list";
    }
    //메모 수정
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") int id,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // 기존 메모 조회 (소유자 확인)
        MemoDTO memo = memoService.getMemo(id);
        if (memo == null || memo.getUser_id() != loginUser.getId()) {
            // 남의 메모거나 없는 메모면 목록으로
            return "redirect:/memo/list";
        }

        // 값 수정
        memo.setTitle(title);
        memo.setContent(content);

        // DB 업데이트
        memoService.updateMemo(memo);

        // 수정 후 다시 상세 페이지로 이동 (혹은 목록으로 보내도 상관 없음)
        return "redirect:/memo/" + id;
    }

}
