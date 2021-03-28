package woo.woot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import woo.woot.domain.Item;
import woo.woot.domain.Review;
import woo.woot.service.ItemService;
import woo.woot.service.MemberService;
import woo.woot.service.OrderService;
import woo.woot.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final ReviewService reviewService;
    private final MemberService memberService;

    @GetMapping(value = "/items/{itemId}/order")
    public String orderForm(@PathVariable("itemId") Long itemId, Model model, HttpServletRequest req) {

        HttpSession session = req.getSession();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("memberForm") != null) {
            //모델에 멤버폼 넣기
            log.info("member name : " + ((MemberForm) session.getAttribute("memberForm")).getName());
            MemberForm memberForm = (MemberForm) session.getAttribute("memberForm");
            model.addAttribute("memberForm", memberForm);
        }
        //모델에 해당 아이템 도메인 넣기
        log.info("order's itemId : " + itemId);
        Item item = itemService.findOne(itemId);

        model.addAttribute("item", item);

        //모델에 해당 아이템 리뷰 도메인 넣기
        List<Review> reviews = reviewService.findEachReview(itemId);
        model.addAttribute("reviews", reviews);

        //모델에 리뷰 작성용 리뷰폼 넣기
        model.addAttribute("reviewForm", new ReviewForm());

        return "items/orderForm";
    }

    @PostMapping(value = "/items/{itemId}/review")
    public String registerReview(@PathVariable("itemId") Long itemId, ReviewForm reviewForm, Model model, HttpServletRequest req) {
        log.info("review content : "+reviewForm.getContent());

        HttpSession session = req.getSession();
        MemberForm memberForm = new MemberForm();
        //세션에 로그인 정보가 있으면
        if (session.getAttribute("memberForm") != null) {
            //모델에 멤버폼 넣기
            log.info("registerReview // member name : " + ((MemberForm) session.getAttribute("memberForm")).getName());
            memberForm = (MemberForm) session.getAttribute("memberForm");
            model.addAttribute("memberForm", memberForm);
        }
        //모델에 해당 아이템 도메인 넣기
        log.info("registerReview // order's itemId : " + itemId);
        Item item = itemService.findOne(itemId);

        model.addAttribute("item", item);

        //리뷰 등록
        Review review = new Review();
        review.setItem(item);
        review.setMember(memberService.findByUsername(memberForm.getUsername()));
        review.setContent(reviewForm.getContent());
        review.setReviewDate(LocalDateTime.now());

        reviewService.registerReview(review);

        //모델에 해당 아이템 리뷰 도메인 넣기
        List<Review> reviews = reviewService.findEachReview(itemId);
        model.addAttribute("reviews", reviews);

        //모델에 리뷰 작성용 리뷰폼 넣기
        model.addAttribute("reviewForm", new ReviewForm());

        return "redirect:/items/{itemId}/order";
    }


}
