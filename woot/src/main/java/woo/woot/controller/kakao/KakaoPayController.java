package woo.woot.controller.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import woo.woot.controller.MemberForm;
import woo.woot.domain.Item;
import woo.woot.domain.Member;
import woo.woot.domain.Order;
import woo.woot.service.ItemService;
import woo.woot.service.MemberService;
import woo.woot.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

    private KakaoPay kakaoPay;
    private final ItemService itemService;
    private final OrderService orderService;
    private final MemberService memberService;

    @PostMapping(value = "/items/{itemId}/order/kakaopay")
    public String kakaoPay(@PathVariable("itemId") Long itemId, @RequestParam("count") int count, HttpServletRequest req) {

        HttpSession session = req.getSession();
        MemberForm memberForm = new MemberForm();
        if (session.getAttribute("memberForm") != null) {
            memberForm = (MemberForm) session.getAttribute("memberForm");
        }

        log.info("kakaoPay // item id : " + itemId);
        log.info("kakaoPay // member username : " + memberForm.getUsername());

        Item item = itemService.findOne(itemId);
        Member member = memberService.findByUsername(memberForm.getUsername());

        //주문
        Long orderId = orderService.order(member.getMember_id(),itemId, count);
        log.info("kakaoPay // order_id : " + orderId);
        //카카오페이 세팅
        kakaoPay = new KakaoPay();
        kakaoPay.setOrder_id(orderId);
        kakaoPay.setUsername(member.getUsername());
        kakaoPay.setItem_name(item.getItem_name());
        kakaoPay.setPrice(item.getPrice()*count);
        kakaoPay.setCount(count);
        kakaoPay.setItem_id(itemId);

        String kakaoPayUrl = kakaoPay.kakaoPayReady();

        return "redirect:"+kakaoPayUrl;
    }

    @GetMapping(value = "/items/order/kakaopay/success")
    public String success(@RequestParam("pg_token") String pg_token, Model model) {

        KakaoPayApprovalVO kakaoPayApprovalVO = kakaoPay.kakaoPayInfo(pg_token);

        model.addAttribute("info", kakaoPayApprovalVO);
        model.addAttribute("itemId", kakaoPay.getItem_id());

        return "items/success";
    }

    @GetMapping(value = "/items/order/kakaopay/fail")
    public String fail() {

        //주문 취소
        orderService.cancelOrder(kakaoPay.getOrder_id());

        return "redirect:/";
    }

}
