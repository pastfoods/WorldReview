package com.example.WorldReview.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.WorldReview.dto.ReviewDTO;
import com.example.WorldReview.dto.UserDTO;
import com.example.WorldReview.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WorldController {

    private final ReviewService reviewService;

    public WorldController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 1. 세계지도 페이지
    @GetMapping("/world")
    public String worldPage() {
        return "world-map";  // 위 파일 이름이 world-map.html 이라고 가정
    }


 // 2. 국가별 리뷰 페이지
    @GetMapping("/world/{countryCode}")
    public String countryReviews(@PathVariable("countryCode") String countryCode,
                                 Model model,
                                 HttpSession session) {

        List<ReviewDTO> reviews = reviewService.getReviews(countryCode);
        double avgRating = reviewService.getAverageRating(countryCode);

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        model.addAttribute("countryCode", countryCode);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("loginUser", loginUser); // HTML에서 loginUser 쓰려면 필요

        return "country-reviews";
    }


    // 3. 리뷰 작성 (로그인 필요)
    @PostMapping("/world/{countryCode}/review")
    public String createReview(@PathVariable("countryCode") String countryCode,
                               @RequestParam("rating") double rating,
                               @RequestParam("content") String content,
                               HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        ReviewDTO review = new ReviewDTO();
        review.setCountry_code(countryCode);
        review.setUser_id(loginUser.getId());
        review.setRating(rating);
        review.setContent(content);

        reviewService.createReview(review);

        return "redirect:/world/" + countryCode;
    }

    // 4. 리뷰 수정 (작성자만)
    @PostMapping("/world/review/{id}/update")
    public String updateReview(@PathVariable("id") int id,
                               @RequestParam("rating") double rating,
                               @RequestParam("content") String content,
                               HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        ReviewDTO original = reviewService.getReview(id);
        if (original == null || original.getUser_id() != loginUser.getId()) {
            return "redirect:/world/" + (original != null ? original.getCountry_code() : "");
        }

        original.setRating(rating);
        original.setContent(content);
        reviewService.updateReview(original);

        return "redirect:/world/" + original.getCountry_code();
    }

    // 5. 리뷰 삭제 (작성자만)
    @PostMapping("/world/review/{id}/delete")
    public String deleteReview(@PathVariable("id") int id,
                               HttpSession session) {

        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        ReviewDTO original = reviewService.getReview(id);
        if (original != null && original.getUser_id() == loginUser.getId()) {
            String countryCode = original.getCountry_code();
            reviewService.deleteReview(id);
            return "redirect:/world/" + countryCode;
        }

        return "redirect:/world";
    }

}
