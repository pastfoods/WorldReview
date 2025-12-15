package com.example.WorldReview.controller;

import com.example.WorldReview.dto.ReviewDTO;
import com.example.WorldReview.service.ReviewService;
import com.example.WorldReview.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;   // 이미 만들어 둔 서비스 사용

    /** 나라별 리뷰 목록 페이지 */
    @GetMapping("/world/{code}")
    public String countryReview(@PathVariable("code") String countryCode,
                                Model model,
                                HttpSession session) {

        // 1) 해당 국가 리뷰 목록
        List<ReviewDTO> reviews = reviewService.getReviews(countryCode);

        // 2) 평균 별점
        double avgRating = reviewService.getAverageRating(countryCode);

        // 3) 로그인 사용자 (있으면)
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        model.addAttribute("countryCode", countryCode);
        model.addAttribute("reviews", reviews);
        model.addAttribute("avgRating", avgRating);
        model.addAttribute("loginUser", loginUser);

        return "country-review";   // templates/country-review.html
    }
}
